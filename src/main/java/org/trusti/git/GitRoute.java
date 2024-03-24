package org.trusti.git;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.trusti.SendFilesRoute;

import java.io.File;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class GitRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:import-git")
                .process(exchange -> {
                    String workspace = exchange.getIn().getHeader("workspace", String.class);
                    Path workspacePath = Paths.get(workspace);
                    if (workspacePath.toFile().exists()) {
                        try (var stream = Files.walk(workspacePath)) {
                            stream
                                    .sorted(Comparator.reverseOrder())
                                    .map(Path::toFile)
                                    .forEach(File::delete);
                        }
                    }
                })
                .toD("git:${header.workspace}?operation=clone&remotePath=${header.repository}&branchName=${header.ref}");

        from("direct:import-git2")
                .process(exchange -> {
                    String rootDirectory = exchange.getIn().getBody(String.class);
                    String workingDirectory = exchange.getIn().getHeader("workingDirectory", String.class);

                    Path path = Paths.get(rootDirectory, workingDirectory);
                    try (var stream = Files.walk(path)) {
                        List<File> filesList = stream
                                .filter(Files::isRegularFile)
                                .filter(e -> e.getFileName().toString().endsWith(".json"))
                                .map(e -> e.toFile())
                                .toList();

                        exchange.getIn().setBody(filesList);
                    }
                })
                .split(body()).parallelProcessing()
                    .setHeader(SendFilesRoute.TARGET_URL_HEADER_NAME, header("output"))
                    .to("direct:send-file")
                .end();
    }

}

package org.trusti.git;

import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.HashMap;
import java.util.Map;

@Command(name = "git", mixinStandardHelpOptions = true, description = "Import data from a git repository")
public class GitCommand implements Runnable {

    @Inject
    ProducerTemplate producerTemplate;

    @Parameters(paramLabel = "repository", description = "The git repository.")
    String repository;

    @CommandLine.Option(names = {"--output", "-o"}, required = true, description = "The URL where the files are going to be sent to.")
    String output;

    @CommandLine.Option(names = {"--ref", "-r"}, description = "The branch, tag or SHA to checkout")
    String ref;

    @CommandLine.Option(names = {"--workspace", "-ws"}, description = "Where the repository will be clone", defaultValue = "target/repository")
    String workspace;

    @CommandLine.Option(names = {"--working-directory", "-wd"}, description = "Directory within the repository.")
    String workingDirectory;

    @Override
    public void run() {
        System.out.println("Started ingestion from " + repository);

        Map<String, Object> headers = new HashMap<>();
        headers.put("workspace", workspace);
        headers.put("repository", repository);
        headers.put("ref", ref);
        headers.put("workingDirectory", workingDirectory);
        headers.put("output", output);

        producerTemplate.requestBodyAndHeaders("direct:import-git", repository, headers);
        producerTemplate.requestBodyAndHeaders("direct:import-git2", workspace, headers);

        System.out.println("Finished successfully");
    }

}

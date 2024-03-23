package org.trusti;

import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.Map;

@Command(name = "importer", mixinStandardHelpOptions = true)
public class ImporterCommand implements Runnable {

    @Inject
    ProducerTemplate producerTemplate;

    @Parameters(paramLabel = "source", description = "The HTTP server where the CSAF files are stored.")
    String source;

    @CommandLine.Option(names = {"--output", "-o"}, required = true, description = "The URL where the files are going to be sent to.")
    String output;

    @Override
    public void run() {
        System.out.printf("Started ingestion from %s", source);

        producerTemplate.requestBodyAndHeaders("direct:discover-url-directory", source, Map.of(FileRoute.OUTPUT_HEADER, output));

        System.out.println("Finished successfully");
    }

}

package org.acme;

import jakarta.inject.Inject;
import org.apache.camel.ProducerTemplate;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "greeting", mixinStandardHelpOptions = true)
public class MainCommand implements Runnable {

    @Inject
    ProducerTemplate producerTemplate;

    @Parameters(paramLabel = "source", defaultValue = "https://access.redhat.com/security/data/csaf/v2/advisories/", description = "The HTTP server where the CSAF files are stored.")
    String source;

    @Override
    public void run() {
        System.out.println("Started ingestion");

        producerTemplate.requestBody("direct:discover-url-directory", source);

        System.out.println("Finished successfully");
    }

}

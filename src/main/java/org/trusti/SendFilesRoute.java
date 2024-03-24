package org.trusti;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.hc.client5.http.HttpHostConnectException;

@ApplicationScoped
public class SendFilesRoute extends RouteBuilder {

    public static final String TARGET_URL_HEADER_NAME = "TARGET_URL";

    @Override
    public void configure() throws Exception {
        from("direct:send-file")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .toD("${header." + TARGET_URL_HEADER_NAME + "}")
                .setBody(header(Exchange.HTTP_RESPONSE_CODE));
    }

}

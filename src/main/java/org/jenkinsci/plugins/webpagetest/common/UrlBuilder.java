package org.jenkinsci.plugins.webpagetest.common;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

public class UrlBuilder {
    private String serverUrl;
    private String apiKey;

    public UrlBuilder(String serverUrl, String apiKey) {
        this.serverUrl = serverUrl;
        this.apiKey = apiKey;
    }

    public String invoke(String targetUrl) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(serverUrl);
        builder.setPath("/" + WptConst.RunTest);
        builder.addParameter("url", targetUrl);
        builder.addParameter("runs","1");
        builder.addParameter("f","json");
        builder.addParameter("k",apiKey);
        return builder.build().toString();
    }
}

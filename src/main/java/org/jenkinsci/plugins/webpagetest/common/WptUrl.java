package org.jenkinsci.plugins.webpagetest.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class WptUrl {

    public static Builder builder(String wptServerUrl, String url) {
        return new WptUrl.Builder(wptServerUrl, url);
    }

    public static class Builder {
        private String wptServerUrl;
        private String url;
        private List<NameValuePair> instance= new ArrayList<NameValuePair>();


        public Builder(String wptServerUrl, String url) {
            this.wptServerUrl = wptServerUrl;
            this.url = url;
            instance.add(new BasicNameValuePair("f","json"));
        }

        public Builder WithWptServerUrl(String wptServerUrl) {
            this.wptServerUrl = wptServerUrl;
            return this;
        }

        public Builder WithApiKey(String apiKey) {
            instance.add(new BasicNameValuePair("k",apiKey));
            return this;
        }

        public Builder WithTargetUrl(String targetUrl) {
            instance.add(new BasicNameValuePair("url", targetUrl));
            return this;
        }

        public Builder WithNumberOfRuns(int numberOfRuns) {
            instance.add(new BasicNameValuePair("runs",String.valueOf(numberOfRuns)));
            return this;
        }

        public Builder WithLocation(String location) {
            instance.add(new BasicNameValuePair("location",location));
            return this;
        }

        public Builder other(List<NameValuePair> other) {
            instance.addAll(other);
            return this;
        }

        public String buildUrl() throws URISyntaxException {
            URIBuilder builder = new URIBuilder(this.wptServerUrl);
            builder.setPath("/" + this.url);
            builder.addParameters(this.instance);
            return builder.build().toString();
        }
    }
}

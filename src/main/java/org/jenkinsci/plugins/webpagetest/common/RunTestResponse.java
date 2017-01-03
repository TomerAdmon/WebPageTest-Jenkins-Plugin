package org.jenkinsci.plugins.webpagetest.common;


public class RunTestResponse {
    public int statusCode;
    public String statusText;
    public data data;

    public class data {
        public String testId;
        public String xmlUrl;
        public String jsonUrl;
        public String summaryCSV;
        public String ownerKey;
        public String detailCSV;
        public String userUrl;
    }
}




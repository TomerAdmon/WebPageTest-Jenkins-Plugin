package org.jenkinsci.plugins.webpagetest.common;

public class TestStatusResponse {
    public int statusCode;
    public String statusText;
    public data data;

    public class data {
        public int statusCode;
        public String statusText;
        public String testId;
        public int runs;
        public int fvonly;
        public String location;
        public String startTime;
        public int fvRunsCompleted;
        public int rvRunsCompleted;
    }
}

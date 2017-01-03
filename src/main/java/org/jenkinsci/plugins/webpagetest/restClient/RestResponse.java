package org.jenkinsci.plugins.webpagetest.restClient;

import org.json.JSONObject;

public class RestResponse {
    private final int statusCode;
    private final JSONObject data;

    public RestResponse(int statusCode, JSONObject data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public JSONObject getData() {
        return data;
    }
}

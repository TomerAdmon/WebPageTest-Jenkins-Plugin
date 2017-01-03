package org.jenkinsci.plugins.webpagetest;

import com.google.gson.GsonBuilder;
import hudson.model.BuildListener;
import org.jenkinsci.plugins.webpagetest.common.WptConst;
import org.jenkinsci.plugins.webpagetest.restClient.RestClient;
import org.jenkinsci.plugins.webpagetest.restClient.RestResponse;
import org.jenkinsci.plugins.webpagetest.common.RunTestResponse;
import org.jenkinsci.plugins.webpagetest.common.WptUrl;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import static org.jenkinsci.plugins.webpagetest.common.WptConst.TestCompleted;

public class WebPageTestManager {
    private final BuildListener listener;
    private final String serverUrl;
    private final String apiKey;

    public WebPageTestManager(BuildListener listener, String serverUrl, String apiKey) {
        this.listener = listener;
        this.serverUrl = serverUrl;
        this.apiKey = apiKey;
    }

    public boolean RunTest(String targetUrl,
                                  int retryCount,
                                  String location) throws URISyntaxException, InterruptedException {
        String testId = Run(targetUrl, retryCount, location);
        WaitForTestToComplete(testId);
        return true;
    }

    public void WaitForTestToComplete(String testId) throws InterruptedException {
        String url = serverUrl + "/" + WptConst.TestStatus +"?f=json&test=" + testId;

        if(apiKey != null && !apiKey.isEmpty()){
            url += "&k=" + apiKey;
        }

        String status = new String();
        listener.getLogger().println("Test Started, Waiting for test to complete");
        while (!status.equals(TestCompleted)) {
            RestResponse restResponse = ExecuteGet(url);
            status = restResponse.getData().get("statusText").toString();
            TimeUnit.SECONDS.sleep(WptConst.TestStatusCheckIntervalSeconds);
        }
        listener.getLogger().println("Test Complete");
    }

    private String Run(String targetUrl,
                              int retryCount,
                              String location) throws URISyntaxException {
        WptUrl.Builder wptBuilder = WptUrl.builder(serverUrl, WptConst.RunTest)
                .WithNumberOfRuns(retryCount)
                .WithTargetUrl(targetUrl);

        if(apiKey != null && !apiKey.isEmpty()){
            wptBuilder.WithApiKey(apiKey);
        }

        if(location!= null && !location.isEmpty()){
            wptBuilder.WithLocation(location);
        }

        String url = wptBuilder.buildUrl();

        listener.getLogger().println("URL: "+ url);

        RestResponse restResponse = ExecuteGet(url);

        JSONObject responseData = restResponse.getData();
        RunTestResponse runTestResponse = new GsonBuilder()
                .create()
                .fromJson(responseData.toString(), RunTestResponse.class);

        listener.getLogger().println("Test Id: " + runTestResponse.data.testId);
        return runTestResponse.data.testId;
    }

    private RestResponse ExecuteGet(String url) {
        RestResponse restResponse = RestClient.Get(url,
                MediaType.APPLICATION_JSON_TYPE);

        if (restResponse.getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + restResponse.getStatusCode()
                    + restResponse.getData().toString());
        }

        Response response = new GsonBuilder().create()
                .fromJson(restResponse.getData().toString(), Response.class);

        if (response.statusCode != 200 &&
                response.statusCode != 101 &&
                response.statusCode != 100) {
            throw new RuntimeException("Failed : WPT returned an error : "
                    + response.statusCode + ", "
                    + response.statusText);
        }
        return restResponse;
    }

    private class Response {
        private int statusCode;
        private String statusText;
    }
}
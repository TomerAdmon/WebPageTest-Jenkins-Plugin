package org.jenkinsci.plugins.webpagetest.common;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jenkinsci.plugins.webpagetest.common.WptConst;
import org.jenkinsci.plugins.webpagetest.common.WptUrl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WptUrlTest {
    @Test
    public void Test_Url_Builder() throws Exception {
        String s = WptUrl.builder("https://webpagetest.org", WptConst.RunTest)
                .WithApiKey("A.3294802984")
                .WithNumberOfRuns(1)
                .buildUrl();

        Assert.assertEquals(s, "https://webpagetest.org/runtest.php?f=json&k=A.3294802984&runs=1");
    }

    @Test
    public void Test_Url_Builder_With_Others() throws Exception {
        List<NameValuePair> others = new ArrayList<NameValuePair>();
        others.add(new BasicNameValuePair("fvonly", "1"));
        others.add(new BasicNameValuePair("r", "12345"));
        String s = WptUrl.builder("https://webpagetest.org", WptConst.RunTest)
                .WithApiKey("A.3294802984")
                .WithNumberOfRuns(1)
                .other(others)
                .buildUrl();

        Assert.assertEquals(s, "https://webpagetest.org/runtest.php?f=json&k=A.3294802984&runs=1&fvonly=1&r=12345");
    }
}
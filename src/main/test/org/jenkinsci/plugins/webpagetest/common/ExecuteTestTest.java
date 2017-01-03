package org.jenkinsci.plugins.webpagetest.common;

import hudson.model.BuildListener;
import junit.framework.TestCase;
import org.jenkinsci.plugins.webpagetest.WebPageTestManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecuteTestTest extends TestCase {
    public static final String HTTPS_WEBPAGETEST_ORG = "https://webpagetest.org";
    public static final String TARGET_URL = "google.com";
    public static final String API_KEY = "Key";

    @Test
    public void testExecute_WithValidLocation_TestCompletes() throws Exception {
        BuildListener buildListener = mock(BuildListener.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(buildListener.getLogger()).thenReturn(new PrintStream(outputStream));
        WebPageTestManager webPageTestManager = new WebPageTestManager(buildListener, HTTPS_WEBPAGETEST_ORG, API_KEY);

        webPageTestManager.RunTest(TARGET_URL,1, "Dulles");

        Assert.assertTrue(outputStream.toString().contains("Test Complete"));
    }

    @Test
    public void testExecute_WithNoLocation_TestCompletes() throws Exception {
        BuildListener buildListener = mock(BuildListener.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(buildListener.getLogger()).thenReturn(new PrintStream(outputStream));
        WebPageTestManager webPageTestManager = new WebPageTestManager(buildListener, HTTPS_WEBPAGETEST_ORG, API_KEY);

        webPageTestManager.RunTest(TARGET_URL,1, null);

        Assert.assertTrue(outputStream.toString().contains("Test Complete"));
    }


    @Test
    public void testExecute_WithInvalidLocation_TestFailsWithError() throws Exception {
        BuildListener buildListener = mock(BuildListener.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(buildListener.getLogger()).thenReturn(new PrintStream(outputStream));
        WebPageTestManager webPageTestManager = new WebPageTestManager(buildListener, HTTPS_WEBPAGETEST_ORG, API_KEY);

        try {
            webPageTestManager.RunTest(TARGET_URL, 1, "WEST_US_1");
        }
        catch (RuntimeException e){
            assertThat(e.getMessage(), is("Failed : WPT returned an error : 400, Invalid Location, please try submitting your test request again."));
        }
    }
}
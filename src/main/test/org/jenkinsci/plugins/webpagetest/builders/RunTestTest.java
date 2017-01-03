package org.jenkinsci.plugins.webpagetest.builders;

import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RunTestTest {
    @Ignore
    @Test
    public void perform() throws Exception {
        AbstractBuild<?, ?> build = mock(AbstractBuild.class);
        Launcher launcher = mock(Launcher.class);
        BuildListener buildListener = mock(BuildListener.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(buildListener.getLogger()).thenReturn(new PrintStream(outputStream));

        boolean perform = new RunTest("https://webpagetest.org", "google.com",
                "A.sadsada", "Dulles_IE11", 1)
                .perform(build, launcher, buildListener);

        Assert.assertTrue(perform);
    }

}
/*   Copyright 2013, MANDIANT, Eric Lordahl
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.jenkinsci.plugins.webpagetest.builders;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import org.jenkinsci.plugins.webpagetest.WebPageTestManager;
import org.jenkinsci.plugins.webpagetest.WebPageTestBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;

public class RunTest extends WebPageTestBuildStep {

	private final String serverUrl;
	private final String targetUrl;
	private final String apiKey;
	private final String location;
	private final int retryCount;

	@DataBoundConstructor
	public RunTest(String serverUrl, String targetUrl, String apiKey, String location, int retryCount) {
		this.serverUrl = serverUrl;
		this.targetUrl = targetUrl;
		this.apiKey = apiKey;
		this.location = location;
		this.retryCount = retryCount;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws Exception {
		WebPageTestManager webPageTestManager = new WebPageTestManager(listener, serverUrl, apiKey);
		return webPageTestManager.RunTest(targetUrl, retryCount, location);
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getLocation() {
		return location;
	}

	public int getRetryCount() {
		return retryCount;
	}


	@Extension
	public static final class runTestDescriptor extends WptBuildStepDescriptor {

		public runTestDescriptor() {
			load();
		}

		@Override
		public String getDisplayName() {
			return "Run Test";
		}

	}	
}
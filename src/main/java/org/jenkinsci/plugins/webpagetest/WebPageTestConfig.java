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
package org.jenkinsci.plugins.webpagetest;
import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.Launcher;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Items;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import org.jenkinsci.plugins.webpagetest.WebPageTestBuildStep.WptBuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

public class WebPageTestConfig extends Builder {

	private final WebPageTestBuildStep buildStep;

	@DataBoundConstructor
	public WebPageTestConfig(final WebPageTestBuildStep buildStep)
	{
		this.buildStep = buildStep;
    }

	public WebPageTestBuildStep getBuildStep() {
		return buildStep;
	}

	@Override
	public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)  {
        try
        {
            return buildStep.perform(build, launcher, listener);

        } catch (Exception e) {
            listener.getLogger().println(e);
		}
        return false;
	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl)super.getDescriptor();
	}

	@Extension
	public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public DescriptorImpl() {
            load();
        }

		@Initializer(before=InitMilestone.PLUGINS_STARTED)
        public static void addAliases() {
			Items.XSTREAM2.addCompatibilityAlias(
					"org.jenkinsci.plugins.webpagetest.WebPageTestConfig",
					WebPageTestConfig.class
			);
		}

		@Override
		public String getDisplayName() {
			return "Web Page Test Build Step";
		}

		public DescriptorExtensionList<WebPageTestBuildStep, WptBuildStepDescriptor> getBuildSteps() {
			return WebPageTestBuildStep.all();
		}

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> jobType) {
			return true;
		}

	}
}

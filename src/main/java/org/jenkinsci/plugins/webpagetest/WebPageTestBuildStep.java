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
import hudson.ExtensionPoint;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

public abstract class WebPageTestBuildStep implements Describable<WebPageTestBuildStep>, ExtensionPoint {

	public static DescriptorExtensionList<WebPageTestBuildStep, WptBuildStepDescriptor> all() {
		return Jenkins.getInstance().getDescriptorList(WebPageTestBuildStep.class);
	}

	public abstract boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
			throws Exception;

	public WptBuildStepDescriptor getDescriptor() {
		return (WptBuildStepDescriptor)Jenkins.getInstance().getDescriptor(getClass());
	}

	public static abstract class WptBuildStepDescriptor extends Descriptor<WebPageTestBuildStep> {

		protected WptBuildStepDescriptor() { }

		protected WptBuildStepDescriptor(Class<? extends WebPageTestBuildStep> clazz) {
			super(clazz);
		}
	}
}

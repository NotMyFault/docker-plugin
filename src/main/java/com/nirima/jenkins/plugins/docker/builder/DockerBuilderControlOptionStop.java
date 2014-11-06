package com.nirima.jenkins.plugins.docker.builder;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.DockerException;
import hudson.Extension;
import hudson.model.AbstractBuild;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Created by magnayn on 30/01/2014.
 */
public class DockerBuilderControlOptionStop extends DockerBuilderControlOptionStopStart {

    public final boolean remove;

    @DataBoundConstructor
    public DockerBuilderControlOptionStop(String cloudId, String containerId, boolean remove) {
        super(cloudId, containerId);
        this.remove = remove;
    }

    @Override
    public void execute(AbstractBuild<?, ?> build) throws DockerException {
        LOGGER.info("Stopping container " + containerId);
        DockerClient client = getClient(build);
        client.stopContainerCmd(containerId).exec();
        getLaunchAction(build).stopped(client, containerId);
        if( remove )
            client.removeContainerCmd(containerId);
    }


    @Extension
    public static final class DescriptorImpl extends DockerBuilderControlOptionDescriptor {
        @Override
        public String getDisplayName() {
            return "Stop Container";
        }

    }
}

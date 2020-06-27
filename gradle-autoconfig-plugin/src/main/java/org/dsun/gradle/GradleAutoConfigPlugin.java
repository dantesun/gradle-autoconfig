package org.dsun.gradle;

import org.gradle.StartParameter;
import org.gradle.api.Plugin;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import static org.dsun.gradle.ProjectFacade.AUTO_CONFIG_SWITCH;


public class GradleAutoConfigPlugin implements Plugin<Gradle> {
    private static final Logger logger = Logging.getLogger(GradleAutoConfigPlugin.class);

    @Override
    public void apply(Gradle gradle) {
        StartParameter parameter = gradle.getStartParameter();
        if ("false".equals(parameter.getProjectProperties().get(AUTO_CONFIG_SWITCH))) {
            logger.info("[GradleConfig] Gradle AutoConfiguration is globally disabled.");
            return;
        }
        gradle.beforeProject(new GradleAutoConfiguration());
    }
}

package org.dsun.gradle;

import org.dsun.gradle.action.MavenRepositoryAction;
import org.gradle.api.Plugin;
import org.gradle.api.internal.StartParameterInternal;
import org.gradle.api.internal.properties.GradleProperties;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.initialization.DefaultGradlePropertiesLoader;

import java.util.Optional;

public class GradleAutoConfigInitScriptPlugin implements Plugin<Gradle> {
    private static final Logger logger = Logging.getLogger(GradleAutoConfigPlugin.class);

    @Override
    public void apply(Gradle gradle) {
        gradle.settingsEvaluated(settings -> {
            DefaultGradlePropertiesLoader propertiesLoader = new DefaultGradlePropertiesLoader((StartParameterInternal) gradle.getStartParameter());
            GradleProperties gradleProperties = propertiesLoader.loadGradleProperties(settings.getSettingsDir());
            Optional.ofNullable(gradleProperties.find(ProjectFacade.NEXUS_MIRROR_URL)).ifPresent(url -> {
                MavenRepositoryAction action = new MavenRepositoryAction(url);
                settings.getPluginManagement().getRepositories().maven(action);
                logger.info("Plugin repository {}", action);
            });

        });
        gradle.afterProject(project -> new GradleAutoConfigPlugin().apply(project));
    }
}

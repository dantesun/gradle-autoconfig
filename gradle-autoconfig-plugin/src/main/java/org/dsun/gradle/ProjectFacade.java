package org.dsun.gradle;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.initialization.dsl.ScriptHandler;
import org.gradle.api.logging.Logger;
import org.slf4j.helpers.MessageFormatter;

import java.util.Optional;

public class ProjectFacade {
    public static final String NEXUS_MIRROR_URL = "nexus.mirror.url";
    public static final String AUTO_CONFIG_SWITCH = "org.dsun.gradle.autoconfig";
    public static final String BUILD_SRC = "buildSrc";
    public static final String ROOT_PATH = ":";
    private final Project project;
    private final Logger logger;

    private ProjectFacade(Project project) {
        this.project = project;
        this.logger = project.getLogger();
    }

    public static Optional<ProjectFacade> of(Project project) {
        ProjectFacade facade = new ProjectFacade(project);
        if (facade.isAutoConfigurable()) {
            return Optional.of(facade);
        }
        return Optional.empty();
    }

    public boolean isBuildSrc() {
        if (BUILD_SRC.equals(project.getName()) && ROOT_PATH.equals(project.getPath())) {
            logger.info("[GradleConfig][{}] Gradle BuildSrc Project.", project.getName());
            return true;
        }
        return false;
    }

    public void logging(String format, Object... args) {
        String message = MessageFormatter.arrayFormat(format, args).getMessage();
        logger.info("[GradleConfig][{}] {}", project.getName(), message);
    }

    public boolean isAutoConfigurable() {
        if (isBuildSrc()) {
            return false;
        }
        if (!("true".equals(project.getProperties().get(AUTO_CONFIG_SWITCH)))) {
            logging("{} is off.", AUTO_CONFIG_SWITCH);
            return false;
        }
        return true;
    }


    public Optional<String> getProperty(String key) {
        return Optional.ofNullable((String) project.getProperties().get(key));
    }

    public Optional<String> getNexusMirror() {
        return getProperty(NEXUS_MIRROR_URL);
    }

    public void apply() {

    }

    public boolean isRoot() {
        return project.getRootProject() == project;
    }

    public void repositories(Action<RepositoryHandler> configAction) {
        RepositoryHandler repositories = project.getRepositories();
        configAction.execute(repositories);
    }

    public ScriptHandler getBuildscript() {
        return project.getBuildscript();
    }

    public boolean isLeafProject() {
        return project.getChildProjects().isEmpty();
    }
}

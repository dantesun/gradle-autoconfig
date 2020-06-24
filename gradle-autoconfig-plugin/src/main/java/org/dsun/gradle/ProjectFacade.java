package org.dsun.gradle;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.Optional;

public class ProjectFacade {
    public static final String NEXUS_MIRROR_URL = "nexus.mirror.url";
    private final Project project;
    private final Logger logger = Logging.getLogger(ProjectFacade.class);

    private ProjectFacade(Project project) {
        this.project = project;
    }

    public static Optional<ProjectFacade> of(Project project) {
        if (isConfigurable(project)) {
            return Optional.of(new ProjectFacade(project));
        }
        return Optional.empty();
    }

    private static boolean isConfigurable(Project project) {
        Project root = project.getRootProject();
        int size = root.getAllprojects().size();
        //Single Project
        if (size == 1) {
            return true;
        }
        //Multiple Projects
        if (size > 1) {
            // Only apply to sub-project
            return project.getDepth() > 0;
        }
        return false;
    }

    public Optional<String> getProperty(String key) {
        return Optional.ofNullable((String) project.getProperties().get(key));
    }

    public Optional<String> getNexusMirror() {
        return getProperty(NEXUS_MIRROR_URL);
    }

    public void config() {

    }
}

package org.dsun.gradle.action;

import org.dsun.gradle.ProjectFacade;
import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.Optional;

public class SettingUpNexusMirrorAction implements Action<Project> {
    private static final Logger logger = Logging.getLogger(SettingUpNexusMirrorAction.class);

    @Override
    public void execute(Project project) {
        Optional<String> nexusUrl = ProjectFacade.of(project).flatMap(ProjectFacade::getNexusMirror);
        if (!nexusUrl.isPresent()) {
            logger.info("No nexus mirror is configured.");
            return;
        }
        Action<MavenArtifactRepository> nexus_mirror = new MavenRepositoryAction(nexusUrl.get());
        project.getBuildscript().getRepositories().maven(nexus_mirror);
        logger.info("Repository for Build Script: {}", nexus_mirror);
        project.getRepositories().maven(nexus_mirror);
        logger.info("Repository for Dependencies: {}", nexus_mirror);
    }
}

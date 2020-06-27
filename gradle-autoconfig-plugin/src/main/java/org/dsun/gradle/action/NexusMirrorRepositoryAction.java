package org.dsun.gradle.action;

import org.dsun.gradle.ProjectFacade;
import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;

import java.util.Optional;

class NexusMirrorRepositoryAction implements Action<MavenArtifactRepository> {
    private final ProjectFacade project;

    public NexusMirrorRepositoryAction(ProjectFacade project) {
        this.project = project;
    }

    @Override
    public void execute(MavenArtifactRepository repo) {
        Optional<String> repoUrl = project.getNexusMirror();
        project.logging("Nexus Mirror Url {}", repoUrl.orElse("is not set."));
        if (repoUrl.isPresent()) {
            repo.setUrl(repoUrl.get());
            String name = "Nexus Mirror(Auto-Configured)";
            repo.setName(name);
        } else {
            repo.setUrl(RepositoryHandler.DEFAULT_MAVEN_CENTRAL_REPO_NAME);
            repo.setName(RepositoryHandler.MAVEN_CENTRAL_URL);
        }
    }
}

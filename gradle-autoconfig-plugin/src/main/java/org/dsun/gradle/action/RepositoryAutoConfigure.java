package org.dsun.gradle.action;

import org.dsun.gradle.ProjectFacade;
import org.gradle.api.Action;
import org.gradle.api.artifacts.dsl.RepositoryHandler;

public class RepositoryAutoConfigure implements Action<ProjectFacade> {
    private boolean mavenLocal = false;

    public boolean isMavenLocal() {
        return mavenLocal;
    }

    public void setMavenLocal(boolean mavenLocal) {
        this.mavenLocal = mavenLocal;
    }

    @Override
    public void execute(ProjectFacade project) {
        NexusMirrorRepositoryAction repositoryAction = new NexusMirrorRepositoryAction(project);
        Action<RepositoryHandler> repositoriesAction = repositories -> {
            if (mavenLocal) {
                repositories.mavenLocal();
            }
            repositories.maven(repositoryAction);
        };

        if (!project.isRoot()) {
            project.logging("Setting up repositories.");
            project.repositories(repositoriesAction);
            return;
        }

        project.logging("Setting up BuildScript repositories.");
        RepositoryHandler repositories = project.getBuildscript().getRepositories();
        repositories.maven(repositoryAction);
        repositories.maven(repo -> {
            repo.setUrl("https://plugins.gradle.org/m2");
            repo.setName("Gradle Plugin Portal");
        });

        if (project.isLeafProject()) {
            project.logging("Single Project Build");
            project.repositories(handler -> handler.maven(repositoryAction));
        }
    }
}

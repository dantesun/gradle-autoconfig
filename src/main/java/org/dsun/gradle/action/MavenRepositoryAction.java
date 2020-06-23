package org.dsun.gradle.action;

import org.gradle.api.Action;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;

public class MavenRepositoryAction implements Action<MavenArtifactRepository> {
    private final String url;
    private final String name;

    public MavenRepositoryAction(String url) {
        this.url = url;
        name = "GradleAutoConfig Nexus Mirror";
    }

    @Override
    public String toString() {
        return "Repository {" +
                "url=" + url +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void execute(MavenArtifactRepository mavenArtifactRepository) {
        mavenArtifactRepository.setUrl(url);
        mavenArtifactRepository.setName(name);
    }
}

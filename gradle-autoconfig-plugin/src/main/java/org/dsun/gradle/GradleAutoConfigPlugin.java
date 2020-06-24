package org.dsun.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GradleAutoConfigPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        ProjectFacade.of(project).ifPresent(ProjectFacade::config);
    }
}

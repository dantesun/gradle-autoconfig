package org.dsun.gradle;

import org.gradle.api.Action;
import org.gradle.api.Project;

public class GradleAutoConfiguration implements Action<Project> {

    @Override
    public void execute(Project project) {
        ProjectFacade.of(project).ifPresent(ProjectFacade::apply);
    }
}

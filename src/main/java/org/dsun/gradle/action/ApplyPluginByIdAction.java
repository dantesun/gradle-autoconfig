package org.dsun.gradle.action;

import org.gradle.api.Action;
import org.gradle.api.Project;

import java.util.HashMap;
import java.util.Map;

public class ApplyPluginByIdAction implements Action<Project> {
    private final String pluginId;

    private ApplyPluginByIdAction(String pluginId) {
        this.pluginId = pluginId;
    }

    public static ApplyPluginByIdAction of(String pluginId) {
        return new ApplyPluginByIdAction(pluginId);
    }

    @Override
    public final void execute(Project project) {
        if (project.getPluginManager().hasPlugin(pluginId)) {
            project.getLogger().lifecycle(pluginId + " is already applied.");
            return;
        }
        Map<String, String> args = new HashMap<>();
        args.put("plugin", pluginId);
        project.apply(args);
    }
}

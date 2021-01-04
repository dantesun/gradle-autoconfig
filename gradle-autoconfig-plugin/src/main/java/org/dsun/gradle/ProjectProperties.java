package org.dsun.gradle;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

/**
 * @author liwei.sun
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:~/.gradle/autoconfig.yml",
    "system:properties",
    "system:env"})
public interface ProjectProperties extends Config {
    class INSTANCE {
        public static ProjectProperties get() {
            return ConfigFactory.create(ProjectProperties.class);
        }
    }
}

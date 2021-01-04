package org.dsun.gradle;

import org.aeonbits.owner.loaders.Loader;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * @author liwei.sun
 */
public class YamlLoader implements Loader {
    @Override
    public boolean accept(URI uri) {
        return uri.getPath().endsWith(".yml");
    }

    @Override
    public void load(Properties result, URI uri) throws IOException {

    }

    @Override
    public String defaultSpecFor(String uriPrefix) {
        return null;
    }
}

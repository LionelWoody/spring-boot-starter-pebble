package org.woodylab.boot.pebble;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import static org.springframework.util.ClassUtils.isPresent;
import static org.woodylab.boot.pebble.autoconfigure.PebbleProperties.DEFAULT_PREFIX;
import static org.woodylab.boot.pebble.autoconfigure.PebbleProperties.DEFAULT_SUFFIX;
/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-18 上午12:24.
 */
public class PebbleTemplateAvailabilityProvider implements TemplateAvailabilityProvider {
    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        if (!isPresent("com.mitchellbosecke.pebble.PebbleEngine", classLoader)) {
            return false;
        }
        String prefix = environment.getProperty("spring.pebble.prefix", DEFAULT_PREFIX);
        String suffix = environment.getProperty("spring.pebble.suffix", DEFAULT_SUFFIX);

        return resourceLoader.getResource(prefix + view + suffix).exists();
    }
}

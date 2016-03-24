package org.woodylab.boot.pebble.autoconfigure;

import org.springframework.boot.autoconfigure.template.AbstractViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} for Pebble.
 *
 * @author <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午10:31.
 */
@ConfigurationProperties(prefix = "spring.pebble")
public class PebbleProperties extends AbstractViewResolverProperties {

    public static final String DEFAULT_PREFIX = "classpath:/templates/";

    public static final String DEFAULT_SUFFIX = ".html";

    private String prefix = DEFAULT_PREFIX;

    private String suffix = DEFAULT_SUFFIX;

    private int cacheSize = 200;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(final int cacheSize) {
        this.cacheSize = cacheSize;
    }
}

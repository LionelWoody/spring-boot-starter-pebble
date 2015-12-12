package org.woodylab.boot.pebble.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} for Pebble.
 *
 * @author <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午10:31.
 */
@ConfigurationProperties(prefix = "spring.pebble")
public class PebbleProperties {

    public static final String DEFAULT_PREFIX = "classpath:/templates/";

    public static final String DEFAULT_SUFFIX = ".html";

    private String prefix = DEFAULT_PREFIX;

    private String suffix = DEFAULT_SUFFIX;

    private String contentType = "text/html";

    private String charSet = "utf-8";

    private String[] viewNames;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public String[] getViewNames() {
        return viewNames;
    }

    public void setViewNames(String[] viewNames) {
        this.viewNames = viewNames;
    }
}

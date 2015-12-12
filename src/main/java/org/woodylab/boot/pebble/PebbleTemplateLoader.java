package org.woodylab.boot.pebble;

import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.Loader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 本想让它 Deprecated ，可是其他 Loader 都不好使，只好启用
 *
 * @author <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午5:26.
 */
public class PebbleTemplateLoader implements Loader, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private String charset = "UTF-8";

    private String prefix;

    private String suffix;

    @Override
    public Reader getReader(String resourceName) throws LoaderException {
        resourceName = getFullyQualifiedResourceName(resourceName);
        Resource resource = resourceLoader.getResource(resourceName);
        if (resource.exists()) {
            try {
                return new InputStreamReader(resource.getInputStream(), charset);
            } catch (IOException e) {
                throw new LoaderException(e, "Failed to load template: " + resourceName);
            }
        }
        throw new LoaderException(null, "No template exists named: " + resourceName);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private String getFullyQualifiedResourceName(String resourceName) {
        StringBuilder result = new StringBuilder();
        if (prefix != null) {
            if (resourceName.startsWith(prefix)) {
                result.append(resourceName);
            } else {
                result.append(prefix).append(resourceName);
            }
        }
        if (suffix != null) {
            result.append(suffix);
        }
        return result.toString();
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


}

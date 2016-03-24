package org.woodylab.boot.pebble;

import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.utils.PathUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import static java.util.Objects.nonNull;

/**
 * 本想让它 Deprecated ，可是其他 Loader 都不好使，只好启用
 *
 * @author <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午5:26.
 */
public class PebbleTemplateLoader implements Loader<String>, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private String charset = Charset.forName("UTF-8").displayName();

    private String prefix;

    private String suffix;

    @Override
    public Reader getReader(final String resourceName) throws LoaderException {
        final String fullyQualifiedResourceName = getFullyQualifiedResourceName(resourceName);
        final Resource resource = resourceLoader.getResource(fullyQualifiedResourceName);
        if (resource.exists()) {
            try {
                return new InputStreamReader(resource.getInputStream(), charset);
            } catch (IOException e) {
                throw new LoaderException(e, "Failed to load template: " + fullyQualifiedResourceName);
            }
        }
        throw new LoaderException(null, "No template exists named: " + fullyQualifiedResourceName);
    }

    @Override
    public void setResourceLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private String getFullyQualifiedResourceName(final String resourceName) {
        final StringBuilder result = new StringBuilder();
        if (nonNull(prefix) && !resourceName.startsWith(prefix)) {
            result.append(prefix);
        }
        result.append(resourceName);
        if (nonNull(suffix)) {
            result.append(suffix);
        }
        return result.toString();
    }

    @Override
    public void setCharset(final String charset) {
        this.charset = charset;
    }

    @Override
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String resolveRelativePath(final String relativePath, final String anchorPath) {
        return PathUtils.resolveRelativePath(relativePath, anchorPath, File.separatorChar);
    }

    @Override
    public String createCacheKey(final String templateName) {
        return templateName;
    }

}
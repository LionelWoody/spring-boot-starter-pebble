package org.woodylab.boot.pebble;

import com.google.common.cache.CacheBuilder;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.loader.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午11:14.
 */
public class PebbleEngineConfigurer {

    private static final Logger log = LoggerFactory.getLogger(PebbleEngineConfigurer.class);

    private Loader loader;

    private List<Extension> extensions = new ArrayList<>();

    private boolean cache;

    private int cacheSize;

    public PebbleEngine getPebbleEngine() {

        final PebbleEngine.Builder builder = new PebbleEngine.Builder()
                .loader(this.loader)
                .extension(extensions.toArray(new Extension[extensions.size()]));

        if (cache) {
            builder.templateCache(CacheBuilder.newBuilder().maximumSize(cacheSize).build())
                    .tagCache(CacheBuilder.newBuilder().maximumSize(cacheSize).build());
        } else {
            builder.templateCache(CacheBuilder.newBuilder().maximumSize(0).build())
                    .tagCache(CacheBuilder.newBuilder().maximumSize(0).build());
        }

        log.debug("PebbleEngine built! 这里不写几个汉字不显眼！！！");
        return builder.build();
    }

    public void registerExtension(final Object extension) {
        if (extension instanceof Extension) {
            this.extensions.add((Extension) extension);
        }
    }

    // setter
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }


}

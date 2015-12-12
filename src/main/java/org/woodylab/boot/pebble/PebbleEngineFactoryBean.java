package org.woodylab.boot.pebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.loader.Loader;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午11:14.
 */
public class PebbleEngineFactoryBean implements FactoryBean<PebbleEngine> {

    private Loader loader;

    private Extension extension;

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public void setExtension(Extension extension) {
        this.extension = extension;
    }

    @Override
    public PebbleEngine getObject() throws Exception {

        PebbleEngine engine;

        if (this.loader == null) {
            engine = new PebbleEngine();
        } else {
            engine = new PebbleEngine(this.loader);
        }

        if (this.extension != null) {
            engine.addExtension(this.extension);
        }

        return engine;

    }

    @Override
    public Class<? extends PebbleEngine> getObjectType() {
        return PebbleEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}

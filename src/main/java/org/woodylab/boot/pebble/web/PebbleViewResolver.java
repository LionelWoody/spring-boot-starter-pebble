package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.support.ServletContextResourceLoader;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.woodylab.boot.pebble.PebbleTemplateLoader;

import java.util.Locale;

/**
 * 聋夫三拳 —— 我们不写代码，我们只是代码的搬运工。
 * <p>
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午5:17.
 */
public class PebbleViewResolver extends AbstractTemplateViewResolver implements ViewResolver, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(PebbleViewResolver.class);

    private PebbleEngine pebbleEngine = new PebbleEngine();

    public PebbleViewResolver() {
        setViewClass(requiredViewClass());
    }

    @Override
    protected View loadView(String viewName, Locale locale) {

        pebbleEngine.setDefaultLocale(locale);
        PebbleTemplateLoader loader = new PebbleTemplateLoader();
        loader.setResourceLoader(new ServletContextResourceLoader(getServletContext()));
        loader.setPrefix(this.getPrefix());
        loader.setSuffix(this.getSuffix());
        pebbleEngine.setLoader(loader);
        PebbleTemplate template = null;
        try {
            template = pebbleEngine.getTemplate(viewName);
        } catch (PebbleException e) {
            log.warn("拆了硬盘也没找到你要的‘{}’", viewName);
        }
        if (template == null) {
            return null;
        }
        PebbleView view = new PebbleView(template);
        view.setApplicationContext(getApplicationContext());
        view.setServletContext(getServletContext());
        return view;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Loader templateLoader = pebbleEngine.getLoader();
        templateLoader.setPrefix(this.getPrefix());
        templateLoader.setSuffix(this.getSuffix());
    }

    public void setPebbleEngine(PebbleEngine pebbleEngine) {
        this.pebbleEngine = pebbleEngine;
    }

    @Override
    protected Class<?> requiredViewClass() {
        return PebbleView.class;
    }

}

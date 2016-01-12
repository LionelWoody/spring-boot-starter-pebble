package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

import java.util.Locale;

/**
 * 聋夫三拳 —— 我们不写代码，我们只是代码的搬运工。
 * <p>
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午5:17.
 */
public class PebbleViewResolver extends AbstractTemplateViewResolver implements ViewResolver {

    private static final Logger log = LoggerFactory.getLogger(PebbleViewResolver.class);

    private PebbleEngine pebbleEngine;

    public PebbleViewResolver() {
        log.debug("使用 PebbleViewResolver 作为 ViewResolver.");
        setViewClass(requiredViewClass());
    }

    @Override
    protected Class<?> requiredViewClass() {
        return PebbleView.class;
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws PebbleException {

        // Spring 有时候会私自给 viewName 加尾巴，我斩～～
        if(viewName.endsWith(".html")) {
            viewName = viewName.substring(0, viewName.length()-5);
        }
        PebbleView view = new PebbleView(pebbleEngine, viewName);
        view.setApplicationContext(getApplicationContext());
        view.setServletContext(getServletContext());

        return view;

    }

    public void setPebbleEngine(PebbleEngine pebbleEngine) {
        this.pebbleEngine = pebbleEngine;
    }


}

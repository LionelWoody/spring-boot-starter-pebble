package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * 聋夫三拳 —— 我们不写代码，我们只是代码的搬运工。
 *
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午5:16.
 */
public class PebbleView extends AbstractTemplateView {

    private final PebbleEngine engine;
    private final String viewName;

    public PebbleView(final PebbleEngine engine, final String viewName) {
        this.engine = engine;
        this.viewName = viewName;
    }

    @Override
    protected void renderMergedTemplateModel(final Map<String, Object> model, final HttpServletRequest request,
                                             final HttpServletResponse response) throws IOException, PebbleException {

        response.setContentType(getContentType());
        response.setCharacterEncoding("UTF-8");

        final Writer writer = response.getWriter();
        try {
            engine.getTemplate(viewName).evaluate(writer, model, RequestContextUtils.getLocale(request));
        } finally {
            try {
                writer.flush();
            } catch (IOException ignore) {
            }
        }
    }

}

package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.Map;

/**
 * 聋夫三拳 —— 我们不写代码，我们只是代码的搬运工。
 *
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午5:16.
 */
public class PebbleView extends AbstractTemplateView {

    private final PebbleTemplate template;

    public PebbleView(PebbleTemplate template) {
        this.template = template;
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {

        response.setContentType(getContentType());
        response.setCharacterEncoding("UTF-8");

        final Writer writer = response.getWriter();
        try {
            template.evaluate(writer, model, RequestContextUtils.getLocale(request));
        } finally {
            writer.flush();
        }
    }

}

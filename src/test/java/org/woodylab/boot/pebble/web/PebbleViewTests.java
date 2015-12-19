package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.StringLoader;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-12 下午10:22.
 */
public class PebbleViewTests {
    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();

    private AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

    @Before
    public void init() {
        context.refresh();
        MockServletContext servletContext = new MockServletContext();
        context.setServletContext(servletContext);
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
    }

    @Test
    public void test() throws Exception {
        PebbleView view = new PebbleView(new PebbleEngine.Builder().loader(new StringLoader()).build().getTemplate("Hello {{msg}}"));
        view.setApplicationContext(context);
        view.render(Collections.singletonMap("msg", "World"), request , response);
        assertEquals("Hello World", response.getContentAsString());
    }

}

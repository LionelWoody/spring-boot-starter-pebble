package org.woodylab.boot.pebble.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.StaticWebApplicationContext;

import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-12 下午10:25.
 */
public class PebbleViewResolverTests {

    private PebbleViewResolver resolver = new PebbleViewResolver();

    @Before
    public void init() {
        resolver.setApplicationContext(new StaticWebApplicationContext());
        resolver.setServletContext(new MockServletContext());
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
    }

    @Test
    public void resolveNonExistent() throws Exception {
        assertNull(resolver.resolveViewName("bar", null));
    }

    @Test
    public void resolveNullLocale() throws Exception {
        assertNotNull(resolver.resolveViewName("foo", null));
    }

    @Test
    public void resolveDefaultLocale() throws Exception {
        assertNotNull(resolver.resolveViewName("foo", Locale.US));
    }

    @Test
    public void resolveDoubleLocale() throws Exception {
        assertNotNull(resolver.resolveViewName("foo", Locale.CANADA_FRENCH));
    }

    @Test
    public void resolveTripleLocale() throws Exception {
        assertNotNull(resolver.resolveViewName("foo", new Locale("en", "GB", "cy")));
    }

    @Test
    public void resolveSpecificLocale() throws Exception {
        assertNotNull(resolver.resolveViewName("foo", new Locale("de")));
    }

}

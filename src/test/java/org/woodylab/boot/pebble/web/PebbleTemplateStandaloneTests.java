package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.StringLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.StringWriter;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-12 下午10:33.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PebbleTemplateStandaloneTests.Application.class)
@IntegrationTest({"spring.main.web_environment=false"})
public class PebbleTemplateStandaloneTests {

    @Autowired
    private PebbleEngine engine;

    @Test
    public void directCompilation() throws Exception {
        engine.setLoader(new StringLoader());
        StringWriter writer = new StringWriter();
        engine.getTemplate("Hello: {{world}}").evaluate(writer, Collections.singletonMap("world", "World"));
        assertEquals("Hello: World", writer.toString());
    }

    @EnableAutoConfiguration
    @Configuration
    protected static class Application {}

}

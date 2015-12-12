package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.loader.StringLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.woodylab.boot.pebble.autoconfigure.PebbleAutoConfiguration;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-12 下午9:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
public class ApplicationTests {

    @Autowired
    private EmbeddedWebApplicationContext context;
    private int port;

    @Before
    public void init() {
        port = context.getEmbeddedServletContainer().getPort();
    }

    @Test
    public void contextLoads() throws PebbleException, IOException {
        String source = "Hello {{arg}}!";
        PebbleTemplate tmpl = new PebbleEngine(new StringLoader()).getTemplate(source);
        Map<String, Object> context = new HashMap<>();
        context.put("arg", "world");
        StringWriter w = new StringWriter();
        tmpl.evaluate(w, context);
        assertEquals("Hello world!", w.toString()); // returns "Hello world!"
    }

    @Test
    public void testHomePage() throws Exception {
        String body = new TestRestTemplate().getForObject("http://localhost:" + port,
                String.class);
        assertTrue(body.contains("Hello World"));
    }

    @Test
    public void testPartialPage() throws Exception {
        String body = new TestRestTemplate().getForObject("http://localhost:" + port
                + "/partial", String.class);
        assertTrue(body.contains("Hello App"));
    }


    @Configuration
    @EnableAutoConfiguration(exclude = PebbleAutoConfiguration.class)
    @Controller
    public static class Application {

        @RequestMapping("/")
        public String home(Map<String, Object> model) {
            model.put("time", new Date());
            model.put("message", "Hello World");
            model.put("title", "Hello App");
            return "home";
        }

        @RequestMapping("/partial")
        public String layout(Map<String, Object> model) {
            model.put("time", new Date());
            model.put("message", "Hello World");
            model.put("title", "Hello App");
            return "partial";
        }

        @Bean
        public PebbleViewResolver viewResolver() {
            PebbleViewResolver resolver = new PebbleViewResolver();
            resolver.setPrefix("classpath:/templates/");
            resolver.setSuffix(".html");
            resolver.setPebbleEngine(new PebbleEngine());
            return resolver;
        }

        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }

    }

}

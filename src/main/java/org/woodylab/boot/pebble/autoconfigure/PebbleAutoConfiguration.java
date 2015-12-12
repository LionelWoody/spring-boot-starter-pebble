package org.woodylab.boot.pebble.autoconfigure;

import com.mitchellbosecke.pebble.PebbleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.woodylab.boot.pebble.PebbleEngineFactoryBean;
import org.woodylab.boot.pebble.web.PebbleViewResolver;

import javax.annotation.PostConstruct;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Pebble.
 * 小轮子，造起来
 *
 * @author <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午11:28.
 */
@Configuration
@ConditionalOnClass(PebbleEngine.class)
@EnableConfigurationProperties(PebbleProperties.class)
public class PebbleAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PebbleAutoConfiguration.class);

    @Autowired
    private PebbleProperties pebbleProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean(PebbleEngine.class)
    public PebbleEngine pebbleEngine() {
        return new PebbleEngine();
    }

    @Configuration
    @ConditionalOnWebApplication
    protected static class PebbleWebConfiguration {

        @Autowired
        private PebbleProperties pebbleProperties;

        @Bean
        @ConditionalOnMissingBean(PebbleViewResolver.class)
        public PebbleViewResolver pebbleViewResolver(PebbleEngine pebbleEngine) {
            PebbleViewResolver resolver = new PebbleViewResolver();
            resolver.setPrefix(this.pebbleProperties.getPrefix());
            resolver.setSuffix(this.pebbleProperties.getSuffix());
            resolver.setViewNames(this.pebbleProperties.getViewNames());
            resolver.setContentType(this.pebbleProperties.getContentType().toString());
            resolver.setPebbleEngine(pebbleEngine);
            resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
            return resolver;
        }

    }
}

package org.woodylab.boot.pebble.autoconfigure;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.DelegatingLoader;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.support.ServletContextResourceLoader;
import org.woodylab.boot.pebble.PebbleEngineConfigurer;
import org.woodylab.boot.pebble.PebbleExtension;
import org.woodylab.boot.pebble.PebbleTemplateLoader;
import org.woodylab.boot.pebble.web.PebbleViewResolver;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Pebble.
 * 小轮子，造起来
 *
 * @author <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-11 下午11:28.
 */
@Configuration
@ConditionalOnClass({PebbleEngine.class, PebbleEngineConfigurer.class})
@EnableConfigurationProperties(PebbleProperties.class)
public class PebbleAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(PebbleAutoConfiguration.class);

    @Autowired
    private PebbleProperties properties;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void checkTemplateLocationExists() {
        if (this.properties.isCheckTemplateLocation()) {
            final TemplateLocation location = new TemplateLocation(this.properties.getPrefix());
            if (!location.exists(this.applicationContext)) {
                logger.warn("Cannot find template location: " + location
                        + " (please add some templates, check your Pebble "
                        + "configuration, or set spring.pebble."
                        + "checkTemplateLocation=false)");
            }
        }
    }

    @Configuration
    public static class PebbleConfiguration {

        @Autowired
        protected PebbleProperties properties;

        @Bean
        @ConditionalOnMissingBean
        public PebbleEngineConfigurer pebbleEngineConfigurer() {
            return new PebbleEngineConfigurer();
        }

        @Bean
        public BeanPostProcessor pebbleBeanPostProcessor(final PebbleEngineConfigurer pebbleEngineConfigurer) {
            return new BeanPostProcessor() {
                @Override
                public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                    return bean;
                }

                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    final PebbleExtension annotation = findAnnotation(bean.getClass(), PebbleExtension.class);
                    if (nonNull(annotation)) {
                        pebbleEngineConfigurer.registerExtension(bean);
                    }
                    return bean;
                }
            };
        }

    }

    @Configuration
    @ConditionalOnNotWebApplication
    public static class PebbleNonWebConfiguration extends PebbleConfiguration{

        @Bean
        @ConditionalOnMissingBean(PebbleEngine.class)
        public PebbleEngine pebbleEngine(final PebbleEngineConfigurer pebbleEngineConfigurer) {
            pebbleEngineConfigurer.setCache(this.properties.isCache());
            pebbleEngineConfigurer.setCacheSize(this.properties.getCacheSize());
            return pebbleEngineConfigurer.getPebbleEngine();
        }

    }

    @Configuration
    @ConditionalOnClass(EmbeddedWebApplicationContext.class)
    @ConditionalOnWebApplication
    public static class PebbleWebConfiguration extends PebbleConfiguration {

        @Autowired
        private EmbeddedWebApplicationContext context;

        @Bean
        @ConditionalOnMissingBean(PebbleEngine.class)
        public PebbleEngine pebbleEngine(final PebbleEngineConfigurer pebbleEngineConfigurer) {
            final PebbleTemplateLoader loader = new PebbleTemplateLoader();
            loader.setResourceLoader(new ServletContextResourceLoader(context.getServletContext()));
            loader.setPrefix(this.properties.getPrefix());
            loader.setSuffix(this.properties.getSuffix());

            final List<Loader<?>> list = new ArrayList<>();
            list.add(loader);
            list.add(new ClasspathLoader());
            list.add(new FileLoader());

            final DelegatingLoader loaderAll = new DelegatingLoader(list);
            pebbleEngineConfigurer.setLoader(loaderAll);
            pebbleEngineConfigurer.setCache(this.properties.isCache());
            pebbleEngineConfigurer.setCacheSize(this.properties.getCacheSize());

            return pebbleEngineConfigurer.getPebbleEngine();
        }

        @Bean
        @ConditionalOnMissingBean(PebbleViewResolver.class)
        public PebbleViewResolver pebbleViewResolver(final PebbleEngine pebbleEngine) {
            final PebbleViewResolver resolver = new PebbleViewResolver();
            resolver.setPrefix(this.properties.getPrefix());
            resolver.setSuffix(this.properties.getSuffix());
            resolver.setViewNames(this.properties.getViewNames());
            resolver.setContentType(this.properties.getContentType().toString());
            resolver.setPebbleEngine(pebbleEngine);
            resolver.setOrder(LOWEST_PRECEDENCE - 10);
            return resolver;
        }

    }

}

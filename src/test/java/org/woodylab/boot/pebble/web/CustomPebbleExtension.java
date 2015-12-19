package org.woodylab.boot.pebble.web;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplateImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.woodylab.boot.pebble.PebbleExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by <a href="mailto:javaworld@qq.com">Woody</a> @ 15-12-19 下午4:10.
 */
@EnableAutoConfiguration
@PebbleExtension
public class CustomPebbleExtension extends AbstractExtension {

    @Override
    public Map<String, Filter> getFilters() {

        Map<String, Filter> filters = new HashMap<>();

        filters.put("noArgumentsButCanAccessContext", new Filter() {

            @Override
            public List<String> getArgumentNames() {
                return null;
            }

            @Override
            public String apply(Object input, Map<String, Object> args) {
                EvaluationContext context = (EvaluationContext) args.get("_context");
                PebbleTemplateImpl pebbleTemplate = (PebbleTemplateImpl) args.get("_self");
                if (context != null && pebbleTemplate != null) {
                    return "success";
                } else {
                    return "failure";
                }
            }
        });
        return filters;
    }
}

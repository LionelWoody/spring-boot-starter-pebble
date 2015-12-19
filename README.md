Spring Boot Starter Pebble
====

[![Build Status](https://travis-ci.org/LionelWoody/spring-boot-starter-pebble.svg)](https://travis-ci.org/LionelWoody/spring-boot-starter-pebble)
[![Coverage Status](https://coveralls.io/repos/LionelWoody/spring-boot-starter-pebble/badge.svg?branch=master&service=github)](https://coveralls.io/github/LionelWoody/spring-boot-starter-pebble?branch=master)

Spring Boot Starter support for
[Pebble](http://www.mitchellbosecke.com/pebble/home)
(A lightweight but rock solid Java templating engine.).

## Usage

Add `spring-boot-starter-pebble` as dependency:
```gradle
repositories {
    mavenCentral()
}

dependencies {
    compile 'org.woodylab.boot:spring-boot-starter-pebble:0.2.0'
}
```

CustomExtension supported since version 0.2.0, you can define your Extensions, simply annotation it and then you can use it in your template.
```java
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
```

## License

**spring-boot-starter-pebble** is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

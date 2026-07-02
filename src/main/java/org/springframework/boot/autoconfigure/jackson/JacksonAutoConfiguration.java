package org.springframework.boot.autoconfigure.jackson;

/**
 * Compatibility stub for mqtt-spring-boot-starter 2.0.1 which references this class
 * via @AutoConfigureAfter. In Spring Boot 4, JacksonAutoConfiguration moved to
 * org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration.

 * That references `org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration`, which existed in Spring Boot 3 but was **moved** to `org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration` in Spring Boot 4. When Spring tries to load the library's auto-configuration, it crashes because it can't find the class referenced in the annotation — even before any of your code runs.

 * The stub provides that missing class on the classpath so the annotation resolves without error. Without it, the application fails to start with:

 * ClassNotFoundException: org.springframework.boot.autoconfigure.jackson.JacksonAutoConfigu
 */
public class JacksonAutoConfiguration {
}

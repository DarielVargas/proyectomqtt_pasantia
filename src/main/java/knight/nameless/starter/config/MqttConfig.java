package knight.nameless.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tocrhz.mqtt.properties.MqttConfigAdapter;
import com.github.tocrhz.mqtt.convert.PayloadDeserialize;
import com.github.tocrhz.mqtt.convert.PayloadSerialize;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

@Configuration
@Slf4j
@EnableAsync
public class MqttConfig extends MqttConfigAdapter {

    @Override
    public IMqttAsyncClient postCreate(String clientId, String[] serverURIs) throws MqttException {
        return new MqttAsyncClient(serverURIs[0], clientId, new MemoryPersistence());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public PayloadSerialize payloadSerialize(ObjectMapper objectMapper) {
        return source -> {
            try {
                return objectMapper.writeValueAsBytes(source);
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                log.warn("Payload serialize error: {}", e.getMessage(), e);
            }
            return null;
        };
    }

    @Bean
    public PayloadDeserialize payloadDeserialize(ObjectMapper objectMapper) {
        return new PayloadDeserialize() {
            @Override
            @SuppressWarnings("unchecked")
            public <T> Converter<byte[], T> getConverter(Class<T> targetType) {
                return source -> {
                    try {
                        if (targetType == String.class) {
                            return (T) new String(source, StandardCharsets.UTF_8);
                        }
                        return objectMapper.readValue(source, targetType);
                    } catch (IOException e) {
                        log.warn("Payload deserialize error: {}", e.getMessage(), e);
                    }
                    return null;
                };
            }
        };
    }

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("tubemillthread-");
        executor.initialize();
        return executor;
    }
}

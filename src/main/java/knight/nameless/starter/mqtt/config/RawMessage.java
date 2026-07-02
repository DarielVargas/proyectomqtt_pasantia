package knight.nameless.starter.mqtt.config;

import lombok.Data;

@Data
public class RawMessage {

    private TopicFunction topicFunction;
    private String topic;

    private String payload;
}

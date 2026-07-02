package knight.nameless.starter.mqtt.config;

import knight.nameless.starter.mqtt.MqttLogin;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;

import java.util.Map;

@Configuration
public class Integration {

    private final String BaseTopic = "Selladora/L1/HMI/Entrada/";
    Logger logger = LoggerFactory.getLogger(MqttLogin.class);

    @Bean
    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();
//        options.setServerURIs(new String[]{"tcp://10.0.0.184:1883"});
        options.setServerURIs(new String[]{"tcp://localhost:1883"});
        options.setConnectionTimeout(65);
        options.setKeepAliveInterval(20);
        options.setCleanSession(true);

        return options;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions());
        return factory;
    }

    @Bean
    public MessageProducerSupport accountTopics() {

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                "account",
                mqttClientFactory(),
                BaseTopic + "Login",
                BaseTopic + "ConfirmarTurno",
                BaseTopic + "Logout",
                BaseTopic + "LoginMecanico"
        );

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        return adapter;
    }

    @Bean
    public IntegrationFlow accountFlow() {

        return IntegrationFlow.from(accountTopics()).handle(this::mqttMessage2RawMessage)
                .<RawMessage, TopicFunction>route(RawMessage::getTopicFunction, mapping -> mapping

                        .subFlowMapping(TopicFunction.nil, sf -> sf.handle((_, headers) -> {
                            logger.warn("Unmapped topic: {}", headers.get(MqttHeaders.RECEIVED_TOPIC));
                            return null;
                        }))

                        .subFlowMapping(TopicFunction.LoginOperador,
                                sf -> sf.handle("mqttLogin", "loginOperador")
                        )

                        .subFlowMapping(TopicFunction.ConfirmarTurno,
                                sf -> sf.handle("mqttLogin", "confirmarTurno")
                        )

                        .subFlowMapping(TopicFunction.Logout,
                                sf -> sf.handle("mqttLogin", "logout")
                        )

                        .subFlowMapping(TopicFunction.LoginMecanico,
                                sf -> sf.handle("mqttLogin", "loginMecanico")
                        )

                        .defaultOutputToParentFlow()

                ).get();
    }

    private RawMessage mqttMessage2RawMessage(String payload, Map<String, Object> headers) {

        try {

            RawMessage rawMessage = new RawMessage();
            rawMessage.setPayload(payload);

            String topic = headers.get(MqttHeaders.RECEIVED_TOPIC).toString();
            logger.info("topic: {}", topic);

            TopicFunction topicFunction = TopicFunction.of(topic);
            if (topicFunction == null)
                topicFunction = TopicFunction.nil;

            rawMessage.setTopicFunction(topicFunction);
            rawMessage.setTopic(topic);
            return rawMessage;

        } catch (Exception e) {

            RawMessage rawMessage = new RawMessage();
            rawMessage.setTopicFunction(TopicFunction.nil);
            return rawMessage;
        }
    }
}

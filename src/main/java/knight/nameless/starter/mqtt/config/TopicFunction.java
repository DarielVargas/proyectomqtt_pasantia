package knight.nameless.starter.mqtt.config;

public enum TopicFunction {

    LoginOperador("Login"),
    ConfirmarTurno("ConfirmarTurno"),
    Logout("Logout"),
    LoginMecanico("LoginMecanico"),
    nil("null");

    private final String topicName;

    TopicFunction(String topicName) {
        this.topicName = topicName;
    }

    public static TopicFunction of(String topic) {

        final String baseTopic = "Selladora/L1/HMI/Entrada/";

        for (TopicFunction topicFunction : TopicFunction.values()) {

            var completeTopic = baseTopic + topicFunction.topicName;

            if (completeTopic.equalsIgnoreCase(topic))
                return topicFunction;
        }

        return TopicFunction.nil;
    }
}

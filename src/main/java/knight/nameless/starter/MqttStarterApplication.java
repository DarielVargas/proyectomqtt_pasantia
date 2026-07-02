package knight.nameless.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
@EnableAsync
public class MqttStarterApplication {

    static void main(String[] args) {
        SpringApplication.run(MqttStarterApplication.class, args);
    }
}

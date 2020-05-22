package de.janheise.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

@SpringBootApplication
public class MqttApplication {
	@Value("${mqtt.username}")
	String username;
	@Value("${mqtt.password}")
	String password;
	@Value("${mqtt.url}")
	String url;

	@Value("${ttn.client}")
	String client;
	@Value("${ttn.topics}")
	String topics;

	@Autowired
	private SensorDataService service;

	public static void main(String[] args) {
		SpringApplication.run(MqttApplication.class, args);
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setServerURIs(this.url);
		factory.setUserName(this.username);
		factory.setPassword(this.password);
		return factory;
	}

	@Bean
	public IntegrationFlow mqttInbound() {
		return IntegrationFlows.from(
				new MqttPahoMessageDrivenChannelAdapter(client,
						mqttClientFactory(), topics.split(",")))
                .handle(m -> this.service.createFromPayload(m.getPayload().toString()))
				.get();
	}
}

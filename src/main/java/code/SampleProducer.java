package code;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.ProducerBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;

import config.AppConfig;

public class SampleProducer {
	public static void sendMessage() throws PulsarClientException, MalformedURLException {
		PulsarClient client = PulsarClient.builder()
            .serviceUrl(AppConfig.SERVICE_URL)
            .authentication(
                    AuthenticationFactoryOAuth2.clientCredentials(new URL(AppConfig.ISSUER_URL), new URL(AppConfig.CREDENTIALS_URL), AppConfig.AUDIENCE))
            .build();

        ProducerBuilder<byte[]> producerBuilder = client.newProducer().topic(AppConfig.TOPIC)
                .producerName("my-producer-name");
        Producer<byte[]> producer = producerBuilder.create();

        for (int i = 0; i < 10; i++) {
            String message = "my-message-" + i;
            producer.send(message.getBytes());
        }
                
        producer.close();
        client.close();
	}
}

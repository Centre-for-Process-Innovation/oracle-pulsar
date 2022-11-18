package code;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import org.apache.pulsar.client.impl.schema.JSONSchema;

import config.AppConfig;
import interceptor.CustomProducerInterceptor;
import models.Product;
import utils.DBConnection;

public class JSONProducer {
	public static void sendJSONMessage() throws PulsarClientException, MalformedURLException, ClassNotFoundException, SQLException {
		PulsarClient client = PulsarClient.builder()
	            .serviceUrl(AppConfig.SERVICE_URL)
	            .authentication(
	                    AuthenticationFactoryOAuth2.clientCredentials(new URL(AppConfig.ISSUER_URL), new URL(AppConfig.CREDENTIALS_URL), AppConfig.AUDIENCE))
	            .build();
			
		CustomProducerInterceptor interceptor = new CustomProducerInterceptor();
		Producer<Product> producer = client.newProducer(JSONSchema.of(Product.class))
	            .topic(AppConfig.TOPIC)
	            .producerName("product-producer")
	            .intercept(interceptor)
	            .create();
		
		Connection conn = new DBConnection().getDBConnection();
		Statement stmt = conn.createStatement();
		
		String query = "SELECT * FROM Product";
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) {
			Product product = new Product(rs.getInt(1), rs.getString(2), rs.getInt(3));
			producer.send(product);
		}
		
		producer.flush();
		producer.close();
		client.close();
	}
}

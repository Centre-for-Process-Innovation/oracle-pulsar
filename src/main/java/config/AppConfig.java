package config;

public class AppConfig {
	public static final String SERVICE_URL = "pulsar+ssl://ananthu-test.o-9apc0.snio.cloud:6651";
	public static final String TOPIC = "persistent://public/default/test-topic";
	public static final String ISSUER_URL = "https://auth.streamnative.cloud/";
	public static final String CREDENTIALS_URL = "file:///" + System.getProperty("user.dir") + "/src/main/resources/credentials/o-9apc0-free.json";
	public static final String AUDIENCE = "urn:sn:pulsar:o-9apc0:ananthu-test";
	public static final String SUBSCRIPTION = "testsub";
}

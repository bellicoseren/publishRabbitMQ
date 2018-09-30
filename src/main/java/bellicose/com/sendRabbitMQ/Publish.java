package bellicose.com.sendRabbitMQ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Hello world!
 *
 */
public class Publish {
	private static final String QUEUE_NAME = "ratita";

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		try {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);

			for (int i = 0; i < 10; ++i) {
				String message = String.format("Hola ratita %d. Hoy ess %s", i,
						LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
				channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
				System.out.println(String.format("Enviado: %s", message));

				Thread.sleep(1500);
			}
		} finally {
			channel.close();
			connection.close();
		}
	}
}

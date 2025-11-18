package dat250.feedapp.messager;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "poll-exchange";

    @Bean
    public ConnectionFactory connectionFactory() {
        String rabbitMqHost = System.getenv("SPRING_RABBITMQ_HOST");
        //Default
        if (rabbitMqHost == null) {
            rabbitMqHost = "localhost";
        }
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqHost);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }

    @Bean
    public TopicExchange pollExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}

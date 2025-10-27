package dat250.feedapp.messager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollEventListener {

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final ConnectionFactory connectionFactory;

    public PollEventListener(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.objectMapper = new ObjectMapper();
    }

    public void registerListener(UUID pollId) {
        String queueName = "poll." + pollId;
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(message -> {
            try {
                new String(message.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        container.start();
        System.out.println("Registered listener for poll: " + pollId);
    }


}

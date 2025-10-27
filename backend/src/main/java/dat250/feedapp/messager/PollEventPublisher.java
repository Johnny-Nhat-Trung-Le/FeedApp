package dat250.feedapp.messager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat250.feedapp.entities.Vote;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PollEventPublisher {

    @Autowired
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private final ObjectMapper objectMapper;

    public PollEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void publishVote(UUID pollId, Vote vote)  {
        try {
            String message = objectMapper.writeValueAsString(vote);
            String routing = "poll." + pollId + ".vote";
            rabbitTemplate.convertAndSend("poll-exchange", routing, message);
            System.out.println("Sent " + routing + ":" + message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package io.github.pablitohaddad.msuser.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pablitohaddad.msuser.entities.UserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueNotification;
    public void sendNotification(UserNotification notification) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(notification);
        rabbitTemplate.convertAndSend(queueNotification.getName(), json);
    }
}



package cz.cvut.fel.aos.print.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.aos.print.jms.port.Email;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;

@AllArgsConstructor
public class JmsSender {

    private JmsTemplate jmsTemplate;
    private String jmsDestination;
    private ObjectMapper objectMapper;

    @Async
    public void send(Email email) {
        try {
            Thread.sleep(5_000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            jmsTemplate.convertAndSend(jmsDestination, objectMapper.writeValueAsString(email));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

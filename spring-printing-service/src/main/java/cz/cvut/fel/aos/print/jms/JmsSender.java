package cz.cvut.fel.aos.print.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.aos.print.jms.port.Email;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

@AllArgsConstructor
public class JmsSender {

    private JmsTemplate jmsTemplate;
    private String jmsDestination;
    private ObjectMapper objectMapper;

    public void send(Email email) {
        try {
            jmsTemplate.convertAndSend(jmsDestination, objectMapper.writeValueAsString(email));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

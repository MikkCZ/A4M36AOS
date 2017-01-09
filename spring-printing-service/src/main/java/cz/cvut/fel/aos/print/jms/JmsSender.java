package cz.cvut.fel.aos.print.jms;

import cz.cvut.fel.aos.print.jms.port.Email;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

@AllArgsConstructor
public class JmsSender {

    private JmsTemplate jmsTemplate;
    private String jmsDestination;

    public void send(Email email) {
        jmsTemplate.convertAndSend(jmsDestination, email);
    }
}

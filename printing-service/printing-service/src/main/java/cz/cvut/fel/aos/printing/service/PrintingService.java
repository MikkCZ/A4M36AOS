package cz.cvut.fel.aos.printing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "PrintingService")
@Stateless()
@Slf4j
public class PrintingService {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public PrintingService() {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        this.jmsTemplate = new JmsTemplate(connectionFactory);
        this.jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        this.objectMapper = new ObjectMapper();
    }

    @WebMethod(operationName = "print")
    public String print(@WebParam(name = "jsonstring") String jsonstring) {
        try {
            jmsTemplate.convertAndSend("mailbox", this.objectMapper.writeValueAsString(new Email("nobody@example.com", jsonstring)));
        } catch (JsonProcessingException e) {
            log.error("Cannot convert and send message {}.", jsonstring, e);
        }
        return "ABC";
    }
    
    private MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}

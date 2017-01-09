package cz.cvut.fel.aos.print.config;

import cz.cvut.fel.aos.print.jms.JmsReceiver;
import cz.cvut.fel.aos.print.jms.JmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${jms.destination}")
    private String jmsDestination;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Bean
    public JmsSender printSender() {
        return new JmsSender(jmsTemplate, jmsDestination);
    }

    @Bean
    public JmsReceiver printReceiver() {
        return new JmsReceiver();
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}

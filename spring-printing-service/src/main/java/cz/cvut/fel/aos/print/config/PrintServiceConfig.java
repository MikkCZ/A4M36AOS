package cz.cvut.fel.aos.print.config;

import cz.cvut.fel.aos.print.jms.JmsSender;
import cz.cvut.fel.aos.print.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JmsConfig.class})
public class PrintServiceConfig {

    @Autowired
    private JmsSender jmsSender;

    @Bean
    public PrintService printService() {
        return new PrintService(jmsSender);
    }
}

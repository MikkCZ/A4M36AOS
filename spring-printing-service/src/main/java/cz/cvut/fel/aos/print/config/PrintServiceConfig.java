package cz.cvut.fel.aos.print.config;

import cz.cvut.fel.aos.print.service.PrintService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrintServiceConfig {

    @Bean
    public PrintService printService() {
        return new PrintService();
    }
}

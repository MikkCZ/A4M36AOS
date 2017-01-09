package cz.cvut.fel.aos.print.config;

import cz.cvut.fel.aos.print.resource.PrintResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("cz.cvut.fel.aos.print.resource")
@Import({PrintServiceConfig.class})
public class RestConfig {

    @Bean
    public PrintResource printResource() {
        return new PrintResource();
    }
}

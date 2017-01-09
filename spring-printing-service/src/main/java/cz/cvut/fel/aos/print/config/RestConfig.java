package cz.cvut.fel.aos.print.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("cz.cvut.fel.aos.print.resource")
@Import({PrintServiceConfig.class})
public class RestConfig {
}

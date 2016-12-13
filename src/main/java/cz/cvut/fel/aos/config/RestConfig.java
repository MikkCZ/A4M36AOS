package cz.cvut.fel.aos.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("cz.cvut.fel.aos.resource")
@EnableWebMvc
@Import({SecurityConfig.class})
public class RestConfig {
}

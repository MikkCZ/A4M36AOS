package cz.cvut.fel.aos.print.init;

import cz.cvut.fel.aos.print.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication
@Import({RestConfig.class})
public class PrintServiceInit {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PrintServiceInit.class, args);
    }
}

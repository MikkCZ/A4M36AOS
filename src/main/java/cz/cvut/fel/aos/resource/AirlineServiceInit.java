package cz.cvut.fel.aos.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirlineServiceInit {

    /**
     * For development, run with --spring.profiles.active=dev
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirlineServiceInit.class, args);
    }
}

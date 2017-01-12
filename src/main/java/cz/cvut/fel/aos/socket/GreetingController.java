package cz.cvut.fel.aos.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class GreetingController {

    private AtomicInteger counter = new AtomicInteger(0);


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        int count = counter.incrementAndGet();
        return new Greeting("Hello, " + message.getName() + "!" + " " + count);
    }

}

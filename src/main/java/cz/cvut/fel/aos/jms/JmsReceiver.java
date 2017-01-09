package cz.cvut.fel.aos.jms;

import org.springframework.jms.annotation.JmsListener;

public class JmsReceiver {

    @JmsListener(destination = "${jms.destination}")
    public void receive(String email) {
        System.out.println("Sending e-mail to stdout: " + email);
    }
}


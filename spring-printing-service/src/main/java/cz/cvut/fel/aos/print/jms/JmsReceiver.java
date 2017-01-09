package cz.cvut.fel.aos.print.jms;

import cz.cvut.fel.aos.print.jms.port.Email;
import org.springframework.jms.annotation.JmsListener;

public class JmsReceiver {

    @JmsListener(destination = "${jms.destination}")
    public void receive(Email email) {
        System.out.println("Sending e-mail to stdout: " + email);
    }
}

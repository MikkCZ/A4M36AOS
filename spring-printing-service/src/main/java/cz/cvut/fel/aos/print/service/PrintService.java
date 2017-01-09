package cz.cvut.fel.aos.print.service;

import cz.cvut.fel.aos.print.jms.JmsSender;
import cz.cvut.fel.aos.print.jms.port.Email;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PrintService {

    private JmsSender jmsSender;

    public void print(String text) {
        System.out.println("Print service request: " + text);
        jmsSender.send(new Email("nobody@example.com", text));
    }
}

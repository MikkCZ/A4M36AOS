package cz.cvut.fel.aos.printing.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Email {

    private final String address;
    private final String message;

}

package cz.cvut.fel.aos.print.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PrintResource {

    @Autowired
    private cz.cvut.fel.aos.print.service.PrintService printService;

    @RequestMapping(value = "/print", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void print(@RequestBody String text) {
        printService.print(text);
    }
}

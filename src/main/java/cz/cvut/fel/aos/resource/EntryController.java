package cz.cvut.fel.aos.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EntryController {

    @RequestMapping("/")
    public String singleEntryPoint() {
        return "index";
    }
}

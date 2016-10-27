package cz.cvut.fel.aos.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello-world")
    @ResponseBody
    public String helloWorld() {
        return "Hello World!";
    }

}

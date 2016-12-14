package cz.cvut.fel.aos.resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntryController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String singleEntryPoint() {
        return "index";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String helpPoint() {
        return "help";
    }

    @RequestMapping(value = "/destination/all", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String destinsPoint() {
        return "allDestinations";
    }

    @RequestMapping(value = "/destination/new", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String destinationPoint() {
        return "newDestination";
    }
}

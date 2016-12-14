package cz.cvut.fel.aos.resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    public ModelAndView destinationPoint(@RequestParam Map<String,String> allRequestParams) {
        ModelAndView mav = new ModelAndView("newDestination");
        if (allRequestParams.containsKey("id")) {
            mav.addObject("destination_id", allRequestParams.get("id"));
        }
        return mav;
    }
}

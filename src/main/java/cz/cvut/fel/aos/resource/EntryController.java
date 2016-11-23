package cz.cvut.fel.aos.resource;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class EntryController {

    @RequestMapping(value = "/", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
    public String singleEntryPoint() {
        return "index";
    }
}

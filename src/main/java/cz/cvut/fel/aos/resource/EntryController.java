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
    public Map<String, Map<String, String>> singleEntryPoint() {
        return ImmutableMap.of(
                "Destination", ImmutableMap.of(
                        "GET /destination", "",
                        "POST /destination", "",
                        "GET /destination/{id}", "",
                        "PUT /destination/{id}", "",
                        "DELETE /destination/{id}", ""
                ),
                "Flight", ImmutableMap.of(
                        "GET /flight", "",
                        "POST /flight", "",
                        "GET /flight/{id}", "",
                        "PUT /flight/{id}", "",
                        "DELETE /flight/{id}", ""
                ),
                "Reservation", ImmutableMap.of(
                        "GET /reservation", "",
                        "POST /reservation", "",
                        "GET /reservation/{id}", "",
                        "PUT /reservation/{id}", "",
                        "DELETE /reservation/{id}", ""
                )
        );
    }
}

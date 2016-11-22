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
                        "GET /destination", "Return list of all destinations.",
                        "POST /destination", "Crete new destination and return its id.",
                        "GET /destination/{id}", "Return destination with given id.",
                        "PUT /destination/{id}", "Update destination with given id.",
                        "DELETE /destination/{id}", "Delete destination with given id."
                ),
                "Flight", ImmutableMap.of(
                        "GET /flight", "Return list of all flights.",
                        "POST /flight", "Crete new flight and return its id.",
                        "GET /flight/{id}", "Return flight with given id.",
                        "PUT /flight/{id}", "Update flight with given id.",
                        "DELETE /flight/{id}", "Delete flight with given id."
                ),
                "Reservation", ImmutableMap.of(
                        "GET /reservation", "Return list of all reservations.",
                        "POST /reservation", "Crete new reservation and return it with id and password filled.",
                        "GET /reservation/{id}", "Return reservation with given id.",
                        "PUT /reservation/{id}", "Update reservation with given id.",
                        "DELETE /reservation/{id}", "Delete reservation with given id."
                )
        );
    }
}

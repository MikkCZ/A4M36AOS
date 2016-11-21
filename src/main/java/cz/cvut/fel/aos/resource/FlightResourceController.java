package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cz.cvut.fel.aos.resource.params.pagination.Pagination.fromHeaders;
import static cz.cvut.fel.aos.resource.params.sorting.OrderBy.fromXOrder;

@RestController
@RequestMapping("/flight")
public class FlightResourceController {

    @Autowired
    private FlightService flightService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<FlightEntity>> getAllFlights(
            @RequestHeader(value = "X-Order", required = false) String xOrder,
            @RequestHeader(value = "X-Base", required = false) Integer xBase,
            @RequestHeader(value = "X-Offset", defaultValue = "0") Integer xOffset
    ) {
        QueryParams queryParams = new QueryParams()
                .setOrderBy(fromXOrder(xOrder))
                .setPagination(fromHeaders(xBase, xOffset));
        Page<FlightEntity> results = flightService.getAll(queryParams);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count-records", results.getCount().toString());
        return new ResponseEntity(results.getResults(), httpHeaders, HttpStatus.OK);
    }

}

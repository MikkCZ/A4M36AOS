package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.sorting.TimeRangeFilter;
import cz.cvut.fel.aos.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestHeader(value = "X-Offset", defaultValue = "0") Integer xOffset,
            @RequestHeader(value = "X-Filter", required = false) String xFilter
    ) {
        QueryParams queryParams = new QueryParams()
                .setOrderBy(fromXOrder(xOrder))
                .setPagination(fromHeaders(xBase, xOffset));
        Page<FlightEntity> results = flightService.getAll(queryParams, TimeRangeFilter.fromXFilter(xFilter));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count-records", results.getCount().toString());
        return new ResponseEntity(results.getResults(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public FlightEntity getFlight(@PathVariable("id") int id) {
        return flightService.get(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public int createNewFlight(@RequestBody FlightEntity flightEntity) {
        return flightService.create(flightEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateFlight(@PathVariable("id") int id, @RequestBody FlightEntity flightEntity) {
        flightService.update(id, flightEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteFlight(@PathVariable("id") int id) {
        flightService.delete(id);
    }

}

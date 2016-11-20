package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cz.cvut.fel.aos.resource.params.pagination.Pagination.fromHeaders;
import static cz.cvut.fel.aos.resource.params.sorting.OrderBy.fromXOrder;

@RestController
@RequestMapping("/destination")
public class DestinationResourceController {

    @Autowired
    private DestinationService destinationService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<DestinationEntity> getAllDestinations(
            @RequestHeader(value = "X-Order", required = false) String xOrder,
            @RequestHeader(value = "X-Base", required = false) Integer xBase,
            @RequestHeader(value = "X-Offset", defaultValue = "0") Integer xOffset
    ) {
        QueryParams queryParams = new QueryParams()
                .setOrderBy(fromXOrder(xOrder))
                .setPagination(fromHeaders(xBase, xOffset));
        return destinationService.getAll(queryParams);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DestinationEntity getDestination(@PathVariable("id") int id) {
        return destinationService.get(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public int createNewDestination(@RequestBody DestinationEntity destinationEntity) {
        return destinationService.create(destinationEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateDestination(@PathVariable("id") int id, @RequestBody DestinationEntity destinationEntity) {
        destinationService.update(id, destinationEntity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDestination(@PathVariable("id") int id) {
        destinationService.delete(id);
    }
}

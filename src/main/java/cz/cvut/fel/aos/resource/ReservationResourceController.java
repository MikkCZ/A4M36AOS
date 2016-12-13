package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.entities.ReservationEntity;
import cz.cvut.fel.aos.exceptions.InvalidReservationOperationException;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.StringDto;
import cz.cvut.fel.aos.service.ReservationService;
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
@RequestMapping("/reservation")
public class ReservationResourceController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ReservationEntity>> getAllReservations(
            @RequestHeader(value = "X-Order", required = false) String xOrder,
            @RequestHeader(value = "X-Base", required = false) Integer xBase,
            @RequestHeader(value = "X-Offset", defaultValue = "0") Integer xOffset
    ) {
        QueryParams queryParams = new QueryParams()
                .setOrderBy(fromXOrder(xOrder))
                .setPagination(fromHeaders(xBase, xOffset));
        Page<ReservationEntity> results = reservationService.getAll(queryParams);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Count-records", results.getCount().toString());
        return new ResponseEntity<>(results.getResults(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ReservationEntity getReservation(
            @PathVariable("id") int id,
            @RequestHeader(value = "X-Password") String xPassword
    ) {
        return reservationService.getWithPassword(id, xPassword);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ReservationEntity createNewReservation(@RequestBody ReservationEntity reservationEntity) {
        reservationService.create(reservationEntity);
        return reservationEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateReservation(
            @PathVariable("id") int id,
            @RequestBody ReservationEntity reservationEntity,
            @RequestHeader(value = "X-Password") String xPassword
    ) {
        reservationService.updateWithPassword(id, reservationEntity, xPassword);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteReservation(@PathVariable("id") int id) {
        reservationService.delete(id);
    }

    @RequestMapping(value = "/{id}/payment", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payment(@PathVariable("id") int id) {
        reservationService.pay(id);
    }

    @RequestMapping(value = "/{id}/print", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public StringDto print(@PathVariable("id") int id) {
        // TODO: call print service here
        return new StringDto("not implemented yet");
    }

    @ExceptionHandler(value = InvalidReservationOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void invalidReservationOperation(InvalidReservationOperationException e) {
        // noop
    }
}

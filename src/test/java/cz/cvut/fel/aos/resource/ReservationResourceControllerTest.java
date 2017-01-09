package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationResourceControllerTest extends AbstractResourceControllerTest {

    @Autowired
    private ReservationService reservationServiceMock;

    @Before
    public void setUp() {
        Mockito.reset(reservationServiceMock);
    }

    @Test
    public void passesXPasswordToService() throws Exception {
        int id = 42;
        String password = "XXXL";
        mockMvc.perform(
                MockMvcRequestBuilders.get("/reservation/"+id)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Password", password)
        );
        Mockito.verify(reservationServiceMock).getWithPassword(id, password);
    }

    @Test
    public void testPrint() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/reservation/42/mail")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("test@example.com")
        ).andExpect(status().isNoContent());
    }

}
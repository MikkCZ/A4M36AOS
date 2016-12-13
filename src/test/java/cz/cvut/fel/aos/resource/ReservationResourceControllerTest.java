package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.resource.params.StringDto;
import cz.cvut.fel.aos.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

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
        String response = mockMvc.perform(
                MockMvcRequestBuilders.post("/reservation/42/print")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();
        StringDto stringDto = jsonMapper.readValue(response, StringDto.class);
        assertThat(stringDto.getValue(), not(isEmptyOrNullString()));
    }

}
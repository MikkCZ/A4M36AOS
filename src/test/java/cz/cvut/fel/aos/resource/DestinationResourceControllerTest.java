package cz.cvut.fel.aos.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.aos.config.RestConfig;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.sorting.Order;
import cz.cvut.fel.aos.resource.params.sorting.OrderBy;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.test.MockServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext
@ContextConfiguration(classes = {RestConfig.class, MockServiceConfig.class})
public class DestinationResourceControllerTest {

    @Autowired
    private DestinationService destinationServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(destinationServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getsAllDestinations() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/destination/")
                        .header("X-Order", "name:asc")
        );
        Mockito.verify(destinationServiceMock).getAll(new QueryParams().setOrderBy(new OrderBy("name", Order.ASC)));
    }

    @Test
    public void creates() throws Exception {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/destination/")
                        .content(objectMapper.writeValueAsString(entity))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        Mockito.verify(destinationServiceMock).create(entity);
    }

    @Test
    public void gets() throws Exception {
        DestinationEntity toReturn = DestinationEntity.builder().id(42).name("Prague").build();
        Mockito.when(destinationServiceMock.get(toReturn.getId())).thenReturn(toReturn);
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/destination/42"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Mockito.verify(destinationServiceMock).get(toReturn.getId());
        DestinationEntity returned = objectMapper.readValue(response, DestinationEntity.class);
        assertThat(returned.getName(), is(toReturn.getName()));
    }

}
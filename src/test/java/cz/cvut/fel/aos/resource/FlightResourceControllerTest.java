package cz.cvut.fel.aos.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ImmutableList;
import cz.cvut.fel.aos.config.RestConfig;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.entities.FlightEntity;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.pagination.Pagination;
import cz.cvut.fel.aos.resource.params.sorting.Order;
import cz.cvut.fel.aos.resource.params.sorting.OrderBy;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.service.FlightService;
import cz.cvut.fel.aos.test.MockServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext
@ContextConfiguration(classes = {RestConfig.class, MockServiceConfig.class})
public class FlightResourceControllerTest {

    @Autowired
    private FlightService flightServiceMock;

    @Autowired
    private DestinationService destinationServiceMock;

    private ObjectMapper jsonMapper = new ObjectMapper();

    private ObjectMapper xmlMapper = new XmlMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(flightServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getsAllFlightsWithOrder() throws Exception {
        Mockito.when(flightServiceMock.getAll(any())).thenReturn(new Page(ImmutableList.of(), 0L));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/flight/")
                        .header("X-Order", "name:asc")
        );
        Mockito.verify(flightServiceMock).getAll(new QueryParams().setOrderBy(new OrderBy("name", Order.ASC)));
    }

    @Test
    public void getsAllFlightsWithPagination() throws Exception {
        Page<FlightEntity> toReturn = new Page(
                ImmutableList.of(
                        FlightEntity.builder().name("OK42").from(
                                DestinationEntity.builder().id(44).name("Prague").build()
                        ).build())
                ,
                42L
        );
        QueryParams queryParams = new QueryParams().setPagination(new Pagination(1, 1));
        Mockito.when(flightServiceMock.getAll(queryParams)).thenReturn(toReturn);
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/flight/")
                        .header("X-Base", 1)
                        .header("X-Offset", 1)
        ).andReturn().getResponse();
        Mockito.verify(flightServiceMock).getAll(queryParams);
        List<FlightEntity> returned = jsonMapper.readValue(response.getContentAsString(), List.class);
        assertThat(returned, hasSize(1));
        assertThat(Long.valueOf(response.getHeader("X-Count-records")), is(42L));
    }

    @Test
    public void getsJson() throws Exception {
        FlightEntity toReturn = FlightEntity.builder().id(42).name("Prague").from(DestinationEntity.builder().id(44).name("Prague").build()).build();
        Mockito.when(flightServiceMock.get(toReturn.getId())).thenReturn(toReturn);
        Mockito.when(destinationServiceMock.get(44)).thenReturn(DestinationEntity.builder().id(44).name("Prague").build());
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/flight/42")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Mockito.verify(flightServiceMock).get(toReturn.getId());
        FlightEntity returned = jsonMapper.readValue(response, FlightEntity.class);
        assertThat(returned.getName(), is(toReturn.getName()));
        assertThat(returned.getFrom().getName(), is("Prague"));
    }

    @Test
    public void getsXml() throws Exception {
        FlightEntity toReturn = FlightEntity.builder().id(42).name("Prague").build();
        Mockito.when(flightServiceMock.get(toReturn.getId())).thenReturn(toReturn);
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/flight/42")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Mockito.verify(flightServiceMock).get(toReturn.getId());
        FlightEntity returned = xmlMapper.readValue(response, FlightEntity.class);
        assertThat(returned.getName(), is(toReturn.getName()));
    }

}
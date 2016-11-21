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
import cz.cvut.fel.aos.service.FlightService;
import cz.cvut.fel.aos.test.MockServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext
@ContextConfiguration(classes = {RestConfig.class, MockServiceConfig.class})
public class FlightResourceControllerTest {

    @Autowired
    private FlightService flightService;

    private ObjectMapper jsonMapper = new ObjectMapper();

    private ObjectMapper xmlMapper = new XmlMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(flightService);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getsAllFlightsWithOrder() throws Exception {
        Mockito.when(flightService.getAll(any())).thenReturn(new Page(ImmutableList.of(), 0L));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/flight/")
                        .header("X-Order", "name:asc")
        );
        Mockito.verify(flightService).getAll(new QueryParams().setOrderBy(new OrderBy("name", Order.ASC)));
    }

    @Test
    public void getsAllFlightsWithPagination() throws Exception {
        Page<FlightEntity> toReturn = new Page(
                ImmutableList.of(FlightEntity.builder().name("Prague").build()),
                42L
        );
        QueryParams queryParams = new QueryParams().setPagination(new Pagination(1, 1));
        Mockito.when(flightService.getAll(queryParams)).thenReturn(toReturn);
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/flight/")
                        .header("X-Base", 1)
                        .header("X-Offset", 1)
        ).andReturn().getResponse();
        Mockito.verify(flightService).getAll(queryParams);
        List<DestinationEntity> returned = jsonMapper.readValue(response.getContentAsString(), List.class);
        assertThat(returned, hasSize(1));
        assertThat(Long.valueOf(response.getHeader("X-Count-records")), is(42L));
    }

}
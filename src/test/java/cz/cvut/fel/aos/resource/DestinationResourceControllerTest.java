package cz.cvut.fel.aos.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ImmutableList;
import cz.cvut.fel.aos.config.RestConfig;
import cz.cvut.fel.aos.entities.DestinationEntity;
import cz.cvut.fel.aos.resource.pages.Page;
import cz.cvut.fel.aos.resource.params.QueryParams;
import cz.cvut.fel.aos.resource.params.pagination.Pagination;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext
@ContextConfiguration(classes = {RestConfig.class, MockServiceConfig.class})
public class DestinationResourceControllerTest {

    @Autowired
    private DestinationService destinationServiceMock;

    private ObjectMapper jsonMapper = new ObjectMapper();

    private ObjectMapper xmlMapper = new XmlMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        Mockito.reset(destinationServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getsAllDestinationsWithOrder() throws Exception {
        Mockito.when(destinationServiceMock.getAll(any())).thenReturn(new Page<>(ImmutableList.of(), 0L));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/destination/")
                        .header("X-Order", "name:asc")
        );
        Mockito.verify(destinationServiceMock).getAll(new QueryParams().setOrderBy(new OrderBy("name", Order.ASC)));
    }

    @Test
    public void getsAllDestinationsWithPagination() throws Exception {
        Page<DestinationEntity> toReturn = new Page<>(
                ImmutableList.of(DestinationEntity.builder().name("Prague").build()),
                42L
        );
        QueryParams queryParams = new QueryParams().setPagination(new Pagination(1, 1));
        Mockito.when(destinationServiceMock.getAll(queryParams)).thenReturn(toReturn);
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/destination/")
                        .header("X-Base", 1)
                        .header("X-Offset", 1)
        ).andReturn().getResponse();
        Mockito.verify(destinationServiceMock).getAll(queryParams);
        List<DestinationEntity> returned = jsonMapper.readValue(response.getContentAsString(), List.class);
        assertThat(returned, hasSize(1));
        assertThat(Long.valueOf(response.getHeader("X-Count-records")), is(42L));
    }

    @Test
    public void createsJson() throws Exception {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/destination/")
                        .content(jsonMapper.writeValueAsString(entity))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        Mockito.verify(destinationServiceMock).create(entity);
    }

    @Test
    public void createsXml() throws Exception {
        DestinationEntity entity = DestinationEntity.builder().name("Prague").build();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/destination/")
                        .content(xmlMapper.writeValueAsString(entity))
                        .contentType(MediaType.APPLICATION_XML)
        );
        Mockito.verify(destinationServiceMock).create(entity);
    }

    @Test
    public void getsJson() throws Exception {
        DestinationEntity toReturn = DestinationEntity.builder().id(42).name("Prague").build();
        Mockito.when(destinationServiceMock.get(toReturn.getId())).thenReturn(toReturn);
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/destination/42")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Mockito.verify(destinationServiceMock).get(toReturn.getId());
        DestinationEntity returned = jsonMapper.readValue(response, DestinationEntity.class);
        assertThat(returned.getName(), is(toReturn.getName()));
    }

    @Test
    public void getsXml() throws Exception {
        DestinationEntity toReturn = DestinationEntity.builder().id(42).name("Prague").build();
        Mockito.when(destinationServiceMock.get(toReturn.getId())).thenReturn(toReturn);
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/destination/42")
                .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Mockito.verify(destinationServiceMock).get(toReturn.getId());
        DestinationEntity returned = xmlMapper.readValue(response, DestinationEntity.class);
        assertThat(returned.getName(), is(toReturn.getName()));
    }

}
package cz.cvut.fel.aos.resource;

import cz.cvut.fel.aos.config.RestConfig;
import cz.cvut.fel.aos.service.DestinationService;
import cz.cvut.fel.aos.test.MockServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext
@ContextConfiguration(classes = {RestConfig.class, MockServiceConfig.class})
public class DestinationResourceControllerTest {

    @Autowired
    private DestinationService destinationServiceMock;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/destination/"));
        Mockito.verify(destinationServiceMock).getAll();
    }

}
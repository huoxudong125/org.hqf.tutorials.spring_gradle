import org.hqf.tutorials.spring.restapi.dao.CustomerDAO;
import org.hqf.tutorials.spring.restapi.po.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
//@ContextConfiguration(locations = {"classpath:testContext.xml", "classpath:exampleApplicationContext-web.xml"})
@WebAppConfiguration
public class CustomersControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(customerDAO);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private CustomerDAO customerDAO;

    //Add WebApplicationContext field here.

    //The setUp() method is omitted.

    @Test
    public void findAll_CustomersFound_ShouldReturnAllCustomers() throws Exception {
        Customer first = new Customer();
        first.setId(1L);
        first.setFirstName("Frank");

        Customer second = new Customer();
        second.setId(2L);
        second.setFirstName("Xudong");

        when(customerDAO.list()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[1].firstname", is("Frank")))
                .andExpect(jsonPath("$[1].id", is(2)));

//                .andExpect(jsonPath("$[1].firstName", is("Xudong")));

        verify(customerDAO, times(1)).list();
        verifyNoMoreInteractions(customerDAO);
    }
}


package com.example.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GetUserTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetUser() {
        // Arrange
        RestTemplate restTemplateMock = mock(RestTemplate.class);
        User user = new User();
        Address address = new Address();
        Geo geo = new Geo();
        Company company = new Company();
        user.setAddress(address);
        address.setGeo(geo);
        user.setCompany(company);
        //when(restTemplateMock.getForObject("http://127.0.0.1:8080/user/1", User.class)).thenReturn(user);
        when(restTemplateMock.getForObject("https://jsonplaceholder.typicode.com/users/1", User.class)).thenReturn(user);

        Model model = new ConcurrentModel();

        // Act
        ModelAndView modelAndView = new UserController().getUser(1, model);

        // Assert
        assertEquals("user", modelAndView.getViewName());
        assertEquals(user, modelAndView.getModelMap().get("user1"));
        assertEquals(address, modelAndView.getModelMap().get("address1"));
        assertEquals(geo, modelAndView.getModelMap().get("geo1"));
        assertEquals(company, modelAndView.getModelMap().get("company1"));
    }
}



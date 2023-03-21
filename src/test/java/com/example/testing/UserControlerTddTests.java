package com.example.testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControlerTddTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void showMainPage() throws Exception {
        ResponseEntity<User> userControllerResponseEntity = testRestTemplate.getForEntity("/main", User.class);
        assertThat(userControllerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void getUser_should_return_http_404_given_invalid_userID() throws Exception {

        ResponseEntity<User> carResponseEntity = testRestTemplate.getForEntity("/users/0", User.class);
        assertThat(carResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

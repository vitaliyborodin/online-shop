package com.vborodin.onlineshop.userservice.user;

import com.vborodin.onlineshop.userservice.UserServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {UserServiceApplication.class})
public class UserControllerTest {
    private static final String REQUEST_MAPPING = "/users/";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void authenticationCheck() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "some_user", password = "some_user", authorities = {"SOME_ROLE"})
    public void authorizationCheck() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void findAll() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)))
                .andExpect(jsonPath("$[0].login").value("admin"))
                .andExpect(jsonPath("$[1].login").value("user"))
                .andExpect(jsonPath("$[2].login").value("admin2"))
                .andExpect(jsonPath("$[3].login").value("manager"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void save() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)));

        this.mvc.perform(post(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("    {\n" +
                        "        \"login\": \"q1w3e2r4ty51000\",\n" +
                        "        \"email\": \"q1w3e2r4ty51000@asd.test\",\n" +
                        "        \"firstName\": \"q1w3e2r4ty51000\",\n" +
                        "        \"lastName\": \"q1w3e2r4ty51000\",\n" +
                        "        \"address\": \"\",\n" +
                        "        \"role\": \"ADMIN\",\n" +
                        "        \"active\": true,\n" +
                        "        \"password\": \"qwerty\",\n" +
                        "        \"status\": \"ACTIVE\"\n" +
                        "    }"))
                .andExpect(status().isOk());

        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void findById() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING + "1000000")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("admin"));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = {"ADMIN"})
    public void findByNonExistentId() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING + "999999999")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Entity not found")));
    }
}
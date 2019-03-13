package com.vborodin.onlineshop.productservice.product;

import com.vborodin.onlineshop.productservice.ProductServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductServiceApplication.class})
public class ProductControllerTest {
    private static final String REQUEST_MAPPING = "/products/";
    private static final String IMAGE_PNG = "image/png";

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
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void findAll() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(2)))
                .andExpect(jsonPath("$.content[0].name").value("Nokia 3310"))
                .andExpect(jsonPath("$.content[1].name").value("iPhone X"));
    }

    @Test
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void save() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(2)));

        this.mvc.perform(post(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{ \"name\": \"TEST NAME\", \"description\": \"TEST DESCRIPTION\", \"price\": 100, \"quantity\": 100, \"image\": null, \"catalog\": { \"id\": 2, \"name\": null, \"description\": null, \"parent\": null } }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("TEST NAME")));

        this.mvc.perform(get(REQUEST_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void findById() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING + "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void findByNonexistentId() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING + "1000000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Entity not found")));
    }

    @Test
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void findByCatalogId() throws Exception {
        this.mvc.perform(get(REQUEST_MAPPING + "search").param("catalogId", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(1)))
                .andExpect(jsonPath("$.content[0].id", is(1)));
    }

    @Test
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void uploadFile() throws Exception {
        MockMultipartFile pngFile = new MockMultipartFile("file", "test_image.png", IMAGE_PNG, "test image content".getBytes());

        this.mvc.perform(MockMvcRequestBuilders.multipart("/products/1/image")
                .file(pngFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is(IMAGE_PNG)));
    }

    @Test
    @WithMockUser(username = "manager", password = "manager", authorities = {"MANAGER"})
    public void uploadFileForNonexistentProduct() throws Exception {
        MockMultipartFile pngFile = new MockMultipartFile("file", "test_image.png", IMAGE_PNG, "test image content".getBytes());

        this.mvc.perform(MockMvcRequestBuilders.multipart("/products/1000000/image")
                .file(pngFile))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Entity not found")));
    }
}

package swa.eshop.user.rest;

import com.github.javafaker.Faker;
import swa.eshop.user.model.User;
import swa.eshop.user.controller.UserController;
import swa.eshop.user.service.UserService;
import swa.eshop.user.util.Environment;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

public class UserControllerTests {

    ObjectMapper objectMapper;
    MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Faker faker = new Faker();

    private User generateUser() {
        return new User(
                faker.name().username(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                faker.address().fullAddress()
        );
    }

    void setupObjectMapper() {
        this.objectMapper = Environment.getObjectMapper();
    }

    String toJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    <T> T readValue(MvcResult result, Class<T> targetType) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsByteArray(), targetType);
    }

    <T> T readValue(MvcResult result, TypeReference<T> targetType) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsByteArray(), targetType);
    }

    void verifyLocationEquals(String expectedPath, MvcResult result) {
        final String locationHeader = result.getResponse().getHeader(HttpHeaders.LOCATION);
        assertEquals("http://localhost" + expectedPath, locationHeader);
    }

    // TESTS SECTION

    @Before
    public void setUpController() {
        MockitoAnnotations.initMocks(this);
        setupObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setMessageConverters(Environment.createDefaultMessageConverter(), Environment.createStringEncodingMessageConverter())
                .build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsers_returnsAllUsers() throws Exception {
        final List<User> users = IntStream.range(0, 5).mapToObj(i -> generateUser()).collect(Collectors.toList());
        when(userService.getUsers()).thenReturn(users);
        final MvcResult mvcResult = mockMvc.perform(get("/user")).andReturn();
        final List<User> result = readValue(mvcResult, new TypeReference<List<User>>() {});
        assertNotNull(result);
        assertEquals(users.size(), result.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getId(), result.get(i).getId());
            assertEquals(users.get(i).getFirstName(), result.get(i).getFirstName());
            assertEquals(users.get(i).getLastName(), result.get(i).getLastName());
            assertEquals(users.get(i).getEmail(), result.get(i).getEmail());
            assertEquals(users.get(i).getAddress(), result.get(i).getAddress());
        }
    }

    @Test
    public void getUserById_returnsUser_withMatchingId() throws Exception {
        final User user = generateUser();
        user.setId(111L);
        when(userService.getUserById(user.getId())).thenReturn(Optional.of(user));
        final MvcResult mvcResult = mockMvc.perform(get("/user/" + user.getId())).andReturn();
        final User result = readValue(mvcResult, User.class);
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getAddress(), result.getAddress());
    }
}

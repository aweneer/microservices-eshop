package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import swa.eshop.basket.controller.BasketController;
import swa.eshop.basket.model.Basket;
import swa.eshop.basket.service.BasketService;
import util.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class BasketControllerTest {

    ObjectMapper objectMapper;
    MockMvc mockMvc;

    @Mock
    private BasketService basketService;

    @InjectMocks
    private BasketController basketController;

    private long counter = 1L;

    private Basket generateBasket() {
        Basket b = new Basket();
        b.setId(counter);
        b.setUserId(counter+200L);
        Map<Long, Integer> items = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            items.put(Long.parseLong(String.valueOf(i * counter++)), i * i + 1);
        }
        b.setItems(items);
        System.out.println(b);
        return b;
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
        this.mockMvc = MockMvcBuilders.standaloneSetup(basketController)
                .setMessageConverters(Environment.createDefaultMessageConverter(), Environment.createStringEncodingMessageConverter())
                .build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getBaskets_returnsAllBaskets() throws Exception {
        final List<Basket> baskets = IntStream.range(0, 5).mapToObj(i -> generateBasket()).collect(Collectors.toList());
        when(basketService.getBaskets()).thenReturn(baskets);
        final MvcResult mvcResult = mockMvc.perform(get("/basket")).andReturn();
        final List<Basket> result = readValue(mvcResult, new TypeReference<List<Basket>>() {});
        assertNotNull(result);
        assertEquals(baskets.size(), result.size());
        for (int i = 0; i < baskets.size(); i++) {
            assertEquals(baskets.get(i).getId(), result.get(i).getId());
            assertEquals(baskets.get(i).getUserId(), result.get(i).getUserId());
            assertEquals(baskets.get(i).getItems(), result.get(i).getItems());
        }
    }

    @Test
    public void getBasketById_returnsUser_withMatchingId() throws Exception {
        final Basket basket = generateBasket();
        basket.setId(111L);
        when(basketService.getBasketById(basket.getId())).thenReturn(Optional.of(basket));
        final MvcResult mvcResult = mockMvc.perform(get("/basket/" + basket.getId())).andReturn();
        final Basket result = readValue(mvcResult, Basket.class);
        assertNotNull(result);
        assertEquals(basket.getId(), result.getId());
        assertEquals(basket.getUserId(), result.getUserId());
        assertEquals(basket.getItems(), result.getItems());

    }
}

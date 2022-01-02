package swa.eshop.basket.controller;

import swa.eshop.basket.model.Basket;
import swa.eshop.basket.service.BasketService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/basket")
public class BasketController {

    final Logger logger = Logger.getLogger(BasketController.class.getName());

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BasketService basketService;

    // CREATE
    @PostMapping(path = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Basket> createUser(@RequestBody Basket basket) {
        logger.log(Level.INFO, "Basket created:\n" + basket.toString());
        return new ResponseEntity<>(basketService.createBasket(basket), HttpStatus.CREATED);
    }

    // READ
    @GetMapping(path = "/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable Long id) {
        Optional<Basket> basket = basketService.getBasketById(id);
        if (!basket.isPresent()) {
            logger.log(Level.INFO, "Basket read. No basket with said ID found! ID = " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "Basket read:\n" + basket.toString());
        return new ResponseEntity<>(basket.get(), HttpStatus.FOUND);
    }

    // READ
    @GetMapping
    public ResponseEntity<Iterable<Basket>> getBaskets() {
        if (!basketService.getBaskets().iterator().hasNext()) {
            System.out.println("Basket read, multiple: No baskets found");
        }
        logger.log(Level.INFO, "Basket read, multiple:\n" + basketService.toString());
        return new ResponseEntity<>(basketService.getBaskets(), HttpStatus.FOUND);
    }

    // UPDATE
    @PutMapping(value = "/", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Basket> updateBasket(@RequestBody Basket basket) {
        Optional<Basket> optionalBasket = basketService.getBasketById(basket.getId());
        if (!optionalBasket.isPresent()) {
            logger.log(Level.INFO, "Basket update: No such basket found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "Basket update\n" + optionalBasket.get() + "\n=== Basket updated to values:\n" + basket.toString());
        return new ResponseEntity<>(basketService.updateBasket(basket), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Basket> deleteBasket(@PathVariable Long id) {
        Optional<Basket> basket = basketService.getBasketById(id);
        if (!basket.isPresent()) {
            logger.log(Level.INFO, "Basket delete: No basket with such ID found! ID = " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "Basket delete:\n" + basket.toString());
        return new ResponseEntity<>(basketService.deleteBasketById(id), HttpStatus.OK);
    }

    // ADVANCED

    // READ
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<Basket> getBasketByUserId(@PathVariable Long id) {
        Optional<Basket> basket = basketService.getBasketByUserId(id);
        if (!basket.isPresent()) {
            logger.log(Level.INFO, "Basket read, by User ID: No such basket found! ID = " + id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        logger.log(Level.INFO, "Basket read, by User ID:" + basket.get().toString());
        return new ResponseEntity<>(basket.get(), HttpStatus.FOUND);
    }

}

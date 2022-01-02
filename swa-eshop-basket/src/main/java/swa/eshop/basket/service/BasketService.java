package swa.eshop.basket.service;

import swa.eshop.basket.model.Basket;
import swa.eshop.basket.repository.BasketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    // When User logs in
    public Basket createBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public Iterable<Basket> createBaskets(Iterable<Basket> baskets) {
        return basketRepository.saveAll(baskets);
    }


    public Optional<Basket> getBasketById(Long id) {
        return basketRepository.findById(id);
    }

    public Optional<Basket> getBasketByUserId(Long id) {
        return null;
        //return basketRepository.findByUserId(id);
    }

    public Iterable<Basket> getBaskets() {
        return basketRepository.findAll();
    }

    public Basket updateBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    // When User logs out
    public Basket deleteBasket(Basket basket) {
        if (!basketRepository.existsById(basket.getId())) return null;
        Basket foundBasket = basketRepository.findById(basket.getId()).get();
        basketRepository.delete(foundBasket);
        return foundBasket;
    }

    public Basket deleteBasketById(Long id) {
        if (!basketRepository.existsById(id)) return null;
        Basket basket = basketRepository.findById(id).get();
        basketRepository.delete(basket);
        return basket;
    }

    // When User requests emptying the basket
    public Basket emptyBasket(Basket basket) {
        return null;
        //return basketRepository.emptyBasket();
    }




}

package swa.eshop.basket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BasketServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(BasketServiceApp.class, args);
    }
}

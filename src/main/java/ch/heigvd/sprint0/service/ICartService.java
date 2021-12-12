package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Cart;

import java.util.Optional;

public interface ICartService {
    Optional<Cart> findById(String idUser);
    void save(Cart cart);
}

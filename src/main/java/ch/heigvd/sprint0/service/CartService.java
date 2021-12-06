package ch.heigvd.sprint0.service;

import ch.heigvd.sprint0.model.Cart;
import ch.heigvd.sprint0.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {
    final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> findById(String idUser) {
        return cartRepository.findById(idUser);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}

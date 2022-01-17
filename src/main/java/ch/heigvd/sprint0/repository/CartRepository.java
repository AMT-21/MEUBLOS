package ch.heigvd.sprint0.repository;

import ch.heigvd.sprint0.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, String> {
}
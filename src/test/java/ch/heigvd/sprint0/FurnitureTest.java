package test;

import com.example.sprint0.model.Furniture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FurnitureTest {

    Furniture furniture;

    @BeforeAll
    void testClassExist(){
        try {
            Class.forName("com.example.sprint0.model.Furniture");
        } catch (ClassNotFoundException e) {
            Assertions.fail("Manque la classe Furniture");
        }
    }

    @Test
    void testContent() {
        System.out.println("Crée le meuble et vérifie que son contenu existe");
        Furniture f = new Furniture("Meuble", 50);
        Assertions.assertEquals(50, f.getPrice(), "Erreur : pas le bon prix");
        Assertions.assertEquals("Meuble", f.getName(), "Erreur : pas le bon nom");
    }
}
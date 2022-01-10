package ch.heigvd.sprint0;

import org.flywaydb.core.Flyway;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sprint0ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Flyway flyway;

    @Value("${tests.admin.password}")
    private String adminPassword;

    @BeforeAll
    public void init() { // Reset database content
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Order(1)
    void displayArticlesInIndex() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(containsString("Un meuble du grenier")),
                        content().string(containsString("Table en marbre")),
                        content().string(containsString("Armoire style &quot;Louis XVI&quot;")));
    }

    @Test
    @Order(2)
    void displayAllArticlesInShop() throws Exception {
        mvc.perform(get("/shop"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(containsString("Un meuble du grenier")),
                        content().string(containsString("Table en marbre")),
                        content().string(containsString("Armoire style &quot;Louis XVI&quot;")));
    }


    @Test
    @Order(3)
    void displayFilteredArticlesInShop() throws Exception {
        mvc.perform(get("/shop?namecategory=Table"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(CoreMatchers.not(containsString("Un meuble du grenier"))),
                        content().string(containsString("Table en marbre")),
                        content().string(CoreMatchers.not(containsString("Armoire style &quot;Louis XVI&quot;"))));
    }

    @Test
    @Order(4)
    void displayDetailArticle() throws Exception {
        mvc.perform(get("/shop/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(containsString("Un meuble du grenier")),
                        content().string(containsString("Il était dans mon grenier pendant des années")),
                        content().string(containsString("Ajouter au panier")));
    }

    @Test
    void addArticle() throws Exception {
        mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", "0bQkghqoy-uKplp6#ywm"))
                .andDo(print());

        // faire une requête pour créer un article avec le token récupéré depuis le login puis vérifier que le nouvel article est bien ajouté
    }

}

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import javax.servlet.http.Cookie;
import java.util.LinkedList;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Order(5)
    void addArticle() throws Exception {
        // Récupérer le token admin
        MvcResult result = mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", adminPassword))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("tokenJWT");

        // Créer l'article
        mvc.perform(post("/admin/article")
        .cookie(cookie)
        .param("name", "newTestProduct")
        .param("description", "Test description"))
                .andDo(print());

        // Vérifier que le nouvel article est bien présent
        mvc.perform(get("/admin")
                .cookie(cookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(containsString("newTestProduct")));
    }

    @Test
    @Order(6)
    void addArticleWithSameDescription() throws Exception {
        // Récupérer le token admin
        MvcResult result = mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", adminPassword))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("tokenJWT");

        // Créer l'article avec une même description, et vérifier que le message d'erreur soit levé
        mvc.perform(post("/admin/article")
                .cookie(cookie)
                .param("name", "anotherNewTestProduct")
                .param("description", "Il était dans mon grenier pendant des années"))
                .andDo(print())
                .andExpectAll(
                        content().string(containsString("Cette description d&#39;article est déjà utilisée par l&#39;article Un meuble du grenier")));

    }

    @Test
    @Order(7)
    void modifyArticle() throws Exception {
        // Récupérer le token admin
        MvcResult result = mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", adminPassword))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("tokenJWT");

        // Modifier l'article
        mvc.perform(post("/admin/article?id=4")
                .cookie(cookie)
                .param("name", "newTestProductModified")
                .param("description", "Test description"))
                .andDo(print());

        // Vérifier que l'article modifié est bien présent
        mvc.perform(get("/admin")
                .cookie(cookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(containsString("newTestProductModified")));

    }

    @Test
    @Order(8)
    void deleteArticle() throws Exception {
        // Récupérer le token admin
        MvcResult result = mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", adminPassword))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("tokenJWT");

        // Supprimer l'article
        mvc.perform(get("/admin/article/delete")
                .cookie(cookie)
                .param("id", "4"))
                .andDo(print());

        // Vérifier que l'article modifié est bien supprimé
        mvc.perform(get("/admin")
                .cookie(cookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(not(containsString("newTestProductModified"))));

    }

    @Test
    @Order(9)
    void addCategory() throws Exception {
        // Récupérer le token admin
        MvcResult result = mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", adminPassword))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("tokenJWT");

        // Créer une nouvelle catégorie
        mvc.perform(post("/admin/categories")
                .cookie(cookie)
                .param("nameCategory", "newTestCategory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(containsString("newTestCategory")));

    }

    @Test
    @Order(10)
    void deleteCategory() throws Exception {
        // Récupérer le token admin
        MvcResult result = mvc.perform(post("/login")
                .sessionAttr("articles_in_cart", new LinkedList<>())
                .param("inputLogin", "meublos")
                .param("inputPassword", adminPassword))
                .andReturn();

        Cookie cookie = result.getResponse().getCookie("tokenJWT");

        // Supprimer la catégorie précédemment créée
        mvc.perform(get("/admin/categories/confirmDeletion?id=newTestCategory")
                .cookie(cookie))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(
                        content().string(not(containsString("newTestCategory"))));

    }

}

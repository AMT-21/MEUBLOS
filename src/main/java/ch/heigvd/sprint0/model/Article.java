package ch.heigvd.sprint0.model;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.util.Set;
//The @Entity annotation specifies that the class is an entity and is mapped to a database table while the @Table
// annotation specifies the name of the database table to be used for mapping.

@Entity
@Table(name="Meuble")
public class Article {

    /* Primary key, unique identifier
     */
    //The primary key of an entity is specified with the @Id annotation. The @GeneratedValue gives a strategy for
    // generating the values of primary keys.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /* article name
     */
    @Column(name = "nom", unique = true)
    private String name;

    /* unique description
     */
    @Column(name = "description", columnDefinition = "text")
    private String description;

    /* article price
     */
    @Column(name = "prixvente", columnDefinition = "DECIMAL(6,2)")
    private double price;

    /* article material
     */
    @Column(name = "quantite")
    private int stock;

    @OneToMany(mappedBy = "ids.article")
    private Set<Cart_Article> cart_article_list;

    @OneToMany(mappedBy = "article")
    private Set<Article_Category> article_category_list;

    public Article(){

    }

    public Article(Integer id, String name, String description, int price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrixVente(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

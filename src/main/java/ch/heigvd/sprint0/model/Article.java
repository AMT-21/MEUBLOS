package ch.heigvd.sprint0.model;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    /* article name
     */
    @Column(name = "nom", unique = true)
    private String nom;

    /* unique description
     */
    @Column(name = "description", unique = true)
    private String description;

    /* article price
     */
    @Column(name = "prixvente", unique = true)
    private double prixVente;

    /* article material
     */
    @Column(name = "quantite", unique = true)
    private int quantite;


    public Article(){

    }

    public Article(Integer id, String nom, String description, int prixVente, int quantite)
    {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prixVente = prixVente;
        this.quantite = quantite;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }
}

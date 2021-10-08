package ch.heigvd.sprint0.model;

import javax.persistence.*;

@Entity
public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;

    public Pays() { }

    public Pays(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}

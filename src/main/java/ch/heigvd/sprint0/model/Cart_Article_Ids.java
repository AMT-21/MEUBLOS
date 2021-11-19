package ch.heigvd.sprint0.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


// Composite key :
// https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#identifiers-composite
@Embeddable
public class Cart_Article_Ids implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idUserPanier", referencedColumnName = "idUser")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "idMeuble", referencedColumnName = "id")
    private Article article;

    public Cart_Article_Ids(Cart cart, Article article) {
        this.cart = cart;
        this.article = article;
    }

    public Cart_Article_Ids() { }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Cart_Article_Ids ids = (Cart_Article_Ids) o;
        return Objects.equals( cart, ids.cart ) &&
                Objects.equals( article, ids.article );
    }

    @Override
    public int hashCode() {
        return Objects.hash( cart, article );
    }
}

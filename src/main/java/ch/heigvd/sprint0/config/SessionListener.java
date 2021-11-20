package ch.heigvd.sprint0.config;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedList;

@Component
public class SessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent event) {
        // Initialisation du contenu de la session.
        HttpSession session = event.getSession();
        session.setAttribute("articles_in_cart", new LinkedList<>());
    }
}

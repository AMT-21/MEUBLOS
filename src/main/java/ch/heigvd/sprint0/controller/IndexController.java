package ch.heigvd.sprint0.controller;

import ch.heigvd.sprint0.model.Pays;
import ch.heigvd.sprint0.model.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class IndexController {

    @Autowired
    PaysRepository repository;

    @GetMapping("/")
    public String index(/*@RequestParam(name="name", required=false, defaultValue="World") String name,*/ Model model) {
        //repository.save(new Pays(10, "Test"));
        //Iterable<Pays> iterator = repository.findAll();

        /*System.out.println("All country items: ");
        iterator.forEach(pays -> System.out.println(pays));*/

        // converti l'itertator en List
        /*List<Pays> actualList = StreamSupport
                .stream(iterator.spliterator(), false)
                .collect(Collectors.toList());*/
        List<Pays> allCountries = repository.listAllCountries();
        Pays switzerland = repository.findByid(1);

        // Lie la List à la view
        model.addAttribute("countries", allCountries);
        return "index";
    }
}
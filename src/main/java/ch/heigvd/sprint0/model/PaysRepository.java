package ch.heigvd.sprint0.model;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PaysRepository extends CrudRepository<Pays, Integer> {

    @Query("SELECT e FROM Pays e WHERE e.id = :id")
    public Pays findByid(@Param("id") Integer id);

    @Query("SELECT e FROM Pays e")
    public List<Pays> listAllCountries();
}

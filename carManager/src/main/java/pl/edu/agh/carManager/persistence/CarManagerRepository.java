package pl.edu.agh.carManager.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarManagerRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c from Car c")
    List<Car> getCars();
}

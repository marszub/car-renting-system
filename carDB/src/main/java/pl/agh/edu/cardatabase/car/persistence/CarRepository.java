package pl.agh.edu.cardatabase.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c from Car c")
    List<Car> getCars();

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("UPDATE Car c SET c.latitude = ?2, c.longitude = ?3 WHERE c.id = ?1")
    void updateCarLocation(final Integer id, final double latitude, final double longitude);
}

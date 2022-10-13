package pl.agh.edu.cardatabase.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c from Car c")
    List<Car> getCars();

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("UPDATE Car c SET c.latitude = :latitude, c.longitude = :longitude WHERE c.id = :id")
    void updateCarLocation(@Param("id") final Integer id,
                           @Param("latitude") final double latitude,
                           @Param("longitude") final double longitude);
}

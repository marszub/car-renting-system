package pl.agh.edu.cardatabase.car.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("SELECT c from Car c ")
    List<Car> getCars();

    @Query("SELECT DISTINCT c.carCategoryId FROM Car c WHERE c.id = :id")
    Optional<Integer> getCarCategoryId(@Param("id") final Integer id);

    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query("UPDATE Car c SET c.latitude = :latitude, c.longitude = :longitude WHERE c.id = :id")
    void updateCarLocation(@Param("id") final Integer id,
                           @Param("latitude") final double latitude,
                           @Param("longitude") final double longitude);
}

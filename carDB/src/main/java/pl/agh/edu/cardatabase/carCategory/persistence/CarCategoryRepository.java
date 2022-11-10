package pl.agh.edu.cardatabase.carCategory.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> {
    @Query("SELECT c from CarCategory c")
    List<CarCategory> getCarCategories();

    @Query("SELECT c from CarCategory c WHERE c.id = :id")
    Optional<CarCategory> getCarCategoryById(@Param("id") Integer id);
}

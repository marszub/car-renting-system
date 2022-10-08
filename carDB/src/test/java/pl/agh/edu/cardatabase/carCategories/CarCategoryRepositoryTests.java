package pl.agh.edu.cardatabase.carCategories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class CarCategoryRepositoryTests {
    @Autowired
    private CarCategoryRepository carCategoryRepository;

    private final String carCategoryName1 = "Category";
    private final String carCategoryName2 = "Category2";

    @Test
    @Transactional
    void checkIfAfterSavingACarCategoryItIsAcquiredInDBRequestTakingAllCategories() {
        final CarCategory category = carCategoryRepository.save(new CarCategory(carCategoryName1));
        final List<CarCategory> list = carCategoryRepository.getCarCategories();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0)).isEqualTo(category);
    }

    @Test
    @Transactional
    void checkIfSavingMultipleCarCategoriesAllowsToGetAllOfThem() {
        final CarCategory category = carCategoryRepository.save(new CarCategory(carCategoryName1));
        final CarCategory category2 = carCategoryRepository.save(new CarCategory(carCategoryName2));
        final List<CarCategory> list = carCategoryRepository.getCarCategories();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(category);
        assertThat(list.get(1)).isEqualTo(category2);
    }

    @Test
    @Transactional
    void checkIfGettingNotExistingCarCategoryReturnsEmptyOptional() {
        final Optional<CarCategory> optional = carCategoryRepository.getCarCategoryById(1);
        assertThat(optional.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    void checkIfGettingExistingCarCategoryReturnsProperOptional() {
        final CarCategory category = carCategoryRepository.save(new CarCategory(carCategoryName1));
        final Optional<CarCategory> optional = carCategoryRepository.getCarCategoryById(category.getId());
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isEqualTo(category);
    }
}

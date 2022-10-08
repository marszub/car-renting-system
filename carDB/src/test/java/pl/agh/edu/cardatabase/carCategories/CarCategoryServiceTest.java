package pl.agh.edu.cardatabase.carCategories;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryData;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryInputData;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryList;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;
import pl.agh.edu.cardatabase.carCategory.service.CarCategoryService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CarCategoryServiceTest {
    @Mock
    private CarCategoryRepository carCategoryRepository;

    private final CarCategory carCategory = new CarCategory("CarCategory");
    private final CarCategory carCategory2 = new CarCategory("CarCategory2");

    @Test
    @Transactional
    void checkCorrectReturnAfterCreateCarCategory() {
        when(carCategoryRepository.save(any())).thenReturn(carCategory);
        final CarCategoryService carCategoryService = new CarCategoryService(carCategoryRepository);
        final CarCategoryData carCategoryData = carCategoryService.create(new CarCategoryInputData("Name"));
        assertThat(carCategoryData.carCategoryName()).isEqualTo(carCategory.getName());
    }

    @Test
    @Transactional
    void checkIfEmptyCarCategoriesListIsReturnedProperly() {
        when(carCategoryRepository.getCarCategories()).thenReturn(new LinkedList<>());
        final CarCategoryService carCategoryService = new CarCategoryService(carCategoryRepository);
        final CarCategoryList resultList = carCategoryService.getCategories();
        assertThat(resultList.carCategories().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    void checkIfNotEmptyCarCategoriesListIsReturnedProperly() {
        carCategory.setId(0);
        carCategory.setId(1);
        final List<CarCategory> list = Arrays.asList(carCategory, carCategory2);
        when(carCategoryRepository.getCarCategories()).thenReturn(list);
        final CarCategoryService carCategoryService = new CarCategoryService(carCategoryRepository);
        CarCategoryList resultList = carCategoryService.getCategories();
        assertThat(resultList.carCategories().size()).isEqualTo(2);
        assertThat(resultList.carCategories().get(0).carCategoryName()).isEqualTo(carCategory.getName());
        assertThat(resultList.carCategories().get(1).carCategoryName()).isEqualTo(carCategory2.getName());
    }
}

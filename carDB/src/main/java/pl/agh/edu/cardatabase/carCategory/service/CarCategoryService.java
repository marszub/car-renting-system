package pl.agh.edu.cardatabase.carCategory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryData;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryInputData;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryList;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;

import java.util.List;


@Service
public class CarCategoryService {
    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryService(final CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    @Transactional
    public CarCategoryData create(final CarCategoryInputData data) {
        final CarCategory carCategory = carCategoryRepository.save(new CarCategory(data.categoryName()));
        return new CarCategoryData(carCategory);
    }

    public CarCategoryList getCategories() {
        final List<CarCategory> list = carCategoryRepository.getCarCategories();
        return new CarCategoryList(list.stream().map(CarCategoryData::new).toList());
    }
}

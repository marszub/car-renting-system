package pl.agh.edu.cardatabase.carCategory.persistence;

import pl.agh.edu.cardatabase.car.persistence.Car;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carCategories")
public class CarCategory {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "carCategory", cascade = CascadeType.REMOVE)
    private List<Car> carList;

    public CarCategory() {
    }

    public CarCategory(final String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CarCategory carCategory = (CarCategory) o;
        return Objects.equals(id, carCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

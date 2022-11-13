package pl.agh.edu.cardatabase.car.persistence;

import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {
    private final static double DEFAULT_LATITUDE = 49.818069;
    private final static double DEFAULT_LONGITUDE = 19.041309;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Integer carCategoryId;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_category_link", nullable = false)
    private CarCategory carCategory;

    public Car() {
    }

    public Car(final String name, final CarCategory category) {
        this.name = name;
        this.latitude = DEFAULT_LATITUDE;
        this.longitude = DEFAULT_LONGITUDE;
        this.carCategoryId = category.getId();
        this.carCategory = category;
    }

    public CarCategory getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(final CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCarCategoryId() {
        return carCategoryId;
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
        final Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

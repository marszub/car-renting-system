package pl.edu.agh.carManager.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Integer carDB_id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String auth_token;//given by admin to verify the car

    public Car(){}

    public Car(final Integer carDB_id, final String auth_token) {
        this.carDB_id = carDB_id;
        this.latitude = DEFAULT_LATITUDE;
        this.longitude = DEFAULT_LONGITUDE;
        this.auth_token = auth_token;
    }

    public Integer getCarDB_id() {
        return carDB_id;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public String getAuth_token() {
        return auth_token;
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

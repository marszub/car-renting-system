package pl.edu.agh.carManager.carManager.service;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.stereotype.Component;
import pl.edu.agh.carManager.carManager.error.DatabaseConflictError;
import pl.edu.agh.carManager.carManager.persistence.Car;
import pl.edu.agh.carManager.carManager.persistence.CarRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CarsMap {
    private final ConcurrentHashMap<String, Integer> tokenToId = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, String> idToToken = new ConcurrentHashMap<>();
    private final CarRepository carRepository;

    public CarsMap(CarRepository carRepository) {
        this.carRepository = carRepository;
        createMap();
    }

    public void addCar(Integer id, String token) throws DatabaseConflictError {
        try {
            carRepository.save(new Car(id, token));
        } catch (OptimisticEntityLockException e) {
            System.out.println("Conflict in a database");
            throw new DatabaseConflictError();
        }
        tokenToId.put(token, id);
        idToToken.put(id, token);
        printMap();
    }

    public boolean isCarRegistered(String token) {
        return tokenToId.containsKey(token);
    }

    public Integer gerCarsId(final String token) {
        if(!tokenToId.containsKey(token)) {
            return -1;
        }
        return tokenToId.get(token);
    }

    public String getCarsToken(final Integer id) {
        if(!idToToken.containsKey(id)) {
            return null;
        }
        return idToToken.get(id);
    }

    private void createMap() {
        List<Car> carsList = carRepository.getAllCars();
        carsList.forEach(car -> {
            tokenToId.put(car.getToken(), car.getId());
            idToToken.put(car.getId(), car.getToken());
        });
        printMap();
    }

    //TODO DELETE
    private void printMap() {
        System.out.println("--------------------------------------------------------\n\n\n");
        System.out.println(tokenToId);
        System.out.println("---------------------------------------------\n\n\n");
        System.out.println(tokenToId);
        System.out.println("--------------------------------------------------------\n\n\n");
    }
}

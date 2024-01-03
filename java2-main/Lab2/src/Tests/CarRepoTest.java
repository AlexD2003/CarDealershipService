package Tests;

import Domain.Car;
import Repositories.CarRepository;
import Repositories.MemoryRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarRepoTest {
    Car car = new Car(1, "BMW", 2003);
    Car car2 = new Car(2, "Audi", 2005);

    @Test
    public void CarRepoTestStart() {
        CarRepository carRepo = new CarRepository();
        carRepo.add(car);
        carRepo.add(car2);
        carRepo.updateAtId(1, car2);
        assertEquals(car2, carRepo.getById(1));
        carRepo.deleteAtId(1);
        assertEquals(null, carRepo.getById(1));
        assertEquals(car2, carRepo.getById(2));


    }
}

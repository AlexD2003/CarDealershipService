package Tests;

import Domain.Car;
import Repositories.CarRepository;
import ServiceImpl.GenericService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarServiceTest {
    CarRepository carRepo = new CarRepository();
    GenericService<Integer, Car> carService=new GenericService<Integer,Car>(carRepo);
    Car car = new Car(1, "BMW", 2003);
    Car car2 = new Car(2, "Audi", 2005);
    @Test
    public void CarServiceTestStart(){
        carService.add(car);
        carService.add(car2);
        carService.updateAtId(1, car2);
        assertEquals(car2, carService.getEntityById(1));
        assertEquals(car2, carService.getEntityById(2));
        carService.deleteAtId(1);
        assertEquals(null, carService.getEntityById(1));

    }

}

package Tests;

import Domain.Car;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CarTest {
    Car car=new Car(1,"BMW",2003);
    @Test
    public void CarTestStart() {

        Integer x=1;
        assertEquals(x, car.getId());
        assertEquals("BMW", car.getModel());
        assertEquals(2003, car.getYearOfProduction());
        car.setId(2);
        car.setModel("Audi");
        car.setYearOfProduction(2005);
        Integer xy=2;
        assertEquals(xy, car.getId());
        assertEquals("Audi", car.getModel());
        assertEquals(2005, car.getYearOfProduction());
        assertEquals("Car Id: 2 Model of car='Audi', yearOfProduction=2005\n",car.toString());


    }

}

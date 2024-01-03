package Repositories;

import Domain.Car;

import java.util.List;
//basic repo layout using lists
public class CarRepository extends MemoryRepository<Integer,Car> {
    public CarRepository() {
    }
}

package Utilities;

import Domain.Car;
import Domain.Reservation;
import Service.Service;
import Settings.Settings;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UI {
    private  Service<Integer, Car> carService;
    private  Service<Integer, Reservation> reservationService;
    private final Scanner scanner;

    public UI(Service<Integer,Car> carService,Service<Integer,Reservation> reservationService,Scanner scanner) {
        this.scanner=scanner;
        this.carService=carService;
        this.reservationService=reservationService;
    }

    public void start() throws IOException {

        while(true){
            printMenu();
            int choice=scanner.nextInt();
            scanner.nextLine();;
            switch(choice){
                case(1):
                    printAllCars();
                    break;
                case(2):
                    printAllReservations();
                    break;
                case(3):
                    addACar();
                    break;
                case(4):
                    addAReservation();
                    break;
                case(5):
                    deleteCarAtId();
                    break;
                case(6):
                    deleteReservationAtId();
                    break;
                case(7):
                    updateCarAtId();
                    break;
                case(8):
                    updateReservationAtId();
                    break;
                case(9):
                    reports();
                    break;
                case(-1):
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
    private void printMenu(){
        System.out.println("Car rental service");
        System.out.println("1.Print all cars:");
        System.out.println("2.Print all reservations:");
        System.out.println("3.Add a car:");
        System.out.println("4.Add a reservation:");
        System.out.println("5.Delete a car:");
        System.out.println("6.Delete a reservation:");
        System.out.println("7.Update a car:");
        System.out.println("8.Update a reservation:");
        System.out.println("9.Create reports:");
        System.out.println("-1.Exit:");
        System.out.println("Enter your choice:");

    }
    public void printAllCars(){
        System.out.println("All cars:"+carService.getAllEntities());
    }
    public void printAllReservations(){
        System.out.println("All reservations:"+reservationService.getAllEntities());
    }
    public void addACar(){
        System.out.println("Enter the id of the car:");
        int id=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the model of the car:");
        String model=scanner.nextLine();
        System.out.println("Enter the year of production of the car:");
        int yearOfProduction=scanner.nextInt();
        scanner.nextLine();
        Car car=new Car(id,model,yearOfProduction);
        carService.add(car);
    }
    public void addAReservation(){
        System.out.println("Enter the id of the reservation:");
        int id=scanner.nextInt();
        scanner.nextLine();
        for(Car car:carService.getAllEntities()){
            System.out.println(car);
        }
        System.out.println("Enter the id of the car:");
        int carId=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the name of the customer:");
        String customerName=scanner.nextLine();
        System.out.println("Enter the start date of the reservation:");
        String startDate=scanner.nextLine();
        System.out.println("Enter the end date of the reservation:");
        String endDate=scanner.nextLine();
        Reservation reservation=new Reservation(id,carService.getEntityById(carId),customerName,startDate,endDate);
        reservationService.add(reservation);
    }
    public void deleteCarAtId(){
        System.out.println("Enter the id of the car you want to delete:");
        Integer id=scanner.nextInt();
        scanner.nextLine();
        carService.deleteAtId(id);
    }
    public void deleteReservationAtId(){
        System.out.println("Enter the id of the reservation you want to delete:");
        int id=scanner.nextInt();
        scanner.nextLine();
        reservationService.deleteAtId(id);
    }
    public void updateCarAtId(){
        System.out.println("Enter the id of the car you want to update:");
        int id=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new model of the car:");
        String model=scanner.nextLine();
        System.out.println("Enter the new year of production of the car:");
        int yearOfProduction=scanner.nextInt();
        scanner.nextLine();
        Car car=new Car(id,model,yearOfProduction);
        carService.updateAtId(id,car);
    }
    public void updateReservationAtId(){
        System.out.println("Enter the id of the reservation you want to update:");
        int id=scanner.nextInt();
        scanner.nextLine();
        for(Car car:carService.getAllEntities()){
            System.out.println(car);
        }
        System.out.println("Enter the new id of the car:");
        int carId=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new name of the customer:");
        String customerName=scanner.nextLine();
        System.out.println("Enter the new start date of the reservation:");
        String startDate=scanner.nextLine();
        System.out.println("Enter the new end date of the reservation:");
        String endDate=scanner.nextLine();
        Reservation reservation=new Reservation(id,carService.getEntityById(carId),customerName,startDate,endDate);
        reservationService.updateAtId(id,reservation);
    }
    public List<Reservation> getReservationsWithIdLessThanX(Service<Integer, Reservation> reservationService) {
        Iterable<Reservation> iterableReservations = reservationService.getAllEntities();
        System.out.println("Enter the id:");
        int id=scanner.nextInt();
        scanner.nextLine();

        List<Reservation> reservationsList = StreamSupport.stream(iterableReservations.spliterator(), false)
                .filter(reservation -> reservation.getId() < id)
                .collect(Collectors.toList());

        return reservationsList;
    }
    public List<Car> getCarsWithYearInRange(Service<Integer, Car> carService) {
        Iterable<Car> iterableCars = carService.getAllEntities();
        System.out.println("Enter the min year:");
        int minYear=scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the max year:");
        int maxYear=scanner.nextInt();
        scanner.nextLine();
        List<Car> carsInRange = StreamSupport.stream(iterableCars.spliterator(), false)
                .filter(car -> car.getYearOfProduction() >= minYear && car.getYearOfProduction() <= maxYear)
                .collect(Collectors.toList());

        return carsInRange;
    }

        public List<Reservation> getReservationsSameMonth(Service<Integer, Reservation> reservationService) {
            Iterable<Reservation> iterableReservations = reservationService.getAllEntities();

            List<Reservation> reservationsWithSameDates = StreamSupport.stream(iterableReservations.spliterator(), false)
                    .filter(reservation -> reservation.getStartDate().equals(reservation.getEndDate()))
                    .collect(Collectors.toList());

            return reservationsWithSameDates;
        }
    public List<String> getCustomerNameByCarId(Service<Integer, Reservation> reservationService) {
        System.out.println("Enter the car id:");
        int carId = scanner.nextInt();
        scanner.nextLine();

        Iterable<Reservation> reservations = reservationService.getAllEntities();

        List<String> customerNames = StreamSupport.stream(reservations.spliterator(), false)
                .filter(reservation -> reservation.getCar().getId() == carId)
                .map(Reservation::getCustomerName)
                .collect(Collectors.toList());

        return customerNames;
    }
    public List<Car> getCarsByModel(Service<Integer, Car> carService) {
        System.out.println("Enter the car model:");
        String model = scanner.nextLine();

        Iterable<Car> cars = carService.getAllEntities();

        List<Car> carsWithModel = StreamSupport.stream(cars.spliterator(), false)
                .filter(car -> car.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());

        return carsWithModel;
    }

    public void reports(){
        System.out.println("Chose report type:");
        System.out.println("1.All reservations with id less than x:");
        System.out.println("2.All cars with year in range:");
        System.out.println("3.All reservations with same start and end date:");
        System.out.println("4.All customer names by car id:");
        System.out.println("5.All cars by model:");
        System.out.println("Enter your choice:");
        int choice=scanner.nextInt();
        scanner.nextLine();
        switch(choice){
            case(1):
                System.out.println(getReservationsWithIdLessThanX(reservationService));
                break;
            case(2):
                System.out.println(getCarsWithYearInRange(carService));
                break;
            case(3):
                System.out.println(getReservationsSameMonth(reservationService));
                break;
            case(4):
                System.out.println(getCustomerNameByCarId(reservationService));
                break;
            case(5):
                System.out.println(getCarsByModel(carService));
                break;
            default:
                System.out.println("Invalid choice!");
                break;


        }
    }

}

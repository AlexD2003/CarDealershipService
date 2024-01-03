package Main;

import Domain.Car;
import Domain.Reservation;
import GUI.CarsController;
import Repositories.*;
import Service.Service;
import ServiceImpl.GenericDatabaseService;
import ServiceImpl.GenericFileService;
import ServiceImpl.GenericService;
import Settings.Settings;
import Utilities.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;


public class Main extends Application {
    public static void main(String[] args) throws IOException {
        /*UI ui=null;
        GenericFileService<Integer,Car> carService= null;
        GenericFileService<Integer,Reservation> reservationService = null;
        Scanner scanner=new Scanner(System.in);
        Settings settings=new Settings();
        String repository= settings.getProps("Repository");
        switch (repository){
            case("text"):
                String carFilePath=settings.getProps("CarFilePath");
                String reservationFilePath=settings.getProps("ReservationFilePath");
                carService=new GenericFileService<Integer,Car>(new CarRepositoryTextFile(carFilePath));
                reservationService=new GenericFileService<Integer,Reservation>(new ReservationRepositoryTextFile(reservationFilePath));
                ui=new UI(carService,reservationService,scanner);
                ui.start();
                break;
            case("database"):
                String carTable=settings.getProps("CarTable");
                String reservationTable=settings.getProps("ReservationTable");
                CarRepositoryDataBase carRepositoryDataBase=new CarRepositoryDataBase(carTable);
                ReservationRepositoryDataBase reservationRepositoryDataBase=new ReservationRepositoryDataBase(reservationTable);
                GenericDatabaseService<Integer,Car> carService2=new GenericDatabaseService<Integer,Car>(carRepositoryDataBase);
                GenericDatabaseService<Integer,Reservation> reservationService2=new GenericDatabaseService<Integer,Reservation>(reservationRepositoryDataBase);
                ui=new UI(carService2,reservationService2,scanner);
                ui.start();
                break;
            case("generic"):
                CarRepository carRepository=new CarRepository();
                ReservationRepository reservationRepository=new ReservationRepository();
                GenericService<Integer,Car> carService3=new GenericService<Integer,Car>(carRepository);
                GenericService<Integer,Reservation> reservationService3=new GenericService<Integer,Reservation>(reservationRepository);
                ui=new UI(carService3,reservationService3,scanner);
                ui.start();
                break;
            case("binary"):
                String carBinaryFilePath=settings.getProps("CarFilePath");
                String reservationBinaryFilePath=settings.getProps("ReservationFilePath");
                GenericFileService<Integer,Car> carService4=new GenericFileService<Integer,Car>(new CarRepositoryBinaryFile(carBinaryFilePath));
                GenericFileService<Integer,Reservation> reservationService4=new GenericFileService<Integer,Reservation>(new ReservationRepositoryBinaryFile(reservationBinaryFilePath));
                ui=new UI(carService4,reservationService4,scanner);
                ui.start();
                break;
            default:
                System.out.println("Invalid repository type! Check settings!");
                break;
        }*/
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MemoryRepository<Integer, Car> carRepository = new CarRepository();
        MemoryRepository<Integer, Reservation> reservationRepository = new ReservationRepository();
        Service<Integer, Car> carService = new GenericService<Integer, Car>(carRepository);
        carService.add(new Car(1, "BMW", 2003));
        Car car1=new Car(2, "Audi", 2005);
        Service<Integer, Reservation> reservationService = new GenericService<Integer, Reservation>(reservationRepository);
        Car c1=new Car(10,"a",2);
        reservationService.add(new Reservation(10,c1,"a","a","a"));
        carService.add(c1);
        CarsController carsController = new CarsController(carService, reservationService);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Cars.fxml"));
        loader.setController(carsController);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}

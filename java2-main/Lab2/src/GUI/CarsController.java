package GUI;

import Domain.Car;
import Domain.Reservation;
import Repositories.*;
import Service.Service;
import ServiceImpl.GenericDatabaseService;
import ServiceImpl.GenericFileService;
import ServiceImpl.GenericService;
import Settings.Settings;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CarsController {

    private Service<Integer, Car> carService;
    private Service<Integer, Reservation> reservationService;


    public CarsController(Service<Integer, Car> service, Service<Integer, Reservation> reservationService) throws IOException {
        if(new Settings ().getProps("Repository").equals("generic")){
            this.carService = service;
            this.reservationService = reservationService;
        }
        else if(new Settings ().getProps("Repository").equals("database")){
            this.carService=new GenericDatabaseService<>(new CarRepositoryDataBase(new Settings().getProps("CarTable")));
            this.reservationService=new GenericDatabaseService<>(new ReservationRepositoryDataBase(new Settings().getProps("ReservationTable")));
        } else if (new Settings().getProps("Repository").equals("binary")) {
            this.carService = new GenericFileService<>(new CarRepositoryBinaryFile(new Settings().getProps("CarFilePath")));
            this.reservationService = new GenericFileService<>(new ReservationRepositoryBinaryFile(new Settings().getProps("ReservationFilePath")));
        }
        else if(new Settings().getProps("Repository").equals("text")){
            this.carService = new GenericFileService<>(new CarRepositoryTextFile(new Settings().getProps("CarFilePath")));
            this.reservationService = new GenericFileService<>(new ReservationRepositoryTextFile(new Settings().getProps("ReservationFilePath")));
        }
        else{
            throw new IOException("Invalid repository type");
        }
    }
    @FXML
    private TextField RemoveReservationAtIdTextField;
    @FXML
    private ListView<Car> CarsListView;
    @FXML
    private ListView<Reservation> ReservationsList;
    @FXML
    private Button addCarButton;
    @FXML
    void createCarWindow(javafx.scene.input.MouseEvent event) {
        Stage addCarStage = new Stage();
        TextField idField = new TextField();
        TextField modelField = new TextField();
        TextField yearOfProductionField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            String model = modelField.getText();
            int yearOfProduction = Integer.parseInt(yearOfProductionField.getText());
            Car newCar = new Car(id, model, yearOfProduction);
            carService.add(newCar);
            populateList();
            addCarStage.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Id:"),
                idField,
                new Label("Model:"),
                modelField,
                new Label("Year of production:"),
                yearOfProductionField,
                saveButton
        );
        addCarStage.setScene(new Scene(layout));
        addCarStage.show();
    }
    @FXML
    void createReservationWindow(javafx.scene.input.MouseEvent event) {
        Stage addReservationStage = new Stage();
        TextField idField = new TextField();
        TextField carIdField = new TextField();
        TextField clientNameField = new TextField();
        TextField startDateField = new TextField();
        TextField endDateField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            int carId = Integer.parseInt(carIdField.getText());
            String clientName = clientNameField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            Reservation newReservation = new Reservation(id, carService.getEntityById(carId), clientName, startDate, endDate);
            reservationService.add(newReservation);
            populateReservationsList();
            addReservationStage.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Id:"),
                idField,
                new Label("Car id:"),
                carIdField,
                new Label("Client name:"),
                clientNameField,
                new Label("Start date:"),
                startDateField,
                new Label("End date:"),
                endDateField,
                saveButton
        );
        addReservationStage.setScene(new Scene(layout));
        addReservationStage.show();

    }
    @FXML
    private TextField textFieldCarId;
    @FXML
    void pressedKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                int carId = Integer.parseInt(textFieldCarId.getText());
                openCarModelYearInputWindow(carId);
            } catch (NumberFormatException e) {
            }
        }
    }
    @FXML
    private TextField carIdTextFieldRemove;
    @FXML
    private TextField updateReservationAtIdText;
    private void openCarModelYearInputWindow(int carId) {
        Stage carDetailsStage = new Stage();
        TextField modelField = new TextField();
        TextField yearField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String model = modelField.getText();
            int year = Integer.parseInt(yearField.getText());

            Car newCar = new Car(carId, model, year);
            carService.updateAtId(carId, newCar);
            populateList();
            carDetailsStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Car Model:"),
                modelField,
                new Label("Year of Production:"),
                yearField,
                saveButton
        );

        Scene scene = new Scene(layout);
        carDetailsStage.setScene(scene);
        carDetailsStage.show();
    }
    @FXML
    void updateReservationIdPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                int reservationId = Integer.parseInt(updateReservationAtIdText.getText());
                openReservationCarIdInputWindow(reservationId);
            } catch (NumberFormatException e) {
            }
        }
    }
    @FXML
    private Button FileButton;
    private void openReservationCarIdInputWindow(int reservationId){
        Stage reservationDetailsStage = new Stage();
        TextField carIdField = new TextField();
        TextField clientNameField = new TextField();
        TextField startDateField = new TextField();
        TextField endDateField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            int carId = Integer.parseInt(carIdField.getText());
            String clientName = clientNameField.getText();
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            Reservation newReservation = new Reservation(reservationId, carService.getEntityById(carId), clientName, startDate, endDate);
            reservationService.updateAtId(reservationId, newReservation);
            populateReservationsList();
            reservationDetailsStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Car Id:"),
                carIdField,
                new Label("Client Name:"),
                clientNameField,
                new Label("Start Date:"),
                startDateField,
                new Label("End Date:"),
                endDateField,
                saveButton
        );

        Scene scene = new Scene(layout);
        reservationDetailsStage.setScene(scene);
        reservationDetailsStage.show();
    }
    @FXML
    void CarIdTextFieldRemoveContinue(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                int carId = Integer.parseInt(carIdTextFieldRemove.getText());
                carService.deleteAtId(carId);
                populateList();
            } catch (NumberFormatException e) {
            }
        }
    }
    @FXML
    void RemoveReservationAtIdContinue(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                int reservationId = Integer.parseInt(RemoveReservationAtIdTextField.getText());
                reservationService.deleteAtId(reservationId);
                populateReservationsList();
            } catch (NumberFormatException e) {
            }
        }
    }
    void populateList(){
        ObservableList<Car> carList = FXCollections.observableArrayList();
        for(Car car : carService.getAllEntities()){
            carList.add(car);
        }
        CarsListView.setItems(carList);
    }
    void populateReservationsList(){
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        for(Reservation reservation : reservationService.getAllEntities()){
                reservationList.add(reservation);
        }
        ReservationsList.setItems(reservationList);
    }


    public void initialize(){
        populateList();
        populateReservationsList();
    }
    @FXML
    void ConvertToFile(ActionEvent event) {
        openFileWindow();

    }
    @FXML
    void ConvertToMemoryButton(ActionEvent event) {
    }
    @FXML
    void ConvertToMemoryButtonContinue(javafx.scene.input.MouseEvent event) {
        openMemoryWindow();
    }
    public void openMemoryWindow(){
        carService = new GenericService<>(new CarRepository());
        reservationService = new GenericService<>(new ReservationRepository());
        addDefaultValues();
        populateList();
        populateReservationsList();
    }
    public void addDefaultValues(){
        Car car1 = new Car(1, "BMW", 2000);
        Car car2 = new Car(2, "Audi", 2005);
        Car car3 = new Car(3, "Mercedes", 2010);
        Car car4 = new Car(4, "Volkswagen", 2015);
        Car car5 = new Car(5, "Opel", 2020);
        carService.add(car1);
        carService.add(car2);
        carService.add(car3);
        carService.add(car4);
        carService.add(car5);
        Reservation reservation1 = new Reservation(1, car1, "John", "01.01.2021", "02.01.2021");
        Reservation reservation2 = new Reservation(2, car2, "John", "01.01.2021", "02.01.2021");
        Reservation reservation3 = new Reservation(3, car3, "John", "01.01.2021", "02.01.2021");
        Reservation reservation4 = new Reservation(4, car4, "John", "01.01.2021", "02.01.2021");
        Reservation reservation5 = new Reservation(5, car5, "John", "01.01.2021", "02.01.2021");
        reservationService.add(reservation1);
        reservationService.add(reservation2);
        reservationService.add(reservation3);
        reservationService.add(reservation4);
        reservationService.add(reservation5);
    }
    public void openFileWindow(){
        Stage fileStage = new Stage();
        Button textButton = new Button("Text");
        Button binaryButton = new Button("Binary");
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                textButton,
                binaryButton
        );
        Scene scene = new Scene(layout);
        fileStage.setScene(scene);
        fileStage.show();
        textButton.setOnAction(e -> {
            try {
                carService = new GenericFileService<>(new CarRepositoryTextFile(new Settings().getProps("CarFilePath")));
                reservationService = new GenericFileService<>(new ReservationRepositoryTextFile(new Settings().getProps("ReservationFilePath")));
                populateList();
                populateReservationsList();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            fileStage.close();
        });
        binaryButton.setOnAction(e -> {
            try {
                carService = new GenericFileService<>(new CarRepositoryBinaryFile(new Settings().getProps("CarFilePathBinary")));
                reservationService = new GenericFileService<>(new ReservationRepositoryBinaryFile(new Settings().getProps("ReservationFilePathBinary")));
                populateList();
                populateReservationsList();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            fileStage.close();
        });
    }
    @FXML
    void ConvertToDatabaseButton(ActionEvent event) {

    }
    @FXML
    void ConvertToDatabaseButtonContinue(javafx.scene.input.MouseEvent event) throws IOException {
        openDatabaseWindow();
    }
    public void openDatabaseWindow() throws IOException {
        carService = new GenericDatabaseService<>(new CarRepositoryDataBase(new Settings().getProps("CarTable")));
        reservationService = new GenericDatabaseService<>(new ReservationRepositoryDataBase(new Settings().getProps("ReservationTable")));
        populateList();
        populateReservationsList();
    }
    @FXML
    private ListView<Car> CarReports;
    @FXML
    private ListView<Reservation> ReservationReports;

    @FXML
    private Button CarIdLessThanXButton;
    @FXML
    void CarIdLessThanXImpl(javafx.scene.input.MouseEvent event) {
        openCarIdWindow();
    }
    void openCarIdWindow() {
        Stage carIdStage = new Stage();
        TextField idField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                ObservableList<Car> carList = FXCollections.observableArrayList();
                Iterable<Car> iterableCars = carService.getAllEntities();
                carList.addAll(StreamSupport.stream(iterableCars.spliterator(), false)
                        .filter(car -> car.getId() < id)
                        .collect(Collectors.toList()));

                CarReports.setItems(carList);
                carIdStage.close();
            } catch (NumberFormatException ex) {
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Id:"),
                idField,
                saveButton
        );

        carIdStage.setScene(new Scene(layout));
        carIdStage.show();
    }
    @FXML
    private Button YearInRangeButton;
    @FXML
    void YearInRangeButtonImplementation(javafx.scene.input.MouseEvent event) {
        openYearRangeWindow();
    }
    public void openYearRangeWindow(){
        Stage yearRangeStage = new Stage();
        TextField startYearField = new TextField();
        TextField endYearField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                int startYear = Integer.parseInt(startYearField.getText());
                int endYear = Integer.parseInt(endYearField.getText());
                ObservableList<Car> carList = FXCollections.observableArrayList();
                Iterable<Car> iterableCars = carService.getAllEntities();
                carList.addAll(StreamSupport.stream(iterableCars.spliterator(), false)
                        .filter(car -> car.getYearOfProduction() >= startYear && car.getYearOfProduction() <= endYear)
                        .collect(Collectors.toList()));

                CarReports.setItems(carList);
                yearRangeStage.close();
            } catch (NumberFormatException ex) {
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Start year:"),
                startYearField,
                new Label("End year:"),
                endYearField,
                saveButton
        );

        yearRangeStage.setScene(new Scene(layout));
        yearRangeStage.show();
    }
    @FXML
    private Button SameMonthButton;
    @FXML
    void SameMonthButtonImplementation(javafx.scene.input.MouseEvent event) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        Iterable<Reservation> iterableReservations = reservationService.getAllEntities();
        reservationList.addAll(StreamSupport.stream(iterableReservations.spliterator(), false)
                .filter(reservation -> reservation.getStartDate().equals(reservation.getEndDate()))
                .collect(Collectors.toList()));

        ReservationReports.setItems(reservationList);
    }
    @FXML
    private Button GetReservationsByName;
    @FXML
    void GetReservationsByNameImplementation(javafx.scene.input.MouseEvent event) {
        openNameWindow();
    }
    void openNameWindow(){
        Stage nameStage = new Stage();
        TextField nameField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
            Iterable<Reservation> iterableReservations = reservationService.getAllEntities();
            reservationList.addAll(StreamSupport.stream(iterableReservations.spliterator(), false)
                    .filter(reservation -> reservation.getCustomerName().equals(name))
                    .collect(Collectors.toList()));

            ReservationReports.setItems(reservationList);
            nameStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Name:"),
                nameField,
                saveButton
        );

        nameStage.setScene(new Scene(layout));
        nameStage.show();
    }
    @FXML
    private Button GetCarsByButton;

    @FXML
    void GetCarsByModelImplementation(javafx.scene.input.MouseEvent event) {
        openModelWindow();
    }
    void openModelWindow(){
        Stage modelStage = new Stage();
        TextField modelField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String model = modelField.getText();
            ObservableList<Car> carList = FXCollections.observableArrayList();
            Iterable<Car> iterableCars = carService.getAllEntities();
            carList.addAll(StreamSupport.stream(iterableCars.spliterator(), false)
                    .filter(car -> car.getModel().equals(model))
                    .collect(Collectors.toList()));

            CarReports.setItems(carList);
            modelStage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Model:"),
                modelField,
                saveButton
        );

        modelStage.setScene(new Scene(layout));
        modelStage.show();
    }

}

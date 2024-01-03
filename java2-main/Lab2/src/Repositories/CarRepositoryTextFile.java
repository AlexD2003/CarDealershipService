package Repositories;

import Domain.Car;
import java.io.*;
import java.util.Map;

public class CarRepositoryTextFile extends FileRepository<Integer, Car> {

    public CarRepositoryTextFile(String fileName) {
        super(fileName);
        writeToFile();
    }

    @Override
    public void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length != 3) {
                    System.err.println("Line is not valid: " + line);
                    continue;
                }
                else{
                    Car car = new Car(Integer.parseInt(fields[0].trim()), fields[1].trim(), Integer.parseInt(fields[2].trim()));
                    this.storage.put(car.getId(),car);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Car c : getAll()) {
                writer.write(c.getId() + ";" +
                        c.getModel() + ";" +
                        c.getYearOfProduction() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

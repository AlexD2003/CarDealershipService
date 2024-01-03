package Repositories;

import Domain.Car;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarRepositoryBinaryFile extends FileRepository<Integer, Car> {

    public CarRepositoryBinaryFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object readObject = ois.readObject();
            if (readObject instanceof Map) {
                this.storage = (Map<Integer,Car>) readObject;
            } else {
                throw new RuntimeException("Invalid object type in the file");
            }
        } catch (FileNotFoundException e) {
            this.storage = new HashMap<>();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new RuntimeException("Error reading from the file", e);
        }
    }

    @Override
    public void writeToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this.storage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
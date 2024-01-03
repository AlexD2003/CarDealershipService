package Repositories;

import Domain.Car;
import Domain.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;

public class ReservationRepositoryTextFile extends FileRepository<Integer,Reservation> {
    public ReservationRepositoryTextFile(String fileName) {
        super(fileName);
        writeToFile();
    }


    @Override
    public void readFromFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {

            String line = null;
            while((line = reader.readLine()) != null)
            {
                String[] fields = line.split(";");
                if(fields.length != 8)
                {
                    System.err.println("Line is not valid: " + line);
                    continue;
                }
                else
                {
                    int reservationId=Integer.parseInt(fields[0].trim());
                    Car car = new Car(Integer.parseInt(fields[1].trim()), fields[2].trim(), Integer.parseInt(fields[3].trim()));
                    Reservation reservation = new Reservation(reservationId,car,fields[4].trim(),fields[5].trim(),fields[6].trim());
                    this.storage.put(reservation.getId(),reservation);
                }
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Reservation r : getAll())
            {
                writer.write(r.getId() + ";" +
                        r.getCar().getId() + ";" +
                        r.getCar().getModel() + ";" +
                        r.getCar().getYearOfProduction() + ";" +
                        r.getId() + ";" +
                        r.getCustomerName() + ";" +
                        r.getStartDate()+";"+r.getEndDate() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

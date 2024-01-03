package Repositories;

import Domain.Reservation;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class ReservationRepositoryBinaryFile extends FileRepository<Integer,Reservation>{
    public ReservationRepositoryBinaryFile(String fileName) {
        super(fileName);
    }

    @Override
    public void readFromFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            Object readObject = ois.readObject();
            if(readObject instanceof Map)
            {
                this.storage = (Map<Integer, Reservation>)readObject;
            }
            else
            {
                throw new RuntimeException("Invalid object type in the file");
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error reading from the file", e);
        }
    }

    @Override
    public void writeToFile() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new java.io.FileOutputStream(fileName)))
        {
            oos.writeObject(this.storage);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}

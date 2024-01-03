package Repositories;
import Domain.Car;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CarRepositoryDataBase extends DataBaseRepository<Integer, Car>{


    public CarRepositoryDataBase(String tableName) throws IOException {
        super(tableName);
        getData();
    }

    @Override
    public void getData() {
        try{
            openConnection();
            String selectString="SELECT * FROM "+tableName+";";
            try(PreparedStatement ps = connection.prepareStatement(selectString)){
                ResultSet resultSet=ps.executeQuery();
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String model=resultSet.getString("model");
                    int yearOfProduction=resultSet.getInt("yearOfProduction");
                    Car car=new Car(id,model,yearOfProduction);
                    storage.put(car.getId(),car);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void add(Car item) {
        try{
            openConnection();
            String insertString="INSERT INTO "+tableName+" VALUES(?,?,?);";
            try(PreparedStatement ps=connection.prepareStatement(insertString)){
                ps.setInt(1,item.getId());
                ps.setString(2,item.getModel());
                ps.setInt(3,item.getYearOfProduction());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public Car getById(Integer integer) {
        for (Car car:storage.values()){
            if(car.getId()==integer){
                return car;
            }
        }
        return null;

    }

    @Override
    public Iterable<Car> getAll() {
        List<Car> cars=new ArrayList<>();
        for(Car car:storage.values()){
            cars.add(car);
        }
        return cars;
    }

    @Override
    public void updateAtId(Integer id, Car updatedItem) {
        try {
            openConnection();
            String updateString = "UPDATE " + tableName + " SET model=?, yearOfProduction=? WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(updateString)) {
                ps.setString(1, updatedItem.getModel());
                ps.setInt(2, updatedItem.getYearOfProduction());
                ps.setInt(3, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void deleteAtId(Integer id) {
        try {
            openConnection();
            String deleteString = "DELETE FROM " + tableName + " WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(deleteString)) {
                ps.setInt(1, id);
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    storage.remove(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

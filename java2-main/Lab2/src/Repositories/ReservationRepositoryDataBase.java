package Repositories;

import Domain.Car;
import Domain.Reservation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDataBase extends DataBaseRepository<Integer, Reservation> {
    public ReservationRepositoryDataBase(String tableName) throws IOException {
        super(tableName);
    }

    @Override
    public void getData() {
        try {
            openConnection();
            String selectString = "SELECT * FROM " + tableName + ";";
            try (PreparedStatement ps = connection.prepareStatement(selectString)) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int carId = resultSet.getInt("carId");
                    String customerName = resultSet.getString("customerName");
                    String startDate = resultSet.getString("startDate");
                    String endDate = resultSet.getString("endDate");
                    CarRepositoryDataBase carRepositoryDataBase = new CarRepositoryDataBase("Cars");
                    carRepositoryDataBase.getData();
                    boolean ok=false;
                    for(Car car:carRepositoryDataBase.getAll()){
                        if(car.getId()==carId){
                            ok=true;
                            break;
                        }
                    }
                    if(ok==false){
                        throw new RuntimeException("Car with id "+carId+" does not exist");
                    }

                    Car car = carRepositoryDataBase.getById(carId);

                    Reservation reservation = new Reservation(id, car , customerName, startDate, endDate);
                    storage.put(reservation.getId(), reservation);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Reservation item) {
        try {
            openConnection();
            String insertString = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?);";
            try (PreparedStatement ps = connection.prepareStatement(insertString)) {
                ps.setInt(1, item.getId());
                ps.setInt(2, item.getCar().getId());
                ps.setString(3, item.getCustomerName());
                ps.setString(4, item.getStartDate());
                ps.setString(5, item.getEndDate());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation getById(Integer integer) {
        for (Reservation reservation : storage.values()) {
            if (reservation.getId() == integer) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public Iterable<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : storage.values()) {
            reservations.add(reservation);
        }
        return reservations;
    }

    @Override
    public void updateAtId(Integer integer, Reservation updatedItem) {
        try{
            openConnection();
            String updateString="UPDATE "+tableName+" SET carId=?,customerName=?,startDate=?,endDate=? WHERE id=?;";
            try(PreparedStatement ps=connection.prepareStatement(updateString)){
                ps.setInt(1,updatedItem.getCar().getId());
                ps.setString(2,updatedItem.getCustomerName());
                ps.setString(3,updatedItem.getStartDate());
                ps.setString(4,updatedItem.getEndDate());
                ps.setInt(5,updatedItem.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
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


package Repositories;

import Domain.Identifiable;
import Settings.Settings;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public abstract class DataBaseRepository<Id,T extends Identifiable<Id>> extends MemoryRepository<Id, T> {

    protected final String URL = new Settings().getProps("Location");
    protected String tableName;
    protected Connection connection = null;
    public DataBaseRepository(String tableName) throws IOException {
        this.tableName=tableName;
    }
    public abstract void getData();
    public void openConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to connect to the database: " + e.getMessage());
        }
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }


}

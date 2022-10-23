package datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private final String connectionString;
    private final String username;
    private final String password;

    public DBConnector(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    // This should be done with some sort of connection pool if we were actually going to do it like this
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    public <T> T withConnection(DBConnection<T> dbConnection) {
        try (Connection connection = getConnection()) {
            return dbConnection.commit(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

package dB;

import utils.CustomScanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory extends CustomScanner {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "a3";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection createConnection() throws SQLException {
        createDatabase();
        Connection connection = DriverManager.getConnection(DATABASE_URL + DATABASE_NAME, USER, PASSWORD);
        createTableIfNotExists(connection);
        return connection;
    }

    private static void createDatabase() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
            System.out.println("Database 'a3' created or already exists.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTableIfNotExists(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS clientes ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nome VARCHAR(255) NOT NULL,"
                    + "email VARCHAR(255) NOT NULL)";

            statement.executeUpdate(createTableQuery);
            System.out.println("Table 'clientes' created or already exists.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package datalayer.customer;

import datalayer.DBConnector;
import dto.Customer;
import dto.CustomerCreation;

import java.nio.file.DirectoryNotEmptyException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerStorageImpl implements CustomerStorage {
    private final DBConnector dbConnector;

    public CustomerStorageImpl(DBConnector dbConnector){
        this.dbConnector = dbConnector;
    }

    @Override
    public Customer getCustomerWithId(int customerId) throws SQLException {
        var sql = "select ID, firstname, lastname, birthdate, phoneNumber from Customers where id = ?";
        try (var con = this.dbConnector.getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, customerId);

            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()){
                    var id = resultSet.getInt("ID");
                    var firstname = resultSet.getString("firstname");
                    var lastname = resultSet.getString("lastname");
                    var phoneNumber = resultSet.getString("phoneNumber");
                    return new Customer(id, firstname, lastname, phoneNumber);
                }
                return null;
            }
        }
    }

    public List<Customer> getCustomers() throws SQLException {
        try (var con = this.dbConnector.getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Customer>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname, phoneNumber from Customers")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    Customer c = new Customer(id, firstname, lastname, phoneNumber);
                    results.add(c);
                }
            }

            return results;
        }
    }

    public int createCustomer(CustomerCreation customerToCreate) throws SQLException {
        var sql = "insert into Customers(firstname, lastname) values (?, ?)";
        try (var con = this.dbConnector.getConnection();
            var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customerToCreate.getFirstname());
            stmt.setString(2, customerToCreate.getLastname());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }
}

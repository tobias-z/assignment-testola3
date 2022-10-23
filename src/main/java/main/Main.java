package main;

import datalayer.DBConnector;
import dto.Customer;
import datalayer.customer.CustomerStorageImpl;

import java.sql.SQLException;

public class Main {

    private static final String CON_STR = "jdbc:mysql://localhost:3306/DemoApplication";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static void main(String[] args) throws SQLException {
        CustomerStorageImpl storage = new CustomerStorageImpl(new DBConnector(CON_STR, USER, PASS));

        System.out.println("Got customers: ");
        for(Customer c : storage.getCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }
}

package integration.datalayer.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javafaker.Faker;
import datalayer.DBConnector;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import integration.DBIntegrationTest;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateCustomerTest extends DBIntegrationTest {

    private CustomerStorage customerStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(2);
        customerStorage = new CustomerStorageImpl(new DBConnector(getConnectionString(), "root", getDbPassword()));

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName(), faker.phoneNumber().subscriberNumber(8));
            customerStorage.createCustomer(c);
        }

    }

    @Test
    public void mustSaveCustomerInDatabaseWhenCallingCreateCustomer() throws SQLException {
        // Arrange
        // Act
        customerStorage.createCustomer(new CustomerCreation("John", "Carlssonn", "12341234"));

        // Assert
        var customers = customerStorage.getCustomers();
        assertTrue(
            customers.stream().anyMatch(x ->
                x.getFirstname().equals("John") &&
                    x.getLastname().equals("Carlssonn")));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = customerStorage.createCustomer(new CustomerCreation("a", "b", "12341234"));
        var id2 = customerStorage.createCustomer(new CustomerCreation("c", "d", "12341234"));

        // Assert
        assertEquals(1, id2 - id1);
    }
}

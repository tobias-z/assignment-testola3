package integration.servicelayer.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import datalayer.DBConnector;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import integration.DBIntegrationTest;
import java.sql.SQLException;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

class SvcCreateCustomerTest extends DBIntegrationTest {

    private CustomerService svc;
    private CustomerStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(3);
        storage = new CustomerStorageImpl(new DBConnector(getConnectionString(), "root", getDbPassword()));
        svc = new CustomerServiceImpl(storage);
    }

    @Test
    public void mustSaveCustomerToDatabaseWhenCallingCreateCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var bday = new Date(1239821l);
        int id = svc.createCustomer(firstName, lastName, bday);

        // Act
        var createdCustomer = storage.getCustomerWithId(id);

        // Assert
        assertEquals(firstName, createdCustomer.getFirstname());
        assertEquals(lastName, createdCustomer.getLastname());
    }
}

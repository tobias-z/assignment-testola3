package integration.servicelayer.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import integration.ContainerizedDbIntegrationTest;
import java.sql.Date;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

public class SomeOtherIntegrationTest extends ContainerizedDbIntegrationTest {

    private CustomerService svc;
    private CustomerStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(3);

        storage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        svc = new CustomerServiceImpl(storage);
    }

    @Test
    public void Stuff() throws CustomerServiceException, SQLException {
        // Arrange
        var id = svc.createCustomer("schmeep", "schmoop", Date.valueOf("1987-10-07"));

        // Act
        var c = svc.getCustomerById(id);

        // Assert
        assertEquals("schmeep", c.getFirstname());
        assertEquals("schmoop", c.getLastname());
    }

}

package unit.servicelayer.customer;

import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import datalayer.customer.CustomerStorage;
import java.sql.SQLException;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.customer.CustomerServiceImpl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateCustomerTest {

    // SUT (System Under Test)
    private CustomerService customerService;

    // DOC (Depended-on Component)
    private CustomerStorage storageMock;


    @BeforeAll
    public void beforeAll(){
        storageMock = mock(CustomerStorage.class);
        customerService = new CustomerServiceImpl(storageMock);
    }

    @Test
    public void mustCallStorageWhenCreatingCustomer() throws CustomerServiceException, SQLException {
        // Arrange
        // Act
        var firstName = "a";
        var lastName = "b";
        var birthdate = new Date(123456789L);

        customerService.createCustomer(firstName, lastName, birthdate, "12341234");

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .createCustomer(
                        argThat(x -> x.firstname.equals(firstName) &&
                                x.lastname.equals(lastName)));
    }
}

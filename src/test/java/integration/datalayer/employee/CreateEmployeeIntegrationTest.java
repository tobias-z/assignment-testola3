package integration.datalayer.employee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import integration.EmployeeIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateEmployeeIntegrationTest extends EmployeeIntegrationTest {

    @Test
    void willNotThrowWhenCreatingEmployee() throws Exception {
        assertDoesNotThrow(() -> employeeStorage.createEmployee(getEmployee()));
    }

    @Test
    @DisplayName("when employee is created a valid id is returned")
    void whenEmployeeIsCreatedAValidIdIsReturned() throws Exception {
        int id = employeeStorage.createEmployee(getEmployee());
        assertTrue(id > 0);
    }

}

package integration.datalayer.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import dto.Employee;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetEmployeeByIdIntegrationTest extends EmployeeIntegrationTest {

    @Test
    @DisplayName("will return an empty optional given an invalid id")
    void willReturnAnEmptyOptionalGivenAnInvalidId() throws Exception {
        Optional<Employee> employeeWithId = employeeStorage.getEmployeeWithId(10000);
        assertTrue(employeeWithId.isEmpty());
    }

    @Test
    @DisplayName("will return an employee given a correct id")
    void willReturnAnEmployeeGivenACorrectId() throws Exception {
        int createdId = employeeStorage.createEmployee(getEmployee());
        Optional<Employee> employeeWithId = employeeStorage.getEmployeeWithId(createdId);
        assertTrue(employeeWithId.isPresent());
    }

    @Test
    @DisplayName("will return the correct employee given an id")
    void willReturnTheCorrectEmployeeGivenAnId() throws Exception {
        int createdId = employeeStorage.createEmployee(getEmployee());
        Optional<Employee> employeeWithId = employeeStorage.getEmployeeWithId(createdId);
        Employee employee = employeeWithId.orElseThrow();
        assertEquals(createdId, employee.getId());
    }

}

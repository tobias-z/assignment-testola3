package unit.servicelayer.employee;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.employee.EmployeeNotFoundException;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceImpl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateEmployeeServiceUnitTest {

    private EmployeeStorage employeeStorageMock;
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeStorageMock = mock(EmployeeStorage.class);
        employeeService = new EmployeeServiceImpl(employeeStorageMock);
    }

    @AfterEach
    void tearDown() {
        reset(employeeStorageMock);
    }

    @Test
    @DisplayName("when creating an employee then the employeeStorage is called")
    void whenCreatingAnEmployeeThenTheEmployeeStorageIsCalled() throws Exception {
        String firstname = "bob";
        employeeService.createEmployee(Employee.builder().firstname(firstname).build());
        verify(employeeStorageMock)
            .createEmployee(argThat(employee -> employee.getFirstname().equals(firstname)));
    }

    @Test
    @DisplayName("when calling getEmployeeById and the employeeStorage returns an empty optional then throws")
    void whenCallingGetEmployeeByIdAndTheEmployeeStorageReturnsAnEmptyOptionalThenThrows() throws Exception {
        int employeeId = 1;
        when(employeeStorageMock.getEmployeeWithId(eq(employeeId)))
            .thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    @Test
    @DisplayName("when calling getEmployeeById and the employeeStorage returns an employee then dont throw")
    void whenCallingGetEmployeeByIdAndTheEmployeeStorageReturnsAnEmployeeThenDontThrow() throws Exception {
        int employeeId = 1;
        when(employeeStorageMock.getEmployeeWithId(eq(employeeId)))
            .thenReturn(Optional.of(Employee.builder().build()));
        assertDoesNotThrow(() -> employeeService.getEmployeeById(employeeId));
    }

}

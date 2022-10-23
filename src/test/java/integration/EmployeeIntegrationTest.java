package integration;

import datalayer.DBConnector;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import integration.DBIntegrationTest;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;

public class EmployeeIntegrationTest extends DBIntegrationTest {

    protected EmployeeStorage employeeStorage;

    @BeforeAll
    void setup() {
        runMigration(4);
        DBConnector dbConnector = new DBConnector(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(dbConnector);
    }

    protected static Employee getEmployee() {
        return Employee.builder()
            .firstname("jens")
            .lastname("hans")
            .birthdate(LocalDate.now())
            .build();
    }
}

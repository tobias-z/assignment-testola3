package datalayer.employee;

import dto.Employee;
import java.util.Optional;

public interface EmployeeStorage {

    int createEmployee(Employee employee);

    Optional<Employee> getEmployeeWithId(int employeeId);

}

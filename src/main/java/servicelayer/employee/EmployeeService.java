package servicelayer.employee;

import dto.Employee;

public interface EmployeeService {

    int createEmployee(Employee employee);

    Employee getEmployeeById(Integer employeeId);

}

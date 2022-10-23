package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeStorage employeeStorage;

    @Override
    public int createEmployee(Employee employee) {
        return employeeStorage.createEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        return employeeStorage.getEmployeeWithId(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(String.format("Unable to find employee with id %s", employeeId)));
    }
}

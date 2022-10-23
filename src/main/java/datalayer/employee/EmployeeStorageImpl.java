package datalayer.employee;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import datalayer.DBConnector;
import dto.Employee;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeStorageImpl implements EmployeeStorage {

    private final DBConnector dbConnector;

    @Override
    public int createEmployee(Employee employee) {
        return dbConnector.withConnection(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Employees(firstname, lastname, birthdate) VALUES (?, ?, ?)",
                RETURN_GENERATED_KEYS
            );

            preparedStatement.setString(1, employee.getFirstname());
            preparedStatement.setString(2, employee.getLastname());
            preparedStatement.setDate(3, new Date(employee.getBirthdate().toEpochDay()));
            preparedStatement.executeUpdate();

            return dbConnector.getId(preparedStatement.getGeneratedKeys());
        });
    }

    @Override
    public Optional<Employee> getEmployeeWithId(int employeeId) {
        return dbConnector.withConnection(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employees WHERE ID = ?");
            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(mapToSingleEmployee(resultSet));
        });
    }

    private Employee mapToSingleEmployee(ResultSet resultSet) throws SQLException {
        return Employee.builder()
            .id(resultSet.getInt("ID"))
            .firstname(resultSet.getString("firstname"))
            .lastname(resultSet.getString("lastname"))
            .birthdate(resultSet.getDate("birthdate").toLocalDate())
            .build();
    }
}

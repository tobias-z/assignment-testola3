package datalayer.booking;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import datalayer.DBConnector;
import dto.Booking;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingStorageImpl implements BookingStorage {

    private final DBConnector dbConnector;

    @Override
    public int createBooking(Booking booking) {
        return dbConnector.withConnection(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO Bookings(customerId, employeeId, date, start, end) VALUES (?, ?, ?, ?, ?)",
                RETURN_GENERATED_KEYS
            );

            preparedStatement.setInt(1, booking.getCustomerId());
            preparedStatement.setInt(2, booking.getEmployeeId());
            preparedStatement.setDate(3, new Date(booking.getDate().toEpochDay()));
            preparedStatement.setTime(4, new Time(booking.getStart().toEpochSecond(booking.getDate(), ZoneOffset.MAX)));
            preparedStatement.setTime(5, new Time(booking.getEnd().toEpochSecond(LocalDate.now(), ZoneOffset.MAX)));
            preparedStatement.executeUpdate();

            return dbConnector.getId(preparedStatement.getGeneratedKeys());
        });
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) {
        return dbConnector.withConnection(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Bookings WHERE customerId = ?");
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToMultipleBookings(resultSet);
        });
    }

    @Override
    public Collection<Booking> getAllBookings() {
        return dbConnector.withConnection(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Bookings");
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToMultipleBookings(resultSet);
        });
    }

    private Collection<Booking> mapToMultipleBookings(ResultSet resultSet) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        while (resultSet.next()) {
            bookingList.add(Booking.builder()
                .id(resultSet.getInt("ID"))
                .customerId(resultSet.getInt("customerId"))
                .employeeId(resultSet.getInt("employeeId"))
                .date(resultSet.getDate("date").toLocalDate())
                .start(resultSet.getTime("start").toLocalTime())
                .end(resultSet.getTime("end").toLocalTime())
                .build()
            );
        }
        return bookingList;
    }
}

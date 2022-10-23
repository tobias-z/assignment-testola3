package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.DBConnector;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import dto.CustomerCreation;
import dto.booking.Booking;
import integration.DBIntegrationTest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;

public class BookingIntegrationTest extends DBIntegrationTest {

    protected BookingStorage bookingStorage;
    private CustomerStorage customerStorage;

    @BeforeAll
    public void setup() throws SQLException {
        runMigration(4);
        DBConnector dbConnector = new DBConnector(getConnectionString(), "root", getDbPassword());
        bookingStorage = new BookingStorageImpl(dbConnector);
        customerStorage = new CustomerStorageImpl(dbConnector);

        int size = bookingStorage.getAllBookings().size();
        if (size < 100) {
            addFakeBookings(100 - size);
        }
    }

    private void addFakeBookings(int amountToAdd) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < amountToAdd; i++) {
            int customerId = createFakeCustomer(faker);
            bookingStorage.createBooking(getFakedBooking(faker, customerId));
        }
    }

    private static Booking getFakedBooking(Faker faker, int customerId) {
        LocalDateTime now = LocalDateTime.now();
        return Booking.builder()
            .customerId(customerId)
            .employeeId(1)
            .date(now.toLocalDate())
            .start(now.toLocalTime())
            .end(now.toLocalTime().plusHours(faker.number().randomNumber()))
            .build();
    }

    private int createFakeCustomer(Faker faker) throws SQLException {
        CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName());
        return customerStorage.createCustomer(c);
    }

}

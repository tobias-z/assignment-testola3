package integration.datalayer.booking;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dto.booking.Booking;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateBookingIntegrationTest extends BookingIntegrationTest {

    @Test
    @DisplayName("can create booking")
    void canCreateBooking() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> {
            bookingStorage.createBooking(getBooking(now));
        });
    }

    @Test
    @DisplayName("the returned booking id is a valid id")
    void theReturnedBookingIdIsAValidId() throws Exception {
        int bookingId = bookingStorage.createBooking(getBooking(LocalDateTime.now()));
        assertTrue(bookingId > 0);
    }

    @Test
    @DisplayName("when creating a booking it is correctly stored in the database")
    void whenCreatingABookingItIsCorrectlyStoredInTheDatabase() throws Exception {
        int oldSize = bookingStorage.getAllBookings().size();
        bookingStorage.createBooking(getBooking(LocalDateTime.now()));
        int newSize = bookingStorage.getAllBookings().size();
        assertEquals(1, newSize - oldSize);
    }

    private static Booking getBooking(LocalDateTime now) {
        return Booking.builder()
            .employeeId(1)
            .customerId(1)
            .date(now.toLocalDate())
            .start(now.toLocalTime())
            .end(now.toLocalTime().plusHours(10))
            .build();
    }
}

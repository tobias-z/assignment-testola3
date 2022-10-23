package integration.datalayer.booking;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dto.Booking;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookingsForCustomerIntegrationTest extends BookingIntegrationTest {

    @Test
    @DisplayName("does not throw when getting a valid customer")
    void doesNotThrowWhenGettingAValidCustomer() throws Exception {
        assertDoesNotThrow(() -> bookingStorage.getBookingsForCustomer(1));
    }

    @Test
    @DisplayName("does not return null given an invalid customer id")
    void doesNotReturnNullGivenAnInvalidCustomerId() throws Exception {
        assertNotNull(bookingStorage.getBookingsForCustomer(100000));
    }

    @Test
    @DisplayName("the returned collection has elements when a valid customer id is provided")
    void theReturnedCollectionHasElementsWhenAValidCustomerIdIsProvided() throws Exception {
        assertTrue(bookingStorage.getBookingsForCustomer(1).size() > 0);
    }

    @Test
    @DisplayName("when a new booking is created for a customer then the booking can be found from the customer id")
    void whenANewBookingIsCreatedForACustomerThenTheBookingCanBeFoundFromTheCustomerId() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        int customerId = 1;
        Booking booking = Booking.builder()
            .customerId(customerId)
            .employeeId(1)
            .date(now.toLocalDate())
            .start(now.toLocalTime())
            .end(now.toLocalTime().plusHours(1))
            .build();

        int createdBooking = bookingStorage.createBooking(booking);

        boolean hasTheCreatedBooking = bookingStorage.getBookingsForCustomer(customerId).stream()
            .anyMatch(b -> b.getId().equals(createdBooking));

        assertTrue(hasTheCreatedBooking);
    }

}

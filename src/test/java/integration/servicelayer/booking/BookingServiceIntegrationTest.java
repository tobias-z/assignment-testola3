package integration.servicelayer.booking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import dto.Booking;
import integration.BookingIntegrationTest;
import java.time.LocalDateTime;
import java.util.Collection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsService;

public class BookingServiceIntegrationTest extends BookingIntegrationTest {

    private BookingService bookingService;

    @BeforeAll
    void setupAfter() {
        bookingService = new BookingServiceImpl(bookingStorage, mock(SmsService.class), new CustomerServiceImpl(customerStorage));
    }

    @Test
    @DisplayName("can create booking")
    void canCreateBooking() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        int customerId = customerStorage.getCustomers().get(0).getId();
        int id = bookingService.createBooking(
            customerId, 1, now.toLocalDate(), now.toLocalTime(), now.toLocalTime().plusHours(1)
        );
        assertTrue(id > 0);
    }

    @Test
    @DisplayName("can get by customer id")
    void canGetByCustomerId() throws Exception {
        Collection<Booking> bookingsForCustomer = bookingService.getBookingsForCustomer(1);
        assertFalse(bookingsForCustomer.isEmpty());
    }

}
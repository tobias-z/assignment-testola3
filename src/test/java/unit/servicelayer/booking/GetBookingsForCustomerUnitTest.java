package unit.servicelayer.booking;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceImpl;
import servicelayer.customer.CustomerServiceImpl;
import servicelayer.notifications.SmsService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class GetBookingsForCustomerUnitTest {

    private BookingService bookingService;
    private BookingStorage bookingStorageMock;

    @BeforeEach
    void setup() {
        bookingStorageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(bookingStorageMock, mock(SmsService.class), new CustomerServiceImpl(mock(CustomerStorage.class)));
    }

    @Test
    @DisplayName("when getting books for a customer then the booking service is correctly called")
    void whenGettingBooksForACustomerThenTheBookingServiceIsCorrectlyCalled() throws Exception {
        int customerId = 1;
        when(bookingService.getBookingsForCustomer(eq(customerId))).thenReturn(emptyList());
        bookingService.getBookingsForCustomer(customerId);
        verify(bookingStorageMock, times(1))
            .getBookingsForCustomer(customerId);
    }
}

package unit.servicelayer.booking;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import dto.Customer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
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
public class CreateBookingUnitTest {

    private BookingService bookingService;

    private BookingStorage bookingStorageMock;
    private SmsService smsServiceMock;
    private CustomerStorage customerStorageMock;

    @BeforeEach
    void setup() {
        bookingStorageMock = mock(BookingStorage.class);
        smsServiceMock = mock(SmsService.class);
        customerStorageMock = mock(CustomerStorage.class);
        bookingService = new BookingServiceImpl(bookingStorageMock, smsServiceMock, new CustomerServiceImpl(customerStorageMock));
    }

    @AfterEach
    void tearDown() {
        reset(bookingStorageMock);
        reset(smsServiceMock);
        reset(customerStorageMock);
    }

    @Test
    @DisplayName("when calling createBooking then the bookingStorage is called")
    void whenCallingCreateBookingThenTheBookingStorageIsCalled() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        int customerId = 1;
        when(customerStorageMock.getCustomerWithId(eq(customerId)))
            .thenReturn(new Customer(customerId, "bob", "the builder", "12341234"));

        bookingService.createBooking(
            customerId, 1, now.toLocalDate(), now.toLocalTime(), now.toLocalTime().plusHours(1)
        );

        verify(bookingStorageMock, times(1))
            .createBooking(argThat(booking -> booking.getCustomerId().equals(customerId)));
    }

    @Test
    @DisplayName("when a booking is created then a the sms service is called")
    void whenABookingIsCreatedThenATheSmsServiceIsCalled() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        int customerId = 1;
        String phoneNumber = "12341234";
        when(customerStorageMock.getCustomerWithId(eq(customerId)))
            .thenReturn(new Customer(customerId, "bob", "the builder", phoneNumber));

        bookingService.createBooking(
            customerId, 1, now.toLocalDate(), now.toLocalTime(), now.toLocalTime().plusHours(1)
        );

        verify(smsServiceMock, times(1))
            .sendSms(argThat(smsMessage -> smsMessage.getRecipient().equals(phoneNumber)));
    }

}

package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;
import dto.Customer;
import dto.SmsMessage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import servicelayer.customer.CustomerService;
import servicelayer.customer.CustomerServiceException;
import servicelayer.notifications.NotificationException;
import servicelayer.notifications.SmsService;

@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingStorage bookingStorage;
    private final SmsService smsService;
    private final CustomerService customerService;

    @Override
    public int createBooking(Integer customerId, Integer employeeId, LocalDate date, LocalTime start, LocalTime end) {
        Booking booking = Booking.builder()
            .customerId(customerId)
            .employeeId(employeeId)
            .date(date)
            .start(start)
            .end(end)
            .build();
        int id = bookingStorage.createBooking(booking);

        try {
            Customer customer = customerService.getCustomerById(customerId);
            smsService.sendSms(new SmsMessage(
                customer.getPhoneNumber(),
                String.format("a new booking was created for you with id '%s'", id)
            ));
        } catch (CustomerServiceException e) {
            throw new NotificationException(
                String.format("unable to send sms because no customer was found with id '%s'", customerId)
            );
        }

        return id;
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(Integer customerId) {
        return bookingStorage.getBookingsForCustomer(customerId);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(Integer employeeId) {
        throw new NotImplementedException(
            "This is not yet implemented because we cannot handle this operation in our BookingStorage interface"
        );
    }
}

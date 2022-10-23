package servicelayer.booking;

import dto.Booking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface BookingService {

    int createBooking(Integer customerId, Integer employeeId, LocalDate date, LocalTime start, LocalTime end);

    Collection<Booking> getBookingsForCustomer(Integer customerId);

    Collection<Booking> getBookingsForEmployee(Integer employeeId);

}

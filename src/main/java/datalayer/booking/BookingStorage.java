package datalayer.booking;

import dto.Booking;
import java.util.Collection;

public interface BookingStorage {

    int createBooking(Booking booking);

    Collection<Booking> getBookingsForCustomer(int customerId);

    Collection<Booking> getAllBookings();

}

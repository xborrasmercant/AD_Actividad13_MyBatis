
import java.util.List;

public interface BookingMapper {
    void insertBooking(Booking booking);
    void updateBooking(Booking booking);
    void deleteBookingById(String id);
    void deleteAllBookings();
    Booking getBookingById(String id);
    void loadXmlData(String xmlFilePath);
    void saveDataToCsv(String csvFilePath);
}
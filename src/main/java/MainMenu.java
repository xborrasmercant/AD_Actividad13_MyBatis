import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu implements BookingMapper{
    private final String MYBATIS_CONFIG_PATH = "mybatis-config.xml";
    ArrayList<Booking> bookingsCollection = new ArrayList<>();
    private static BookingMapper bMapper;
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession session;
    private BookingMapper bookingMapper;

    public void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String option;

        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(MYBATIS_CONFIG_PATH));
            session = sqlSessionFactory.openSession();
            this.bookingMapper = session.getMapper(BookingMapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Main menu
        while (true) {

            System.out.println();
            System.out.println("===============================");
            System.out.println("1. Load XML file");
            System.out.println("2. Save data to CSV");
            System.out.println("3. Delete all bookings");
            System.out.println("4. Insert booking");
            System.out.println("5. Delete booking");
            System.out.println("6. Modify booking");
            System.out.println("7. Exit");
            System.out.println("===============================");

            System.out.print("Enter an option please (1-7): ");
            option = input.nextLine();
            System.out.println("-------------------------------");

            switch (option) {
                case "1" -> {
                    System.out.print("Enter the XML file path: ");
                    String xmlFilePath = input.nextLine();
                    loadXmlData(xmlFilePath);
                }
                case "2" -> {
                    System.out.print("Enter the CSV file path: ");
                    String csvFilePath = input.nextLine();
                    saveDataToCsv(csvFilePath);
                }
                case "3" -> deleteAllBookings();
                case "4" -> insertBooking(Booking.createNewBooking(Booking.Type.NEW));
                case "5" -> {
                    System.out.print("Enter Booking ID to delete: ");
                    String bookingId = input.nextLine();
                    deleteBookingById(bookingId);
                }
                case "6" -> {
                    System.out.print("Enter Booking ID to update: ");
                    String bookingId = input.nextLine();
                    updateBooking(getBookingById(bookingId));
                }
                case "7" -> System.exit(0);
                default -> System.out.println("Invalid option. Please, try again.");
            }
        }
    }

    @Override
    public void insertBooking(Booking booking) {
        try {
            bookingMapper.insertBooking(booking);
            session.commit();
            System.out.println("Booking added successfully!");
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            System.out.println("Error adding booking. Rolling back changes.");
        }
    }



    @Override
    public void updateBooking(Booking booking) {
        try {
            // Update booking
            booking = Booking.createNewBooking(Booking.Type.UPDATE);

            // Commit the changes to the database
            bookingMapper.updateBooking(booking);
            session.commit();
            System.out.println("Booking updated successfully!");
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            System.out.println("Error updating booking. Rolling back changes.");
        }
    }


    @Override
    public void deleteBookingById(String id) {
        try {
            bookingMapper.deleteBookingById(id);
            session.commit();
            System.out.println("Booking deleted successfully!");
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            System.out.println("Error deleting booking. Rolling back changes.");
        }
    }

    @Override
    public void deleteAllBookings() {
        try {
            bookingMapper.deleteAllBookings();
            session.commit();
            System.out.println("All bookings deleted successfully!");
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            System.out.println("Error deleting all bookings. Rolling back changes.");
        }
    }

    @Override
    public Booking getBookingById(String id) {
        try {
            return bookingMapper.getBookingById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void loadXmlData(String xmlFilePath) {
        try {
            bookingMapper.loadXmlData(xmlFilePath);
            session.commit();
            System.out.println("XML data loaded successfully!");
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            System.out.println("Error loading XML data. Rolling back changes.");
        }

    }

    @Override
    public void saveDataToCsv(String csvFilePath) {
        try {
            bookingMapper.saveDataToCsv(csvFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
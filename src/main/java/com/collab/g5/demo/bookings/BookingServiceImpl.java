package com.collab.g5.demo.bookings;

import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    /**
     * Declaring both of services that is requiored.
     */
    private BookingsRepository bookingsRepository;
    private UserServiceImpl userServiceImpl;

    /**
     * Instantiating the variables by making use of Constructor Injection
     *
     * @param bookingsRepository
     * @param userServiceImpl
     */
    @Autowired
    public BookingServiceImpl(BookingsRepository bookingsRepository, UserServiceImpl userServiceImpl) {
        this.bookingsRepository = bookingsRepository;
        this.userServiceImpl = userServiceImpl;

    }

    /**
     * This method will return all the bookings that are in the database without any filter.
     *
     * @return all the bookings that are in the database.
     */
    @Override
    public List<Bookings> getAllBookings() {
        return bookingsRepository.findAll();
    }

    /**
     * This will only return all the bookings that belongs to this particular user specified by the email.
     *
     * @param email
     * @return all bookings that is made by this user.
     */
    @Override
    public List<Bookings> getAllMyBookings(String email) {
        return bookingsRepository.findBookingsByEmail(email);
    }

    /**
     * This will return all the past bookings that is made by this user
     * This method will essentially retrieve all the bookings from the database that
     * belongs to this user and will then iterate through and delete those
     * that Date is after today's date
     *
     * @param user
     * @return all past bookings that is made by this user.
     */
    @Override
    public List<Bookings> getAllMyPastBookings(User user) {
        LocalDateTime now = LocalDateTime.now();
        List<Bookings> bookingsList = bookingsRepository.findAllByUser(user);
        Iterator<Bookings> iterator = bookingsList.iterator();
        while (iterator.hasNext()) {
            Bookings b = iterator.next();
            if (!b.getBDate().isBefore(ChronoLocalDate.from(now))) {
                iterator.remove();
            }
        }
        System.out.println(bookingsList.size());

        return bookingsList;
    }

    /**
     * This will return all the upcoming bookings that is made by this user
     * This method will essentially retrieve all the bookings from the database that
     * belongs to this user and will then iterate through and delete those
     * that Date is before today date.
     *
     * @param user
     * @return list of bookings that belongs to user and its upcoming.
     */
    @Override
    public List<Bookings> getAllMyUpcomingBookings(User user) {
        LocalDateTime now = LocalDateTime.now().plusDays(1L);

        List<Bookings> bookingsList = bookingsRepository.findAllByUser(user);
        Iterator<Bookings> iterator = bookingsList.iterator();
        while (iterator.hasNext()) {
            Bookings b = iterator.next();
            if (b.getBDate().isBefore(ChronoLocalDate.from(now))) {
                iterator.remove();
            }
        }
        return bookingsList;
    }

    /**
     * This is to retrieve for a specific date, how many bookings there are.
     *
     * @param cid
     * @param date
     * @return number of bookings that is "Confirmed" on that date.
     */
    @Override
    public int getBookingsCountByDate(int cid, LocalDate date) {
        return bookingsRepository.getBookingsCountByDate(cid, date);
    }

    /**
     * This will retrieve the number of bookings that this particular user have for this particular month.
     *
     * @param email
     * @param date
     * @return number of bookings that is made by this user for this month.
     */
    @Override
    public int getBookingsCountByUserAndMonth(String email, LocalDate date) {
        int month = date.getMonthValue();
        return bookingsRepository.getBookingsCountByUserAndMonth(email, month);
    }

    /**
     * This method will only be triggered if and only if the total number of bookings pending and Confirmed status
     * for that particular date exceeds the daily limit of the company. It will then loop through the database to find
     * for the next booking that is made by one of the user's from the same company and set that status to be Confirmed.
     *
     * @param cid
     * @param date
     */
    @Override
    public void autoUpdateBookings(int cid, LocalDate date) {
        List<User> userList = userServiceImpl.getAllUsers();
        //removing all the users that is not from the same company
        userList.removeIf(temp -> temp.getCompany().getCid() != cid);

        List<Bookings> bookingsList = bookingsRepository.findAll();
        //remove all the bookings that does not have the same booking date
        bookingsList.removeIf(temp -> !temp.getBDate().toString().equals(date.toString()));
        //remove all the bookings that have its status to be set to "Confirmed"
        bookingsList.removeIf(temp -> temp.getStatus().equals("Confirmed"));

        //so now my bookingsList will only contain those that are pending on that date.

        //removing all entries from the bookingsList as long as they are from the same company
        bookingsList = removeDuplicates(bookingsList, userList);

        //Getting the smallest BID
        List<Integer> bookingListBID = new ArrayList<>();
        bookingsList.forEach(temp -> bookingListBID.add(temp.getBid()));
        int smallestBID = Collections.min(bookingListBID);

        //so now i will update the smallestBID's status to be Confirmed.
        bookingsRepository.updateBookings(smallestBID);
    }

    /**
     * Takes in a bookingsList and a user list and will check if they are in the same company.
     * if they are not from the same company, will then remove that entry from the booking list.
     *
     * @param bookingsList
     * @param userList
     * @return List of bookings made by the same company
     */
    @Override
    public List<Bookings> removeDuplicates(List<Bookings> bookingsList, List<User> userList) {
        Iterator<Bookings> bookingsIterator = bookingsList.iterator();
        while (bookingsIterator.hasNext()) {
            Boolean isFromSameCompany = false;
            Bookings temp = bookingsIterator.next();
            for (User u : userList) {
                if (temp.getUser().getEmail() == u.getEmail()) {
                    isFromSameCompany = true;
                }
            }
            if (!isFromSameCompany) {
                bookingsIterator.remove();
            }

        }
        return bookingsList;
    }

    /**
     * Returns a bookings based on its id
     *
     * @param id
     * @return the booking itself if it can be found or null otherwise.
     */
    @Override
    public Bookings getBookingsById(int id) {
        return bookingsRepository.findById(id).orElse(null);
    }

    /**
     * Given a specific booking ID, we will first retrieve the particular booking
     * before setting its booking date and status to be the new booking and returning it.
     *
     * @param id
     * @param bookings
     * @return the newly updated Bookings.
     */
    @Override
    public Bookings updateBookings(int id, Bookings bookings) {
        return bookingsRepository.findById(id).map(booking -> {
            booking.setBDate(bookings.getBDate());
            booking.setStatus(bookings.getStatus());
            return bookingsRepository.save(booking);
        }).orElse(null);
    }

    /**
     * Delete a booking based on its object
     *
     * @param bookings
     */
    @Override
    public void delete(Bookings bookings) {
        bookingsRepository.delete(bookings);
    }

    /**
     * Remove a booking with the given id
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a booking will also remove all its associated reviews
     */
    @Override
    public void deleteById(int id) {
        bookingsRepository.deleteById(id);
    }

    /**
     * This method is to ensure that each user is only allowed to book 1 bookings for that particular date.
     *
     * @param userEmail
     * @param date
     * @return number of bookigns for the specific user and date
     */
    @Override
    public int checkForDuplicateBookings(String userEmail, LocalDate date) {
        return bookingsRepository.checkForDuplicateBookings(userEmail, date);
    }

    /**
     * Saves the bookings instance to the database.
     *
     * @param bookings
     * @return the booking itself.
     */
    @Override
    public Bookings save(Bookings bookings) {
        return bookingsRepository.save(bookings);
    }

    /**
     * This will return all the bookings that is in that the specific user made.
     *
     * @param email
     * @return
     */
    @Override
    public int getBookingsCountByEmail(String email) {
        return bookingsRepository.findBookingsCountByEmail(email);
    }

    /**
     * Get the list of bookings that is for the email specified.
     *
     * @param email
     * @return list bookings that is made by the user.
     */
    @Override
    public List<Bookings> getBookingByUser(String email) {

        if (userServiceImpl.getUserByEmail(email) == null) {
            return null;

        }
        ArrayList<Bookings> toReturn = new ArrayList<>();
        for (Bookings b : bookingsRepository.findAll()) {
            if (b.getUser().getEmail().equals(email)) {
                toReturn.add(b);
            }
        }

        return toReturn;
    }
}



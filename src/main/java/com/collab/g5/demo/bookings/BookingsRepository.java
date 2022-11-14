package com.collab.g5.demo.bookings;

import com.collab.g5.demo.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * We only need this interface declaration
 * Spring will automatically generate an implementation of the repo
 * <p>
 * JpaRepository provides more features by extending PagingAndSortingRepository, which in turn extends CrudRepository
 */
@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    //this will essentially get the amount of bookings that the user has booked for this particular month.
    @Query("SELECT count(b) FROM Bookings b WHERE b.user.email = ?1 and MONTH(b.bDate)=MONTH(now()) and b.status='Confirmed'")
    int findBookingsCountByEmail(String email);

    @Query(value = "select count(*) from company c inner join \n" +
            "( select * from bookings b inner join user u where b.user_useremail = u.email ) as bookinguser using (cid) \n" +
            "where c.cid = ?1 and bookinguser.status = \"confirmed\" and bookinguser.b_date = ?2", nativeQuery = true)
    int getBookingsCountByDate(int cid, LocalDate bDate);

    @Query(value = "select count(*) from bookings b where MONTH(b.b_date) =  ?2 and b.user_useremail =?1 ", nativeQuery = true)
    int getBookingsCountByUserAndMonth(String email, int month);

    @Modifying
    @Transactional
    @Query(value = "update bookings b set status = \"Confirmed\"  where bid = ?1", nativeQuery = true)
    void updateBookings(int bid);

    @Query(value = "select count(*) from bookings where user_useremail = ?1 and b_date = ?2", nativeQuery = true)
    int checkForDuplicateBookings(String userEmail, LocalDate date);

    List<Bookings> findAllByUser(User user);

    @Query("SELECT b FROM Bookings b WHERE b.user.email = ?1")
    List<Bookings> findBookingsByEmail(String email);


}

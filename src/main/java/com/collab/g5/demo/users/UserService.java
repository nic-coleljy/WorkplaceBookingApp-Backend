package com.collab.g5.demo.users;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    @Query("select u from User u")
    User getUserByEmail(String Email);

    void addNewUser(User newUser);

    void delete(User user);

    /**
     * Change method's signature: do not return a value for delete operation
     *
     * @param userEmail
     */
    void deleteById(String userEmail);

    boolean getVaccinatedByEmail(String email);

    User updatePassword(String password, User user);

    User updateFname(String fname, User user);

    User updateLName(String lName, User user);

    User updateEmail(String email, User user);

    User updateVaccination(boolean vaccination, User user);

    void forgetPassword(String email);

    User setForgetPassword(String email, String password);


//    boolean containsUser(String userEmail);

}

package com.collab.g5.demo.users;

import com.collab.g5.demo.email.Mail;
import com.collab.g5.demo.email.MailService;
import com.collab.g5.demo.security.WebSecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    /**
     * Declaring the variables.
     */
    private UserRepository userRepository;
    private MailService mailService;
    private WebSecurityConfig webSecurityConfig;

    /**
     * Instantiating all the variables.
     *
     * @param userRepository
     * @param mailService
     * @param webSecurityConfig
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, MailService mailService, WebSecurityConfig webSecurityConfig) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.webSecurityConfig = webSecurityConfig;

    }

    /**
     * This function will get triggered when the user forgets his password.
     * It will send an email and to the user and provides them with a link to reset their password.
     *
     * @param email
     */
    @Override
    public void forgetPassword(String email) {
        User user = userRepository.getById(email);
        Mail mail = new Mail();
        mail.setMailFrom("weloveis211@gmail.com");
        String userEmail = user.getEmail();
        System.out.println("User Email in Service Implementation is " + userEmail);
        System.out.println("user Object is " + user);
        mail.setMailTo(userEmail);
        mail.setMailSubject("Welcome to Company X ," + user.getEmail());
        mail.setMailContent("Dear " + user.getName() + ",\n\n" +
                "It appears that you have trouble logging in.\n" +

                "Please use the link below " +
                " https://frontend-five-eta.vercel.app/UpdatePassword " +
                "To reset your password.\n" +

                "Warm Regards, \n"
                + "CS203G5 Team"

        );
        mailService.sendEmail(mail);
    }

    /**
     * This is to persist a user into our database and an email will be sent out to them with the password.
     *
     * @param user
     */
    @Override
    public void addNewUser(User user) {
        Mail mail = new Mail();
        mail.setMailFrom("weloveis211@gmail.com");
        String userEmail = user.getEmail();
        System.out.println("User Email in Service Implementation is " + userEmail);
        System.out.println("user Object is " + user);
        mail.setMailTo(userEmail);
        mail.setMailSubject("Welcome to Company X ," + user.getEmail());
        mail.setMailContent("Dear " + user.getName() + "\n\n" +
                "We are excited to get you on board our application.\n " +
                "\n" +
                "You may login with these details:\n" +
                "Username - " + userEmail + "\n" +
                "Password - password1. \n " +
                "\n" +
                "Please change your password in profile settings upon entering the application. \n" +
                "Using our application you will be able to check-in and book slots with greater convenience.\n" +
                "In addition, you may choose to keep up to date with the latest news and covid regulations\n" +
                "Wishing you a great experience!"
                + "\nWarm Regards, \n"
                + "CS203G5 Team"

        );
        mailService.sendEmail(mail);

        //setting default password to be password1
        String encodedPassword = webSecurityConfig.passwordEncoder().encode("password1");
        user.setVaccinated(false);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    /**
     * This will retrieve all the users from the database.
     *
     * @return a List of users that are in the database.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * This will retrieve a user based on their email.
     *
     * @param Email
     * @return Returns a user if it exists based on the email or else it will return null.
     */
    @Override
    public User getUserByEmail(String Email) {

        Optional<User> optionalUser = userRepository.findByEmail(Email);
        if (optionalUser.isEmpty()) {
            return null;
        }

        return optionalUser.get();
    }

    /**
     * Deletes a user based on the user object
     *
     * @param user
     */
    @Override
    public void delete(User user) {
        userRepository.delete(user);

    }

    /**
     * Remove a user with the given userEmail
     * Spring Data JPA does not return a value for delete operation
     * Cascading: removing a user will also remove all its associated information
     */
    @Override
    public void deleteById(String userEmail) {
        userRepository.deleteById(userEmail);
    }

    /**
     * Queries the databaase for the user vaccinated status
     *
     * @param email
     * @return True or False depedning if the user is vaccinated.
     */
    @Override
    public boolean getVaccinatedByEmail(String email) {
        return userRepository.getVaccinatedByEmail(email);
    }

    /**
     * This allows the user to update his password and will take in a string and a user object
     * so as to uniquely identify each user from another.
     *
     * @param password
     * @param user
     * @return User object with the password updated.
     */
    @Override
    public User updatePassword(String password, User user) {
        User userExist = userRepository.getById(user.getEmail());
        if (userExist == null) {
            // throw an exception
            return null;
        }
        String encodedPassword = webSecurityConfig.passwordEncoder().encode(password);
        userExist.setPassword(encodedPassword);
        userRepository.save(userExist);
        return userExist;
    }

    /**
     * Updates the first name of the particular user.
     * It will take in the first name and the user object, and will first query if the user exists.
     * If the user exists, it will then update the first name of the user and persist this changes in the database
     * Else it will return null.
     *
     * @param fName
     * @param user
     * @return User with the updated first name if it exists, else return null
     */
    @Override
    public User updateFname(String fName, User user) {
        User userExist = userRepository.getById(user.getEmail());
        if (userExist == null) {
            // throw an exception
            return null;
        }

        userExist.setFname(fName);
        userRepository.save(userExist);
        return userExist;
    }

    /**
     * Updates the last name of the particular user.
     * It will take in the last name and the user object, and will first query if the user exists.
     * If the user exists, it will then update the last name of the user and persist this changes in the database
     * Else it will return null.
     *
     * @param lName
     * @param user
     * @return User with the updated last name if it exists, else return null
     */
    @Override
    public User updateLName(String lName, User user) {
        User userExist = userRepository.getById(user.getEmail());
        if (userExist == null) {
            // throw an exception
            return null;
        }

        userExist.setLname(lName);
        userRepository.save(userExist);
        return userExist;
    }

    /**
     * Updates the email of the particular user.
     * It will take in the email and the user object, and will first query if the user exists.
     * If the user exists, it will then update the email of the user and persist this changes in the database
     * Else it will return null.
     *
     * @param email
     * @param user
     * @return User with the updated last name if it exists, or else return null.
     */
    @Override
    public User updateEmail(String email, User user) {
        User userExist = userRepository.getById(user.getEmail());
        if (userExist == null) {
            // throw an exception
            return null;
        }

        userExist.setEmail(email);
        userRepository.save(userExist);
        return userExist;
    }

    /**
     * Updates the vaccination status of the particular user.
     * It will take in the vaccination status and the user object, and will first query if the user exists.
     * If the user exists, it will then update the vaccination status of the user and persist this changes in the database
     * Else it will return null.
     *
     * @param vaccination
     * @param user
     * @return User with the updated last name if it exists, or else return null.
     */
    @Override
    public User updateVaccination(boolean vaccination, User user) {
        User userExist = userRepository.getById(user.getEmail());
        if (userExist == null) {
            // throw an exception
            return null;
        }

        userExist.setVaccinated(vaccination);
        userRepository.save(userExist);
        return userExist;
    }

    /**
     * Triggers when the user forgets his/her password
     * Takes in a email and password,  and will first query if the user exists.
     * If the user exists, proceeds to update the password
     * Else, return null.
     *
     * @param email
     * @param password
     * @return User with the updated last name if it exists, or else return null.
     */
    @Override
    public User setForgetPassword(String email, String password) {

        User user = getUserByEmail(email);

        if (user == null) {
            return null;
        }

        String encodedPassword = webSecurityConfig.passwordEncoder().encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user;


    }

}

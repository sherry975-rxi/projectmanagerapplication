package project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Repository.UserRepository;
import project.dto.UserDTO;
import project.model.Address;
import project.model.Profile;
import project.model.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Class UserContainer that contains all lists and methods to build lists of
 * users
 *
 * @author Group3
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private List<User> usersContainer;


    /**
     * Constructor for UserContainer includes usersList creation
     */
    public UserService() {
        this.usersContainer = new ArrayList<>();
    }
    
    /**
     * Constructor created for JPA purposes.
     * @param userRepository
     */
    public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
}

    /**
     * Creates an instance of User using Creator pattern that creates User objects in a manner suitable to the situation.
     *
     * @param name     Name of the user
     * @param email    Email of the user
     * @param idNumber idNumber of the user
     * @param function Function of the user
     * @param phone    Phone of the user
     * @param street   Street of the user
     * @param zipCode  ZipCode of the user
     * @param city     City of the user
     * @param district District of the user
     * @param country  Country of the user
     * @return the user created and instantiated
     */
    public User createUser(String name, String email, String idNumber, String function, String phone, String street,
                           String zipCode, String city, String district, String country) {

        User newUser = new User(name, email, idNumber, function, phone);

        Address newAddress = newUser.createAddress(street, zipCode, city, district, country);

        newUser.addAddress(newAddress);

        userRepository.save(newUser);

        return newUser;
    }


    /**
     * Creates a user from a userDTO
     *
     * @param userDTO UserDTO to create a user
     */
    public void createUserWithDTO(UserDTO userDTO) {

        // Instantiates the user
        User newUser = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getIdNumber(), userDTO.getFunction(),
                userDTO.getPhone());

        // Creates a new address
        Address newAddress = newUser.createAddress(userDTO.getStreet(), userDTO.getZipCode(), userDTO.getCity(),
                userDTO.getDistrict(), userDTO.getCountry());

        // Adds the address to user list
        newUser.addAddress(newAddress);

        // Sets the user password
        newUser.setPassword(userDTO.getPassword());

        // Adds the user to User repository
        this.addUserToUserRepositoryX(newUser);

    }

    /**
     * Method that saves the user to the database
     *
     * @param user user to save
     */
    public void addUserToUserRepositoryX(User user) {
        this.userRepository.save(user);
    }

    /**
     * This method allows the administrator to see if a given user already exists in
     * company (userContainer) DB
     *
     * @param addedUser user
     * @return TRUE if user exists in company. FALSE if user doesn’t exist
     * in the company's userContainer
     */
    public boolean isUserinUserContainer(User addedUser) {

        boolean result = false;
       if ( this.userRepository.findByEmail(addedUser.getEmail()) != null) {
           result = true;
       }
        return result;
    }

    /**
     * This method returns a copy of the list of all users (usersContainer)
     *
     * @return allUsers This is the copy of the List of all Users in the userContainer
     */
    public List<User> getAllUsersFromUserContainer() {

        return userRepository.findAll();

    }

    /**
     * This method returns all users that possess a certain email address. It fetches information directly from the Database.
     * \
     *
     * @param email parameter used to fetch users from the DataBase
     * @return all users that possess a certain email address
     */
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    /**
     * This method feeds the list of all Users in the Company (userContainer) with the user data that is in the DB
     */
    public void updateUserContainer() {
        usersContainer.clear();
        this.userRepository.findAll().forEach(usersContainer::add);
    }


    /**
     * This method returns a list of all active collaborators in the Company
     * accessed by DB
     *
     * @return all active Collaborators
     */
    public List<User> getAllActiveCollaboratorsFromRepository() {
        return userRepository.findAllByUserProfile(Profile.COLLABORATOR);
    }

    /**
     * This method allows the Administrator to access the user list (userContainer) and search users
     * by parts of email. This is achieved by using the .contains() method.
     *
     * @param partOfEmail This is not the complete user email but a part of the email string
     * @return userListThatContainsPiecesOfEmailString. The list with users that have
     * the query piece of email
     */
    public List<User> searchUsersByPartsOfEmail(String partOfEmail) {

        List<User> userListThatContainsPiecesOfEmailString = new ArrayList<>();

        List<User> allUsers = userRepository.findAll();

        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getEmail().contains(partOfEmail)) {
                userListThatContainsPiecesOfEmailString.add(allUsers.get(i));
            }
        }
        return userListThatContainsPiecesOfEmailString;
    }

    /**
     * This method allows the administrator to search
     * users in the Company by profile. This method accesses the DB
     *
     * @param searchProfile Profile of a user
     * @return list of users from the userContainer with users that possess a certain profile
     */
    public List<User> searchUsersByProfile(Profile searchProfile) {

        return userRepository.findAllByUserProfile(searchProfile);
    }

    /**
     * This method checks if an e-mail inserted by the user is valid or not
     *
     * @param email email
     * @return TRUE if email is valid. FALSE if email is invalid
     */
    public boolean isEmailAddressValid(String email) {

        boolean result = true;

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
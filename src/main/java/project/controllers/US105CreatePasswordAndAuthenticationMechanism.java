package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.User;
import project.services.UserService;

@Controller
public class US105CreatePasswordAndAuthenticationMechanism {

    @Autowired
    private UserService userService;

    public US105CreatePasswordAndAuthenticationMechanism() {
        //Empty constructor created for JPA integration tests

    }



    /**
     * This method set a new password to user and change the variable firstLogin to false and
     * save the changes in DB
     *
     * @param user
     * @param newPassword
     */
    public void setUserPassword(User user, String newPassword) {

        user.setPassword(newPassword);

        setFirstLogin(user);

        updateUser(user);
    }

    /**
     *  Method that saves the user to the database
     *
     * @param user
     */
    private void updateUser(User user) {

        userService.updateUser(user);
    }

    /**
     * This method change the user variable firstLogin to false.
     *
     * @param user
     */
    private void setFirstLogin(User user) {

        user.setFirstLogin(false);
    }

}

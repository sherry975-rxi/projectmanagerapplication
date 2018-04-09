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

        user.setHasLoggedIn(true);
    }

    /**
     * Method to find and return the question associated with a specific user
     * @param user user whose question we are searching for
     * @return the question of the user
     */
    public String questionAuthentication(User user){

        return user.getQuestion();

    }

    /**
     * Method to determine if the answer provided is the right one
     * @param answer the answer provided by the user
     * @param user the user trying to login
     * @return true if it's the right answer, false if it isn't
     */
    public boolean isRightAnswer(String answer, User user){
        return answer.equalsIgnoreCase(user.getAnswer());
    }

}

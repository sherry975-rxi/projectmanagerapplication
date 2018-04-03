package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.Profile;
import project.model.User;
import project.services.UserService;

@Controller
public class US110andUS112SetUserProfileController {

	@Autowired
	private UserService userService;

	public US110andUS112SetUserProfileController() {
		//Empty constructor created for JPA integration tests

	}

	public UserService getUserContainer() {
		return userService;
	}

	/**
	 * This method sets the assigned user as Director
	 * 
	 */
	public void setUserAsDirector(User user) {
		user.setUserProfile(Profile.DIRECTOR);
		userService.updateUser(user);
	}

	/**
	 * This method sets the assigned user as Collaborator
	 * 
	 */
	public void setUserAsCollaborator(User user) {
		user.setUserProfile(Profile.COLLABORATOR);
		userService.updateUser(user);
	}

	/**
	 * This is a simple utility method to convert the User's profile into a String
	 * to be displayed in the UI
	 * 
	 * @return the String with the User's profile
	 */
	public String userProfileAsString(User user) {
		String output;
		switch (user.getUserProfile()) {
		case DIRECTOR:
			output = "Director";
			break;
		case COLLABORATOR:
			output = "Collaborator";
			break;
		default:
			output = "Unassigned";
		}

		return output;
	}
}

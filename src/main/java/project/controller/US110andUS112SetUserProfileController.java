package project.controller;

import project.model.Profile;
import project.model.User;

public class US110andUS112SetUserProfileController {

	/**
	 * This method sets the assigned user as Director
	 * 
	 */
	public void setUserAsDirector(User user) {
		user.setUserProfile(Profile.DIRECTOR);
	}

	/**
	 * This method sets the assigned user as Collaborator
	 * 
	 */
	public void setUserAsCollaborator(User user) {
		user.setUserProfile(Profile.COLLABORATOR);
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

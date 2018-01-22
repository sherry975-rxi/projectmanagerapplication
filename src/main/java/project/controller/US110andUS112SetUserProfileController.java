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

}

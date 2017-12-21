package project.controller;

import project.model.Profile;
import project.model.User;

public class SetUserProfileController {
	
	User toChangeProfile;
	
	
	/**
	 * This constructor receives a user whose profile will be changed
	 * 
	 * @param User
	 */
	public SetUserProfileController(User toChangeProfile) {
		
		this.toChangeProfile=toChangeProfile;
	}
	
	/**
	 * This method sets the assigned user as Director
	 * 
	 */
	public void setUserAsDirector() {
		this.toChangeProfile.setUserProfile(Profile.DIRECTOR);
	}
	
	/**
	 * This method sets the assigned user as Collaborator
	 * 
	 */
	public void setUserAsCollaborator() {
		this.toChangeProfile.setUserProfile(Profile.COLLABORATOR);
	}

}

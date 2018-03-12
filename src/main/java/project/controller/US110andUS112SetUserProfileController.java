package project.controller;

import project.model.Profile;
import project.model.ProjectContainer;
import project.model.User;
import project.model.UserContainer;

public class US110andUS112SetUserProfileController {
	
	private UserContainer userContainer;
	private ProjectContainer projectContainer;

	public US110andUS112SetUserProfileController() {
		userContainer = new UserContainer();
		projectContainer = new ProjectContainer();

	}

	public UserContainer getUserContainer() {
		return userContainer;
	}

	public ProjectContainer getProjectContainer() {
		return projectContainer;
	}

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
			output = "Director2";
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

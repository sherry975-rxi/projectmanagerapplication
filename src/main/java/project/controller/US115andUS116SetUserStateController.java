package project.controller;

import project.Services.UserService;
import project.model.User;

public class US115andUS116SetUserStateController {


	User toChangeState;
	UserService userContainer;


	/**
	 * This controller recieves a user whose state is to be changed
	 * 
	 * 
	 * @param toChange
	 *            selected User
	 */
	public US115andUS116SetUserStateController(User toChange) {
		this.toChangeState = toChange;
	}

	/**
	 * 
	 * This method is called only after the controller is created, and changes the
	 * user's state
	 *
	 * Then, it saves the updated user to the database	 *
	 * 
	 */
	public void changeUserState() {
		toChangeState.changeUserState();
		userContainer.addUserToUserRepositoryX(toChangeState);

	}

	/**
	 * This is a simple utility method to convert the User's state into a String to
	 * be displayed in the UI
	 * 
	 * @return the String with the User's state
	 */
	public String userStateAsString() {
		String output;

		if (toChangeState.isSystemUserStateActive()) {
			output = "(ACTIVE)";
		} else {
			output = "(DISABLED)";
		}

		return output;
	}
}

package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.Services.UserService;
import project.model.User;


public class US115andUS116SetUserStateController {

	private UserService userContainer;

	User toChangeState;
	public User getToChangeState() {
		return toChangeState;
	}

	public void setToChangeState(User toChangeState) {
		this.toChangeState = toChangeState;
	}





	public UserService getUserContainer() {
		return userContainer;
	}

	public void setUserContainer(UserService userContainer) {
		this.userContainer = userContainer;
	}

	/**
	 * This controller recieves a user whose state is to be changed
	 * 
	 * 
	 * @param toChange
	 *            selected User
	 */
	public US115andUS116SetUserStateController() {	}

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

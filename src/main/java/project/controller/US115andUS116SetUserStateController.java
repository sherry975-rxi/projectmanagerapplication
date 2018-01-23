package project.controller;

import project.model.User;

public class US115andUS116SetUserStateController {

	User toChangeState;

	/**
	 * This controller recieves a user whose state is to be changed
	 * 
	 * 
	 * @param the
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
	 * 
	 */
	public void changeUserState() {
		toChangeState.changeUserState();
	}

	/**
	 * This is a simple utility method to convert the User's data into a String to
	 * be displayed in the UI
	 * 
	 * @return the String with the User's ID, name, state, profile
	 */
	public String userDataAsString() {
		String output = toChangeState.getIdNumber() + " (";

		if (toChangeState.isUserActive())
			output += "ACTIVE) - ";
		else
			output += "DISABLED) - ";

		switch (toChangeState.getUserProfile()) {
		case DIRECTOR:
			output += "Director";
			break;
		case COLLABORATOR:
			output += "Collaborator";
			break;
		default:
			output += "Unassigned";
		}

		output += ": " + toChangeState.getName();

		return output;
	}
}

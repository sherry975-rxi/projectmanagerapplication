package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.User;
import project.services.UserService;

@Controller
public class US115andUS116SetUserStateController {

	@Autowired
	private UserService userContainer;

	private User toChangeState;

	public US115andUS116SetUserStateController() {
		//Empty constructor created for JPA integration tests
	}

	public User getToChangeState() {
		return toChangeState;
	}

	public void setToChangeState(User toChangeState) {
		this.toChangeState = toChangeState;
	}


	/**
	 * 
	 * This method is called only after the controller is created, and changes the
	 * user's state
	 *
	 * Then, it saves the updated user to the database *
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

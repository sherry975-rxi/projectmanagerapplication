package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.User;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class US130ListUsersController {

	@Autowired
	private UserService userContainer;

	List<User> userList;

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public US130ListUsersController() {
		//Empty constructor created for JPA integration tests
	}

	/**
	 * This controllers returns a list of all users in the User repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<String> listUsersController() {
		this.userList = userContainer.getAllUsersFromUserContainer();
		List<String> userListAsString = new ArrayList<>();

		for (int i = 0; i < userList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowUser = "[" + showIndex.toString() + "] \n" + userDataToString(userList.get(i));
			userListAsString.add(toShowUser);
		}

		return userListAsString;

	}

	/**
	 * This method selects a User and returns it to the UI, to be handled by the
	 * Admin
	 * 
	 * @return User to be Returned and handled by the Admin
	 */
	public User selectUser(int index) {
		User result;
		int actualIndex = index - 1;
		if (actualIndex >= 0 && actualIndex < userList.size()) {
			result = userList.get(actualIndex);
		} else {
			result = null;
		}
		return result;

	}

	/**
	 * This is a utility method that converts a User object into a String of data,
	 * to be displayed in the UI
	 *
	 * @param toConvert
	 *            to be converted
	 * @return String of the user's data
	 */
	public String userDataToString(User toConvert) {
		String profile = "";
		switch (toConvert.getUserProfile()) {
		case DIRECTOR:
			profile = "Director";
			break;
		case COLLABORATOR:
			profile = "Collaborator";
			break;
		default:
			profile = "Unassigned";
		}

		return toConvert.getIdNumber() + " - " + profile + ": " + toConvert.getName() + " (" + toConvert.getEmail()
				+ "; " + toConvert.getPhone() + ") - " + toConvert.getFunction();
	}
}

package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.User;

public class US130ListUsersController {

	List<User> userList;
	User selectedUser = null;

	/**
	 * This controller returns a list of all users in the User Repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<String> listUsersController() {
		this.userList = Company.getTheInstance().getUsersRepository().getAllUsersFromRepository();
		List<String> userListAsString = new ArrayList<>();

		for (int i = 0; i < userList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowUser = "::Index " + showIndex.toString() + ":: \n" + userDataToString(userList.get(i));
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
		int actualIndex = index - 1;
		if (actualIndex >= 0 && actualIndex < userList.size()) {
			selectedUser = userList.get(actualIndex);
		}
		return selectedUser;

	}

	/**
	 * This is a utility method that converts a User object into a String of data,
	 * to be displayed in the UI
	 * 
	 * @param User
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

		String data = toConvert.getIdNumber() + " - " + profile + ": " + toConvert.getName() + " ("
				+ toConvert.getEmail() + "; " + toConvert.getPhone() + ") - " + toConvert.getFunction();

		return data;
	}
}

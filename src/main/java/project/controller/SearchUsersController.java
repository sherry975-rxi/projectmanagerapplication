package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class SearchUsersController {

	List<User> userList;
	User selectedUser = null;

	/**
	 * Este controlador permite ao Administrador pesquisar utilizadores do sistema
	 * Pode pesquisar pelo email, ou pelo perfil de utilizador
	 * 
	 */

	/**
	 * @param profileToSearch
	 *            The type of of profile to search for
	 * @return This method returns a list of user's Data Strings with the profile
	 *         that match the user profile
	 */
	public List<String> searchUsersByProfileController(Profile profileToSearch) {
		this.userList = Company.getTheInstance().getUsersRepository().searchUsersByProfile(profileToSearch);

		List<String> userListAsString = new ArrayList<>();

		for (int i = 0; i < userList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowUser = showIndex.toString() + ":: " + userDataToString(userList.get(i));
			userListAsString.add(toShowUser);
		}

		return userListAsString;

	}

	/**
	 * @param emailToSearch
	 *            The string of email to search for
	 * @return This method returns a list of user's Data Strings with an email that
	 *         contain the same text that the user typed
	 */
	public List<String> searchUsersByEmailController(String emailToSearch) {
		this.userList = Company.getTheInstance().getUsersRepository().searchUsersByEmail(emailToSearch);

		List<String> userListAsString = new ArrayList<>();

		for (int i = 0; i < userList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowUser = showIndex.toString() + ":: " + userDataToString(userList.get(i));
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

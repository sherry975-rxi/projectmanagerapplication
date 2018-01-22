package project.controller;

import java.util.List;

import project.model.Company;
import project.model.Profile;
import project.model.User;

public class SearchUsersController {

	/**
	 * Este controlador permite ao Administrador pesquisar utilizadores do sistema
	 * Pode pesquisar pelo email, ou pelo perfil de utilizador
	 * 
	 */

	/**
	 * @param profileToSearch
	 *            The type of of profile to search for
	 * @return This method returns a list of users with the profile that match the
	 *         user profile
	 */
	public List<User> searchUsersByProfileController(Profile profileToSearch) {

		return Company.getTheInstance().getUsersRepository().searchUsersByProfile(profileToSearch);

	}

	/**
	 * @param emailToSearch
	 *            The string of email to search for
	 * @return This method returns a list of users with an email that contain the
	 *         same text that the user typed
	 */
	public List<User> searchUsersByEmailController(String emailToSearch) {

		return Company.getTheInstance().getUsersRepository().searchUsersByEmail(emailToSearch);

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

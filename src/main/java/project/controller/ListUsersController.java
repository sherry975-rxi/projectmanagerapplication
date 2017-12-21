package project.controller;

import java.util.List;

import project.model.Company;
import project.model.User;

public class ListUsersController {

	
	/**
	 *  This controller returns a list of all users in the User Repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<User> listUsersController() {

		return Company.getTheInstance().getUsersRepository().getAllUsersFromRepository();

	}
}

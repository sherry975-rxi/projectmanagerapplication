package project.controller;

import java.util.ArrayList;
import java.util.List;

import project.model.Company;
import project.model.EffortUnit;
import project.model.Project;
import project.model.User;

/**
 * @author Group 3
 * 
 *         This class implements the controller that allows a user to create a
 *         Project.
 *
 */
public class US301CreateProjectController {

	Company myCompany = Company.getTheInstance();
	List<User> activeCollaboratorList;
	User selectedUser = null;
	Project createdProject = null;

	/**
	 * This method creates a project from the controller by calling the create
	 * project method in project repository class, and adds it to the project
	 * repository.
	 * 
	 * @param name
	 *            Name to be set as the project name
	 * @param description
	 *            Description to be set as the project description
	 * @param projectManager
	 *            User to be set as the project manager
	 * 
	 * @return Returns the project created
	 */
	public Project createProject(String name, String description, User projectManager) {

		createdProject = myCompany.getProjectsRepository().createProject(name, description, projectManager);

		myCompany.getProjectsRepository().addProjectToProjectRepository(createdProject);

		return createdProject;
	}

	/**
	 * This controller returns a list of all activeCollaborators in the User
	 * Repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<String> listActiveCollaborators() {
		this.activeCollaboratorList = myCompany.getUsersRepository().getAllActiveCollaboratorsFromRepository();
		List<String> userListAsString = new ArrayList<>();

		for (int i = 0; i < activeCollaboratorList.size(); i++) {
			Integer showIndex = i + 1;
			String toShowUser = "[" + showIndex.toString() + "] \n" + userDataToString(activeCollaboratorList.get(i));
			userListAsString.add(toShowUser);
		}

		return userListAsString;

	}

	/**
	 * This method selects a User and returns it to the UI, to be assigned as
	 * ProjectManager by the Director
	 * 
	 * @return User to be Returned and handled by the Director
	 */
	public User selectCollaborator(int index) {
		int actualIndex = index - 1;
		if (actualIndex >= 0 && actualIndex < activeCollaboratorList.size()) {
			selectedUser = activeCollaboratorList.get(actualIndex);
		}
		return selectedUser;

	}

	/**
	 * This method changes the effort Units from the default (hours) to
	 * person/Month. It can only be called after a project has been created
	 * 
	 */
	public void changeEffortUnitToPersonMonth() {

		createdProject.setEffortUnit(EffortUnit.PERSON_MONTH);

	}

	/**
	 * This method is called after the project is created and sets the project's
	 * budget as the chosen value. By default, budget is set to "0"
	 * 
	 * @param Integer
	 *            value that will become the project's budget
	 */
	public void changeBudget(int budget) {

		createdProject.setProjectBudget(budget);
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

		String data = toConvert.getIdNumber() + ": " + toConvert.getName() + " (" + toConvert.getEmail() + "; "
				+ toConvert.getPhone() + ") - " + toConvert.getFunction();

		return data;
	}

}

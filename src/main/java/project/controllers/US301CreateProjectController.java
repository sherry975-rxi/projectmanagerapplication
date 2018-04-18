package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import project.model.EffortUnit;
import project.model.Project;
import project.model.User;
import project.services.ProjectService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group 3
 * 
 *         This class implements the controllers that allows a user to create a
 *         Project.
 *
 */
@Controller
public class US301CreateProjectController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;

	private List<User> activeCollaboratorList;
	private User selectedUser = null;
	private Project createdProject = null;

	public US301CreateProjectController() {
		//Empty constructor created for JPA integration tests
	}

	public void setActiveCollaboratorList(List<User> activeCollaboratorList) {
		this.activeCollaboratorList = activeCollaboratorList;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void setCreatedProject(Project createdProject) {
		this.createdProject = createdProject;
	}

	/**
	 * This method creates a project from the controllers by calling the create
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

		createdProject = projectService.createProject(name, description, projectManager);

		projectService.addProjectToProjectContainer(createdProject);

		return createdProject;
	}

	/**
	 * This controllers returns a list of all activeCollaborators in the User
	 * repository
	 * 
	 * @return List<User> a copy of the User database
	 */
	public List<String> listActiveCollaborators() {

		activeCollaboratorList = this.userService.getAllActiveCollaboratorsFromRepository();

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

		createdProject.setEffortUnit(EffortUnit.PM);

	}

	/**
	 * This method is called after the project is created and sets the project's
	 * budget as the chosen value. By default, budget is set to "0"
	 * 
	 * @param budget
	 *            value that will become the project's budget
	 */
	public void changeBudget(int budget) {

		createdProject.setProjectBudget(budget);
        projectService.updateProject(createdProject);
	}

	/**
	 * This method allows the direct to select which cost calculation method he wants
	 * to make available for the project
	 */
	public void selectCalculationMethods(List<Integer> allowedMethods) {
	    createdProject.setAvailableCalculationMethods(allowedMethods);
	    createdProject.setCalculationMethod(allowedMethods.get(0));
	    projectService.updateProject(createdProject);

    }

    public List<Integer> allowDisableCalculationMethods(List<Integer> list, Integer selected) {
        if(!list.contains(selected)) {
            list.add(selected);
        } else if (list.size()>1){
            list.remove(selected);
        }

        return list;
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

		return toConvert.getIdNumber() + ": " + toConvert.getName() + " (" + toConvert.getEmail() + "; "
				+ toConvert.getPhone() + ") - " + toConvert.getFunction();
	}

}

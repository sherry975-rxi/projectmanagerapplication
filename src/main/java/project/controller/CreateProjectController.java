package project.controller;

import project.model.Company;
import project.model.Project;
import project.model.User;

public class CreateProjectController {

	Company myCompany;
	String name;
	String description;
	User projectManager;

	/**
	 * Constructor for project creation controller
	 * 
	 * @param name
	 *            Name to be set as the project name
	 * @param description
	 *            Description to be set as the project description
	 * @param projectManager
	 *            User to be set as the project manager
	 */
	public CreateProjectController(String name, String description, User projectManager) {
		this.myCompany = Company.getTheInstance();
		this.name = name;
		this.description = description;
		this.projectManager = projectManager;
		createProjectController(name, description, projectManager);
	}

	/**
	 * This method creates a project from the controller by calling the create
	 * project method in project repository class, and returns the project created.
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
	public Project createProjectController(String name, String description, User projectManager) {

		return myCompany.getProjectsRepository().createProject(name, description, projectManager);
	}

}

package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Repository.ProjectsRepository;

/**
 * Company Class for building Repositories of Users and Projects (according to
 * certain specifications)
 * 
 * @author Group3
 *
 */
@Service
public final class Company {

	private UserContainer usersContainer;
	private ProjectContainer projectContainer;
	private static Company theInstance;

	/**
	 * Constructor for Company includes usersContainer creation and
	 * projectContainer creation
	 */
	@Autowired
	private Company(project.Repository.UserRepository userRepository, ProjectsRepository projectRepository) {
		this.usersContainer = new UserContainer(userRepository);
		this.projectContainer = new ProjectContainer(projectRepository);
		theInstance = this;
	}

	private Company() {
		this.usersContainer = new UserContainer();
		this.projectContainer = new ProjectContainer();
	}



	/**
	 * Method to instantiate one Company only.
	 * 
	 * @return the only instance of Company that the program is allowed to have
	 */
	public static synchronized Company getTheInstance() {
		if (theInstance == null)
			theInstance = new Company();

		return theInstance;
	}

	/**
	 * This method clears the Company.
	 */
	public static void clear() {

		theInstance = null;

	}

	/**
	 * This method returns the Repository of projects (projectContainer)
	 * 
	 * @return projectContainer This is the Repository of all Projects created
	 */
	public ProjectContainer getProjectsContainer() {
		return this.projectContainer;
	}

	/**
	 * This method returns the Repository of users (usersContainer)
	 * 
	 * @return usersContainer This is the Repository of all Users created
	 */
	public UserContainer getUsersContainer() {
		return this.usersContainer;
	}
}
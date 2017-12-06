package main.java.project.model;

/**
 * Company Class for building Lists of Users and Projects (according to certain
 * specifications)
 * 
 * @author Group3
 *
 */
public class Company {

	private UserRepository usersRepository;
	private ProjectRepository projectsRepository;
	static private Company _theInstance;

	/**
	 * Method to instantiate one Company only.
	 * 
	 * @return the only instance of Company that the program is allowed to have
	 */
	public static Company getTheInstance() {
		if (_theInstance == null)
			_theInstance = new Company();

		return _theInstance;
	}

	/**
	 * Constructor for Company includes usersList creation and projectsList creation
	 */
	private Company() {
		this.usersRepository = new UserRepository();
		this.projectsRepository = new ProjectRepository();
	}

	/**
	 * This method returns the list of projects (projectsList)
	 * 
	 * @return projectsList This is the List of all Projects created
	 */
	public ProjectRepository getProjectsRepository() {
		return this.projectsRepository;
	}

	/**
	 * This method returns the list of users (usersList)
	 * 
	 * @return usersList This is the List of all Users created
	 */
	public UserRepository getUsersList() {
		return this.usersRepository;
	}
}
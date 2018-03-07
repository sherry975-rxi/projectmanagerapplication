package project.model;

/**
 * Company Class for building Repositories of Users and Projects (according to
 * certain specifications)
 * 
 * @author Group3
 *
 */
public final class Company {

	private UserContainer usersRepository;
	private ProjectContainer projectsRepository;
	private static Company theInstance;

	/**
	 * Constructor for Company includes usersRepository creation and
	 * projectsRepository creation
	 */
	private Company() {
		this.usersRepository = new UserContainer();
		this.projectsRepository = new ProjectContainer();
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
	 * This method returns the Repository of projects (projectsRepository)
	 * 
	 * @return projectsRepository This is the Repository of all Projects created
	 */
	public ProjectContainer getProjectsRepository() {
		return this.projectsRepository;
	}

	/**
	 * This method returns the Repository of users (usersRepository)
	 * 
	 * @return usersRepository This is the Repository of all Users created
	 */
	public UserContainer getUsersRepository() {
		return this.usersRepository;
	}
}
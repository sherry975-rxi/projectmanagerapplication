package project.model;

/**
 * Company Class for building Repositorys of Users and Projects (according to
 * certain specifications)
 * 
 * @author Group3
 *
 */
public final class Company {

	private UserRepository usersRepository;
	private ProjectRepository projectsRepository;
	private static Company _theInstance;

	/**
	 * Method to instantiate one Company only.
	 * 
	 * @return the only instance of Company that the program is allowed to have
	 */
	public synchronized static Company getTheInstance() {
		if (_theInstance == null)
			_theInstance = new Company();

		return _theInstance;
	}

	/**
	 * This method clears the Company.
	 */
	public void clear() {

		_theInstance = null;

	}

	/**
	 * Constructor for Company includes usersRepository creation and
	 * projectsRepository creation
	 */
	private Company() {
		this.usersRepository = new UserRepository();
		this.projectsRepository = new ProjectRepository();
	}

	/**
	 * This method returns the Repository of projects (projectsRepository)
	 * 
	 * @return projectsRepository This is the Repository of all Projects created
	 */
	public ProjectRepository getProjectsRepository() {
		return this.projectsRepository;
	}

	/**
	 * This method returns the Repository of users (usersRepository)
	 * 
	 * @return usersRepository This is the Repository of all Users created
	 */
	public UserRepository getUsersRepository() {
		return this.usersRepository;
	}
}
package project.model;

import com.google.common.base.Joiner;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to build Projects.
 * 
 * From this class one can create a new Project(object), which is defined by its
 * name, email, idNumber, function, address list, phone number, and by its
 * profile.
 * 
 * @author Group 3
 *
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int status;
	@OneToOne
	private User projectManager;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private EffortUnit effortUnit;
	private double budget;
	private Calendar startdate;
	private Calendar finishdate;

    private int calculationMethod;

    private String availableCalculationMethods;

    public static final int PLANNING = 0; // planeado
	public static final int INITIATION = 1; // arranque
	public static final int EXECUTION = 2; // execução
	public static final int DELIVERY = 3; // entrega
	public static final int REVIEW = 4; // garantia
	public static final int CLOSE = 5; // fecho

	public static final int FIRST_COLLABORATOR = 1;
	public static final int LAST_COLLABORATOR = 2;
	public static final int AVERAGE_COLLABORATOR = 3;
	public static final int FIRST_LAST_COLLABORATOR = 4;

	static final long serialVersionUID = 43L;


	/**
	 * Empty Constructor for Project
	 */
	public Project(){

	}

	/**
	 * Project Constructor that demands the fields name, description and the Project
	 * Manager and contains the other non mandatory fields idCode, status,
	 * projectTaskList, projectTeam
	 * 
	 * @param name
	 * @param description
	 * @param projectManager
	 */
	public Project(String name, String description, User projectManager) {

		Objects.requireNonNull(name, "Name of the project cannot be null");
		Objects.requireNonNull(description, "Description of the project cannot be null");
		Objects.requireNonNull(projectManager, "ProjectManager of the project cannot be null");

		this.name = name;
		this.description = description;
		this.projectManager = projectManager;
		this.effortUnit = EffortUnit.HOURS;
		this.budget = 0;
		this.status = PLANNING;
		this.calculationMethod = FIRST_COLLABORATOR;
		this.startdate = null;
		this.finishdate = null;
		this.availableCalculationMethods = "1,2,3";

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

    public int getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(int calculationMethod) {
        this.calculationMethod = calculationMethod;
    }


	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	/**
	 * Create a Task using Creation Pattern
	 * 
	 * @param description
	 *            of the task
	 * @return A new Task object
	 */
	public Task createTask(String description) {
		Objects.requireNonNull(description, "Description of the Task cannot be null");

		
		return new Task(description, this);
	}

	/**
	 * Creates an instance of ProjectCollaborator
	 * 
	 * @param collaborator
	 *            User to add to Project Collaborator
	 * @param costPerEffort
	 *            Cost per Effort of user In Project
	 * 
	 * @return The new Project Collaborator instantiated
	 */
	public ProjectCollaborator createProjectCollaborator(User collaborator, int costPerEffort) {

		Objects.requireNonNull(collaborator, "User cannot be null");

		ProjectCollaborator newCollaborator = new ProjectCollaborator(collaborator, costPerEffort);
		newCollaborator.setProject(this);

		return newCollaborator;
	}

	/**
	 * Returns the start date of the project
	 * 
	 * @return startDate
	 */
	public Calendar getStartdate() {
		return startdate;
	}

	/**
	 * Sets the start date of the project
	 * 
	 * @param startdate
	 */
	public void setStartdate(Calendar startdate) {
		this.startdate = startdate;
	}

	/**
	 * Returns the finish date of the project
	 * 
	 * @return finishDate
	 */
	public Calendar getFinishdate() {
		return finishdate;
	}

	/**
	 * Sets the finish date of the project
	 * 
	 * @param finishdate
	 */
	public void setFinishdate(Calendar finishdate) {
		this.finishdate = finishdate;
	}

	/**
	 * This method allows the projectManager to be changed
	 * 
	 * @param newProjectManager
	 *            New Project Manager to Set
	 */
	public void setProjectManager(User newProjectManager) {
		this.projectManager = newProjectManager;
	}

	/**
	 * Get the users that is Project's Manager
	 * 
	 * @return projectManager
	 */
	public User getProjectManager() {
		return this.projectManager;
	}



	/**
	 * Get the project's description
	 * 
	 * @return description of project (String)
	 */
	public String getProjectDescription() {
		return this.description;
	}

	/**
	 * This method allows to set the project's description
	 * 
	 * @param newDescription
	 *            to set
	 * 
	 */
	public void setProjectDescription(String newDescription) {
		this.description = newDescription;
	}

	/**
	 * This method returns the state of the attribute status that can be planning,
	 * initiation, execution , delivery, review and close.
	 * 
	 * @return the status planning, initiation, execution , delivery, review or
	 *         close
	 */
	public int getProjectStatus() {
		return this.status;
	}

	public String getProjectStatusName() {
		switch (status) {
		case 0:
			return "Planning";
		case 1:
			return "Initiation";
		case 2:
			return "Execution";
		case 3:
			return "Delivery";
		case 4:
			return "Review";
		case 5:
			return "Closed";
		default:
			return "Unknown";

		}
	}

	/**
	 * This method allows the setting of the Project status, regardless of how it is
	 * previously instanced
	 * 
	 * @param newStatus
	 * @return the set status that can be planning, initiation, execution ,
	 *         delivery, review or close
	 */
	public void setProjectStatus(int newStatus) {
		this.status = newStatus;
	}

	/**
	 * Check if the user provided is the project manager or not. This method exists
	 * because when adding a user to a UserTeam, the user cannot be the project
	 * manager.
	 * 
	 * @param toCheck
	 *            User provided to check if he is the Project Manager
	 * @return TRUE if the user is the project manager False if the user is not the
	 *         project manager
	 */
	public boolean isProjectManager(User toCheck) {
		boolean result = false;
		if (toCheck.equals(this.projectManager)) {
			result = true;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 3;
		result = prime * result + (Integer) id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return id == other.id;
	}

	/**
	 * Get Project idCode
	 * 
	 * @return idCode of Project
	 */
	public int getIdCode() {
		return this.id;
	}

	/**
	 * Get Project effortUnit
	 * 
	 * @return EfforUnit of Project
	 */
	public EffortUnit getEffortUnit() {
		return this.effortUnit;
	}

	/**
	 * Set Project effortUnit
	 */
	public void setEffortUnit(EffortUnit newEffortUnit) {
		this.effortUnit = newEffortUnit;
	}

	/**
	 * Get Project budget
	 * 
	 * @return Budget (int) of Project
	 */
	public double getProjectBudget() {
		return this.budget;
	}

	/**
	 * Set Project budget
	 */
	public void setProjectBudget(double newBudget) {
		this.budget = newBudget;
	}


	/**
	 * This method returns the name of the project in a String
	 * 
	 * @return The name of the project
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Checks if a project has a status considered Active
	 * 
	 * @return TRUE if is active FALSE if not
	 */
	public Boolean isProjectActive() { 
		return this.getProjectStatus() == PLANNING || this.getProjectStatus() == INITIATION || this.getProjectStatus() ==  EXECUTION  || this.getProjectStatus() == DELIVERY;
	}

    public List<Integer> getAvailableCalculationMethods() {
        return Arrays.asList(availableCalculationMethods.split(",")).stream().
				map(method -> Integer.parseInt(method)).collect(Collectors.toList());
    }
	public void setAvailableCalculationMethods(List<Integer> availableCalculationMethods) {

		this.availableCalculationMethods = Joiner.on(',').join(availableCalculationMethods);
	}


	/**
	 * This method recieves an integer corresponding to one of the cost calculation methods,
	 * and returns true or false depending on whether or not the Director has allowed that calculation method
	 *
	 * @param method
	 * @return
	 */
	public boolean isCalculationMethodAllowed(Integer method) {
		return getAvailableCalculationMethods().contains(method);
	}


}
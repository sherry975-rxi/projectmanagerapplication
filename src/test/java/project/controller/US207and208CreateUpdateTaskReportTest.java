package project.controller;

import org.junit.After;
import org.junit.Before;
import project.model.*;

public class US207and208CreateUpdateTaskReportTest {
	Company myCompany;
	UserRepository userRepository;
	User user1;
	User userAdmin;
	ProjectCollaborator collab1;
	TaskCollaborator taskCollab1;
	Project project1;
	ProjectRepository projectRepository;
	Task testTask;
	US207and208CreateUpdateTaskReportControllers controller;

	@Before
	public void setUp() {
		// create company

		myCompany = Company.getTheInstance();

		// creates an UserRepository
		userRepository = myCompany.getUsersRepository();

		// creates a ProjectsRepository
		projectRepository = myCompany.getProjectsRepository();

		userRepository.getAllUsersFromRepository().clear();

		// create user
		user1 = userRepository.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
				"2401-00", "Test", "Testo", "Testistan");
		// add user to user list
		userRepository.addUserToUserRepository(user1);
		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		// create project
		project1 = projectRepository.createProject("name3", "description4", userAdmin);
		// add project to project repository
		projectRepository.addProjectToProjectRepository(project1);
		// create project collaborator
		collab1 = project1.createProjectCollaborator(user1, 0);
		// add project collaborator to project team
		project1.addProjectCollaboratorToProjectTeam(collab1);
		// create task
		testTask = project1.getTaskRepository().createTask("Tarefa para testar controlador");
		// add task to project
		project1.getTaskRepository().addProjectTask(testTask);
		// create task collaborator
		taskCollab1 = project1.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned().get(0)
				.createTaskCollaborator(collab1);
		// assign task collaborator to task
		project1.getTaskRepository().getAllTasksWithoutCollaboratorsAssigned().get(0)
				.addTaskCollaboratorToTask(taskCollab1);

	}

	@After
	public void tearDown() {
		Company.clear();
		myCompany = null;
		userRepository = null;
		user1 = null;
		userAdmin = null;
		collab1 = null;
		taskCollab1 = null;
		project1 = null;
		projectRepository = null;
		testTask = null;
		controller = null;

	}


}

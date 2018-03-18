package project;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
	private static User userAdmin;
	private static User userCollab;
	private static User userDirector;
	private static User userJSilva;
	private static Task taskOne;
	private static Task taskTwo;
	private static Project projOne;
	private static ProjectCollaborator projCollab1;
	private static Profile collabProfile;

	@Autowired
	private TaskService taskContainer;
	@Autowired
	private ProjectService projContainer;
	@Autowired
	private UserService userContainer;

	public static void main(String[] args) {
		SpringApplication.run(HelloJpaApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		// //demo.demoRun();
		//
		//
		// // Instantiates the UserContainer & ProjectContainer
		// UserContainer userContainer = new UserContainer();
		// ProjectContainer projectContainer = new ProjectContainer();
		//
		// // Instantiate the users, sets their passwords
		// userAdmin = userContainer.createUser("Teresa Ribeiro", "admin@gmail.com",
		// "001",
		// "Administrator", "917653635", "Avenida dos Aliados", "4000-654", "Porto",
		// "Porto", "Portugal");
		// userAdmin.setPassword("123456");
		// userDirector = userContainer.createUser("Roberto Santos",
		// "director@gmail.com", "002",
		// "Director", "917653636", "Avenida dos Aliados", "4000-654", "Porto", "Porto",
		// "Portugal");
		// userDirector.setPassword("abcdef");
		//
		// userJSilva = userContainer.createUser("João Silva", "jsilva@gmail.com",
		// "010", "Comercial",
		// "937653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto",
		// "Portugal");
		// userJSilva.setPassword("switch");
		// User userATirapicos = userContainer.createUser("Andreia Tirapicos",
		// "atirapicos@gmail.com", "011",
		// "Comercial", "955553635", "Avenida de Franca", "4455-654", "Leca da
		// Palmeira", "Porto", "Portugal");
		// userATirapicos.setPassword("tirapicos");
		// User projectManager = userContainer.createUser("Sara Pereira",
		// "spereira@gmail.com", "012",
		// "Técnica de recursos humanos", "9333333", "Rua Torta", "4455-666", "Leca da
		// Palmeira", "Porto",
		// "Portugal");
		//
		// // set user as collaborator
		// userDirector.setUserProfile(Profile.DIRECTOR);
		// userJSilva.setUserProfile(Profile.COLLABORATOR);
		// userATirapicos.setUserProfile(Profile.COLLABORATOR);
		// projectManager.setUserProfile(Profile.COLLABORATOR);
		//
		//
		// // addition of users to the company
		// userContainer.addUserToUserRepositoryX(userAdmin);
		// userContainer.addUserToUserRepositoryX(userDirector);
		// userContainer.addUserToUserRepositoryX(userJSilva);
		// userContainer.addUserToUserRepositoryX(userATirapicos);
		// userContainer.addUserToUserRepositoryX(projectManager);
		//
		//
		//
		// // Instantiates a project and add it to the company
		// Project projectGP = projectContainer.createProject("Gestão de Projetos",
		// "Aplicação para Gestão de Projetos", projectManager);
		// Project projectApostas = projectContainer.createProject("Apostas Online",
		// "Plataforma Web para Apostas", projectManager);
		// Project projectHomeBanking = projectContainer.createProject("HomeBanking",
		// "Aplicação iOS para HomeBanking", userJSilva);
		//
		// TaskCollaborator tWorkerJSilva;
		// TaskCollaborator tWorkerATirapicos;
		//
		// // Add data to project1
		// // add start date to project
		// Calendar startDate = Calendar.getInstance();
		// startDate.set(2018, Calendar.JANUARY, 23, 12, 31, 00);
		// projectGP.setStartdate(startDate);
		//
		// // add finish date to project
		// Calendar finishDate = Calendar.getInstance();
		// finishDate.set(2018, Calendar.FEBRUARY, 2, 12, 31, 00);
		// projectGP.setFinishdate(finishDate);
		//
		// // addition of projects to the company
		// projectContainer.saveProjectInRepository(projectGP);
		// projectContainer.saveProjectInRepository(projectApostas);
		// projectContainer.saveProjectInRepository(projectHomeBanking);
		//
		// // set "EXECUTION" status of projects
		// projectGP.setProjectStatus(2);
		// projectApostas.setProjectStatus(2);
		// projectHomeBanking.setProjectStatus(2);
		// Calendar projstartdate = Calendar.getInstance();
		// projstartdate.set(2017, 10, 05);
		// projectHomeBanking.setStartdate(projstartdate);
		// projectHomeBanking.setProjectBudget(5000);
		//
		// // Create Project collaborators
		// // addition of ProjectCollaborators 1 and 2 to projectGP and projectApostas
		// // addition of ProjectCollaborators 2 and 3(user projectManager) to
		// // projectHomeBanking
		//
		//
		// projectGP.addUserToProjectTeam(userJSilva, 2);
		// ProjectCollaborator projcollabJSilvaGP =
		// projectGP.findProjectCollaborator(userJSilva);
		// projectApostas.addUserToProjectTeam(userATirapicos, 3);
		// ProjectCollaborator projcollabATirapicos =
		// projectApostas.findProjectCollaborator(userATirapicos);
		// projectApostas.addUserToProjectTeam(userJSilva, 3);
		// ProjectCollaborator projcollabJSilvaAp =
		// projectApostas.findProjectCollaborator(userJSilva);
		// projectHomeBanking.addUserToProjectTeam(projectManager, 4);
		// ProjectCollaborator projcollabManager =
		// projectHomeBanking.findProjectCollaborator(projectManager);
		//
		//
		//
		// // Instantiates a task
		//
		// // create task deadline
		// Calendar taskDeadlineDate = Calendar.getInstance();
		// taskDeadlineDate.set(Calendar.YEAR, 2017);
		// taskDeadlineDate.set(Calendar.MONTH, Calendar.FEBRUARY);
		//
		// Task taskAp1 = projectApostas.getTaskRepository().createTask("Desenvolver
		// código para responder à US399");
		// projectApostas.getTaskRepository().addTaskToProject(taskAp1);
		// taskAp1.getTaskState().doAction(taskAp1);
		// taskAp1.setStartDate(startDate);
		// taskAp1.setTaskDeadline(taskDeadlineDate);
		//
		// Task taskAp2 = projectApostas.getTaskRepository().createTask("Implementar
		// sistema de segurança");
		// projectApostas.getTaskRepository().addTaskToProject(taskAp2);
		// taskAp2.getTaskState().doAction(taskAp2);
		// taskAp2.setStartDate(startDate);
		// taskAp2.setTaskDeadline(taskDeadlineDate);
		//
		// Task taskGP = projectGP.getTaskRepository().createTask("Adicionar
		// colaboradores às tarefas planeadas.");
		// projectGP.getTaskRepository().addTaskToProject(taskGP);
		// taskGP.getTaskState().doAction(taskGP);
		// taskGP.setStartDate(startDate);
		// taskGP.setTaskDeadline(taskDeadlineDate);
		//
		// Task taskHB = projectHomeBanking.getTaskRepository().createTask("Permitir
		// ligação a sites de noticias");
		// projectHomeBanking.getTaskRepository().addTaskToProject(taskHB);
		// taskHB.getTaskState().doAction(taskHB);
		// taskHB.setStartDate(startDate);
		// taskHB.setTaskDeadline(taskDeadlineDate);
		//
		// taskAp1.addTaskCollaboratorToTask(taskAp1.createTaskCollaborator(projcollabJSilvaAp));
		// taskGP.addTaskCollaboratorToTask(taskGP.createTaskCollaborator(projcollabJSilvaGP));
		// taskHB.addTaskCollaboratorToTask(taskHB.createTaskCollaborator(projcollabManager));
		//
		// projectHomeBanking.createTaskRemovalRequest(projcollabManager, taskHB);
		// projectApostas.createTaskAssignementRequest(projcollabATirapicos, taskAp1);
		//
		// TaskCollaborator taskCollabJSilvaAp =
		// taskAp1.getTaskCollaboratorByEmail("jsilva@gmail.com");
		// taskAp1.createReport(taskCollabJSilvaAp, taskDeadlineDate, 20.5);
		//
		// taskAp2.createTaskDependence(taskAp1, 0);
		//
		//
		// projectContainer.saveProjectInRepository(projectGP);
		// projectContainer.saveProjectInRepository(projectApostas);
		// projectContainer.saveProjectInRepository(projectHomeBanking);
		//
		// MainMenuUI.mainMenu();

		// Instantiates the UserContainer, defines user "userAdmin", saves it in
		// Repository
		userAdmin = userContainer.createUser("Teresa Ribeiro", "admin@gmail.com", "001", "Administrator", "917653635",
				"Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userCollab = userContainer.createUser("Vanda Miranda", "vmiranda@gmail.com", "002", "Software Developer",
				"911111111", "Avenida das Marias", "4444-654", "Matosinhos", "Porto", "Portugal");
		collabProfile = Profile.COLLABORATOR;
		userCollab.setUserProfile(collabProfile);
		userContainer.addUserToUserRepositoryX(userCollab);

		// Instantiates the ProjectContainer, defines project "projOne", saves it in
		// Repository
		projOne = projContainer.createProject("POne", "teste", userAdmin);
		// projContainer.addProjectToProjectContainer(projOne);

		// Instantiates the ProjectCollaborator, defines projCollab "projCollab1", save
		// it in Repository
		projCollab1 = projOne.createProjectCollaborator(userAdmin, 3);
		projContainer.addProjectCollaborator(projCollab1);

		// Instantiates the TaskContainer, defines task "taskOne", saves it in
		// Repository
		// taskOne = taskContainer.createTask("Desenvolver código para responder à
		// US399");
		// taskOne = projOne.createTaskinProject("Desenvolver código para responder à
		// US499");
		taskContainer.createTask("Desenvolver código para responder à US499", projOne);

		userContainer.isUserinUserContainer(userAdmin);

//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println(userContainer.isUserinUserContainer(userAdmin));
//		System.out.println(userContainer.getAllUsersFromUserContainer().get(0).getName());
//		System.out.println(userContainer.getUserByEmail("admin@gmail.com").getName());
//		System.out.println(userContainer.searchUsersByPartsOfEmail("@gmail.com").get(0).getName());
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println(userContainer.getAllActiveCollaboratorsFromRepository().get(0).getName());
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println(userContainer.searchUsersByProfile(collabProfile).get(0).getName());
//		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//		System.out.println(userContainer.searchUsersByPartsOfEmail("admin@gmail.com").get(0).getName());
		
		userContainer.isUserinUserContainer(userAdmin);
		Optional<ProjectCollaborator> projectCollaborator = projContainer.findProjectCollaborator(userCollab, projOne);
		Optional<ProjectCollaborator> pCollabFound = projContainer.findProjectCollaborator(userAdmin, projOne);

		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.print(projectCollaborator.map(projCollab -> projCollab.getCollaborator().getName()).orElse("     NOT FOUND!   "));
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.print(pCollabFound.map(projCollab -> projCollab.getCollaborator().getName()).orElse("     NOT FOUND!   "));
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		taskTwo = projOne.createTask("Desenvolver código para responder à US499");
		taskContainer.saveTask(taskTwo);

	}
}
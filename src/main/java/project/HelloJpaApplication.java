package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.controller.UpdateDbToContainersController;
import project.model.*;
import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.OnGoing;
import project.ui.console.MainMenuUI;

import java.util.Calendar;


@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
    private static User userAdmin;
    private static User userDirector;
    private static User userJSilva;

    //@Autowired
    //private DemoComponent demo;

    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        //demo.demoRun();


        // Instantiates the company
        Company myCompany = Company.getTheInstance();

        // Instantiate the users, sets their passwords
        userAdmin = myCompany.getUsersContainer().createUser("Teresa Ribeiro", "admin@gmail.com", "001",
                "Administrator", "917653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
        userAdmin.setPassword("123456");
        userDirector = myCompany.getUsersContainer().createUser("Roberto Santos", "director@gmail.com", "002",
                "Director", "917653636", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
        userDirector.setPassword("abcdef");

        userJSilva = myCompany.getUsersContainer().createUser("João Silva", "jsilva@gmail.com", "010", "Comercial",
                "937653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
        userJSilva.setPassword("switch");
        User userATirapicos = myCompany.getUsersContainer().createUser("Andreia Tirapicos", "atirapicos@gmail.com", "011",
                "Comercial", "955553635", "Avenida de Franca", "4455-654", "Leca da Palmeira", "Porto", "Portugal");
        userATirapicos.setPassword("tirapicos");
        User projectManager = myCompany.getUsersContainer().createUser("Sara Pereira", "spereira@gmail.com", "012",
                "Técnica de recursos humanos", "9333333", "Rua Torta", "4455-666", "Leca da Palmeira", "Porto",
                "Portugal");

        // set user as collaborator
        userDirector.setUserProfile(Profile.DIRECTOR);
        userJSilva.setUserProfile(Profile.COLLABORATOR);
        userATirapicos.setUserProfile(Profile.COLLABORATOR);
        projectManager.setUserProfile(Profile.COLLABORATOR);


        // addition of users to the company
        myCompany.getUsersContainer().addUserToUserRepositoryX(userAdmin);
        myCompany.getUsersContainer().addUserToUserRepositoryX(userDirector);
        myCompany.getUsersContainer().addUserToUserRepositoryX(userJSilva);
        myCompany.getUsersContainer().addUserToUserRepositoryX(userATirapicos);
        myCompany.getUsersContainer().addUserToUserRepositoryX(projectManager);



        // Instantiates a project and add it to the company
        Project projectGP = myCompany.getProjectsContainer().createProject("Gestão de Projetos",
                "Aplicação para Gestão de Projetos", projectManager);
        Project projectApostas = myCompany.getProjectsContainer().createProject("Apostas Online",
                "Plataforma Web para Apostas", projectManager);
        Project projectHomeBanking = myCompany.getProjectsContainer().createProject("HomeBanking",
                "Aplicação iOS para HomeBanking", userJSilva);

        TaskCollaborator tWorkerJSilva;
        TaskCollaborator tWorkerATirapicos;

        // Add data to project1
        // add start date to project
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, Calendar.JANUARY, 23, 12, 31, 00);
        projectGP.setStartdate(startDate);

        // add finish date to project
        Calendar finishDate = Calendar.getInstance();
        finishDate.set(2018, Calendar.FEBRUARY, 2, 12, 31, 00);
        projectGP.setFinishdate(finishDate);

        // addition of projects to the company
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectGP);
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectApostas);
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectHomeBanking);

        // set "EXECUTION" status of projects
        projectGP.setProjectStatus(2);
        projectApostas.setProjectStatus(2);
        projectHomeBanking.setProjectStatus(2);
        Calendar projstartdate = Calendar.getInstance();
        projstartdate.set(2017, 10, 05);
        projectHomeBanking.setStartdate(projstartdate);
        projectHomeBanking.setProjectBudget(5000);

        // Create Project collaborators
        // addition of ProjectCollaborators 1 and 2 to projectGP and projectApostas
        // addition of ProjectCollaborators 2 and 3(user projectManager) to
        // projectHomeBanking
        projectGP.addUserToProjectTeam(userJSilva, 2);
        projectApostas.addUserToProjectTeam(userJSilva, 2);
        projectGP.addUserToProjectTeam(userATirapicos, 2);
        projectApostas.addUserToProjectTeam(userATirapicos, 2);
        projectHomeBanking.addUserToProjectTeam(userATirapicos, 2);
        projectHomeBanking.addUserToProjectTeam(projectManager, 2);


                ProjectCollaborator projcollabJSilva = new ProjectCollaborator(userJSilva, 2);
        ProjectCollaborator projcollabATirapicos = new ProjectCollaborator(userATirapicos, 2);
        ProjectCollaborator projcollabManager = new ProjectCollaborator(projectManager, 2);


        // Instantiates a task
        Task taskGP1 = projectGP.getTaskRepository().createTask("Desenvolver código para responder à US399");
        projectGP.getTaskRepository().addProjectTask(taskGP1);
        // Creates a new taksCollaborator
        tWorkerJSilva = new TaskCollaborator(projcollabJSilva);
        // Adds the taskCollaborator to task1
        taskGP1.addTaskCollaboratorToTask(tWorkerJSilva);

        OnGoing onGoingState = new OnGoing(taskGP1);
        taskGP1.setTaskState(onGoingState);
        taskGP1.setStartDate(startDate);

        // create task deadline
        Calendar taskDeadlineDate = Calendar.getInstance();
        taskDeadlineDate.set(Calendar.YEAR, 2017);
        taskDeadlineDate.set(Calendar.MONTH, Calendar.FEBRUARY);
        taskGP1.setTaskDeadline(taskDeadlineDate);

        taskGP1.createReport(tWorkerJSilva, Calendar.getInstance(), 10);
        taskGP1.getReports().get(0).setReportedTime(20);

        Task taskGP2 = projectGP.getTaskRepository().createTask("Desenvolver código para responder à US122");
        projectGP.getTaskRepository().addProjectTask(taskGP2);

        // Instantiates a task
        Task taskGP3 = projectGP.getTaskRepository().createTask("Fazer refatoração.");
        projectGP.getTaskRepository().addProjectTask(taskGP3);
        Task taskGP5 = projectGP.getTaskRepository().createTask("Adicionar colaboradores às tarefas planeadas.");
        projectGP.getTaskRepository().addProjectTask(taskGP5);
        Task taskGP6 = projectGP.getTaskRepository().createTask("Criar tarefas.");
        projectGP.getTaskRepository().addProjectTask(taskGP6);
        // Creates a new taksCollaborator
        tWorkerATirapicos = new TaskCollaborator(projcollabATirapicos);

        // taskGP#3 changes to "Finished"
        // changes from "Created" to "Planned"
        startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        taskGP3.setEstimatedTaskStartDate(startDate);
        finishDate = Calendar.getInstance();
        finishDate.add(Calendar.MONTH, 1);
        taskGP3.setTaskDeadline(finishDate);
        taskGP3.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskGP3.addProjectCollaboratorToTask(projcollabJSilva);
        taskGP3.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskGP3.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        taskGP3.setStartDate(startDate);
        taskGP3.getTaskState().changeToOnGoing();
        // pass from "OnGoing" to "Finished"
        Calendar testDate = (Calendar) finishDate.clone();
        taskGP3.setFinishDate(testDate);
        taskGP3.getTaskState().changeToFinished();
        // Adds the taskCollaborator to taskGP3
        taskGP3.addTaskCollaboratorToTask(tWorkerATirapicos);

        // taskGP#5 changes to finished
        // changes from "Created" to "Planned"
        Calendar estimatstartDate2 = Calendar.getInstance();
        estimatstartDate2.add(Calendar.YEAR, -1);
        estimatstartDate2.add(Calendar.MONTH, -3);
        estimatstartDate2.add(Calendar.DAY_OF_MONTH, 0);
        taskGP5.setEstimatedTaskStartDate(estimatstartDate2);
        Calendar finishDate2 = Calendar.getInstance();
        finishDate2.add(Calendar.YEAR, -1);
        finishDate2.add(Calendar.MONTH, -3);
        finishDate2.add(Calendar.DAY_OF_MONTH, 6);
        taskGP5.setTaskDeadline(finishDate2);
        taskGP5.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskGP5.addProjectCollaboratorToTask(projcollabJSilva);
        taskGP5.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskGP5.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        Calendar startDate2 = Calendar.getInstance();
        startDate2.add(Calendar.YEAR, -1);
        startDate2.add(Calendar.MONTH, -3);
        startDate2.add(Calendar.DAY_OF_MONTH, 1);
        taskGP5.setStartDate(startDate2);
        taskGP5.getTaskState().changeToOnGoing();
        // pass from "OnGoing" to "Finished"
        Calendar testDate2 = Calendar.getInstance();
        testDate2.add(Calendar.YEAR, 0);
        testDate2.add(Calendar.MONTH, -1);
        testDate2.add(Calendar.DAY_OF_MONTH, 2);
        taskGP5.setFinishDate(testDate2);
        taskGP5.getTaskState().changeToFinished();

        // Creates a taskGP4 and sets it to cancelled
        Task taskGP4 = projectGP.getTaskRepository().createTask("Cancelled Task");
        projectGP.getTaskRepository().addProjectTask(taskGP4);
        Cancelled cancelledState = new Cancelled(taskGP4);
        taskGP4.setTaskState(cancelledState);

        // Create taskHB5 of projectHomeBanking
        Task taskHB1 = projectHomeBanking.getTaskRepository().createTask("Criar indicadores de acesso");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB1);
        Task taskHB2 = projectHomeBanking.getTaskRepository()
                .createTask("Permitir diferentes configurações para pagina pessoal");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB2);
        Task taskHB3 = projectHomeBanking.getTaskRepository().createTask("Permitir ligação a sites de noticias");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB3);
        Task taskHB4 = projectHomeBanking.getTaskRepository().createTask("Alterar configurações para acesso");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB4);
        Task taskHB5 = projectHomeBanking.getTaskRepository().createTask("Implementar sistema de segurança");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB5);
        Task taskHB6 = projectHomeBanking.getTaskRepository().createTask("Mostrar vista de administrador");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB6);
        Task taskHB7 = projectHomeBanking.getTaskRepository().createTask("Permitir alteração de dados de cliente");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB7);
        Task taskHB8 = projectHomeBanking.getTaskRepository().createTask("Gerar relatórios de investimentos");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB8);
        Task taskHB9 = projectHomeBanking.getTaskRepository().createTask("Criar botão personalizar");
        projectHomeBanking.getTaskRepository().addProjectTask(taskHB9);

        // Task 3.1 (taskHB1) is in state created

        // Task 3.2 (taskHB2) set to planned
        // necessary to pass from "Created" to "Planned"
        startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        taskHB2.setEstimatedTaskStartDate(startDate);
        finishDate = Calendar.getInstance();
        finishDate.add(Calendar.MONTH, 1);
        taskHB2.setTaskDeadline(finishDate);
        taskHB2.getTaskState().changeToPlanned();

        // Task 3.3 (taskHB3) set to assigned
        // necessary to pass from "Created" to "Planned"
        Calendar estimatedStartDate = Calendar.getInstance();
        estimatedStartDate.add(Calendar.MONTH, -1);
        estimatedStartDate.add(Calendar.DAY_OF_MONTH, 5);
        taskHB3.setEstimatedTaskStartDate(estimatedStartDate);
        Calendar estimatedFinishDate1 = Calendar.getInstance();
        estimatedFinishDate1.add(Calendar.MONTH, -1);
        estimatedFinishDate1.add(Calendar.DAY_OF_MONTH, 6);
        taskHB3.setTaskDeadline(estimatedFinishDate1);
        taskHB3.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB3.addProjectCollaboratorToTask(projcollabManager);
        taskHB3.getTaskState().changeToAssigned();

        // Task 3.4 (taskHB4) set to ready
        // necessary to pass from "Created" to "Planned"
        Calendar estimatedStartDate2 = Calendar.getInstance();
        estimatedStartDate2.add(Calendar.MONTH, -1);
        estimatedStartDate2.add(Calendar.DAY_OF_MONTH, 6);
        taskHB4.setEstimatedTaskStartDate(estimatedStartDate2);
        Calendar estimatedFinishDate2 = Calendar.getInstance();
        estimatedFinishDate2.add(Calendar.MONTH, -1);
        estimatedFinishDate2.add(Calendar.DAY_OF_MONTH, 7);
        taskHB4.setTaskDeadline(estimatedFinishDate2);
        taskHB4.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB4.addProjectCollaboratorToTask(projcollabManager);
        taskHB4.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskHB4.getTaskState().changeToReady();

        // Task 3.5 (taskHB5) set to ongoing
        // necessary to pass from "Created" to "Planned"
        startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        taskHB5.setEstimatedTaskStartDate(startDate);
        Calendar estimatedFinishDate = Calendar.getInstance();
        estimatedFinishDate.add(Calendar.MONTH, 1);
        taskHB5.setTaskDeadline(finishDate);
        taskHB5.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB5.addProjectCollaboratorToTask(projcollabManager);
        taskHB5.addProjectCollaboratorToTask(projcollabATirapicos);
        taskHB5.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskHB5.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        taskHB5.setStartDate(startDate);
        taskHB5.getTaskState().changeToOnGoing();

        // Task 3.6 (taskHB6) set to finished
        Calendar estimatedStartDate4 = Calendar.getInstance();
        estimatedStartDate4.add(Calendar.MONTH, -1);
        estimatedStartDate4.add(Calendar.DAY_OF_MONTH, 6);
        taskHB6.setEstimatedTaskStartDate(estimatedStartDate4);
        Calendar estimatedFinishDate4 = Calendar.getInstance();
        estimatedFinishDate4.add(Calendar.MONTH, -3);
        estimatedFinishDate4.add(Calendar.DAY_OF_MONTH, 17);
        taskHB6.setTaskDeadline(estimatedFinishDate4);
        taskHB6.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB6.addProjectCollaboratorToTask(projcollabManager);
        taskHB6.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskHB6.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        taskHB6.setStartDate(startDate);
        taskHB6.getTaskState().changeToOnGoing();
        // necessary to pass from "OnGoing" to "Finished"
        Calendar finishDate1 = Calendar.getInstance();
        finishDate1.add(Calendar.MONTH, 0);
        finishDate1.add(Calendar.DAY_OF_MONTH, 13);
        taskHB6.setFinishDate(finishDate1);
        taskHB6.getTaskState().changeToFinished();

        // Task 3.7 (taskHB7) set to ongoing with expired deadline
        taskHB7.setEstimatedTaskStartDate(estimatedStartDate4);
        taskHB7.setTaskDeadline(estimatedFinishDate4);
        taskHB7.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB7.addProjectCollaboratorToTask(projcollabManager);
        taskHB7.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskHB7.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        taskHB7.setStartDate(startDate);
        taskHB7.getTaskState().changeToOnGoing();

        // Task 3.8 (taskHB8) set to cancelled
        taskHB8.setEstimatedTaskStartDate(estimatedStartDate4);
        taskHB8.setTaskDeadline(estimatedFinishDate4);
        taskHB8.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB8.addProjectCollaboratorToTask(projcollabManager);
        taskHB8.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskHB8.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        taskHB8.setStartDate(startDate);
        taskHB8.getTaskState().changeToOnGoing();
        // necessary to pass from "OnGoing" to "Cancelled"
        taskHB8.setCancelDate();
        taskHB8.getTaskState().changeToCancelled();

        // Task 3.9 (taskHB9) set to standby
        taskHB9.setEstimatedTaskStartDate(estimatedStartDate4);
        taskHB9.setTaskDeadline(estimatedFinishDate4);
        taskHB9.getTaskState().changeToPlanned();
        // necessary to pass from "Planned" to "Assigned"
        taskHB9.addProjectCollaboratorToTask(projcollabManager);
        taskHB9.getTaskState().changeToAssigned();
        // pass from "Assigned" to "Ready"
        taskHB9.getTaskState().changeToReady();
        // necessary to pass from "Ready" to "OnGoing"
        taskHB9.setStartDate(startDate);
        taskHB9.getTaskState().changeToOnGoing();
        // necessary to pass from "OnGoing" to "StandBy"
        taskHB9.removeAllCollaboratorsFromTaskTeam();
        taskHB9.getTaskState().changeToStandBy();

        // Request assignment of collaborator to task 3.2 (taskHB2)
        projectHomeBanking.createTaskAssignementRequest(projcollabATirapicos, taskHB2);

        // Request of removal of collaborator projcollabATirapicos from task 3.5
        // (taskHB5)
        projectHomeBanking.createTaskRemovalRequest(projcollabATirapicos, taskHB5);

        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectGP);
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectApostas);
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectHomeBanking);
/**
//        //Creates a company.
        Company company = Company.getTheInstance();
//        //Creates the user through the company
        User manuel = company.getUsersContainer().createUser("Manuel", "user2@gmail.com", "001", "Manger",
                "930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
//        //Saves the user to the database
      company.getUsersContainer().addUserToUserRepositoryX(manuel);
//
//        //Creates the project through the company
        Project project = company.getProjectsContainer().createProject("NOME PROJECTO", "DESCRIÇÃO PROJECTO", manuel);
//
//        //Creates the task through the project
        Task task = project.getTaskRepository().createTask("Description Task");
        Task task2 = project.getTaskRepository().createTask("Inject Dependencies");
//        //Adds the task to the task repository
        project.getTaskRepository().addProjectTask(task);
        project.getTaskRepository().addProjectTask(task2);
        //Creates ProjectCollaborator through the Project
//        ProjectCollaborator manuelProjCollab = project.createProjectCollaborator(manuel,7);
//        //Adds the projectCollaborator to the ProjectTeam
//        //project.addProjectCollaboratorToProjectTeam(manuelProjCollab);
//        project.addUserToProjectTeam(manuel,7000);
//        //Adds taskCollaborator to Task
//        project.getTaskRepository().getTaskByID(task.getTaskID()).addProjectCollaboratorToTask(manuelProjCollab);
//        project.getTaskRepository().getTaskByID(task2.getTaskID()).addProjectCollaboratorToTask(manuelProjCollab);
//        //Create Task Report
//        Calendar date = Calendar.getInstance();
//        project.getTaskRepository().getTaskByID(task.getTaskID()).createReport(task.getTaskTeam().get(0), date, 5 );
//
//        //Create dependency between two tasks
//        project.getTaskRepository().getTaskByID(task.getTaskID()).setStartDate(date);
//        project.getTaskRepository().getTaskByID(task.getTaskID()).setTaskDeadline(date);
//        project.getTaskRepository().getTaskByID(task2.getTaskID()).createTaskDependence(task,2);
//
//        //Saves the project to the database
        company.getProjectsContainer().addProjectToProjectContainerX(project);
//        //Gets the project from the database by the id
//        Project projectAAA = company.getProjectsContainer(). getProjectById(1);
//        //Gets the task through the project taskRepository
//        Task taskAAA = projectAAA.getTaskRepository().getProjectTaskRepository().get(0);
//
//        //Expected output
//        System.out.println("-----------------------------TEST-----------------------------");
//        System.out.println(taskAAA.getDescription());
//        System.out.println("-----------------------------TEST-----------------------------");
*/


        //UpdateDbToContainersController uu = new UpdateDbToContainersController();
        //uu.updateDBtoContainer();

        MainMenuUI.mainMenu();

    }




}
package project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.model.*;
import project.model.taskstateinterface.Created;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.ui.console.MainMenuUI;

import java.util.Calendar;

@Component
public class RunConsole implements CommandLineRunner {

    private static User userAdmin;
    private static User userDirector;
    private static User userJSilva;

    private static Company myCompany;

    @Override
    public void run(String... args) throws Exception {

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
        ProjectCollaborator projcollabJSilvaGP = projectGP.findProjectCollaborator(userJSilva);
        projectApostas.addUserToProjectTeam(userATirapicos, 3);
        ProjectCollaborator projcollabATirapicos = projectApostas.findProjectCollaborator(userATirapicos);
        projectApostas.addUserToProjectTeam(userJSilva, 3);
        ProjectCollaborator projcollabJSilvaAp = projectApostas.findProjectCollaborator(userJSilva);
        projectHomeBanking.addUserToProjectTeam(projectManager, 4);
        ProjectCollaborator projcollabManager = projectHomeBanking.findProjectCollaborator(projectManager);



        // Instantiates a task

        // create task deadline
        Calendar taskDeadlineDate = Calendar.getInstance();
        taskDeadlineDate.set(Calendar.YEAR, 2017);
        taskDeadlineDate.set(Calendar.MONTH, Calendar.FEBRUARY);

        Task taskAp1 = projectApostas.getTaskRepository().createTask("Desenvolver código para responder à US399");
        projectApostas.getTaskRepository().addTaskToProject(taskAp1);
        Planned onGoingStateAp1 = new Planned(taskAp1);
        taskAp1.setTaskState(onGoingStateAp1);
        taskAp1.setStartDate(startDate);
        taskAp1.setTaskDeadline(taskDeadlineDate);

        Task taskAp2 = projectApostas.getTaskRepository().createTask("Implementar sistema de segurança");
        projectApostas.getTaskRepository().addTaskToProject(taskAp2);
        Created onGoingStateAp2 = new Created(taskAp2);
        taskAp2.setTaskState(onGoingStateAp2);
        taskAp2.setStartDate(startDate);
        taskAp2.setTaskDeadline(taskDeadlineDate);

        Task taskGP = projectGP.getTaskRepository().createTask("Adicionar colaboradores às tarefas planeadas.");
        projectGP.getTaskRepository().addTaskToProject(taskGP);
        OnGoing onGoingStateGP = new OnGoing(taskGP);
        taskGP.setTaskState(onGoingStateGP);
        taskGP.setStartDate(startDate);
        taskGP.setTaskDeadline(taskDeadlineDate);

        Task taskHB = projectHomeBanking.getTaskRepository().createTask("Permitir ligação a sites de noticias");
        projectHomeBanking.getTaskRepository().addTaskToProject(taskHB);
        OnGoing onGoingStateHB = new OnGoing(taskHB);
        taskHB.setTaskState(onGoingStateHB);
        taskHB.setStartDate(startDate);
        taskHB.setTaskDeadline(taskDeadlineDate);

        taskAp1.addTaskCollaboratorToTask(taskAp1.createTaskCollaborator(projcollabJSilvaAp));
        taskGP.addTaskCollaboratorToTask(taskGP.createTaskCollaborator(projcollabJSilvaGP));
        taskHB.addTaskCollaboratorToTask(taskHB.createTaskCollaborator(projcollabManager));

        projectHomeBanking.createTaskRemovalRequest(projcollabManager, taskHB);
        projectApostas.createTaskAssignementRequest(projcollabATirapicos, taskAp1);

        TaskCollaborator taskCollabJSilvaAp = taskAp1.getTaskCollaboratorByEmail("jsilva@gmail.com");
        taskAp1.createReport(taskCollabJSilvaAp, taskDeadlineDate, 20.5);

        taskAp2.createTaskDependence(taskAp1, 0);


        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectGP);
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectApostas);
        myCompany.getProjectsContainer().addProjectToProjectContainerX(projectHomeBanking);

        MainMenuUI.mainMenu();

    }
}

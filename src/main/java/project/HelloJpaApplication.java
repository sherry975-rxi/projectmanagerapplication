package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.model.*;

import java.util.Calendar;


@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
    //@Autowired
    //private DemoComponent demo;

    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        //demo.demoRun();
        // MainMenuUI.main();

        //Creates a company.
        Company company = Company.getTheInstance();
        //Creates the user through the company
        User manuel = company.getUsersContainer().createUser("Manuel", "user2@gmail.com", "001", "Manger",
                "930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        //Saves the user to the database
        company.getUsersContainer().addUserToRepository(manuel);

        //Creates the project through the company
        Project project = company.getProjectsContainer().createProject("NOME PROJECTO", "DESCRIÇÃO PROJECTO", manuel);

        //Creates the task through the project
        Task task = project.getTaskRepository().createTask("Description Task");
        Task task2 = project.getTaskRepository().createTask("Inject Dependencies");
        //Adds the task to the task repository
        project.getTaskRepository().addProjectTask(task);
        project.getTaskRepository().addProjectTask(task2);
        //Creates ProjectCollaborator through the Project
        ProjectCollaborator manuelProjCollab = project.createProjectCollaborator(manuel,7);
        //Adds the projectCollaborator to the ProjectTeam
        //project.addProjectCollaboratorToProjectTeam(manuelProjCollab);
        project.addUserToProjectTeam(manuel,7000);
        //Adds taskCollaborator to Task
        project.getTaskRepository().getTaskByID(task.getTaskID()).addProjectCollaboratorToTask(manuelProjCollab);
        project.getTaskRepository().getTaskByID(task2.getTaskID()).addProjectCollaboratorToTask(manuelProjCollab);
        //Create Task Report
        Calendar date = Calendar.getInstance();
        project.getTaskRepository().getTaskByID(task.getTaskID()).createReport(task.getTaskTeam().get(0), date, 5 );

        //Create dependency between two tasks
        project.getTaskRepository().getTaskByID(task.getTaskID()).setStartDate(date);
        project.getTaskRepository().getTaskByID(task.getTaskID()).setTaskDeadline(date);
        project.getTaskRepository().getTaskByID(task2.getTaskID()).createTaskDependence(task,2);

        //Saves the project to the database
        company.getProjectsContainer().saveProject(project);
        //Gets the project from the database by the id
        Project projectAAA = company.getProjectsContainer(). getProjectById(1);
        //Gets the task through the project taskRepository
        Task taskAAA = projectAAA.getTaskRepository().getProjectTaskRepository().get(0);

        //Expected output
        System.out.println("-----------------------------TEST-----------------------------");
        System.out.println(taskAAA.getDescription());
        System.out.println("-----------------------------TEST-----------------------------");



    }




}
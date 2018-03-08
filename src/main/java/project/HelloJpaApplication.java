package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.model.Company;
import project.model.Project;
import project.model.Task;
import project.model.User;


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
        User manger = company.getUsersContainer().createUser("Manel", "user2@gmail.com", "001", "Manger",
                "930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        //Saves the user to the database
        company.getUsersContainer().addUserToRepository(manger);

        //Creates the projecto through the company
        Project project = company.getProjectsContainer().createProject("NOME PROJECTO", "DESCRIÇÃO PROJECTO", manger);

        //Creates the task through the project
        Task task = project.getTaskRepository().createTask("DESCRIÇÃO TAREFA");
        //Adds the task to the task repository
        project.getTaskRepository().addProjectTask(task);
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
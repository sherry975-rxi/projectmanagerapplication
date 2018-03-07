package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.Repository.ProjectsRepository;
import project.Repository.UserRepository;
import project.Repository.ProjCollabRepository;
import project.Repository.TaskRepository;
import project.model.*;

import java.util.Calendar;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectsRepository projRepository;

    @Autowired
    private ProjCollabRepository projCollabRepository;

    @Autowired
    private TaskRepository taskRepository;


    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }
    @Override
    public void run(String... strings) throws Exception {

        if(userRepository.findAll().isEmpty() && projRepository.findAll().isEmpty()) {
            dummyData();
        }

        for (User u : userRepository.findAll()) {
            logger.info(u.toString());
        }

        for (Project p : projRepository.findAll()) {
            logger.info(p.toString());
        }

    }


    public void dummyData() {

        Company myCompany = Company.getTheInstance();
        User manger = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Manger",
                "930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        User manelinho = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Enabler",
                "940000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        User zeDasCouves = myCompany.getUsersRepository().createUser("Zezinho", "user4@gmail.com", "003", "Janitor",
                "950000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");

        myCompany.getUsersRepository().addUserToUserRepository(manger);
        myCompany.getUsersRepository().addUserToUserRepository(manelinho);
        myCompany.getUsersRepository().addUserToUserRepository(zeDasCouves);


        Project myProjectTest = myCompany.getProjectsRepository().createProject("Test ze boot", "Test ze spring boot well yes?", manger);
        Project myProjectExperiment = myCompany.getProjectsRepository().createProject("Social experiment", "Manelinho is best meneger", manelinho);
        myCompany.getProjectsRepository().addProjectToProjectRepository(myProjectTest);
        myCompany.getProjectsRepository().addProjectToProjectRepository(myProjectExperiment);


        ProjectCollaborator manelinhoCollab = myProjectTest.createProjectCollaborator(manelinho, 68);
        myProjectTest.addProjectCollaboratorToProjectTeam(manelinhoCollab);

        Task tester = myProjectTest.getTaskRepository().createTask("Testing ze test");
        myProjectTest.getTaskRepository().addProjectTask(tester);

        TaskCollaborator manelinhoTaskCol = tester.createTaskCollaborator(manelinhoCollab);
        tester.addTaskCollaboratorToTask(manelinhoTaskCol);
        tester.createReport(manelinhoTaskCol);
        tester.getReports().get(0).setReportedTime(70);

        Calendar testerDeadline = Calendar.getInstance();
        testerDeadline.add(Calendar.DAY_OF_YEAR, 60);
        tester.setTaskDeadline(testerDeadline);


        Task dependentTest = myProjectTest.getTaskRepository().createTask("This task is very dependent");
        myProjectTest.getTaskRepository().addProjectTask(dependentTest);
        dependentTest.createTaskDependence(tester, 20);

        myProjectTest.createTaskRemovalRequest(manelinhoCollab, tester);

        userRepository.save(manger);
        userRepository.save(manelinho);
        userRepository.save(zeDasCouves);
        projRepository.save(myProjectTest);
        projRepository.save(myProjectExperiment);
        projCollabRepository.save(manelinhoCollab);
        taskRepository.save(tester);
        taskRepository.save(dependentTest);


    }

}
package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.Repository.ProjCollabRepository;
import project.Repository.ProjectsRepository;
import project.Repository.TaskRepository;
import project.Repository.UserRepository;
import project.model.*;

import java.util.Calendar;

@Component
public class DemoComponent {

    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectsRepository projRepository;

    @Autowired
    private ProjCollabRepository projCollabRepository;

    @Autowired
    private TaskRepository taskRepository;

    public void demoRun() {

        logger.info("Welcome to demo mode!\n");

        if (userRepository.findAll().isEmpty() && projRepository.findAll().isEmpty()) {
            logger.info("Creating dummy data...\n");
            dummyData();
        } else {
            logger.info("Dummy data NOT created!\n");
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
        myCompany.getProjectsRepository().addProjectToProjectContainer(myProjectTest);
        myCompany.getProjectsRepository().addProjectToProjectContainer(myProjectExperiment);


        ProjectCollaborator manelinhoCollab = myProjectTest.createProjectCollaborator(manelinho, 68);
        myProjectTest.addProjectCollaboratorToProjectTeam(manelinhoCollab);

        Task tester = myProjectTest.getTaskRepository().createTask("Testing ze test");
        myProjectTest.getTaskRepository().addProjectTask(tester);

        TaskCollaborator manelinhoTaskCol = tester.createTaskCollaborator(manelinhoCollab);
        tester.addTaskCollaboratorToTask(manelinhoTaskCol);
        tester.createReport(manelinhoTaskCol, Calendar.getInstance(), 10);
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
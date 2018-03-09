package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        String ruaBla = "Rua Bla";
        String blabla ="Blabla";
        String blablabla = "Blablabla";
        String blablablabla = blabla+blabla;
        String blalandia ="Blalandia";

        Company myCompany = Company.getTheInstance();
        User manger = myCompany.getUsersContainer().createUser("Manel", "user2@gmail.com", "001", "Manger",
                "930000000", ruaBla, blabla, blablabla, blablablabla, blalandia);
        User manelinho = myCompany.getUsersContainer().createUser("Manelinho", "user3@gmail.com", "002", "Enabler",
                "940000000", ruaBla, blabla, blablabla, blablablabla, blalandia);
        User zeDasCouves = myCompany.getUsersContainer().createUser("Zezinho", "user4@gmail.com", "003", "Janitor",
                "950000000", ruaBla, blabla, blablabla, blablablabla, blalandia);

        myCompany.getUsersContainer().addUserToUserRepository(manger);
        myCompany.getUsersContainer().addUserToUserRepository(manelinho);
        myCompany.getUsersContainer().addUserToUserRepository(zeDasCouves);


        Project myProjectTest = myCompany.getProjectsContainer().createProject("Test ze boot", "Test ze spring boot well yes?", manger);
        Project myProjectExperiment = myCompany.getProjectsContainer().createProject("Social experiment", "Manelinho is best meneger", manelinho);
        myCompany.getProjectsContainer().addProjectToProjectContainer(myProjectTest);
        myCompany.getProjectsContainer().addProjectToProjectContainer(myProjectExperiment);


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
        taskRepository.save(tester);
        taskRepository.save(dependentTest);


    }

}

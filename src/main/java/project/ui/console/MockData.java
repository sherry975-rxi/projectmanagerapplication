package project.ui.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.model.*;
import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;

@Component
public class MockData {



    private UserService userService;
    private ProjectService projectService;
    private TaskService taskService;
    private BCryptPasswordEncoder passwordEncoder;

    private MockData() {

    }


    @Autowired
    public MockData(UserService userService, ProjectService projectService, TaskService taskService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Instantiates data and saves it to the database
     */
    public void instantiateMockData() {
        
        String street = "Avenida exemplo"; 
        String zipCode = "3700-000";
        String country = "country"; 
        String city = "Porto";

    // Instantiate the users, sets their passwords
    User userAdmin = userService.createUser("Teresa Ribeiro", "admin@gmail.com", "001",
            "Administrator", "917653635", street, zipCode, city, city, country);
        userAdmin.setPassword(passwordEncoder.encode("Pull2018"));
        userAdmin.setUserProfile(Profile.ADMIN);
		userAdmin.setQuestion("1");
        userAdmin.setAnswer("Kiko");

    User userDirector = userService.createUser("Roberto Santos", "director@gmail.com", "002",
            "Director", "917653636", street, zipCode, city, city, country);
        userDirector.setPassword(passwordEncoder.encode("kiwi2011"));
        userDirector.setUserProfile(Profile.DIRECTOR);
        userDirector.setQuestion("2");
        userDirector.setAnswer("School of Rock");

    User userJMatos = userService.createUser("Jorge Matos", "jmatos@gmail.com", "010", "Comercial",
            "937653635", street, zipCode, city, city, country);
        userJMatos.setPassword(passwordEncoder.encode("switch"));
        userJMatos.setUserProfile(Profile.COLLABORATOR);
        userJMatos.setQuestion("3");
        userJMatos.setAnswer("My Home");

    User userATirapicos = userService.createUser("Andreia Tirapicos", "atirapicos@gmail.com", "011",
            "Comercial", "955553635", street, "4455-654", "Leca da Palmeira", city, country);
        userATirapicos.setPassword(passwordEncoder.encode("tirapicos"));
        userATirapicos.setUserProfile(Profile.COLLABORATOR);
        userATirapicos.setQuestion("1");
        userATirapicos.setAnswer("Rocky, the rock");

    User projectManager = userService.createUser("Sara Pereira", "spereira@gmail.com", "012",
            "Técnica de recursos humanos", "9333333", street, "4455-666", "Leca da Palmeira", city,
            country);
        projectManager.setPassword(passwordEncoder.encode("manager"));
        projectManager.setUserProfile(Profile.COLLABORATOR);
        projectManager.setQuestion("2");
        projectManager.setAnswer("South Lake, Utah");




    Project project = projectService.createProject("Projecto Sprint 2", "Demo para a sprint", projectManager);
    ProjectCollaborator projectCollaborator = project.createProjectCollaborator(userATirapicos, 10);
		projectService.addProjectCollaborator(projectCollaborator);
		projectService.updateProject(project);


        userService.updateUser(userAdmin);
        userService.updateUser(userDirector);
        userService.updateUser(userJMatos);
        userService.updateUser(userATirapicos);
        userService.updateUser(projectManager);

        // Create and add tasks to Task repository
        Task taskFinished = taskService.createTask("Remove code smells", project);

        // Add User to tasks, and forces states as Ongoing
        taskFinished.addProjectCollaboratorToTask(projectCollaborator);

        taskFinished.setTaskState(new OnGoing());
        taskFinished.setCurrentState(StateEnum.ONGOING);

        // Create reports in the tasks
        taskFinished.createReport(taskFinished.getTaskCollaboratorByEmail("atirapicos@gmail.com"), Calendar.getInstance(), 0);

        // Set task state as finished last month, forces state as finished
        Calendar finishDate = Calendar.getInstance();
        finishDate.add(Calendar.MONTH, -1);

        taskFinished.setFinishDate(finishDate);

        taskFinished.setTaskState(new Finished());
        taskFinished.setCurrentState(StateEnum.FINISHED);

        Report reportA = taskFinished.getReports().get(0);
        reportA.setReportedTime(10);

        taskService.saveTask(taskFinished);

    }
}

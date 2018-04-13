package project.ui.console;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

@Component
public class MockData {



    private UserService userService;
    private ProjectService projectService;
    private TaskService taskService;

    private MockData() {

    }

    @Autowired
    public MockData(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }


    /**
     * Instantiates data and saves it to the database
     */
    public void instantiateMockData() {

    // Instantiate the users, sets their passwords
    User userAdmin = userService.createUser("Teresa Ribeiro", "admin@gmail.com", "001",
            "Administrator", "917653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userAdmin.setPassword("123456");
		userAdmin.setQuestion("1");
		userAdmin.setAnswer("test answer");

    User userDirector = userService.createUser("Roberto Santos", "director@gmail.com", "002",
            "Director", "917653636", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userDirector.setPassword("abcdef");
		userAdmin.setQuestion("2");
		userAdmin.setAnswer("test answer");

    User userJMatos = userService.createUser("Jorge Matos", "jmatos@gmail.com", "010", "Comercial",
            "937653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userJMatos.setPassword("switch");
		userAdmin.setQuestion("3");
		userAdmin.setAnswer("test answer");

    User userATirapicos = userService.createUser("Andreia Tirapicos", "atirapicos@gmail.com", "011",
            "Comercial", "955553635", "Avenida de Franca", "4455-654", "Leca da Palmeira", "Porto", "Portugal");
		userATirapicos.setPassword("tirapicos");
		userAdmin.setQuestion("1");
		userAdmin.setAnswer("test answer");

    User projectManager = userService.createUser("Sara Pereira", "spereira@gmail.com", "012",
            "Técnica de recursos humanos", "9333333", "Rua Torta", "4455-666", "Leca da Palmeira", "Porto",
            "Portugal");
		projectManager.setPassword("manager");
		userAdmin.setQuestion("2");
		userAdmin.setAnswer("test answer");

    Project project = projectService.createProject("Projecto Sprint 2", "Demo para a sprint", projectManager);
    ProjectCollaborator projectCollaborator = project.createProjectCollaborator(userATirapicos, 10);
		projectService.addProjectCollaborator(projectCollaborator);
		projectService.updateProject(project);


		userDirector.setUserProfile(Profile.DIRECTOR);
		userJMatos.setUserProfile(Profile.COLLABORATOR);
		userATirapicos.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);


		userService.addUserToUserRepositoryX(userAdmin);
		userService.addUserToUserRepositoryX(userDirector);
		userService.addUserToUserRepositoryX(userJMatos);
		userService.addUserToUserRepositoryX(userATirapicos);
		userService.addUserToUserRepositoryX(projectManager);

    }
}

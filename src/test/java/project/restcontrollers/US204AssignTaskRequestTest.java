package project.restcontrollers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;


//Configure H2
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class US204AssignTaskRequestTest {


    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

    @Autowired
    private MockMvc mockMvc;


    private User userOne;
    private User userTwo;
    private User userThree;
    private Project projectOne;
    private Task taskOne;
    private Task taskTwo;
    private ProjectCollaborator projCollabTwo;
    private ProjectCollaborator projCollabThree;


    @Before
    public void setUp() throws Exception{

        // create userOne and save in DB
        userOne = userService.createUser("Ana", "ana@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");

        // create userTwo and save in DB
        userTwo = userService.createUser("Joao", "joao@gmail.com", "02", "collaborator", "221238447", "Rua Porto",
                "5480", "Porto", "Porto", "Portugal");

        // create user3
        userThree = userService.createUser("Rui", "rui@gmail.com", "03", "collaborator", "221378449", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");


        // create a project with userOne as manager
        projectOne = projectService.createProject("Restful", "Implement API Rest", userOne);

        // create 2 tasks
        taskOne = taskService.createTask("Rest Controller", projectOne);

        taskTwo = taskService.createTask("Rest Controller Test", projectOne);

        // add users to projectOne creating a project collaborator for each
        projCollabTwo = projectService.createProjectCollaborator(userTwo, projectOne, 20);
        projCollabThree = projectService.createProjectCollaborator(userThree, projectOne, 60);




    }


    @After
    public void clear() throws Exception{
        userOne = null;
        userTwo = null;
        projectOne = null;
        taskOne = null;
        taskTwo = null;
        projCollabTwo = null;
        projCollabThree = null;

    }
    @Test
    public void test() {
    }



}

package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
//	   private static User userAdmin;
//	    private static User userCollab;
//	    private static User userDirector;
//	    private static User userJSilva;
//	    private static Task taskOne;
//	    private static Task taskTwo;
//	    private static Project projOne;
//	    private static ProjectCollaborator projCollab1;
//	    private static Profile collabProfile;
//	    @Autowired
//	    private TaskService taskContainer;
//	    @Autowired
//	    private ProjectService projContainer;
//	    @Autowired
//	    private UserService userContainer;

	public static void main(String[] args) {
		SpringApplication.run(HelloJpaApplication.class, args);
	}
	
	 @Override
	    public void run(String... strings) throws Exception {
	 }

}
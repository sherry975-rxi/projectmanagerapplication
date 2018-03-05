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
import project.model.*;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectsRepository projRepository;

    @Autowired
    private ProjCollabRepository projCollabRepository;


    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }
    @Override
    public void run(String... strings) throws Exception {

        Company myCompany = Company.getTheInstance();
        User manger = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Manger",
                "930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        User manelinho = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Enabler",
                "940000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        myCompany.getUsersRepository().addUserToUserRepository(manger);
        myCompany.getUsersRepository().addUserToUserRepository(manelinho);

        Project myProjectTest = myCompany.getProjectsRepository().createProject("Test ze boot", "Test ze spring boot well yes?", manger);
        Project myProjectExperiment = myCompany.getProjectsRepository().createProject("Social experiment", "Manelinho is best meneger", manelinho);
        myCompany.getProjectsRepository().addProjectToProjectRepository(myProjectTest);
        myCompany.getProjectsRepository().addProjectToProjectRepository(myProjectExperiment);


        ProjectCollaborator manelinhoCollab = myProjectTest.createProjectCollaborator(manelinho, 68);
        myProjectTest.addProjectCollaboratorToProjectTeam(manelinhoCollab);


        userRepository.save(manger);
        userRepository.save(manelinho);
        projRepository.save(myProjectTest);
        projRepository.save(myProjectExperiment);
        projCollabRepository.save(manelinhoCollab);


        for (User u : userRepository.findAll()) {
            logger.info(u.toString());
        }

    }


}
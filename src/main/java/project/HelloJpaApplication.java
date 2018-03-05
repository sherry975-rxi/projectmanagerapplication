package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.Repository.ProjectsRepository;
import project.Repository.UserRepository;
import project.model.Company;
import project.model.Project;
import project.model.User;
@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectsRepository projRepository;
    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }
    @Override
    public void run(String... strings) throws Exception {
        Company myCompany = Company.getTheInstance();
        User newUser2 = myCompany.getUsersRepository().createUser("Manel", "user2@gmail.com", "001", "Empregado",
                "930000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        User newUser3 = myCompany.getUsersRepository().createUser("Manelinho", "user3@gmail.com", "002", "Telefonista",
                "940000000", "Rua Bla", "BlaBla", "BlaBlaBla", "BlaBlaBlaBla", "Blalandia");
        Project myProjectA = new Project(1, "Projecto 1", "Projecto Abcd", newUser2);
        Project myProjectB = new Project(2, "Projecto 2", "Projecto Efgh", newUser3);
        myCompany.getUsersRepository().addUserToUserRepository(newUser2);
        myCompany.getUsersRepository().addUserToUserRepository(newUser3);
        myCompany.getProjectsRepository().addProjectToProjectRepository(myProjectA);
        myCompany.getProjectsRepository().addProjectToProjectRepository(myProjectB);
        userRepository.save(newUser2);
        userRepository.save(newUser3);
        projRepository.save(myProjectA);
        projRepository.save(myProjectB);
        for (User u : userRepository.findAll()) {
            logger.info(u.toString());
        }
    }
}
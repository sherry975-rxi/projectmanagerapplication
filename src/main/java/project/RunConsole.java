package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.model.Profile;
import project.model.User;
import project.repository.UserRepository;
import project.services.UserService;
import project.ui.console.MainMenuUI;
import project.ui.console.loadfiles.loadprojects.LoadProject;
import project.ui.console.loadfiles.loaduser.LoadUser;
import project.ui.console.loadfiles.loaduser.LoadUserFactory;

@Component
public class RunConsole implements CommandLineRunner {

	@Autowired
	private UserService userService; //Tirar isto quando possivel.
	@Autowired
	private UserRepository userRepository; //Tirar isto quando possivel.
	@Autowired
	MainMenuUI main;
	@Autowired
	LoadProject projectDb;
	@Autowired
	LoadUserFactory loadUserFactory;

	@Override
	public void run(String... args) throws Exception {

		// Instantiate the users, sets their passwords
		User userAdmin = userService.createUser("Teresa Ribeiro", "admin@gmail.com", "001",
				"Administrator", "917653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userAdmin.setPassword("123456");

		User userDirector = userService.createUser("Roberto Santos", "director@gmail.com", "002",
				"Director", "917653636", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userDirector.setPassword("abcdef");

		User userJMatos = userService.createUser("Jorge Matos", "jmatos@gmail.com", "010", "Comercial",
				"937653635", "Avenida dos Aliados", "4000-654", "Porto", "Porto", "Portugal");
		userJMatos.setPassword("switch");

		User userATirapicos = userService.createUser("Andreia Tirapicos", "atirapicos@gmail.com", "011",
				"Comercial", "955553635", "Avenida de Franca", "4455-654", "Leca da Palmeira", "Porto", "Portugal");
		userATirapicos.setPassword("tirapicos");

		User projectManager = userService.createUser("Sara Pereira", "spereira@gmail.com", "012",
				"Técnica de recursos humanos", "9333333", "Rua Torta", "4455-666", "Leca da Palmeira", "Porto",
				"Portugal");
		projectManager.setPassword("manager");


		userDirector.setUserProfile(Profile.DIRECTOR);
		userJMatos.setUserProfile(Profile.COLLABORATOR);
		userATirapicos.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);


		userRepository.save(userAdmin);
		userRepository.save(userDirector);
		userRepository.save(userJMatos);
		userRepository.save(userATirapicos);
		userRepository.save(projectManager);

		LoadUser loadUserDb = loadUserFactory.getLoadUserType("Utilizador_v00_Dt1.xml");
		loadUserDb.loadUsers("Utilizador_v00_Dt1.xml");

    	projectDb.loadProject("Projeto_v00_Dt1.xml");


        main.mainMenu();
    }
    
}

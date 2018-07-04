package project.ui.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import project.ui.console.MainMenuUI;
import project.ui.console.loadfiles.loadprojects.ProjectReader;

import project.ui.console.loadfiles.loaduser.UserReader;
import project.ui.console.MockData;

@Component
@Profile("!test")
public class RunConsole implements CommandLineRunner {

	@Autowired
	MockData mockData;
	@Autowired
	MainMenuUI main;
	@Autowired
	ProjectReader reader;
	@Autowired
	UserReader userReader;



	@Override
	public void run(String... args) throws Exception {

		mockData.instantiateMockData();
		userReader.readFile("Utilizador_v00_Dt1.xml");
		userReader.readFile("Utilizador_v01_Dt2.xml");
    	reader.readFile("ProjetoCorrigido_v01_Dt1.xml");
		reader.readFile("ProjetoCorrigido_v01_Dt2.xml");
    	main.mainMenu();
    }
    
}

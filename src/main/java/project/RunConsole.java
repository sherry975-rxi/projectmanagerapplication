package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.model.*;
import project.ui.console.MainMenuUI;
import project.utils.LoadProjectData;
import project.utils.LoadUserData;

import java.util.Optional;

@Component
public class RunConsole implements CommandLineRunner {

	@Autowired
	LoadUserData feedDb;
	@Autowired
	MainMenuUI main;

	@Autowired
	LoadProjectData projectDb;

    @Override
    public void run(String... strings) throws Exception {

    	feedDb.loadUsers("Utilizador_v00.xml");

    	projectDb.loadProject("Projeto_v00.xml");


        main.mainMenu();
    }
    
}

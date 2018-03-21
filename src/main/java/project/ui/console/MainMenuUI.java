package project.ui.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.Services.ProjectService;
import project.Services.TaskService;
import project.Services.UserService;
import project.controller.UpdateDbToContainersController;
import project.model.Profile;
import project.model.User;
import project.ui.console.administrator.AdminMenuUI;
import project.ui.console.collaborator.CollaboratorMainMenuUI;
import project.ui.console.collaborator.US101UserRegisterUI;
import project.ui.console.collaborator.US208LoginUI;
import project.ui.console.director.DirectorMenuUI;

import java.util.Scanner;

@Component
public class MainMenuUI {

	@Autowired
	UserService userService;
	@Autowired
	ProjectService projService;
	@Autowired
	TaskService taskService;

	protected static User userAdmin;
	protected static User userDirector;
	protected static User userJSilva;



	public void mainMenu() {
		//Updates de DataBase
        UpdateDbToContainersController infoUpdater = new UpdateDbToContainersController();
        infoUpdater.setProjectContainer(projService);
        infoUpdater.setUserContainer(userService);
		infoUpdater.updateDBtoContainer();


		userJSilva = userService.getAllUsersFromUserContainer().get(2);
		userDirector = userService.getAllUsersFromUserContainer().get(1);
		userAdmin = userService.getAllUsersFromUserContainer().get(0);
		
		displayOptions();
	}

	public static void displayOptions() {

		printImage();

		Scanner input = new Scanner(System.in);
		boolean condition = true;
		while (condition) {

			System.out.println();
			System.out.println("                  MAIN MENU");
			System.out.println("______________________________________________");
			System.out.println("[1] New Registration");
			System.out.println("[2] Login");
			System.out.println("[3] Administrator");
			System.out.println("[4] Director");
			System.out.println("[5] Collaborator");
			System.out.println("[E] EXIT");
			System.out.println("______________________________________________");
			System.out.println();
			System.out.println("Please select an option:");
			System.out.println();

			String choice = input.nextLine().toUpperCase();
			System.out.println();
			switch (choice) {
				case "1":
					US101UserRegisterUI userRegister = new US101UserRegisterUI();
					userRegister.userRegister();
					break;
				case "2":
					US208LoginUI doLogin = new US208LoginUI();
					doLogin.doLogin();
					break;
				case "3":
					AdminMenuUI adminMenu = new AdminMenuUI(userAdmin);
					adminMenu.adminMenu();
					break;

				case "4":
					userDirector.setUserProfile(Profile.DIRECTOR);
					DirectorMenuUI directorMenu = new DirectorMenuUI(userDirector);
					directorMenu.directorMenu();
					break;


				case "5":
					//userJSilva.setUserProfile(Profile.COLLABORATOR);
					CollaboratorMainMenuUI collaboratorMenu = new CollaboratorMainMenuUI(userJSilva);
					collaboratorMenu.displayOptions();

					break;

				case "E":
					condition=false;
					System.out.println("----YOU HAVE EXIT FROM APPLICATION----");
					break;

				default:
					System.out.println("Choose a valid option:");
					break;
			}
		}

	}

	public static void printImage() {

		System.out.println();
		System.out.println("");
		System.out.println("");
		System.out.println("          `.----..  ...    ...`   `..` .:-            `.----.`  ...    `..`           ");
		System.out.println("         +dmddddhy  dmd`  +dddd`  /dd/ omd`  :++`    /hdmddddo `dmh    :mm/          ");
		System.out.println("         mNd-.....  sNN-  dN/mN/  yNm. -/: `.sNN/.. /NNh-....` `mNd    /NN+       ");
		System.out.println("         yNmyo:-`   :NN+ -Nm`yNh  mNy  smm`.ydNNhhs hNN:       `mNm++++yNN+        ");
		System.out.println("         `:shdmmds` `mNh oNy /Nm`-NN/  yNN`  oNN-   hNN-       `mNmyyyyhNN+           ");
		System.out.println("            `-sNN+  yNm`dN/ `mN/oNm`  yNN`  oNN-   sNN+        `mNd    /NN+       ");
		System.out.println("        /+++++hNN/  /NNsNm`  hNhhNy   yNN`  oNNs/- -dNms++++/  `mNd    /NN+         ");
		System.out.println("         oyhhhhhs/   `syyy+   -yyyy-   +yy`  `oyhy/  .+yhhhhyo `yys    -yy:           ");
		System.out.println(
				"                                                                                                   ");
		System.out.println("");
		System.out.println("");
	}
}

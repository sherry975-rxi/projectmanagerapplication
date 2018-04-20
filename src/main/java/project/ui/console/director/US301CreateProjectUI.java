package project.ui.console.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.controllers.US301CreateProjectController;
import project.model.EffortUnit;
import project.model.User;

import java.util.*;
import java.util.logging.Logger;


@Component
public class US301CreateProjectUI {
	@Autowired
	private US301CreateProjectController controller;

	String projectName;
	String projectDescription;
	String projectManagerName;

	EffortUnit currentEffortUnit;

	Integer budget;
	User projectManager;

	List<Integer> availableCalculationMethods;

	boolean isProjectCreationOngoing;

	String mainCommand;
	String dataInput;

	public void createProject() {


		projectName = "(Insert Name)";
		projectDescription = "(Insert Description)";
		projectManagerName = "(Select Manager)";

		currentEffortUnit = EffortUnit.HOURS;

		budget = 0;
		projectManager = null;

		availableCalculationMethods = new ArrayList<>(Arrays.asList(1,2,3));

		isProjectCreationOngoing = true;

		mainCommand = null;
		dataInput=null;



		Scanner mainComm = new Scanner(System.in);
		Scanner dataIn = new Scanner(System.in);

		System.out.println("To create a project, input the following fields:");
		System.out.println(viewOptions());
		System.out.println("");

		while (isProjectCreationOngoing) {
			System.out.println("Please choose a command to input a field:");
			mainCommand = mainComm.nextLine().toUpperCase();

			switch (mainCommand) {
			case "1":
				System.out.println("Please insert a name for your project:");
				dataInput = dataIn.nextLine();
				projectName = dataInput;
				break;

			case "2":
				System.out.println("Please insert a description for your project:");
				dataInput = dataIn.nextLine();
				projectDescription = dataInput;
				break;

			case "3":
				dataIn = this.caseThree(controller, dataIn);
				break;

			case "4":
				dataIn = this.caseFour(dataIn);
				break;

			case "5":
				dataIn = this.caseFive(dataIn);
				break;
			case "6":
			    dataIn = this.caseSix(dataIn);
				break;

			case "C":
				this.caseC(controller);
				break;

			case "B":
				System.out.println("Project creation cancelled!");
				System.out.println("");
				isProjectCreationOngoing = false;
				break;

			default:
				System.out.println("You have the following data:");
				System.out.println(viewOptions());
				System.out.println("");
			}

		}

		System.out.println("Returning to main menu...");

	}

	/**
	 * This method displays the available commands, as well as the data already
	 * inputed in each of the fields
	 * 
	 * @return a String of all commands and data to be used in Project creation
	 */
	private String viewOptions() {
		String options = "[1] - Project Name: " + projectName;
		options += "\n[2] - Project Description: " + projectDescription;
		options += "\n[3] - Project Manager: " + projectManagerName;
		options += "\n[4] - Project Effort Unit: " + currentEffortUnit.toString();
		options += "\n[5] - Project Budget: " + budget;
		options += "\n[6] - Project Cost Calculation Methods: " + availableCalculationMethods.toString();
		options += "\n[any key] - View Data and commands";
		options += "\n[C] - Create Project with chosen data";
		options += "\n[B] - Cancel Project creation and return to Director Menu";

		return options;
	}

	/**
	 * Case three deals with the selection of a project manager. It receives the
	 * controllers and the data Input Scanner, and executes the Project Manager
	 * selection method
	 * 
	 * @param controller
	 * @param dataIn
	 * 
	 * @return the given scanner, so its input can be refreshed and not read by the
	 *         next scanner
	 */
	private Scanner caseThree(US301CreateProjectController controller, Scanner dataIn) {
		List<String> projectCollaborators = controller.listActiveCollaborators();

		if (projectCollaborators.isEmpty()) {
			System.out.println("No Collaborators available, project creation impossible!!");
			isProjectCreationOngoing = false;

		} else {
			System.out.println("Please select a collaborator:");
			System.out.println("");
			for (String other : projectCollaborators) {
				System.out.println(other);
				System.out.println("");
			}
			System.out.println("(Type only the number from the list, invalid number won't change current selection");

			if (dataIn.hasNextInt()) {
				projectManager = controller.selectCollaborator(dataIn.nextInt());
				dataInput = dataIn.nextLine();

				if (projectManager != null) {
					projectManagerName = projectManager.getName();
				}
			}

			else {
				dataInput = dataIn.nextLine();
				System.out.println("Not a number!");
				System.out.println("");
			}
		}
		return dataIn;

	}

	/**
	 * Case four deals with the selection of effort units. It receives the scanner
	 * and current Effort Units as String, and executes the selection method
	 * 
	 * @param dataIn
	 * 
	 * @return the given scanner, so its input can be refreshed and not read by the
	 *         next scanner
	 */
	private Scanner caseFour(Scanner dataIn) {
		System.out.println("Press [0] to change the effort Unit of your project:");
		System.out.println("(Currently: " + currentEffortUnit.toString() + ")");
		System.out.println("");
		dataInput = dataIn.nextLine();
		if ("0".equals(dataInput)) {
			if (currentEffortUnit.equals(EffortUnit.HOURS)) {
				currentEffortUnit = EffortUnit.PM;
				System.out.println("The effort Unit of this project changed to Person/Month.");
			} else {
				currentEffortUnit = EffortUnit.HOURS;
				System.out.println("The effort Unit of this project changed to Hours.");
			}
			System.out.println("");
		}
		return dataIn;
	}

	/**
	 * Case five deals with the selection of budget. It receives the scanner and,
	 * and executes the selection method
	 * 
	 * @param dataIn
	 * 
	 * @return the given scanner, so its input can be refreshed and not read by the
	 *         next scanner
	 */
	private Scanner caseFive(Scanner dataIn) {
		System.out.println("Please type the budget of your project:");
		System.out.println("(Currently:" + budget + ")");
		if (dataIn.hasNextInt()) {
			budget = dataIn.nextInt();
			dataInput = dataIn.nextLine();
		} else {
			dataInput = dataIn.nextLine();
			System.out.println("Not a number!");
		}
		return dataIn;
	}

	private Scanner caseSix(Scanner dataIn) {

		Logger log = Logger.getAnonymousLogger();

		System.out.println("In case the same collaborator works on the same report with different costs, the Project Manager can calculate with:");
		System.out.println("[1] - The collaborator's earliest cost, when the report was created");
        System.out.println("[2] - The collaborator's latest cost");
        System.out.println("[3] - The average of all the collaborator's costs");

        System.out.println("");
        Integer number=0;

        boolean loop=true;
        while(loop) {
            System.out.println("Currently enabled: " + availableCalculationMethods.toString());
            System.out.println("");
            System.out.println("[choose between 1 and 3 to activate/inactivate]");
            System.out.println("[any key exit]");
            System.out.println("(You must have at least 1 calculation method!)");
            System.out.println("");
            try  {

                number = dataIn.nextInt();

                if(number>0 && number <4) {
                    availableCalculationMethods=controller.allowDisableCalculationMethods(availableCalculationMethods, number);
                } else {
                    loop=false;
                }

            } catch (InputMismatchException i) {
                loop=false;
                log.info(i.getMessage());
            }
        }

		return dataIn;
	}



	/**
	 * This case attempts to create the project with the data provided. Should a
	 * project manager not exit, creation will fail and return to data submission
	 * form
	 * 
	 * @param controller
	 */

	private void caseC(US301CreateProjectController controller) {
		if (projectManager == null) {
			System.out.println("Please select a Project Manager first!");
			System.out.println("");
		} else {
			controller.createProject(projectName, projectDescription, projectManager);
			controller.changeBudget(budget);
			controller.selectCalculationMethods(availableCalculationMethods);
			if (currentEffortUnit.equals(EffortUnit.PM)) {
				controller.changeEffortUnitToPersonMonth();
			}
			System.out.println("Project created!");
			System.out.println("");
			isProjectCreationOngoing = false;

		}
	}

}

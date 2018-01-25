package project.ui.uiProjectManager.uiPmManageTeam;

import project.controller.PrintProjectInfoController;
import project.controller.US351AddColaboratorToProjectTeamController;

public class US351AddCollaboratorToProjectTeamUI {

	public void addCollaboratorToProjectTeam() {
		String line = "______________________________________________";
		US351AddColaboratorToProjectTeamController controller = new US351AddColaboratorToProjectTeamController();

		if (controller.getAllProjects().isEmpty()) {
			System.out.println("There are no projects!");
		} else {
			System.out.println(line);
			System.out.println("PROJECT LIST");
			System.out.println(line);

			for (int i = 0; i < controller.getAllProjects().size(); i++) {
				PrintProjectInfoController projectInfo = new PrintProjectInfoController(
						controller.getAllProjects().get(i).getIdCode());
				System.out.println("");
				System.out.println(line);
				System.out.println("PROJECT " + projectInfo.printProjectNameInfo().toUpperCase());
				System.out.println(line);
				System.out.println("ID: " + projectInfo.printProjectIDCodeInfo());
				System.out.println("STATUS: " + projectInfo.printProjectStatusInfo());
				System.out.println("DESCRIPTION: " + projectInfo.printProjectDescriptionInfo());
				System.out.println("START DATE: " + projectInfo.printProjectStartDateInfo());
				System.out.println("FINISH DATE: " + projectInfo.printProjectFinishDateInfo());
				System.out.println("PROJECT MANAGER: " + projectInfo.printProjectManagerInfo());
				System.out.println("PROJECT TEAM: " + projectInfo.printProjectTeamInfo());
				System.out.println("PROJECT BUDGET: " + projectInfo.printProjectBudgetInfo());
				System.out.println(line);
			}

			System.out.println("");
		}
	}

}

/**
 * 
 */
package project.ui.uiCollaborator;

import java.util.Scanner;

import project.controller.PrintProjectInfoController;
import project.controller.PrintTaskInfoController;
import project.controller.US204v2createRequestAddCollaboratorToTaskTeamController;
import project.model.ProjectCollaborator;
import project.model.User;

/**
 * In this UI the user (project collaborator) confirms his intention to assign
 * himself to a task as task collaborator. If he confirms this intent, an
 * assignment request is sent to the project manager of this project and if the
 * project manager confirms the assignment, the UI returns a message confirming
 * the effectiveness of this addition.
 * 
 * @author Group3
 *
 */

public class US204v2CreateTaskAssignmentToCollaboratorUI {

	User user;
	String taskID;
	Integer projID;
	ProjectCollaborator projcollab;

	/**
	 * Constructor
	 * 
	 * @param user
	 *            User to add task to user task list
	 * 
	 * @param taskID
	 *            Task to add to user task list
	 */
	public US204v2CreateTaskAssignmentToCollaboratorUI(User user, String taskID) {

		this.user = user;
		this.taskID = taskID;
		this.projID = null;
	}

	public void createTaskAssignment() {

		PrintProjectInfoController printProjectInfoController = new PrintProjectInfoController(this.projID);
		String projectName = printProjectInfoController.printProjectNameInfo();
		PrintTaskInfoController printTaskInfoController = new PrintTaskInfoController(this.taskID, this.projID);
		String taskName = printTaskInfoController.printTaskNameInfo();

		printLion();

		System.out.println("PROJECT - " + projectName);
		System.out.println("");
		System.out.println("                     TASK                    ");
		System.out.println("*** " + taskName + " ***");
		System.out.println("______________________________________________");

		Scanner input = new Scanner(System.in);

		System.out.println("Are you sure you want to assign yourself to this task ? \n");
		System.out.println("[Y] to add");
		System.out.println("[N] to cancel \n");

		String yerOrNo = input.nextLine();

		US204v2createRequestAddCollaboratorToTaskTeamController controller = new US204v2createRequestAddCollaboratorToTaskTeamController(
				taskID, user);

		// In case user writes something different from "y" or "n"
		while (!("n".equalsIgnoreCase(yerOrNo)) && !("y".equalsIgnoreCase(yerOrNo))) {
			System.out.println("\nInvalid answer. Try again (\"y\" or \"n\")");
			yerOrNo = input.nextLine();
		}

		if (yerOrNo.equalsIgnoreCase("y")) {
			if (controller.createTaskTeamRequest() == true) {
				System.out.println("Your request is pending approval.");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(taskID, projID, user);
				taskDetailsUI.taskDataDisplay();
			} else {
				System.out.println("Your request was not done.");
				TaskDetailsUI taskDetailsUI = new TaskDetailsUI(taskID, projID, user);
				taskDetailsUI.taskDataDisplay();
			}
		} else {
			System.out.println("Your request was not created.");
			TaskDetailsUI taskDetailsUI = new TaskDetailsUI(taskID, projID, user);
			taskDetailsUI.taskDataDisplay();
		}
	}

	public void printLion() {

		System.out.println(
				"                                                                                                                                                      ");
		System.out.println(
				"                                                                                                                                                      ");
		System.out.println(
				"                                                                                                                                                      ");
		System.out.println(
				"                                                                                                                                                      ");
		System.out.println(
				"                                                            `           ```.---`                                                                      ");
		System.out.println(
				"                                                    ``.-:/+/--::- ``  `//:oo+:` ````                                                                  ");
		System.out.println(
				"                                                ``-:/+ssyyhdddhhhs:-.:yhhdhs+/++++/-``                                                                ");
		System.out.println(
				"                                             ``.-/osyhyyyyyyhdmmmmdhyydddhysosyyyyyo+/:--.``                                                          ");
		System.out.println(
				"                                          ``.-/+syyhyyyso++ooyyyhddhhhdyyoo+++oyhhhdddhhhys+:.`                                                       ");
		System.out.println(
				"                                        `-:+syhhdddhysoo+++/++soyyhhyyyoo++///++osyhddddddhs+:.`                                                      ");
		System.out.println(
				"                                       `-/oyddmddhhysooooooo+o++syyyyss++o+//+++ooossyhdddddhs+.`                                                     ");
		System.out.println(
				"                                     `.:+ssyyhdddhyysooooooooo/+++/+o+o+++oo++o++ooosyyyhhhhyyy+:.                                                    ");
		System.out.println(
				"                                     `.:/++osydhysoooooo+++++++/::-:/://+++++++++oooooosyyyysssso/`                                                   ");
		System.out.println(
				"                                     ``:++oosssoo+ossooo+//://::::::::/:/+:+/++ooooooo+++oyyyyysso.                                                   ");
		System.out.println(
				"                                       -o+++/+o+ooo++++s+::--:::/:/+://///::+++o+o+ssooosssyhyysso`                                                   ");
		System.out.println(
				"                                        /+://ossoo+::/s/:---:///+++yo+///:::/+++oo//ohmmmmdhhysso:`                                                   ");
		System.out.println(
				"                                       ..//:/+so///:/+/-----:-:++osy+++/::////+o+o/:/shNMNdso+os-`                                                    ");
		System.out.println(
				"                                      ``./h//+:-:+/+/:-::---..-:/oyo/::--:/:/oosooo::/ohdmds++dd/`                                                    ");
		System.out.println(
				"                                       .odh+-.-:++/::-:::.....---/o/::::--+::ossyso/::/ysoyyosmdy-`                                                   ");
		System.out.println(
				"                                     `-+o/-..:////+///so+:-`.....-:-.-:/....-/+osyso::/ss+:++smNdo.                                                   ");
		System.out.println(
				"                                     ---...-/+/:+//:/oh/+/h:`...----.-:/../ysohdsyy+//:+o+/-::ohdds.                                                  ");
		System.out.println(
				"                                    ```..--:////+/:::/-//+yo....--:.-://-/hdooo:/+ss+++/+++/:---+hdh:                                                 ");
		System.out.println(
				"                                    `...---:////::----/:.-:+-----::--:+/+hs/--:://ss+oo++++/:---:/ymd+`                                               ");
		System.out.println(
				"                                   `/:...--:///:--.-..---:/+/:::::////+oyhys/:///+yo++/////::-----+hNms.                                              ");
		System.out.println(
				"                                  `::-...-::///:..---...-//:-:::++oo+++/oyyho////ss+///:///:::---.:ymNds:`                                            ");
		System.out.println(
				"                                  .:-....--::/+:...-..-.:/:-://++ooo++ss+/hyho++/++:+//://:/::----:shdms:.                                            ");
		System.out.println(
				"                                 `.--.`..--:////-.....-./:-:::/++++++oooo+yyhys+://:+::/://::::---:smNNms-`                                           ");
		System.out.println(
				"                                 `::/-...--:::/+/-....`.::::::::::::/+ooooysyyso/::/::///::/:------+dmNNds-                                           ");
		System.out.println(
				"                                 .+o/....---:://+/...``----::oss/:/+sysoo+osshso///::::///::/:----:/hdmmmh/`                                          ");
		System.out.println(
				"                                ./++:.`..--::://++-..``-..`/sdNmddddNNmhs-//oys++o++/::/:::::/:--:/oohdddds.                                          ");
		System.out.println(
				"                               `-/++:.....-:-::/+++:-..-...-..-+dddmy:..::+/+oooossy+/::/::///::--+shhhddhy+`                                         ");
		System.out.println(
				"                               .:/+/:.-....---://oo+/-.-....``` `od:```..--::/+oohdho////:::::/---+yymddhhyo:`                                        ");
		System.out.println(
				"                              `-/++++--....:-::/++///+:.`````   `-h.`````...-:/oydhhy+::::-:--::-//hdddhyyso/`                                        ");
		System.out.println(
				"                              .:/++o+:-.`.:-.:::/:-:/::-...`` ``.:y:.```...--/ooyysss+/:::--:-::-+symmdhyss//:`                                       ");
		System.out.println(
				"                              -:++oo//-.`--.---:-.-:::+:::-.``-/oyysss+-.-:+osshsys//++/:----::::/dhmNdyysss-..                                       ");
		System.out.println(
				"                             `-//sy+++/..-..----.--:/oo+/so//:...--.../syhyhdhdmhso:-:///:----/:/odNmNNdyosys:-....``                                 ");
		System.out.println(
				"                             .:/oysoss+.-....:---::/+so++/.`````````````.//syhhdhs//:://:::---:/+yNNNNNNmysyhsssooo++//:.`                            ");
		System.out.println(
				"                             :-:yysyhy/--.-.-:--/::/+++oo/.`  ```````````.:oyhhhs+/:/-:/:-::::-/odNMMNNNNmhyyyysyhyyyyyso+/-                          ");
		System.out.println(
				"                            `-`/syhdhyo:....:--::/+//+oso++//----:..--..-+shhyys++::::::/:-::/-/odNMMNNNMNmdhyhyydddhdhhys++:                         ");
		System.out.println(
				"                             `-+ydmdmhy+...::::////+/oyysyyys+osos++syyyyhyhmhso+/::/:::::--://+sdNMMNNNNMNmdhhhhddmdddhys++/-                        ");
		System.out.println(
				"                             `:/yddNmhms---:.-:+::+/ooyyssyyssyyyyshsydddhohdyso++////:::::-:/+oydNMMMNNNMMNmmdhddmmmdhyyo+++:                        ");
		System.out.println(
				"                             ``/hmNmmNNy/::-.-//-/+ooosyyyyosyyhyhohsyhhdyyddsso+so/////:/:::+++ymNMMMMMNNMMNmmdddNNmhyss+++/-                        ");
		System.out.println(
				"                              `sdmmmNNmh++:.-:::-/+++ooyys+osssshyshhyyshyyhhyss+oo+:::::::/+os+smNMMMMMMNNMMNmmddmmdysoo+///:                        ");
		System.out.println(
				"                              -ydmmNMNmhos/-+/::/:///o+os+++yoysdyyyyhysyyyysshso++//:/+/://yyhyydNMMMMMMNNMNNNmmmmdyooo++//::.                       ");
		System.out.println(
				"                              /+dmNMMNmdyds/s++o/++////+o+//s+sydyyhoyhsoyyso+syo/+:+::+o//osddmdmNMMMMMMMNNNNNNmmdhyso++//:::/                       ");
		System.out.println(
				"                             `:sydNMMNmNmNdsyssyyhs+++++o+/+osyhhhddsydhoysy+/+so+o:++ooyosoymNNNNNMMMNMMMNNNNNNmmhhys+///:--//`                      ");
		System.out.println(
				"                              -/:yNNNmNNNNNhmdhmmdssooo+so++oshhyhdhhshhsysy+++yyosossysmhhhhNMMMNMMMMNMNNNNNNNNmdhhyo+///:--//`                      ");
		System.out.println(
				"                              .-.odmNNNNNNNNNmmNNmNmyyhyoy+osydhhdhhhsyyssyyooohdohddhmdNNmdNNMMMNMMMMMNMNNNNNmNdhhhhyo//:--/+:                       ");
		System.out.println(
				"                              .`.odmmmNNNNNNNNNMNNMNdmymhhhoshdmddhdmhyyysyoosyhdymNNNNMMMMNMMMMMNMNMMMNNNNNNNmNdhhdys+/----o+`                       ");
		System.out.println(
				"                              ` `syyymNNNNNNNNNNNMMMmNmmNmmhyhmNmdmdNdhdhssohdmmmmMMMMMMMMMMMMMMMMMNMMMMMNNNNNmmdddhys+:--:o+-                        ");
		System.out.println(
				"                                .o/-+dNNNNNNNNNNNNNNNNMMMMNNmdmMNNdNNmdmdyhhmNNNMMMMMMMMMMMMMMMMMMMNMMMMMNNNNNmddddhyso/:os/.                         ");
		System.out.println(
				"                                -:``+dNNNNNNNNNNNNNNNNMMNMNMNNNMNNNNNNmNddmmNMMMMMMMMMMMMMMMMMMMMMMMNMMMMMNNNmmmdddhysosys-                           ");
		System.out.println(
				"                                -. -smmhdNNNNNNNNNNNNNNNNMNMMMNNMMMMMNMNNNMNNNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNNmmmdhysssss:`                            ");
		System.out.println(
				"                                `` +ddyhdNNNNNNNNNNmNNNNNNNNNNMNMMMMMMMMNMMNNMMMMMMMMMMMMMMMMMMMMMMMNNMMMMNNNmmdhssys:`                               ");
		System.out.println(
				"                                  .yhsysymNNNNNmNNmmmmmmNmmmmmmmNMMMMMMMNMMNNMMMMMMMMMMMMMMMNNmNNMMMMNMMMNNNNmmhso+-                                  ");
		System.out.println(
				"                                 `+y/-..ymmmmNNmddhyysyyyhhyss+++oydNMMMMMMMMMMMMMMMMMMMMMMmhyhmmNNMMMMMMNNNNmdy+o:                                   ");
		System.out.println(
				"                                 .:-`  :hyy+ohdyss+/::///++++++++///+oshmNMMMMMMMMMMMMMMMms+osyhddmNNMMMMNNNNdho//.                                   ");
		System.out.println(
				"                                 ``   .o++s+oss++/:--..-----:://++++++////ohNMMMMMMMMMMMd::/+ossyhdmNNMNNNNNmhs+:-`                                   ");
		System.out.println(
				"                                    `-oyhdmmd+/:::--.......-----::://///:::+/yNMMMMMMMNy/:/oo++oosydmmmmNmhdh+:-`                                     ");
		System.out.println(
				"                                 `.:oyhhdmmhyo:-:--.........----:----:::::/+//+dNMMMNh/--::+++///+oyddhhhs//+.`                                       ");
		System.out.println(
				"                                `-/oydhyddhsss/---............----.--::-://///:/oyhy/----.-://+++++odNdso:..``                                        ");
		System.out.println(
				"                                `.-/++ssyyhyyyo:---..-.-.........`..-:-::///+/::/:-:-::-:/+:-:++:/++yNdh+-`                                           ");
		System.out.println(
				"                                     ``..-::+oo/:-.........-....```.---:/:---::/::::-:-+dh+:/++//++/:so:.`                                            ");
		System.out.println(
				"                                              ``````............```.....++`..-://:://+/-oo/:-+sds//:-::-`                                             ");
		System.out.println(
				"                                                        ``````````````..-/````.-::./s+s/:ss/ohyo://--://`                                             ");
		System.out.println(
				"                                                                ```````-/-.```-s/:.-hm+y:hmd/-.---/o:::`                                              ");
		System.out.println(
				"                                                                    ````/.`` ``:+.-`/y+//+hNs:---::---`                                               ");
		System.out.println(
				"                                                                       ````` ```.....:///:/+:--``````                                                 ");
		System.out.println(
				"                                                                                                                                                      ");
		System.out.println(
				"                                                                                                                                                      ");
		System.out.println(
				"                                                                                                                                                      ");

	}

}

package project.model.taskstateinterface;

import org.springframework.hateoas.Link;
import project.model.StateEnum;
import project.model.Task;
import project.restcontroller.RestProjectController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class Created implements TaskStateInterface {

	 /**
     * Method that checks if a task that is currently in the created state meets the conditions to change to the planned state.
     *
     * @param task to check if it has the conditions to change to the planned state
     */
	@Override
    public void doAction(Task task) {
		Planned plannedState = new Planned();
		if(plannedState.isValid(task)) {
			task.setTaskState(plannedState);
			task.setCurrentState(StateEnum.PLANNED);
	    }
    }

	/**
	 * Method that creates the rels available to this state and adds the links to the task
	 *
 	 * @param task Task to get links from
	 * @param projectId Id of the project to create the links
	 */
	@Override
	public void addRels(Task task, int projectId){

		//Not yet implemented - PATCH
		Link reference = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).withRel("Add estimated dates");
		task.add(reference);

		//Not yet implemented POST
		Link reference2 = linkTo(RestProjectController.class).slash(projectId).slash("tasks").slash(task.getTaskID()).slash("team").withRel("Add Project Collaborator");
		task.add(reference2);
	}
}

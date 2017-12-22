package project.controller;

import project.model.Task;

/**
 * @author Group 3
 * 
 *         This class implements the controller correspondent to the
 *         createDependenceFromTask functionality.
 *
 */
public class CreateDependenceFromTaskController {

	/**
	 * @param taskDependent
	 *            Task that will be set as dependent from another one.
	 * @param taskReference
	 *            Task that will be set as the reference.
	 * @param incrementDays
	 *            Days that will be incremented to the estimated start date of the
	 *            reference task in order to set the estimated start date of the
	 *            dependent task
	 */
	public void createDependenceFromTask(Task taskDependent, Task taskReference, int incrementDays) {

		taskDependent.createTaskDependence(taskReference, incrementDays);

	}

}

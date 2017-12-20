package project.controller;

import project.model.Project;
import project.model.User;

public class CreateUS350v02Controller {

	public void addProjectCollaboratorToProjectTeam(User u1, Project p1) {

		p1.addProjectCollaboratorToProjectTeam(p1.createProjectCollaborator(u1, 0));

	}
}

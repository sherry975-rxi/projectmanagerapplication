package project.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.ProjCollabRepository;
import project.model.ProjectCollaborator;

@Service
public class ProjectCollaboratorService {
	
	@Autowired
	ProjCollabRepository projectCollaboratorRepository; 
		

	public void addProjectCollaborator(ProjectCollaborator user) {
		
		this.projectCollaboratorRepository.save(user);
			
	}	
}

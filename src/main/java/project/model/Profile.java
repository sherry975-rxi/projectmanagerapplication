package project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Profile Class for setting profiles in Users
 * 
 * @author Group3
 *
 */

public enum Profile {

	UNASSIGNED, COLLABORATOR, DIRECTOR;

}
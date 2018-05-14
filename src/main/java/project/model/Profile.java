package project.model;

/**
 * Profile Class for setting profiles in Users
 * 
 * @author Group3
 *
 */

public enum Profile {

	UNASSIGNED("ROLE_UNASSIGNED"), COLLABORATOR("ROLE_COLLABORATOR"), DIRECTOR("ROLE_DIRECTOR"), ADMIN("ROLE_ADMIN");

	private String description;

	Profile(String description) {
		this.description = description;
	}

	public static boolean contains(String test){
		for (Profile other: Profile.values()) {
			if (other.name().equals(test)){
				return true;
			}

		}
		return false;
	}

	public String getDescription() {
		return this.description;
	}





}
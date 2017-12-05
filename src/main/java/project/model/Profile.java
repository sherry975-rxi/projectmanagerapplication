package code;

/**
 * Profile Class for setting profiles in Users
 * 
 * @author Group3
 *
 */

public class Profile {

	private int profile;
	public static final int VISITOR = 0;
	public static final int COLLABORATOR = 1;
	public static final int DIRECTOR = 2;

	/**
	 * Constructs a default user profile
	 */
	public Profile() {
		this.profile = VISITOR;
	}

	/**
	 * Sets profile as Visitor
	 */
	public void setVisitor() {
		this.profile = VISITOR;
	}

	/**
	 * Sets profile as Collaborator
	 */
	public void setCollaborator() {
		this.profile = COLLABORATOR;
	}

	/**
	 * Sets profile as Director
	 */
	public void setDirector() {
		this.profile = DIRECTOR;
	}

// WHY????? Should be deleted
//	/**
//	 * Changes profile to the one provided if an object of the class Company is also
//	 * provided
//	 * 
//	 * @param ob
//	 *            object provided to check if it's a Company object
//	 * @param profile
//	 *            profile to which to change this.profile
//	 */
//	public void changeUserProfile(User user, Profile profile) {
//		this.profile = profile.profile;
//	}

	/**
	 * Returns the profile
	 * 
	 * @return profile
	 */
	public int getProfileInt() {
		return this.profile;
	}

}
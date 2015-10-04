package entity;
/**
* The Admin class inherits MailUser class. This type of user is able to:
* Create a new user, set rights for user, delete users
*/
public class Admin extends MailUser {
	/**
	 * Constructor initializes an admin user
	 * Assigns the rights of the admin
	 * @param name name of the admin
	 */
	public Admin(String name)
	{
		super(name);
		setUserRights("admin");
	}
	
	/**
	 * Creates a new user and assigns a private mailbox to the user
	 * sends the new user info to the Directory Manager to store it to the database
	 * @param name name of the new user
	 * @param rights the rights of the user: "admin, bcast, or normal"
	 * @return user object.
	 */
	public MailUser create_user(String name, String rights)
	{
		// If the rights type is invalid abort user creation
		if (!(rights.equals("normal")) && !(rights.equals("bcast")) )
		{
			System.out.println("Invalid user rights, creating a user failed.");
			return null;
		}
		MailUser user = new MailUser(name);
		set_rights(user,rights);
		return user;
	}
	
	/**
	 * Assigns rights to a user
	 * @param rights the created user is either "admin, bcast, or normal"
	 */
	public void set_rights(MailUser user,String rights)
	{
		user.setUserRights(rights);
	}
}

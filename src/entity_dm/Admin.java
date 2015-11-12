/**
 * The entity package contains the objects of the program
 */
package entity_dm;

import javax.persistence.*;

/**
* The Admin class inherits MailUser class. This type of user is able to:
* Create a new user, set rights for user, delete users
*/
@Entity
@Table(name="MAILUSER")
@DiscriminatorValue("admin")
public class Admin extends MailUser {
	/** Empty Constructor*/
	public Admin(){}	

	/**
	 * Constructor initializes an admin user
	 * Assigns the rights of the admin
	 * @param name name of the admin
	 */
	public Admin(String name, String pword)
	{
		super(name,pword);
		setUserRights("admin");
	}
	
	/**
	 * Creates a new user and assigns a private mailbox to the user
	 * sends the new user info to the Directory Manager to store it to the database
	 * @param name name of the new user
	 * @param rights the rights of the user: "admin, bcast, or normal"
	 * @return user object.
	 */
	public MailUser create_user(String name,String pword, String rights)
	{
		MailUser user = new MailUser(name,pword);
		set_rights(user,rights);
		return user;
	}
	
	/**
	 * Assigns rights to a user
	 * @param rights the created user is either "admin, bcast, or normal"
	 */
	public void set_rights(MailUser user,String rights)
	{
		if (!(rights.equals("normal")) && !(rights.equals("bcast")) && !(rights.equals("admin")))
		{
			System.out.println("Invalid user rights, Rights will be forced to normal.");
			rights = "normal";
		}
		user.setUserRights(rights);
	}
}

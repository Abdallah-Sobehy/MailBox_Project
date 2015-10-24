package ejb;
/**
* Interfce for 
* @author Mostafa Fateen
* @author Abdallah Sobehy
* Created on 10/8/2015.
*/
import javax.ejb.Remote;
import entity.MailUser;

@Remote 
public interface DirectoryManagerInt {

	/**
	 * Creates a new user with the input user name and rights
	 * Persists the new user into the database.
	 * returns Id of the created user.
	 */
	public int addUser(String username, String userrights);
	
	/**
	 * removes a Mailuser from the database
	 * @param id of the user to be removed
	 */
	public String removeUser(int id);
	
	public boolean signIn(String username, String password );
	/**
	 * @return a string containing info about logged in user: name, ID, rights
	 */
	public String printUser ();
	/**
	 * @return a string containing info about user with given ID: name, ID, rights
	 */
	public String printUser (int id);
	/**
	 * @return a string representing the menu of available options for the admin: add, remove, list users or their rights
	 */
	public String admin_menu();
	/**
	 * Returns a strong of all users in the data base.
	 */
	public String list_all_users();
	
}

/**
 * The ejb package contains the server's logic of the program
 */
package ejb;

import javax.ejb.Remote;
import entity.MailUser;

/**
 * Interfce for  the directory manager
 *
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 */

@Remote 
public interface DirectoryManagerInt {

	/**
	 * Signs in the Admin for the first time when he opens the program.
	 * If there was no USERS in the database, i.e. it is the first time to run the program, it takes a new user name
	 * and password and creates a new admin with these params
	 *
	 * @param username
	 * @param password
	 * @return Boolean indicating whether the sign in successful or not
	 */
	public boolean signIn(String username, String password );

	/**
	 * Add a user to the database and assign his rights
	 *
	 * @param username
	 * @param userrights
	 * @return the generated user ID
	 */
	public int addUser(String username, String userrights);

	/**
	 * Removes a user from the database
	 *
	 * @param id
	 * @return a string declaring whether the operation failed or not + the id of the user
	 */
	public String removeUser(int id);

	/**
	 * A simple method to return the info of the current user
	 *
	 * @return a string containing the ID, Name, and his Rights
	 */
	public String printUser ();

	/**
	 * A simple method to return the info of a specified user by hid ID
	 *
	 * @param id
	 * @return a string containing the ID, Name, and his Rights
	 */
	public String printUser (int id);

	/**
	 * A simple printer to list all users in the database with their ID and Rights
	 *
	 * @return a string containing the info of all users
	 */
	public String list_all_users();

	/**
	 * A simple method that returns a string containing the main menu of the directory manager
	 * from which the user choses the actions he wants
	 *
	 * @return The action menu
	 */
	public String admin_menu();
}

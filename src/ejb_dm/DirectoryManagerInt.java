/**
 * The ejb package contains the server's logic of the program
 */
package ejb_dm;

import javax.ejb.Remote;

/**
 * Interface for  the directory manager
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
	 * Must be called by the admin client program
	 * @param username
	 * @param password
	 * @return Boolean indicating whether the sign in successful or not
	 */
	public boolean admin_signIn(String username, String password );
	
	/**
	 * Signs in any user given the user name and password
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
	public String addUser(String username,String pword, String userrights);

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
	 * returns a string containing the main menu of the directory manager
	 * from which the user chooses the actions he wants
	 *
	 * @return The action menu
	 */
	public String admin_menu();
	
	/**
	 * The admin updates the rights of a given user, selected by ID
	 * @param id Id of the user to update the rights for
	 * @param rights "admin", "bcast", "normal"
	 * @return true if the operation was succesfful, fales otherwise
	 */
	public boolean updateRights(int id, String rights);
	
	/**
	 * Looks up the user rights of the user with the given ID
	 * @param id ID of the user to obtain its right
	 * @return String of the user rights
	 */
	public String lookupRights(int id);
	
	/**
	 * Looks up the user by name
	 * @param name Name of the user
	 * @return user ID or -1 if not found
	 */
	public int lookupUser(String name);
	
	/**
	 * Looks up the current user ID
	 * @return user ID or -1 if not found
	 */
	public int lookupUser();
	
	/**
	 * @return current user's name
	 */
	public String lookupUserName();
	
	/**
	 * Looks up the user rights of the logged in user.
	 * @return String of the user rights
	 */
	public String lookupRights();
}

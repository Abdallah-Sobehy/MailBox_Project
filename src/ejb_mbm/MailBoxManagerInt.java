package ejb_mbm;


import javax.ejb.Remote;

@Remote
public interface MailBoxManagerInt {
	
	/**
	 * Sends a message to a given receiver with a title and content
	 * @param receiver The receiver's name
	 * @param subject subject of the email
	 * @param content body of the email
	 * @return a string containing the result of the method 
	 */
	public String send_message(String receiver,String subject, String content,String status);
	
	/**
	 * Normal user sign in, calls the Directory Manager for sign in.
	 * @param username
	 * @param password
	 * @return Boolean indicating whether the sign in successful or not
	 */
	public boolean signIn(String username, String password);
	
	/**
	 * returns the info of the current user
	 * @return a string containing the ID, Name, and his Rights
	 */
	public String printUser ();
	
	/**
	 * Sends a message to the newsgroup with a title and content
	 * @param subject subject of the email
	 * @param content body of the email
	 * @return a string containing the result of the method
	 */
	public String broadcast(String subject, String content);
	
	/**
	 * Looks up the user rights of the logged in user.
	 * @return String of the user rights
	 */
	public String lookupUserrights();
	
	/**
	 * returns a string containing the main menu of the mail box manager
	 * from which the user chooses the actions he wants
	 * @return The action menu
	 */
	public String mailbox_menu();
	
	/**
	 * Shows all messages of the current user
	 * @return a string showing the messages': ID, date, sender, subject, status 
	 */
	public String show_all_messages();
	
	/**
	 * Shows all messages of the news room
	 * @return a string showing the messages': ID, date, sender, subject, status 
	 */
	public String show_bc_messages();
	
	/**
	 * Shows the message with the given ID
	 * @param id message id
	 * @return String containing the message
	 */
	public String read_message(int id);
	
	/**
	 * Deletes the message with the given ID if the user has the rights
	 * @param id message id
	 * @return String containing the status of the deletion
	 */
	public String delete_message(int id);
	
	/**
	 * Delete all messages of a certain user with a given name
	 * @param name user name whom his/her messages are to be deleted
	 */
	public void delete_all_messages(String user);
	
	public String show_all_messages_test();
}

package ejb;
/**
* Interfce for 
* @author Mostafa Fateen
* @author Abdallah Sobehy
* Created on 10/8/2015.
*/
import javax.ejb.Remote;
import entity.MailUser;

@Remote public interface DirectoryManagerInt {
	/**
	 * adds MailUser to the database
	 * @param mail_user the user to be added to the database
	 */
	public void addUser(MailUser mail_user);
	
	/**
	 * removes a Mailuser from the database
	 * @param id of the user to be removed
	 */
	public void removeUser(int id);
	
	
}

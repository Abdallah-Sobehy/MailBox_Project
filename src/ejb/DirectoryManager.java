package ejb;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.*;
@Stateful(name="ejb/DirectoryManagerInt")
public class DirectoryManager implements DirectoryManagerInt
{
	@PersistenceContext(unitName="pu1")
	private EntityManager em;
	
	MailUser currentuser = null;
	
	
	/**
	 * adds MailUser to the database
	 * @param mail_user the user to be added to the database
	 */
	public void addUser(MailUser mail_user)
	{
		System.out.println("persisting.........");
		em.persist(mail_user);
	}
	
	public boolean signIn(String username, String password){
		//TODO if rows in users database == 0
		if(true){
			MailUser ad = new MailUser(username);
			currentuser = ad;
			addUser(ad);
			return true;
			}
		//TODO
		//Query by name = username
		//get Mailuser if exists
		//make currentuser = mailuser
		//return true or false depending on whether it worked or not
		return true;
	}
	
	
	public void printUser (String username){
		//TODO
		//query by name 
		//if exists and it should
		// MU.print_info()
		
	}
	
	/**
	 * removes a Mail user from the database
	 * @param id of the user to be removed
	 */
	public void removeUser(int id)
	{
		
	}
	

}

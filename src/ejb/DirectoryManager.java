package ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Collection;
import java.util.List;

import entity.*;
@Stateless
public class DirectoryManager 
{
	@PersistenceContext(unitName="pu1")
	private EntityManager em;
	/**
	 * adds MailUser to the database
	 * @param mail_user the user to be added to the database
	 */
	public void addUser(MailUser mail_user)
	{
		em.persist(mail_user);
	}
	
	/**
	 * removes a Mailuser from the database
	 * @param id of the user to be removed
	 */
	public void removeUser(int id)
	{
		
	}
	

}

package ejb;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entity.*;

@Stateful(name = "ejb/DirectoryManagerInt")
public class DirectoryManager implements DirectoryManagerInt {
	@PersistenceContext(unitName = "pu1")
	private EntityManager em;

	MailUser currentuser = null;

	/**
	 * adds MailUser to the database
	 * 
	 * @param mail_user
	 *            the user to be added to the database
	 */
	public void addUser(MailUser mail_user) {
		em.persist(mail_user);
	}

	public boolean signIn(String username, String password) {
		// If there is no users in the database, the first user should be an
		// admin
		TypedQuery<Long> q = em.createQuery("SELECT count(x) FROM MailUser x",
				Long.class);
		long user_count = q.getSingleResult();
		if (user_count == 0) {
			Admin ad = new Admin(username);
			currentuser = ad;
			addUser(ad);
			return true;
		}
		// If users exist query them for the entered user name, if exist assign current user to the found user.
		else {
			Query q0 = em
					.createQuery("SELECT x FROM MailUser x where x.userName = :username");
			q0.setParameter("username", username);
			try {
				MailUser u = (MailUser) q0.getSingleResult();
				currentuser = u;
				return true;
			} catch (Exception e) {
				return false;
			}
			
		}
		// TODO
		// Query by name = username
		// get Mailuser if exists
		// make currentuser = mailuser
		// return true or false depending on whether it worked or not

	}

	public void printUser(String username) {
		// TODO
		// query by name
		// if exists and it should
		// MU.print_info()

	}

	/**
	 * removes a Mail user from the database
	 * 
	 * @param id
	 *            of the user to be removed
	 */
	public void removeUser(int id) {

	}

}

package ejb;

import java.util.Collection;
import java.util.List;

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
	public int addUser(String username, String userrights) {
		// If the new user is an admin
		if (userrights.compareTo("admin") == 0)
		{
			Admin new_admin = new Admin(username);
			em.persist(new_admin);
			return new_admin.getUserID();
		}
		else 
		{
			MailUser new_user = new MailUser(username);
			new_user.setUserRights("normal");
			em.persist(new_user);
			return new_user.getUserID();
		}
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
			em.persist(ad);
			return true;
		}
		// If users exist query them for the entered user name, if exist assign current user to the found user.
		else {
			Query q0 = em.createQuery("SELECT x FROM MailUser x where x.userName = :username");
			q0.setParameter("username", username);
			try {
				MailUser u = (MailUser) q0.getSingleResult(); // throws an exception if not found.
				// Check if the found user is an admin to login
				if (u.getUserRights().compareTo("admin")==0)
				{
					currentuser = u;
					return true;
				}
				else throw new Exception();// The found user was not an admin
			} catch (Exception e) {
				return false;
			}
			
		}

	}

	public String printUser()
	{
		return currentuser.print_info();
	}
	
	public String printUser(int id) {
		// TODO
		// query by name
		// if exists and it should
		// MU.print_info()
		Query q0 = em.createQuery("SELECT x FROM MailUser x where x.userID = :id");
		q0.setParameter("id", id);
		try {
			MailUser u = (MailUser) q0.getSingleResult(); // throws an exception if not found.
			return u.print_info();
		} catch (Exception e) {
			return "No user found with ID: " + id;
		}
			
	}
	/**
	 * @return a string representing the menu of available options for the admin: add, remove, list users or their rights
	 */
	public String admin_menu()
	{
		String tmp = "Choose one of the following options: \n";
		tmp += "1 - Add a new mail user\n";
		tmp += "2 - Remove a new mail user\n";
		tmp += "3 - List all users\n";
		tmp += "4 - update user rights\n";
		tmp += "5 - lookup user rights\n";
		tmp += "6 - Exit\n";
		return tmp;
	}

	/**
	 * removes a Mail user from the database
	 * 
	 * @param id
	 *            of the user to be removed
	 */
	public String removeUser(int id) {
		try
		{
			Query q0 = em.createQuery("DELETE FROM MailUser x where x.userID = :id");
			q0.setParameter("id", id);
			q0.executeUpdate();
			return "Delet successful of User with ID: " + id;
		} catch (Exception e)
		{
			return "User with ID: " + id + " was not found. Delete Failed";
		}
	}
	
	public String list_all_users()
	{
		 Query query = em.createQuery("SELECT u FROM MailUser u ");
		 List<MailUser> results = query.getResultList();
		 //return results;
		 String tmp = "";
		 for (int i =0; i<results.size();i++) {
			 tmp += results.get(i).print_info() + "\n";
		}
		 return tmp;
	}

}

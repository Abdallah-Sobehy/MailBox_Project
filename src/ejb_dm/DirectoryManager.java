/**
 * The ejb package contains the server's logic of the program
 */
package ejb_dm;

import entity_dm.*;


import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ejb_mbm.MailBoxManagerInt;

import java.util.List;

/**
 * The Directory Manager manages the users and their access to the system. In
 * the final version of the system, the Mail Box Manager verifies with the
 * Directory Manager the user rights concerning the access to the common news
 * group.
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 */

@Stateful(name = "ejb_dm/DirectoryManagerInt")
public class DirectoryManager implements DirectoryManagerInt {
	MailUser currentuser = null;
	@PersistenceContext(unitName = "pu11")
	private EntityManager em;
	
	// To call methods from mail box manager
	InitialContext ic;
	MailBoxManagerInt mbm;
	
	/**
	 * Private method only used inside the class to instantiate the
	 * InitialContext and DirectoryManagerInt
	 */
	private void LookUpMailBoxManager() {
		try {
			ic = new InitialContext();
			mbm = (MailBoxManagerInt) ic.lookup("ejb_mbm.MailBoxManagerInt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Signs in the Admin for the first time when he opens the program. If there
	 * was no USERS in the database, i.e. it is the first time to run the
	 * program, it takes a new user name and password and creates a new admin
	 * with these params
	 *
	 * @param username
	 * @param password
	 * @return Boolean indicating whether the sign in successful or not
	 */
	public boolean admin_signIn(String username, String password) {
		// If there is no users in the database, the first user should be an
		// admin
		TypedQuery<Long> q = em.createQuery("SELECT count(x) FROM MailUser x",
				Long.class);
		long user_count = q.getSingleResult();
		if (user_count == 0) {
			Admin ad = new Admin(username, password);
			currentuser = ad;
			em.persist(ad);
			return true;
		}
		// If users exist query them for the entered user name, if exist assign
		// current user to the found user.
		else {
			Query q0 = em
					.createQuery("SELECT x FROM MailUser x where x.userName = :username");
			q0.setParameter("username", username);
			try {
				MailUser u = (MailUser) q0.getSingleResult(); // throws an
																// exception if
																// not found.
				// Check if the found user is an admin to login
				if (u.getUserRights().compareTo("admin") == 0
						&& u.getPassword().compareTo(password) == 0) {
					currentuser = u;
					LookUpMailBoxManager(); // To be able to call methods from the mail box manager
					return true;
				} else
					throw new Exception();// The found user was not an admin
			} catch (Exception e) {
				return false;
			}

		}

	}

	/**
	 * Signs in any user given the user name and password
	 * 
	 * @param username
	 * @param password
	 * @return Boolean indicating whether the sign in successful or not
	 */
	public boolean signIn(String username, String password) {
		Query q0 = em.createQuery("SELECT x FROM MailUser x where x.userName = :username");
		q0.setParameter("username", username);

		try {
			MailUser u = (MailUser) q0.getSingleResult(); // throws an exception
															// if not found
			if (u.getPassword().compareTo(password) == 0) {
				currentuser = u;
				return true;
			}
			throw new Exception();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Add a user to the database and assign his rights
	 *
	 * @param username
	 * @param userrights
	 * @return a string showing the status of the add
	 */
	public String addUser(String username, String pword, String userrights) {
		if (lookupUser(username) == -1) {
			if (username.compareToIgnoreCase("News_Group") == 0)
				return "failed in creating user; username is invalid";
			if (userrights.compareTo("admin") == 0) {
				Admin new_admin = new Admin(username, pword);
				em.persist(new_admin);
				return "New User created successsfuly."
						+ "\n-----------------------------------------------------\n"
						+ printUser(new_admin.getUserID());
			} else if (userrights.compareTo("bcast") == 0) {
				BcastUser new_user = new BcastUser(username, pword);
				new_user.setUserRights("bcast");
				em.persist(new_user);
				return "New User created successsfuly."
						+ "\n-----------------------------------------------------\n"
						+ printUser(new_user.getUserID());
			} else if (userrights.compareTo("normal") == 0) {
				MailUser new_user = new MailUser(username, pword);
				new_user.setUserRights("normal");
				em.persist(new_user);
				return "New User created successsfuly."
						+ "\n-----------------------------------------------------\n"
						+ printUser(new_user.getUserID());
			} else
				return "Failed in creating new user";
		}
		else
			return "Failed in creating new user; username already exists";
	}

	/**
	 * Removes a user from the database
	 *
	 * @param id
	 * @return a string declaring whether the operation failed or not + the id
	 *         of the user
	 */
	public String removeUser(int id) {
		try {
			// First check if the user already exists,
			// if it is not found an exception will be thrown indicating delete
			// failed.
			Query q = em.createQuery("SELECT u FROM MailUser u where u.userID = :id");
			q.setParameter("id", id);
			MailUser u = (MailUser) q.getSingleResult();
			mbm.delete_all_messages(u.getUserName());
			Query q0 = em.createQuery("DELETE FROM MailUser x where x.userID = :id");
			q0.setParameter("id", id);
			q0.executeUpdate();
			return "Delete successful of User with ID: " + id;
		} catch (Exception e) {
			return "User with ID: " + id + " was not found. Delete Failed";
		}
	}

	/**
	 * A simple method to return the info of the current user
	 *
	 * @return a string containing the ID, Name, and his Rights
	 */
	public String printUser() {
		return currentuser.print_info();
	}

	/**
	 * A simple method to return the info of a specified user by hid ID
	 *
	 * @param id
	 * @return a string containing the ID, Name, and his Rights
	 */
	public String printUser(int id) {
		Query q0 = em
				.createQuery("SELECT x FROM MailUser x where x.userID = :id");
		q0.setParameter("id", id);
		try {
			MailUser u = (MailUser) q0.getSingleResult(); // throws an exception
															// if not found.
			return u.print_info();
		} catch (Exception e) {
			return "No user found with ID: " + id;
		}

	}

	/**
	 * A simple printer to list all users in the database with their ID and
	 * Rights
	 *
	 * @return a string containing the info of all users
	 */
	public String list_all_users() {
		Query query = em.createQuery("SELECT u FROM MailUser u ");
		List<MailUser> results = query.getResultList();
		// return results;
		String tmp = "";
		for (int i = 0; i < results.size(); i++) {
			tmp += results.get(i).print_info() + "\n";
		}
		return tmp;
	}

	/**
	 * A simple method that returns a string containing the main menu of the
	 * directory manager from which the user choses the actions he wants
	 *
	 * @return The action menu
	 */
	public String admin_menu() {
		String tmp = "\n*******************************************\n";
		tmp += "Choose one of the following options: \n";
		tmp += "1 - Add a new mail user\n";
		tmp += "2 - Remove an existing mail user\n";
		tmp += "3 - List all users\n";
		tmp += "4 - update user rights\n";
		tmp += "5 - lookup user rights\n";
		tmp += "6 - Exit\n";
		tmp += "*******************************************\n";
		return tmp;
	}

	/**
	 * The admin updates the rights of a given user, selected by ID
	 * 
	 * @param id
	 *            Id of the user to update the rights for
	 * @param rights
	 *            "admin", "bcast", "normal"
	 * @return true if the operation was succesfful, fales otherwise
	 */
	public boolean updateRights(int id, String rights) {
		Query q0 = em
				.createQuery("SELECT o FROM MailUser o where o.userID = :id");
		q0.setParameter("id", id);
		try {
			MailUser u = (MailUser) q0.getSingleResult(); // throws an exception
															// if not found.
			Query q1 = em
					.createQuery("UPDATE MailUser SET userRights = :rights WHERE userID = :id");
			q1.setParameter("id", id);
			q1.setParameter("rights", rights);
			q1.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Looks up the user rights of the user with the given ID
	 * 
	 * @param id
	 *            ID of the user to obtain its right
	 * @return String of the user rights
	 */
	public String lookupRights(int id) {
		String this_rights = null;
		try {
			Query q = em
					.createQuery("SELECT u FROM MailUser u where u.userID = :id");
			q.setParameter("id", id);
			MailUser u = (MailUser) q.getSingleResult();
			this_rights = u.getUserRights();
			return "User with ID: " + id + " is a(n) " + this_rights + " user";
		} catch (Exception e) {
			return "User with ID: " + id + " was not found.";
		}
	}

	/**
	 * Looks up the user by name
	 * 
	 * @param name
	 *            Name of the user
	 * @return user ID or -1 if not found
	 */
	public int lookupUser(String name) {
		try {
			Query q = em
					.createQuery("SELECT u FROM MailUser u where u.userName = :name");
			q.setParameter("name", name);
			MailUser u = (MailUser) q.getSingleResult();
			return u.getUserID();
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Looks up the current user ID
	 * 
	 * @return user ID or -1 if not found
	 */
	public int lookupUser() {
		return currentuser.getUserID();
	}

	/**
	 * @return current user's name
	 */
	public String lookupUserName() {
		return currentuser.getUserName();
	}

	/**
	 * Looks up the user rights of the logged in user.
	 * 
	 * @return String of the user rights
	 */
	public String lookupRights() {
		return currentuser.getUserRights();
	}

}

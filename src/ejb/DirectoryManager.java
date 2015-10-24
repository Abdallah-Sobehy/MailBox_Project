/**
 * The ejb package contains the server's logic of the program
 */
package ejb;

import entity.*;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The Directory Manager manages the users and their access to the system. In the final version of the system,
 * the Mail Box Manager verifies with the Directory Manager the user rights concerning the access to the
 * common news group.
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 */

@Stateful(name = "ejb/DirectoryManagerInt")
public class DirectoryManager implements DirectoryManagerInt {
    MailUser currentuser = null;
    @PersistenceContext(unitName = "pu1")
    private EntityManager em;

    /**
     * Signs in the Admin for the first time when he opens the program.
     * If there was no USERS in the database, i.e. it is the first time to run the program, it takes a new user name
     * and password and creates a new admin with these params
     *
     * @param username
     * @param password
     * @return Boolean indicating whether the sign in successful or not
     */
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
                if (u.getUserRights().compareTo("admin") == 0) {
                    currentuser = u;
                    return true;
                } else throw new Exception();// The found user was not an admin
            } catch (Exception e) {
                return false;
            }

        }

    }

    /**
     * Add a user to the database and assign his rights
     *
     * @param username
     * @param userrights
     * @return the generated user ID
     */
    public int addUser(String username, String userrights) {
        // If the new user is an admin
        if (userrights.compareTo("admin") == 0) {
            Admin new_admin = new Admin(username);
            em.persist(new_admin);
            return new_admin.getUserID();
        } else {
            MailUser new_user = new MailUser(username);
            new_user.setUserRights("normal");
            em.persist(new_user);
            return new_user.getUserID();
        }
    }

    /**
     * Removes a user from the database
     *
     * @param id
     * @return a string declaring whether the operation failed or not + the id of the user
     */
    public String removeUser(int id) {
        try {
            // First check if the user already exists,
            // if it is not found an exception will be thrown indicating delete failed.
            Query q = em.createQuery("SELECT u FROM MailUser u where u.userID = :id");
            q.setParameter("id", id);
            MailUser u = (MailUser) q.getSingleResult();
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
     * A simple printer to list all users in the database with their ID and Rights
     *
     * @return a string containing the info of all users
     */
    public String list_all_users() {
        Query query = em.createQuery("SELECT u FROM MailUser u ");
        List<MailUser> results = query.getResultList();
        //return results;
        String tmp = "";
        for (int i = 0; i < results.size(); i++) {
            tmp += results.get(i).print_info() + "\n";
        }
        return tmp;
    }

    /**
     * A simple method that returns a string containing the main menu of the directory manager
     * from which the user choses the actions he wants
     *
     * @return The action menu
     */
    public String admin_menu() {
        String tmp = "Choose one of the following options: \n";
        tmp += "1 - Add a new mail user\n";
        tmp += "2 - Remove an existing mail user\n";
        tmp += "3 - List all users\n";
        tmp += "4 - update user rights\n";
        tmp += "5 - lookup user rights\n";
        tmp += "6 - Exit\n";
        return tmp;
    }

}

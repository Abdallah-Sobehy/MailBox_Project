package ejb_mbm;

import entity_mbm.*;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import ejb_dm.DirectoryManagerInt;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Stateful(name = "ejb_mbm/MailBoxManagerInt")
public class MailBoxManager implements MailBoxManagerInt {
	@PersistenceContext(unitName = "pu11")
	private EntityManager em;
	InitialContext ic;
	DirectoryManagerInt dm;
	
	
	/**
	 * Private method only used inside the class to instantiate the
	 * InitialContext and DirectoryManagerInt
	 */
	private void LookUpDirectoryManager() {
		try {
			ic = new InitialContext();
			dm = (DirectoryManagerInt) ic.lookup("ejb_dm.DirectoryManagerInt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a message to a given receiver with a title and content
	 * Returns message ID
	 */
	public String send_message(String receiver, String subject, String content, String status) {
		if(receiver != "News_Group"){
			int id= dm.lookupUser(receiver);
			if(id == -1) // User not found
				return "Recepient not found, sending message failed";
		}

		if(receiver == "News_Group" && dm.lookupRights().compareTo("normal") == 0)
			return " You do not have the right to broadcast a message, contact an admin to gain the rights ";
		Message msg = new Message();
		msg.setReceiver(receiver);
		msg.setSubject(subject);
		msg.setContent(content);
		msg.setStatus(status);
		msg.setSender(dm.lookupUserName());
		msg.setDate(new Date().toString());
		em.persist(msg);
		return "Message sent successfully";
	}
	
	/**
	 * Sends a message to the newsgroup with a title and content
	 * @param subject subject of the email
	 * @param content body of the email
	 * @return message ID
	 */
	public String broadcast(String subject, String content){
		return send_message("News_Group", subject, content, "bcast");
	}
	

	/**
	 * Normal user sign in, calls the Directory Manager for sign in.
	 * 
	 * @param username
	 * @param password
	 * @return Boolean indicating whether the sign in successful or not
	 */
	public boolean signIn(String username, String password) {
		LookUpDirectoryManager();
		return dm.signIn(username, password);
	}

	/**
	 * returns the info of the current user
	 * 
	 * @return a string containing the ID, Name, and his Rights
	 */
	public String printUser() {
		return dm.printUser();
	}

	/**
	 * Looks up the user rights of the logged in user.
	 * 
	 * @return String of the user rights
	 */
	public String lookupUserrights() {
		return dm.lookupRights();
	}

	/**
	 * returns a string containing the main menu of the mail box manager from
	 * which the user chooses the actions he wants
	 * 
	 * @return The action menu
	 */
	public String mailbox_menu() {
		String tmp = "\n*******************************************\n";
		tmp += "Choose one of the following options: \n";
		tmp += "1 - Show all messages\n";
		tmp += "2 - Read a message\n";
		tmp += "3 - Show news group messages\n";
		tmp += "4 - Send a normal message\n";
		tmp += "5 - Send a message to the news group\n";
		tmp += "6 - Delete a message\n";
		tmp += "7 - Exit\n";
		tmp += "*******************************************\n";
		return tmp;
	}
	
	/**
	 * Shows all messages of the current user
	 * returns a string showing the messages': ID, date, subject, status 
	 */
	public String show_all_messages()
	{
		Query query = em.createQuery("SELECT m FROM Message m where m.receiver = :receiver");
		query.setParameter("receiver", dm.lookupUserName()); 
		List<Message> results = query.getResultList();
		// return results;
		String tmp = "Status\tID\tSender\tSubject\tDate\n";
		for (int i = 0; i < results.size(); i++) {
			tmp += results.get(i).getStatus()+ "\t" + results.get(i).toString()+ "\n";
		}
		return tmp;
	}
	
	public String show_all_messages_test()
	{
		Query query = em.createQuery("SELECT m FROM Message m");
		List<Message> results = query.getResultList();
		// return results;
		String tmp = "Status\tID\tSender\tSubject\tDate\n";
		for (int i = 0; i < results.size(); i++) {
			tmp += results.get(i).getStatus()+ "\t" + results.get(i).toString()+ "\n";
		}
		return tmp;
	}
	
	/**
	 * Shows all messages of the news room
	 * @return a string showing the messages': ID, date, sender, subject, status 
	 */
	public String show_bc_messages(){
		Query query = em.createQuery("SELECT m FROM Message m where m.receiver = :receiver");
		query.setParameter("receiver", "News_Group"); 
		List<Message> results = query.getResultList();
		// return results;
		String tmp = "ID\tSender\tSubject\tDate\n";
		for (int i = 0; i < results.size(); i++) {
			tmp += results.get(i).toString() + "\n";
		}
		return tmp;
	}
	
	/**
	 * Shows the message with the given ID
	 * @param id message id
	 * @return String containing the message
	 */
	public String read_message(int id){
		try {
			Query q = em.createQuery("SELECT m FROM Message m where m.msgID = :id");
			q.setParameter("id", id);
			Message m = (Message) q.getSingleResult();
			if(!(dm.lookupRights().compareTo("admin")==0)){
				if(!(dm.lookupUserName().compareTo(m.getReceiver())==0) && !(m.getStatus().compareTo("bcast")==0))
					return "WRONG MESSAGE ID....";
			}
			Query q1 = em.createQuery("UPDATE Message SET status = :state WHERE msgID = :id");
			q1.setParameter("id", id);
			q1.setParameter("state", "read");
			q1.executeUpdate();
			return m.readMessage();
		} catch (Exception e) {
			return "WRONG MESSAGE ID";
		}
	}

	/**
	 * Deletes the message with the given ID if the user has the rights
	 * @param id message id
	 * @return String containing the status of the deletion
	 */
	public String delete_message(int id){
		try {
			Query q = em.createQuery("SELECT m FROM Message m where m.msgID = :id");
			q.setParameter("id", id);
			Message m = (Message) q.getSingleResult();
			if(!(dm.lookupRights().compareTo("admin")==0)){
				if(!(dm.lookupUserName().compareTo(m.getSender())==0) && m.getStatus().compareTo("bcast")==0)
					return "You do not have the rights to delete";
				if(!(dm.lookupUserName().compareTo(m.getReceiver())==0) && !(m.getStatus().compareTo("bcast")==0))
					return "You do not have the rights to delete";
			}
			
			Query q1 = em.createQuery("DELETE FROM Message x where x.msgID = :id");
			q1.setParameter("id", id);
			q1.executeUpdate();
			return "Message deleted successfuly";
		} catch (Exception e) {
			return "WRONG MESSAGE ID";
		}
	}
	
	/**
	 * Delete all messages of a certain user with a given name
	 * It is called by the directory manager when a user is deleted so all of his/her Mail box messages are deleted
	 * @param name user name whom his/her messages are to be deleted
	 */
	public void delete_all_messages(String user)
	{
		Query query = em.createQuery("SELECT m FROM Message m where m.receiver = :receiver");
		query.setParameter("receiver", user); 
		List<Message> results = query.getResultList();
		for (int i = 0; i < results.size(); i++) {
//			delete_message(results.get(i).getMsgID());
			Query q1 = em.createQuery("DELETE FROM Message x where x.msgID = :id");
			q1.setParameter("id", results.get(i).getMsgID());
			q1.executeUpdate();
		}
	}
	
}

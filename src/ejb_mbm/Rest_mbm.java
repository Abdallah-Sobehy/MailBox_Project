package ejb_mbm;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

/**
 * The class Rest_mbm is the rest server for the mail box manager
 *
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 *
 * @version 1.0
 */

@Path("/Mail")
public class Rest_mbm {
	InitialContext ic;
	MailBoxManagerInt mbm;

	public Rest_mbm() throws JAXBException, NamingException {
		ic = new InitialContext();
		mbm = (MailBoxManagerInt) ic.lookup(MailBoxManagerInt.class.getName());
	}

	@GET
	@Path("/signin/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sign_in(@PathParam("input") String input) {
		String[] s = input.split(":");
		if (s.length != 2)
			return "WRONG Input\nPlease enter the username:password";
		String username = s[0];
		String password = s[1];
		if (mbm.signIn(username, password)) {
			return "Login Successful, Welcome:\n-----------------------------------------------------\n"
					+ mbm.printUser()
					+ "\n\tAll your messages\n-----------------------------------------------------\n"
					+ mbm.show_all_messages();
		} else
			return "\nWrong user name or password, try again.";
	}

	@GET
	@Path("/read/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public String read_message(@PathParam("input") String input) {
		String[] s = input.split(":");
		if (s.length != 3)
			return "WRONG Input\nPlease enter the username:password:message_id";
		String username = s[0];
		String password = s[1];
		int id = Integer.parseInt(s[2]);
		if (mbm.signIn(username, password)) {
			return "Login Successful, Welcome:\n-----------------------------------------------------\n"
					+ mbm.printUser()
					+ "\n              Message\n-----------------------------------------------------\n"
					+ mbm.read_message(id);
		} else
			return "\nWrong user name or password, try again.";
	}

	@GET
	@Path("/newsgroup/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public String show_news_group(@PathParam("input") String input) {
		String[] s = input.split(":");
		if (s.length != 2)
			return "WRONG Input\nPlease enter the username:password";
		String username = s[0];
		String password = s[1];
		if (mbm.signIn(username, password)) {
			return "Login Successful, Welcome:\n-----------------------------------------------------\n"
					+ mbm.printUser()
					+ "\n              All messages in group news\n-----------------------------------------------------\n"
					+ mbm.show_bc_messages();
		} else
			return "\nWrong user name or password, try again.";
	}

	@GET
	@Path("/send/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public String send(@PathParam("input") String input) {
		String[] s = input.split(":");
		if (s.length != 5)
			return "WRONG Input\nPlease enter the username:password:receiver:subject:body";
		String username = s[0];
		String password = s[1];
		String receiver = s[2];
		String subject = s[3];
		String body = s[4];
		if (mbm.signIn(username, password)) {
			return "Login Successful, Welcome:\n-----------------------------------------------------\n"
					+ mbm.printUser()
					+ "\n              Status of message\n-----------------------------------------------------\n"
					+ mbm.send_message(receiver, subject, body, "unread");
		} else
			return "\nWrong user name or password, try again.";
	}

	@GET
	@Path("/broadcast/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public String broadcast(@PathParam("input") String input) {
		String[] s = input.split(":");
		if (s.length != 4)
			return "WRONG Input\nPlease enter the username:password:subject:body";
		String username = s[0];
		String password = s[1];
		String subject = s[2];
		String body = s[3];
		if (mbm.signIn(username, password)) {
			return "Login Successful, Welcome:\n-----------------------------------------------------\n"
					+ mbm.printUser()
					+ "\n              Status of message\n-----------------------------------------------------\n"
					+ mbm.broadcast(subject, body);
		} else
			return "\nWrong user name or password, try again.";
	}

	@GET
	@Path("/delete/{input}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete_message(@PathParam("input") String input) {
		String[] s = input.split(":");
		if (s.length != 3)
			return "WRONG Input\nPlease enter the username:password:message_id";
		String username = s[0];
		String password = s[1];
		int id = Integer.parseInt(s[2]);
		if (mbm.signIn(username, password)) {
			return "Login Successful, Welcome:\n-----------------------------------------------------\n"
					+ mbm.printUser()
					+ "\n              Message\n-----------------------------------------------------\n"
					+ mbm.delete_message(id);
		} else
			return "\nWrong user name or password, try again.";
	}

}

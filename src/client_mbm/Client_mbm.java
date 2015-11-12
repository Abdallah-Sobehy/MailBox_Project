/**
 * The client package contains the client's logic of the mail box manager
 */
package client_mbm;


import javax.naming.InitialContext;

import ejb_dm.DirectoryManagerInt;
import ejb_mbm.MailBoxManagerInt;

import java.io.Console;

/**
 * The class Client is the client interface for accessing the mail box manager
 *
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 *
 * @version 1.0
 */

public class Client_mbm {
    public static void main(String args[]) {
    	try 
    	{
	    	InitialContext ic = new InitialContext();
	        MailBoxManagerInt mbm = (MailBoxManagerInt) ic.lookup("ejb_mbm.MailBoxManagerInt");
	        // Login Part -------------------------------------------
	        String username, password = null;
            Console console = System.console();
            
            do{
				System.out.println("Enter user name to login");
                username = console.readLine();
                password = String.valueOf(console.readPassword("Enter password: "));
				if (mbm.signIn(username, password)){
					System.out.println("Login Successful, Welcome:" 
									  +"\n-----------------------------------------------------" );
					System.out.println(mbm.printUser());
				//	System.out.println("Testing user rights: " + mbm.lookupUserrights());
					break;
				}
				else System.out.println("\nWrong user name or password, try again." );
			}while(true);
            
            // Post sign in options -----------------------------------------------
            while (true) 
            {
				System.out.println(mbm.mailbox_menu());
						
				//switch-case:
				//  1 - Show all messages
            	//	2 - Read a message
				//	3 - show news group
				//	4 - Send a normal message
				//  5 - Send a message to the new group
				//	6 - Delete a message
            	//  7 - Exit
            
	            int choice = Integer.parseInt(console.readLine());
	            switch (choice) {
				//1-show all messages
				case 1:
					System.out.println(mbm.show_all_messages());
					break;
	
				//2- read a message
				case 2:
					System.out.println("Enter message id:");
					System.out.println(mbm.read_message(Integer.parseInt(console.readLine())));
					break;
	
				//3-show news group
				case 3:
					System.out.println(mbm.show_bc_messages());
					break;
	
				//4-Sends a normal message
				case 4:
					System.out.println("To:");
					String receiver = console.readLine();
					System.out.println("Subject:");
					String subject = console.readLine();
					System.out.println("Body:");
					String content = console.readLine();
					System.out.println(mbm.send_message(receiver, subject, content,"unread"));
					break;
	
				//5- Sends a message to the news group
				case 5:
					System.out.println("Subject:");
					String subjectbc = console.readLine();
					System.out.println("Body:");
					String contentbc = console.readLine();
					System.out.println(mbm.broadcast(subjectbc, contentbc));
					break;
				//6-Deletes a message
				case 6:
					System.out.println("Enter message id:");
					System.out.println(mbm.delete_message(Integer.parseInt(console.readLine())));
					break;	
				//7-Exit the program
				case 7:
					return;
	            }
            }
    	} 
    	catch(Exception e) 
    	{
			e.printStackTrace();
		}
	}
}

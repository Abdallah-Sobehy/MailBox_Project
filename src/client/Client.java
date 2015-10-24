package client;
import javax.naming.InitialContext;
import javax.ejb.*;
import ejb.DirectoryManagerInt;
import java.util.Scanner;

//TODO Change the class name to admin_client
public class Client {
    public static void main(String args[]) {
        try {
        	 String username, password = null;
             Scanner in = new Scanner(System.in);
             InitialContext ic = new InitialContext();
             DirectoryManagerInt dm = (DirectoryManagerInt) ic.lookup("ejb.DirectoryManagerInt");
             // Login Part -------------------------------------------
             do{
            	 System.out.println("Enter admin user name to login");
                 username = in.nextLine();
            	 //username = "abdallah";
                // System.out.println("Enter password");
                // password = in.nextLine();
				if (dm.signIn(username, password))
				{
					System.out.println("Login Successful, Welcome:" );
					System.out.println(dm.printUser());
					break;
				}
				else System.out.println("Login Unsuccessful, try again." );
		             
             }while(true);
             // Post Login options
             while (true)
             {
            	 System.out.println(dm.admin_menu());
            	 int choice = Integer.parseInt(in.nextLine());
            	 
            	 // Checking choice
            	 switch (choice)
            	 {
            	 // Admin chooses to add a user
            	 case 1:
            		 String new_user_name, new_user_rights;
            		 System.out.println("Enter the user name of the new user");
            		 new_user_name = in.nextLine();
            		 System.out.println("Press 1 to create an admin, 2 to create a normal user.");
            		 if (Integer.parseInt(in.nextLine()) == 1)
            			 new_user_rights = "admin";
            		 else new_user_rights = "normal";
            		 
            		 int id = dm.addUser(new_user_name, new_user_rights);
            		 System.out.println("New User created successsfuly.");
            		 System.out.println(dm.printUser(id));
            		 break;
            	 // remove a user with a diven ID
            	 case 2:
            		 System.out.println("Enter the Id of the user to be removed.");
            		 System.out.println(dm.removeUser(Integer.parseInt(in.nextLine())));
            		 break;
            	// List all users	 
            	 case 3:
            		 System.out.println(dm.list_all_users());
            	 // Admin wants to exit
            	 case 6:
            		 return;
            	 }
             }
             
             
        } catch(Exception e) {
            	 e.printStackTrace();
            	 }
        }
}

/**
 * The client package contains the client's logic of the program
 */
package client;
import javax.naming.InitialContext;
import ejb.DirectoryManagerInt;
import java.io.Console;

/**
 * The class Cliesnt is the admin interface for accessing the directory manager
 *
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 *
 * @version 1.0
 */

public class Client {
    public static void main(String args[]) {
        try {
			String username, password = null;
            Console console = System.console();
            InitialContext ic = new InitialContext();
            DirectoryManagerInt dm = (DirectoryManagerInt) ic.lookup("ejb.DirectoryManagerInt");
            // Login Part -------------------------------------------
            do{
				System.out.println("Enter admin user name to login");
                username = console.readLine();
                password = String.valueOf(console.readPassword("Enter password: "));
				if (dm.signIn(username, password)){
					System.out.println("Login Successful, Welcome:" 
									  +"\n-----------------------------------------------------" );
					System.out.println(dm.printUser());
					break;
				}
				else System.out.println("\nWrong user name or password, try again." );
			}while(true);

			// Post Login options
            while (true) {
				System.out.println(dm.admin_menu());

				//Gives User the Choice of:
				//1-Add a new mail user
				//2-Remove an existing mail user
				//3-List all users
				//4-Update a specific user's rights
				//5-Lookup a specific user's rights
				//6-Exit the program

				
				
				int choice = Integer.parseInt(console.readLine());
				// Checking choice
				switch (choice) {
					//1-Add a new mail user
					case 1:
						String new_user_name, new_pword, new_user_rights;
						System.out.println("Enter user name");
						new_user_name = console.readLine();
						new_pword = String.valueOf(console.readPassword("Enter password: "));
						//TODO fix 3:Broad cast
						System.out.println("Make the user:\n 1:Admin\n 2:Normal\n 3:Broad Cast\n");
						if (Integer.parseInt(console.readLine()) == 1)
							new_user_rights = "admin";
						else new_user_rights = "normal";
						int id = dm.addUser(new_user_name, new_pword, new_user_rights);
						System.out.println("New User created successsfuly."
										  +"\n-----------------------------------------------------");
						System.out.println(dm.printUser(id));
						break;

					//2-Remove an existing mail user
					case 2:
						System.out.println("Enter the Id of the user to be removed.");
						System.out.println(dm.removeUser(Integer.parseInt(console.readLine())));
						break;

					//3-List all users
					case 3:
						System.out.println("\n-----------------------------------------------------\n"
										  +dm.list_all_users());
						break;

					//4-Update a specific user's rights
					case 4:
						String new_rights;
						int this_id;
						do{
						System.out.println("Enter ID of the user");
						this_id= Integer.parseInt(console.readLine());
						//TODO fix 3:Broad cast
						System.out.println("Make the user:\n 1:Admin\n 2:Normal\n 3:Broad Cast\n");
						if (Integer.parseInt(console.readLine()) == 1)
							new_rights = "admin";
						else new_rights = "normal";
						}while(!dm.updateRights(this_id, new_rights));
						break;

					//5-Lookup a specific user's rights
					case 5:
						int t_id;
						String message;
						System.out.println("Enter ID of the user");
						t_id= Integer.parseInt(console.readLine());
						message= dm.lookupRights(t_id);
						System.out.println(message);
						break;

					//6-Exit the program
					case 6:
						return;
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

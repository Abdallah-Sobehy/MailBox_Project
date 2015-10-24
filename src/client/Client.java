/**
 * The client package contains the client's logic of the program
 */
package client;
import javax.naming.InitialContext;
import ejb.DirectoryManagerInt;
import java.util.Scanner;

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
            Scanner in = new Scanner(System.in);
            InitialContext ic = new InitialContext();
            DirectoryManagerInt dm = (DirectoryManagerInt) ic.lookup("ejb.DirectoryManagerInt");
            // Login Part -------------------------------------------
            do{
				System.out.println("Enter admin user name to login");
                username = in.nextLine();
            	// System.out.println("Enter password");
                // password = in.nextLine();
				if (dm.signIn(username, password)){
					System.out.println("Login Successful, Welcome:" );
					System.out.println(dm.printUser());
					break;
				}
				else System.out.println("Login Unsuccessful, try again." );
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

				int choice = Integer.parseInt(in.nextLine());
				// Checking choice
				switch (choice) {
					//1-Add a new mail user
					case 1:
						String new_user_name, new_user_rights;
						System.out.println("Enter user name");
						new_user_name = in.nextLine();
						//TODO fix 3:Broad cast
						System.out.println("Make the user:\n 1:Admin\n 2:Normal\n3:Broad Cast\n");
						if (Integer.parseInt(in.nextLine()) == 1)
							new_user_rights = "admin";
						else new_user_rights = "normal";
						int id = dm.addUser(new_user_name, new_user_rights);
						System.out.println("New User created successsfuly.");
						System.out.println(dm.printUser(id));
						break;

					//2-Remove an existing mail user
					case 2:
						System.out.println("Enter the Id of the user to be removed.");
						System.out.println(dm.removeUser(Integer.parseInt(in.nextLine())));
						break;

					//3-List all users
					case 3:
						System.out.println(dm.list_all_users());
						break;

					//4-Update a specific user's rights
					case 4:
						break;

					//5-Lookup a specific user's rights
					case 5:
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

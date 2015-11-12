/**
 * The client package contains the client's logic of the directory manager
 */
package client_dm;

import javax.naming.InitialContext;

import ejb_dm.DirectoryManagerInt;

/**
 * The class AutoClient_dm is the automatic directory manager test class
 *
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 *
 * @version 1.0
 */
public class AutoClient_dm {
	public static void main(String args[]) {
		try {
			DirectoryManagerInt dm;
			InitialContext ic;

			ic = new InitialContext();
			dm = (DirectoryManagerInt) ic.lookup("ejb_dm.DirectoryManagerInt");

			System.out.println("\n\n********************Login Trials*********************\n\n");
			
			System.out.println("Trying to login with Mohab (not a user)\n");
			if (dm.admin_signIn("Mohab", "321"))
				System.out.println("Login Successful\n");
			else
				System.out.println("Login Failed\n");

			System.out.println("Trying to login with Abdallah (not an admin)\n");
			if (dm.admin_signIn("Abdallah", "321"))
				System.out.println("Login Successful\n");
			else
				System.out.println("Login Failed\n");

			System.out.println("Trying to login with Mostafa (an admin) but with a wrong password\n");
			if (dm.admin_signIn("Mostafa", "321"))
				System.out.println("Login Successful\n");
			else
				System.out.println("Login Failed\n");
			
			System.out.println("Trying to login with Mostafa (an admin)\n");
			if (dm.admin_signIn("Mostafa", "1234567890"))
				System.out.println("Login Successful\n");
			else
				System.out.println("Login Failed\n");
			
			System.out.println("\n\n********************User Creation Trials*********************\n\n");
			
			
			System.out.println("Trying to create a user with an existing user name");
			System.out.println(dm.addUser("Mostafa", "nsdns", "normal"));
			
			System.out.println("Trying to create a normal user");
			System.out.println(dm.addUser("Maha", "nsdns", "normal"));
			
			System.out.println("Trying to create a bcast user");
			System.out.println(dm.addUser("Maheed", "nsdns", "bcast"));
			
			System.out.println("Trying to create an admin");
			System.out.println(dm.addUser("Mariam", "nsdns", "admin"));
			
			
			System.out.println("\n\n********************Checkin User Rights Trials*********************\n\n");
			
				
			System.out.println("Checking Rights of Maha (normal user)");
			System.out.println(dm.lookupRights(dm.lookupUser("Maha")));
			
			System.out.println("Checking Rights of Maheed (bcast user)");
			System.out.println(dm.lookupRights(dm.lookupUser("Maheed")));
			
			System.out.println("Checking Rights of Mariam (admin)");
			System.out.println(dm.lookupRights(dm.lookupUser("Mariam")));
			
			System.out.println("Checking Rights of Mahy (not a user)");
			System.out.println(dm.lookupRights(dm.lookupUser("Mahy")));
			
			
			System.out.println("\n\n********************Listing All Users Trials*********************\n\n");
			
			
			System.out.println(dm.list_all_users());
			
			
			System.out.println("\n\n********************Updating User Rights Trials*********************\n\n");
			
			
			System.out.println("Update Rights of Maha (normal user) to bcast");
			if(dm.updateRights(dm.lookupUser("Maha"), "bcast"))
				System.out.println("Update Successful");
			else
				System.out.println("Update Failed");
			
			System.out.println("Update Rights of Mahy (not a user) to bcast");
			if(dm.updateRights(dm.lookupUser("Mahy"), "bcast"))
				System.out.println("Update Successful");
			else
				System.out.println("Update Failed");
			
			
			System.out.println("\n\n********************Deleting User Trials*********************\n\n");
			
			
			System.out.println("Checking Rights of Maha (normal user)");
			System.out.println(dm.removeUser(dm.lookupUser("Maha")));
			
			System.out.println("Checking Rights of Maheed (bcast user)");
			System.out.println(dm.removeUser(dm.lookupUser("Maheed")));
			
			System.out.println("Checking Rights of Mariam (admin)");
			System.out.println(dm.removeUser(dm.lookupUser("Mariam")));
			
			System.out.println("Checking Rights of Mariam (not a user any more)");
			System.out.println(dm.removeUser(dm.lookupUser("Mariam")));
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

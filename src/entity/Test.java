package entity;

public class Test {
	public static void main(String args[])
	{
		// Must start with at least one admin
		Admin ad = new Admin("Abdallah");
		ad.print_info();
		// Create a normal user
		MailUser u_normal = ad.create_user("fateen", "normal");
		u_normal.print_info();
		// Create a broadcast user
		MailUser u_bcast = ad.create_user("zeus", "bcast");
		u_bcast.print_info();
		// Create a user with an unacceptable type
		MailUser u_invalid = ad.create_user("teet", "xxx");
		
		
	}

}

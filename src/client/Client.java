package client;
import javax.naming.InitialContext;
import javax.ejb.*;
import ejb.DirectoryManagerInt;
import java.util.Scanner;


public class Client {
    public static void main(String args[]) {
        try {
        	 String username, password = null;
             Scanner in = new Scanner(System.in);
             InitialContext ic = new InitialContext();
             DirectoryManagerInt dm = (DirectoryManagerInt) ic.lookup("ejb.DirectoryManagerInt");
             
             do{
            	 System.out.println("Enter username");
                 username = in.nextLine();
            	 //username = "abdallah";
                // System.out.println("Enter password");
                // password = in.nextLine();
             }while(!dm.signIn(username, password));
             
             dm.printUser(username);
             
        } catch(Exception e) {
            	 e.printStackTrace();
            	 }
        }
}

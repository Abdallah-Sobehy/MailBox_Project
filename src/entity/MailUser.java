package entity;

/**
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 * Created by Mostafa Fateen on 10/4/2015.
 */
import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.CascadeType.*;

import java.util.Collection;
import java.util.ArrayList;

@Entity
public class MailUser implements Serializable {
	/** Users count, also used to generate ID for users.*/
	private static int usersCount = 0;
    private int userID;
    private String userName;
    //TODO add mailbox here
    //TODO [ADVANCED] Password + its methods
    /**userRights could have one of three values "admin, bcast, or normal"*/
    private String userRights;
    public MailUser() {}
	/**
	 * Constructor initializes a user
	 * @param name name of the user
	 */
    public MailUser(String name)
    {
    	userName = name;
    	setUserID(usersCount);
    	usersCount ++;
    	//TODO assign a mailbox
    	//TODO assign password
    }
    
    
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserRights(String userRights) {
        this.userRights = userRights;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Id
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserRights() {
        return userRights;
    }

    void sendMessage(String message, int receiverID){
        //TODO after writing the message
    }

    void checkMessages(){
        // print some stuff
        //TODO after writing the message
    }

    void deleteMessage(){
        //TODO after writing the message
    }

    void broadCast(String message){
        if(userRights.equals("normal"))
                return;

        //TODO after writing the message
    }
    
    public void print_info()
    {
    	System.out.println("user:" + getUserName() + " User ID: "+ getUserID() + " User rights: " + getUserRights());
    }
}

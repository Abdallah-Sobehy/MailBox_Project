package entity_dm;

/**
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 */
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="USERRIGHTS",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue(value="normal")
public class MailUser{

    private int userID;
    private String userName;
    private String password;
    //TODO add mailbox here
    //TODO [ADVANCED] Password + its methods
    /**userRights could have one of three values "admin, bcast, or normal"*/
    private String userRights;
    public MailUser() {}
	/**
	 * Constructor initializes a user
	 * @param name name of the user
	 */
    public MailUser(String name, String pword)
    {
    	userName = name;
    	password = pword;
    	//TODO assign a mailbox
    	//TODO assign password
    }
    
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public void setPassword(String passWord){
    	this.password = passWord;    	
    }

    public void setUserRights(String userRights) {
        this.userRights = userRights;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }
    
    public String getPassword(){
    	return password;    	
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
    
    public String print_info(){
    	return "user: "+ getUserName() + "\t User ID: " + getUserID() 
    			+ "\t User rights: " + getUserRights()+"\n-----------------------------------------------------";
    }
    
    public String toString()    {
    	return "user:" + getUserName() + " User ID: "+ getUserID() + " User rights: " + getUserRights();
    }



}

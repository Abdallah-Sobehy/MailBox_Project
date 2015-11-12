package entity_dm;
import javax.persistence.*;

@Entity
@Table(name="MAILUSER")
@DiscriminatorValue("bcast")
public class BcastUser extends MailUser {
	public BcastUser() {}
	
	public BcastUser(String name, String pword)
	{
		super(name,pword);
		setUserRights("bcast");
	}

}

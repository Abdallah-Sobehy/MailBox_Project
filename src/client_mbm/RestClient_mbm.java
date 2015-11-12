/**
 * The client package contains the client's logic of the mail box manager
 */
package client_mbm;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * The class RestClient_mbm is the automated test for the rest with the mail box manager
 *
 *
 * @author Mostafa Fateen
 * @author Abdallah Sobehy
 *
 * @version 1.0
 */


public class RestClient_mbm {

	static final String REST_URI = "http://localhost:7777/MyServer/";

	public static void main(String[] args) {

		Client client = Client.create(new DefaultClientConfig());
		URI uri = UriBuilder.fromUri(REST_URI).build();
		WebResource service = client.resource(uri);
		
		System.out.println("Simple sign in from user Mostafa (an admin) : \n"+
				service.path("Mail/signin/Mostafa:1234567890").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Simple sign in from user Abdallah (a broadcast) : \n"+
				service.path("Mail/signin/Abdallah:321").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Simple sign in from user Abdallah (a broadcast) with a wrong password : \n"+
				service.path("Mail/signin/Abdallah:21").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Simple sign in from user Aballah (a non existant user) : \n"+
				service.path("Mail/signin/Aballah:21").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Mostafa sign in and checks an email with ID=3851 : \n"+
				service.path("Mail/read/Mostafa:1234567890:3851").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
				
		System.out.println("Mostafa sign in and checks an email with a wrong message ID=905061850 : \n"+
				service.path("Mail/read/Mostafa:1234567890:905061850").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Show all messages in the news group : \n"+
				service.path("Mail/newsgroup/Mostafa:1234567890").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Send a mail to Abdallah from Mostafa : \n"+
				service.path("Mail/send/Mostafa:1234567890:Abdallah:first rest:this is the first rest").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");

		System.out.println("Send a mail to a wrong user from Mostafa : \n"+
				service.path("Mail/send/Mostafa:1234567890:Abllah:first rest:this is the first rest").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Delete a message with wrong ID : \n"+
				service.path("Mail/delete/Mostafa:1234567890:8765767").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Send a broad cast from Mostafa (an admin) : \n"+
				service.path("Mail/broadcast/Mostafa:1234567890:bcast title:bcast body").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");
		
		System.out.println("Send a broad cast from Abdallah (a bcast) : \n"+
				service.path("Mail/broadcast/Abdallah:321:bcast title:bcast body").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");

		System.out.println("Send a broad cast from Mahmoud (a normal user) : \n"+
				service.path("Mail/broadcast/Mahmoud:123:bcast title:bcast body").type(MediaType.TEXT_PLAIN).get(String.class));
		System.out.println("*******************************");

	}

}

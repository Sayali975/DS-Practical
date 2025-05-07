import java.rmi.*;
import java.util.Scanner;



public class Client{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		try{
			String serverURL = "rmi://localhost/Server";
			ServerIntf serverIntf = (ServerIntf) Naming.lookup(serverURL);
			
			System.out.print("Enter First String: ");
			String str1 = sc.nextLine();
			
			System.out.print("Enter Second String: ");
			String str2 = sc.nextLine();
			
			
		
			System.out.println("--------------- Results ---------------");
			System.out.println("Strings After Joining Is: " + serverIntf.stringJoin(str1, str2));
			
			
		}catch(Exception e){
			System.out.println("Exception Occurred At Client!" + e.getMessage());
		}
		
	}

}

/*Server.java
import java.rmi.*;

public class Server{
	
	public static void main(String[] args){
	
		try{
			ServerImpl serverImpl = new ServerImpl();
			Naming.rebind("Server", serverImpl);
			
			System.out.println("Server Started....");
		
		}catch(Exception e){
			System.out.println("Exception Occurred At Server!" + e.getMessage());
		}
	}
	
	
}*/

/*ServerImpl.java
import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject
	implements ServerIntf {
	
		public ServerImpl() throws RemoteException{
		
		}
		public String stringJoin(String str1, String str2) throws RemoteException{
			String result = str1 + str2;

			return result;
		}
}
 */

 /*ServerIntf.java
 import java.rmi.*;


interface ServerIntf extends Remote{
	// Syntax for method declaration: access_specifier return_type method_name(arguments...){ return value}
	public String stringJoin(String str1, String str2) throws RemoteException;
	
}
  
  */

/*Commands
 
Open terminal at file location

Terminal 1:
javac *.java
rmiregistry

Terminal 2:
java Server

Terminal 3:
java Client
 */


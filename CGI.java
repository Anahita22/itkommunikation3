import java.sql.*;  
import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.Socket;
import java.net.*; 

public class CGI {
public static void main(String[] args) {
String type = "Content-Type: text/html\n\n";
String output = "<html>" +
        "<p></p>" +
        "</html>";

try {
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
String[] data = {in.readLine()};

System.out.println(type);
//System.out.println(output + data[0]);

String [] infoFromUser = data[0].split("&");
int lenPassword = infoFromUser[1].length();
String password = infoFromUser[1].substring(18,lenPassword);

int lenUsername = infoFromUser[0].length();
String user = infoFromUser[0].substring(18,lenUsername);


ConnectToDB ob = new ConnectToDB();


String resultFromDB = ob.validateUser(user,password);

if(resultFromDB!="du findes desvaerre ikke"){
System.out.println(output + resultFromDB+"<meta http-equiv=\"refresh\" content=\"10;url=http://192.168.239.25/kontrolpanel.html\" /> ");
}

else{

System.out.println(output+"Du fandtes desvaerre ikke i vores lokale system,<br> men vi fandt ovensaaende aftale paa folgende server 192.168.239.27");

}








} catch (IOException ioe) {
System.out.println("<p>IOException reading POST data:" + ioe +
"</p>");
}







}
}







class ConnectToDB{  
public String validateUser(String username, String password){  

String velkommen = "du findes desvaerre ikke";
try{  
//Class.forName("com.mysql.cj.jdbc.Driver");  
Connection con=DriverManager.getConnection(  
"jdbc:mysql://localhost:3306/ejournal?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","nakisa","2019");  
//here sonoo is database name, root is username and password  
Statement stmt=con.createStatement();  
ResultSet rs=stmt.executeQuery("select * from Person where Username = '"+username+"' and Password ='"+password+"'");  
while(rs.next())


velkommen = "Velkommen "+rs.getString(3)+" "+ rs.getString(4) +"du vil om faa sekunder blive aotomatisk redirectet til din side";  
con.close();

if(velkommen =="du findes desvaerre ikke"){

TcpSenderClient c = new TcpSenderClient();

 c.getData();
}
}




catch(Exception e){

 System.out.println(e);

}

return velkommen;  
}


}


class TcpSenderClient {
        private static String remoteServerAddr = "192.168.239.27";
        private static int remoteServerPort = 5000;

        public void  getData() throws Exception {
              

try{
  InetAddress remoteServerInetAddr = InetAddress.getByName(remoteServerAddr);
                Socket localSocket = new Socket(remoteServerInetAddr, remoteServerPort);

                String message = "ID 22";

              //  System.out.println("Connected to remote client: " + localSocket.getRemoteSocketAddress());
                OutputStream outToServer = localSocket.getOutputStream(); //pointer to stream which can send data to server
                DataOutputStream out = new DataOutputStream(outToServer);

                out.writeUTF(message); //data to send to server

                InputStream inFromServer = localSocket.getInputStream(); //pointer to stream which can receive data from server
                DataInputStream in = new DataInputStream(inFromServer);

               System.out.println(in.readUTF()); //data received from server
                localSocket.close();



}catch(Exception e){

}
        }
}



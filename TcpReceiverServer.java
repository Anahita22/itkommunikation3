import java.net.*;
import java.io.*;
 
public class TcpReceiverServer extends Thread {
        private static int remoteServerPort = 5555;
        private ServerSocket localServerSocket = null;
 
        public TcpReceiverServer() {
                try {
                        localServerSocket = new ServerSocket(remoteServerPort);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
 
        public void run() {
                while(true) {
                        try {
                                System.out.println("Waiting for client on port " + localServerSocket.getLocalPort() + "...");
                                Socket server = localServerSocket.accept();
 
                                System.out.println("connected to remote client " + server.getRemoteSocketAddress());
                                DataInputStream in = new DataInputStream(server.getInputStream());
 
                                System.out.println(in.readUTF());
                                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                                out.writeUTF("Response/Ack to your data received at server : " + server.getLocalSocketAddress() + "\nThank You!");
                                server.close();
                        } catch (SocketTimeoutException s) {
                                System.out.println("Socket timed out!");
                                break;
                        } catch (IOException e) {
                                e.printStackTrace();
                                break;
                        }
                }
        }
 
        public static void main(String[] args) throws Exception {
                TcpReceiverServer tcpReceiverServer = new TcpReceiverServer();
                tcpReceiverServer.start();
        }
 
}

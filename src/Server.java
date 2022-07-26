import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Serializable {
    public static void main(String[] args) throws IOException, ClassNotFoundException{

        try {
            //creating server socket instance to create a server
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("Server Started.");

            while (true) {
                //client socket instance to accept requests from client
                Socket clientSocket = serverSocket.accept();

                //establishing output and input object stream to send and receive object from and to client
                ObjectOutputStream op = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ip = new ObjectInputStream((clientSocket.getInputStream()));

                //reading client input
                DataInput dI = (DataInput) ip.readObject();
                System.out.println("Received data from the Client.");
                //displaying input sent by client
                dI.display();
                System.out.println();

                //calculating result into object of ResultOutput class
                ResultOutput rO = new ResultOutput(dI);

                //sending the result object to the client
                op.writeObject(rO);
                System.out.println("Sent Result to Client.");
                System.out.println();
            }
        } catch(IOException | ClassNotFoundException e)
        {System.out.println(e.getMessage());}
    }
}
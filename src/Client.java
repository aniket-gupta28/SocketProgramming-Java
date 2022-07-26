import java.io.*;
import java.util.Scanner;
import java.net.*;


public class Client{
    public static void main(String[] args)  throws IOException{

        Scanner input = new Scanner(System.in);
        //taking input from user
        System.out.println("Enter the number of nodes you wish to create.");
        int nodes = input.nextInt();
        int[][] adjMat = new int[nodes][nodes];
        System.out.println("Making a matrix with " + nodes + " nodes.");
        for (int i = 0; i < nodes; i++) {
            System.out.println("Enter elements of row " + (i + 1) + " of the matrix");
            for (int j = 0; j < nodes; j++) {
                adjMat[i][j] = input.nextInt();
            }
        }
        System.out.println("Enter path length.");
        int pathlength = input.nextInt();
        System.out.println("Enter start and end node.");
        String startNode = input.next();
        String endNode = input.next();
        //input taken

        try
        {
            //creating socket to communicate with server
            Socket clientSocket = new Socket("localhost", 9000);
            System.out.println("Client Connected");

            //establishing input and output stream to send objects
            ObjectOutputStream op = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ip = new ObjectInputStream(clientSocket.getInputStream());

            //creating object of DataInput class from input taken from the user
            DataInput inp = new DataInput(adjMat, pathlength, startNode, endNode, nodes);
            System.out.println("Sending following data to server");
            inp.display();//displaying input object on terminal
            op.writeObject(inp);//sending input object to server
            System.out.println("Sent data to the server.");
            op.flush();

            System.out.println("Reading data from server.");
            ResultOutput res = (ResultOutput) ip.readObject();//reading resulting object from server

            //creating text to be published on the resulting rendered image
            String resString = "";
            if (res.ans=='N') {
                resString = "No, there is no path of length " + pathlength + " from node " + startNode.toUpperCase() + " to " + endNode.toUpperCase() + ".";
            } else {
                resString = "Yes, there exists a path of length " + pathlength + " from node " + startNode.toUpperCase() + " to " + endNode.toUpperCase() + ".";
            }

            //creating name of the image
            String name  = "img" + String.valueOf(System.currentTimeMillis());
            System.out.println("Received data from Server.");

            //displaying resulting object data
            res.display(name);

            //rendering the image received and result string
            res.resImage.imageDisplay(name,resString);
            clientSocket.close();
        }
        catch(IOException | ClassNotFoundException ex)
        {System.out.println(ex.getMessage());}

    }
}
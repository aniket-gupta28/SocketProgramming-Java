import java.io.Serializable;
//class to take input and store the graph/adjacency matrix
public class DataInput implements Serializable {
    int[][] adjInput;
    int pathlength;
    int noOfNodes;
    String fromNode, toNode;

    public DataInput(int[][] adjMat, int pathInput, String startNode, String endNode, int n) {
        this.adjInput = adjMat;
        this.pathlength = pathInput;
        this.fromNode = startNode;
        this.toNode = endNode;
        this.noOfNodes = n;
        System.out.println("Object created.");
    }
    //Display function to display the input
    public void display(){
        System.out.println("Matrix: ");
        for(int i=0;i<this.noOfNodes;i++){
            char s = (char) ('A'+i);
            System.out.print(s + " - ");
            for(int j=0;j<this.noOfNodes;j++){
                System.out.print(this.adjInput[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Path Length = " + this.pathlength);
        System.out.println("From Node " + this.fromNode.toUpperCase() + " to Node " + this.toNode.toUpperCase() + ".");
    }

}

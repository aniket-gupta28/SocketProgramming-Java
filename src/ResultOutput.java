import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

//output object class to store result (Y or N) and image in bytestream and adjacency list of input matrix
public class ResultOutput implements Serializable {
    char ans;
    ArrayList<ArrayList<Integer>> adjList;
    imageCreator resImage ;

    //constructor to calculate the result of path existence by taking object of DataInput as argument
    //we take the input from user and convert to DataInput object and declare an object of ResultOutput class on server
    //the server reads input object from client and sends argument to the constructor of ResultOutput class
    //the constructor checks the path and calls imageCreator function from imageCreator class
    //and stores the values into an object of ResultObject class
    public ResultOutput(DataInput node) throws IOException {
        this.adjList = toList(node.adjInput);//convert adjacency matrix to result adjacency list
        //if path exists assign Y otherwise assign N
        if(!existPath(this.adjList,node))
            this.ans = 'N';
        else
            this.ans = 'Y';

        //calling imageCreator function
        this.resImage = new imageCreator(node);
        System.out.println("Object Created.");

    }

    //function to display result
    public void display(String name){
        System.out.println("Received Answer = " + this.ans);
        System.out.println("Image Name - " + name);
    }
    //function to check if a path exist from the input start node to input end node
    public static boolean existPath(ArrayList<ArrayList<Integer>> list, DataInput node) {
        // Create an array of visited nodes
        boolean[] hasVisited = new boolean[node.noOfNodes];
        int startNode = node.fromNode.toUpperCase().charAt(0) - 'A';
        int endNode = node.toNode.toUpperCase().charAt(0) - 'A';
        ArrayList<Integer> pathList = new ArrayList<>();
        ArrayList<Integer> pathLength = new ArrayList<>();
        pathList.add(startNode);

        pathDFSSearch(list, startNode, endNode, pathLength, hasVisited, pathList);

        return pathLength.contains(node.pathlength);
    }

    //using DFS for path search recursively
    //the image does not work for cyclic graphs
    //we cannot take pathlength in that case(cyclic graph)
    private static void pathDFSSearch(ArrayList<ArrayList<Integer>> adjList, Integer fromNode, Integer toNode, ArrayList<Integer> pathLengths, boolean[] hasVisited, ArrayList<Integer> pathList) {

        if (fromNode.equals(toNode)) {
            pathLengths.add(pathList.size()-1);
            return;
        }

        hasVisited[fromNode] = true;

        for (Integer i : adjList.get(fromNode))
            if (!hasVisited[i]) {
                pathList.add(i);
                pathDFSSearch(adjList, i, toNode, pathLengths, hasVisited, pathList);
                pathList.remove(i);
            }
        hasVisited[toNode] = false;
    }

    //converting the adjacency matrix to adjacency list
    public ArrayList<ArrayList<Integer>> toList(int[][] arr){

            int l = arr[0].length;
            ArrayList<ArrayList<Integer>> adjListArray
                    = new ArrayList<>(l);


            for (int i = 0; i < l; i++) {
                adjListArray.add(new ArrayList<>());
            }

            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if (arr[i][j] == 1) {
                        adjListArray.get(i).add(j);
                    }
                }
            }

            return adjListArray;

    }
}
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import org.apache.commons.collections15.Transformer;
//class with function to create image of graph and convert it into byte stream to be sent to client

public class imageCreator extends JFrame {
    byte[] image;//storing image as bytestream

    //creating image from object of DataInput class
    public imageCreator(DataInput node) throws IOException {
        DirectedSparseGraph<String, String> g = new DirectedSparseGraph<>();
        //adding vertices to the graph as per no. of nodes given
        for(int i=0;i<node.noOfNodes;i++){
            String s = String.valueOf((char)(i+'A'));
            g.addVertex(s);
        }

        int edgeCounter = 0;//variable to store number of edges to name each edge

        //making edges in the graph
        for(int i=0;i< node.noOfNodes;i++){
            for(int j = 0; j<node.noOfNodes; j++){
                if(node.adjInput[i][j]==1){
                    edgeCounter++;
                    String s = "Edge " + (char)(edgeCounter);
                    String from = String.valueOf((char)(i+'A'));
                    String to = String.valueOf((char)(j+'A'));
                    g.addEdge(s, from, to);
                }
            }
        }


        //creating a visualization of the graph to convert to bytestream
        VisualizationImageServer<String, String> img = new VisualizationImageServer<>(new CircleLayout<>(g),
                new Dimension(600, 600));
        Transformer<String, String> transformer = new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        };
        img.getRenderContext().setVertexLabelTransformer(transformer);

        //putting the image on JFrame
        JFrame frame = new JFrame("Received Graph");
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(img);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        //creating a buffered image
        BufferedImage image = new BufferedImage(img.getWidth(),img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();//making the graph graphics on the buffered image
        img.print(g2d);
        g2d.dispose();//disposing the graphics2D instance
        frame.dispose();//disposing frame instance
        ByteArrayOutputStream imgStream = new ByteArrayOutputStream();//object to store byte stream
        ImageIO.write(image, "jpg", imgStream);//converting buffered image to byte stream

        this.image = imgStream.toByteArray();
    }


    //function to display image by using bytestream
    //the arguments used for image name and result if path exists or not to be displayed with the image
    public void imageDisplay(String name, String res) throws IOException {

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(this.image));
        JFrame frame = new JFrame("Received Graph - " + name);
        JLabel label = new JLabel();
        frame.setBackground(Color.WHITE);
        label.setText(res);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(label);
        frame.pack();
        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(new ImageIcon(image));
        frame.getContentPane().add(imgLabel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


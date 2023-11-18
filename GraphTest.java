package Assignment4;
/*
Author: Meghan Wilcox
Project: CSC 330 Assignment 4
Class Description: This is the main class for my Assignment 4 program. In this class, we initialize a seperate graph for each text file we read from, 
    capturing the actions performed on that graph in tr/catch blocks. This class reads an adjacency list from a text file, and uses the utility methods defined     
    in the Graph class to assemble a graph from this information. In addition, this program performs a Breadth-First Traversal on each graph, and outputs the 
    minimum distance of each node to the starting node.  
*/
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class GraphTest {

    public static void main(String[] args) {
        
        /*initialization of a scanner, and the first file to be read */
        Scanner input = new Scanner(System.in);
        
        /*Display file choices to the user */
        System.out.println("Which file would you like to run?");
        System.out.println("1. input1.txt");
        System.out.println("2. input2.txt");
        System.out.println("3. input3.txt");
        
        /* capture the users choice in a variable */
        int choice = input.nextInt();
        
        /* create a string object for the file name */
        String file = "";
        
        /*depending on the input choice, assign the file to be that corresponding file name*/
        if (choice == 1){
            file = "input1.txt";
        }
        else if (choice == 2){
            file = "input2.txt";
        }
        else{
            file = "input3.txt";
        }
        
        /* this try/catch block handles reading the file, creating the nodes for each line of the file */
        try {
            /*create a file reader and read the first line as the start node*/
            BufferedReader reader1 = new BufferedReader(new FileReader(file));
            String start = reader1.readLine();
            
            String line;
            int counter = 0;
            Node startingNode = new Node(0, "zero");
            Node newNode;
                
            /* create a list to hold the nodes we create */
            ArrayList<Node> nodeList = new ArrayList<>();
            
            /*read the rest of the lines of the file, creating a node for each line */
            while((line = reader1.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }
                
                /* if the reader gets to the node that should be the start node, name that node "start" */
                if(counter == Integer.parseInt(start)){
                    startingNode = new Node(counter, "start");
                    nodeList.add(startingNode);
                }
                else{
                    newNode = new Node(counter, Integer.toString(counter));
                    nodeList.add(newNode);
                }
                counter++;
            }
            reader1.close();  
            
            /* create a graph object */
            Graph graph = new Graph(false, 0, startingNode);
            
            /* add the nodes to the graph */
            for(int i = 0; i < nodeList.size(); i++){
                graph.addVertex(nodeList.get(i), graph);
            }
            
            /* create a second reader to read the file again and add the edges to each node */
            BufferedReader reader2 = new BufferedReader(new FileReader(file));
            String firstLine = reader2.readLine();
            
            String line2;
            int counter2 = 0;
            
            /* for each line/node split the line into an array of edges */
            while((line2 = reader2.readLine()) != null){
                if(line2.trim().isEmpty()){
                    continue;
                }
                
                /* split the line inot the edges that are to be assigned */
                String[] edges = line2.split(" ");
                
                /* add an edge between the current node and the neighbor nide specified by the line */
                Node currentNode = nodeList.get(counter2);
                for (String edge : edges){
                    if(!(edge.isEmpty())){
                        Node neighborNode = nodeList.get(Integer.parseInt(edge));
                        graph.addEdge(currentNode, neighborNode);
                    } 
                }
                counter2++;
            }
            reader2.close();
                     
            System.out.println();
            
            /*perform a BFS traversal on the graph */
            graph.BFS(startingNode, graph);
            
            /*create an array to store the distances of each node from the start node */
            int[] nodeDistances = new int[graph.numVertices];
            int j = 0;
            for (Node node : nodeList){
                nodeDistances[j] = node.distance;
                j++;
            }
            
            /*output the minimum distances of each node from the start node in a array format */
            System.out.println();
            System.out.println("Minimum Distance from the Starting Node for each Node: ");
            System.out.print("[");
            for(int i  = 0; i < nodeDistances.length - 1; i++){
                System.out.print(nodeDistances[i] + ", ");
            }
            System.out.print(nodeDistances[nodeDistances.length - 1] + "]");
        }
        /*catch the exception */
        catch(IOException e){
            System.out.println("An error occured reading the file.");
        }
    }   
}

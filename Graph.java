package Assignment4;
/*
Author: Meghan Wilcox
Project: CSC 330 Assignment 4
Class Description: This is the Graph class, defining the structure of our graph. It has the following methods and corresponding purposes:
     - addVertex(this method adds a vertex onto our graph)
     - addEdge/addEdgeHelp(these two methods work together to add an edge between 2 vertices/nodes on our graph)
     - getneighbors(this method returns a LinkedList of the vertex names that are neighbors to a specified vertex has)
     - BFS(performs a Breadth First Search Traversal on teh graph and keeps track of the minimum distance of each node from the starting node)
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Graph {
    /* Each node maps to a list of all his neighbors */
    private HashMap<Node, LinkedList<Node>> adjacencyMap;
    private boolean directed;
    int numVertices;
    Node startingNode;
    
    
    /*constructor for the graph class
    has a boolean directed attribute , a starting node, and a HashMap to store the adjacency values
    */
    public Graph(boolean directed, int numVertices, Node startingNode) {
        this.startingNode = startingNode;
        this.numVertices = numVertices;
        this.directed = directed;
        adjacencyMap = new HashMap<>();
    }
        
    /*addvertex: check if a node is already on the map:
        if the node is not already on the graph - it adds a new node into the adjacency map, with its own linkedList to store its neigbors */
    public void addVertex(Node newNode, Graph graph){
        if(!adjacencyMap.containsKey(newNode)){
            adjacencyMap.put(newNode, new LinkedList<>());
            graph.numVertices++;
        }
    }
     
    /*addEdgeHelp: updates the list of neighbors for a node when we want to add an edge, or add a neighbor.
        This method is responsible for adding the edge between the vertices */
    public void addEdgeHelper(Node a, Node b) {
        //store the list of neighbors for a in temp
        LinkedList<Node> tmp = adjacencyMap.get(a);

        //if a has neighbors, check if b is already in the list of neighbors
        if (tmp != null) {
            tmp.remove(b);
        } else { //temp is null, so a has no neighbors, create a linkedlist to store its neighbors
            tmp = new LinkedList<>();
        }
        
        //add b to the list of neighbors
        tmp.add(b);
        
        //update the adjacency map with the new list of neighbors for a
        adjacencyMap.put(a, tmp);
    }
    
    /* addEdge: works with the addEdgeHelp method to add an edge between 2 vertices on the graph */
    public void addEdge(Node source, Node destination) {
        
        // Make sure that every used node shows up in our .keySet()
        if (!adjacencyMap.keySet().contains(source)) {
            adjacencyMap.put(source, new LinkedList<>());
        }
        
        //adds the edge between source and destination
        addEdgeHelper(source, destination);

        if (!directed) {
            // If it's an undirected graph, add only one edge.
            if (!adjacencyMap.keySet().contains(destination)) {
                adjacencyMap.put(destination, new LinkedList<>());
            }

            addEdgeHelper(destination, source);
        }
    }  
   
    /*getNeighbors: checks if v has neighbors, if it does, it extracts thier names and outputs a list of thier names */
    public List<String> getNeighbors(Node v){
        //put the current neighbors of v into a linked list
        LinkedList<Node> neighbors = adjacencyMap.get(v);
        
        //if there are no current neighbors of v, output an empty ArrayList
        if(neighbors == null){
            return new ArrayList<>();
        }
        
        //v has neighbors, so we crate a list to store the names of the neighbors
        List<String> neighborsOfV = new ArrayList<>();
        
        //iterate through the list of neighbors, extracting the name of each and adding it to the list
        for (Node neighbor : neighbors){
            neighborsOfV.add(neighbor.name);
        }
        return neighborsOfV;
    }
        
    /*BFS: performs a bread first search on the graoh starting at a specified node */ 
    public void BFS(Node startNode, Graph graph){
        //initialize an array to hold the visited values, and a queue to maintain the order that nodes are visited in 
        boolean[] visited = new boolean[graph.numVertices];
        Queue<Node> queue = new LinkedList<>();
        
        int[] distance = new int[graph.numVertices];
        Arrays.fill(distance, -1);
        distance[startNode.n] = 0;
        
        //mark the first node as visited, and add to the queue
        visited[startNode.n] = true;
        queue.add(startNode);
        
        System.out.println("BFS Traversal:");
        
        
        //while the queue is not empty, dequeue the bottom element and display it as a visited node
        while(!queue.isEmpty()){
            Node currentNode = queue.poll();
            
            System.out.println("Visited Node " + currentNode.n + " with distance " + distance[currentNode.n]);
            
            //put the list of neighbors of that current node into a new list
            LinkedList<Node> neighbors = adjacencyMap.get(currentNode);
            
            //if that list is empty, add the neighbors of the current node to the queue if they have not been visited already
            if(neighbors != null){
                for(Node neighbor : neighbors){
                    if(!visited[neighbor.n]){
                        visited[neighbor.n] = true;
                        queue.add(neighbor);
                        distance[neighbor.n] = distance[currentNode.n] + 1;
                        neighbor.distance = distance[neighbor.n];
                    }
                }
            }
        }
    }

}



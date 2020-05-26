/* ShortestPaths.java
   CSC 226 - Fall 2019
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths
   
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>
   
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
   
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.*;


//Do not change the name of the ShortestPaths class
//https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
public class ShortestPaths{
	private static class vertex{
			int number;		//Not directed graph, just describe the endpoints
			int distance;

		}
	private static class vertexComparator implements Comparator<vertex>{
		public int compare(vertex x, vertex y){
			//1 positive, 0 equal, -1 negative
			return x.distance - y.distance; //This is for the PriorityQueue.
		}
	}
    //TODO: Your code here
    public static int n; // number of vertices
    /* ShortestPaths(adj) 
       Given an adjacency list for an undirected, weighted graph, calculates and stores the
       shortest paths to all the vertices from the source vertex.
       
       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
       
       All weights will be positive.
    */
	
	private static IndexMinPQ<Integer> pq; // Using indexPQ to check if the path is the shortest
	private static int dist[]; 	// Where the shortest path stores
	private static int pre[];	// Where the parents store
	//private static int v;
	private static Set<Integer> visited; // Check if the vertex has been marked visited


    static void ShortestPaths(int[][][] adj, int source){
		n = adj.length;
		 
 		pq = new IndexMinPQ<Integer>(n);
		pre = new int[n];
		dist = new int[n];
		visited = new HashSet<Integer>(); // Using hashset to store the visited vertex

		for (int i = 0; i<n; i++){
			dist[i] = 2147483647; // Set the distance to be the max_value of integer
		}

		pq.insert(source,0);	// Marked the source distance to be 0
		dist[source] = 0;
		pre[source] = -4;		// Marked the source's parents to be negative
	//TODO: Your code here
		while(visited.size() != n){
			int u = pq.delMin(); // Always start from the shortest distance remain in visited vertex.

			visited.add(u);		// Marked visited

			visit_neighbours(adj,u); // Get to all neighbours
		}
    }
    static void visit_neighbours(int[][][] adj, int u){
		int w = 0;			// Current distance
		int temp = 0;		// New distance
		vertex t = new vertex();

		for(int i = 0; i < adj[u].length; i++){	// Visit all the neighbours of u
			t.number = adj[u][i][0];	
			t.distance = adj[u][i][1];


			if(!visited.contains(t.number)){	// If any of the neighbours has not been visited
				w = t.distance;
				temp = dist[u]+w;

				if(temp < dist[t.number]){		// If the current distance is longer than the new distance
					dist[t.number] = temp;		// Update the distance
					pre[t.number] = u;			// Update the parents
				}

				if(pq.contains(t.number) == true){ // If the vertex already in the PQ
					if(pq.keyOf(t.number)>temp){	// If the new distance is shorter
						pq.decreaseKey(t.number, temp);} // Update the current distance
				}										// Instead of add new pair of vertex and distance
				else{								
					pq.insert(t.number,temp);
				}
			}
		}

	}
	// Print all the parents until reach the source
	static void PrintPre(int r){
		if(pre[r] == -4){ // Basis case to be when reach the source
			return;
		}
		PrintPre(pre[r]);
		System.out.print(" --> " +r); // Print all the paths 
	}
    static void PrintPaths(int source){
	//TODO: Your code here
		for( int i = 0; i < dist.length;i++){
			System.out.print("The path from "+source+" to "+i+" is: 0");
			PrintPre(i); // Call the function to print all the path on the shortest path
			System.out.println(" and the total distance is : "+dist[i]); // Print total distance for shortest path
		}
    }
    
    
    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    //If a file argument was provided on the command line, read from the file
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else{
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
	
	int graphNum = 0;
	double totalTimeSeconds = 0;
	
	//Read graphs until EOF is encountered (or an error occurs)
	while(true){
	    graphNum++;
	    if(graphNum != 1 && !s.hasNextInt())
		break;
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();
	    int[][][] adj = new int[n][][];
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++){
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++){
		    int weight = s.nextInt();
		    if(weight > 0) {
			edgeList.add(new int[]{j, weight});
		    }
		    valuesRead++;
		}
		adj[i] = new int[edgeList.size()][2];
		Iterator it = edgeList.iterator();
		for(int k = 0; k < edgeList.size(); k++) {
		    adj[i][k] = (int[]) it.next();
		}
	    }
	    if (valuesRead < n * n){
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }
	    
	    // output the adjacency list representation of the graph
	    /*for(int i = 0; i < n; i++) {
	    	System.out.print(i + ": ");
	    	for(int j = 0; j < adj[i].length; j++) {
	    	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    	}
	    	System.out.print("\n");
	    }*/
	    
	    long startTime = System.currentTimeMillis();
	    
	    ShortestPaths(adj, 0);
	    PrintPaths(0);
	    long endTime = System.currentTimeMillis();
	    totalTimeSeconds += (endTime-startTime)/1000.0;
	    
	    //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
	}
	graphNum--;
	System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}

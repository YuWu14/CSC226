/* MST.java
   CSC 226 - Fall 2019
   Problem Set 2 - Template for Minimum Spanning Tree algorithm
   
   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.
   
   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt
   
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
   	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
   3
   0 1 0
   1 0 2
   0 2 0
   	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the mst() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
*/

import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.*; 
import java.lang.*; 
import java.io.*; 



public class MST {

	private static class Edge{
		int from;		//Not directed graph, just describe the endpoints
		int to;
		int weight;

	}
	private static class EdgeComparator implements Comparator<Edge>{
		public int compare(Edge x, Edge y){
			//1 positive, 0 equal, -1 negative
			return x.weight - y.weight; //This is for the PriorityQueue.
		}
	}
    /* mst(adj)
       Given an adjacency matrix adj for an undirected, weighted graph, return the total weight
       of all edges in a minimum spanning tree.

       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
    */

	//For each vertices, the original root is themselves.
	public static void self_root(int[] id){
		for(int i = 0; i < id.length;i++){
			id[i] = i;
		}
	}

	//This function did not check the size, since the only output for this program
	// is the weight itself, no MST requires.
	public static void union(int[] id,int m, int n){
		int a = find(id,m);
		int b = find(id,n);
		id[b] = a;
	}

	//Find the parents and root.
	public static int find(int[] id, int i){
		while (i != id[i]){	// Keep find until reach the root.
			return find(id, id[i]);
		}
		return i;
	}

    static int mst(int[][][] adj) {
		int n = adj.length;

	/* Find a minimum spanning tree using Kruskal's algorithm */
	/* (You may add extra functions if necessary) */

	/* ... Your code here ... */
		// Find the number of edges in the graph
		int number_edge = 0;		
		for(int i = 0; i<n;i++){
			number_edge = number_edge + adj[i].length;
		}

		//Store all edges in the PriorityQueue.
		PriorityQueue<Edge> edges = new PriorityQueue<Edge>(new EdgeComparator());
		for(int i = 0; i<n;i++){
			for(int j = 0; j<adj[i].length;j++){
				Edge temp = new Edge();
				temp.from = i;
				temp.to = adj[i][j][0];
				temp.weight = adj[i][j][1];
				edges.add(temp);
			}
		}

		/*for(int i = 0; i<number_edge;i++){
			System.out.println(edges.poll().weight);}*/

		/* Add the weight of each edge in the minimum spanning tree
		to totalWeight, which will store the total weight of the tree.
		*/
		//The list to store all parents and the root.
		int[] id = new int[n];
		self_root(id);

		int totalWeight = 0;
		/* ... Your code here ... */
		int x = 0;

		// Before includes all vertices in the MST, keep looping.
		while(x<n-1){
			Edge temp1=edges.poll();
			// Call the union-find function to create MST.
			int y = find(id,temp1.from);
			int z = find(id,temp1.to);

			// If no circle finded, add the weight to total weight.
			if(y != z){
				totalWeight = totalWeight+temp1.weight; // No MST stores, add the weights.
				x = x + 1;
				union(id,y,z);
			}
		}
		return totalWeight;
		
    }


    public static void main(String[] args) {
	/* Code to test your implementation */
	/* You may modify this, but nothing in this function will be marked */

	int graphNum = 0;
	Scanner s;

	if (args.length > 0) {
	    //If a file argument was provided on the command line, read from the file
	    try {
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else {
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
		
	//Read graphs until EOF is encountered (or an error occurs)
	while(true) {
	    graphNum++;
	    if(!s.hasNextInt()) {
		break;
	    }
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();

	    int[][][] adj = new int[n][][];
	    
	    
	    
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++) {
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++) {
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
	    if (valuesRead < n * n) {
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }

	    // // output the adjacency list representation of the graph
	    // for(int i = 0; i < n; i++) {
	    // 	System.out.print(i + ": ");
	    // 	for(int j = 0; j < adj[i].length; j++) {
	    // 	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    // 	}
	    // 	System.out.print("\n");
	    // }

	    int totalWeight = mst(adj);
	    System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

				
	}
    }

    
}

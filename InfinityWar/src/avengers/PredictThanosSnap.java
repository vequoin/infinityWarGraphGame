package avengers;
import java.util.*;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        long seedNumber = StdIn.readLong();

        int numVertices = StdIn.readInt();

        HashMap<Integer,ArrayList<Integer>> graph = new HashMap<>();
        ArrayList<ArrayList<Integer>> graphThree = new ArrayList<>();
        for(int i = 0; i < numVertices;i++){
            graph.put(i, new ArrayList<>());
            graphThree.add(i, new ArrayList<Integer>());
            for(int j = 0; j < numVertices;j++){
                int connection = StdIn.readInt();
                if(connection == 1){
                    graph.get(i).add(j);
                    graphThree.get(i).add(j);
                }
            }
        }
        System.out.println("Seednumber is " + seedNumber);
        StdRandom.setSeed(seedNumber);
        for(int i = 0; i < numVertices;i++){
            if(StdRandom.uniform() <= 0.5){
                deleteVertexTwo(graph, i);
            }
        }

        HashSet<Integer> remainingPeople = new HashSet<>();
        for(Integer g: graph.keySet()){
            remainingPeople.add(g);
        }

        //DFS(graph,remainingPeople);

        boolean thanosSnapWorks = isConnected(graph);
        StdOut.print(thanosSnapWorks);
        
    	// WRITE YOUR CODE HERE
        

    }

    public static void deleteVertexTwo(HashMap<Integer, ArrayList<Integer>> graph, int num){
        for(int values: graph.keySet()){
            if(graph.get(values).contains(num)){
                graph.get(values).remove(Integer.valueOf(num));
            }
        }
        graph.remove(num);
    }

    public static boolean isConnected(HashMap<Integer, ArrayList<Integer>> graph){

        if(graph.isEmpty()){
            return true;
        }

        HashSet<Integer> connectedPeople = new HashSet<>();
        int firstVertex = 0;
        
        for(Integer v: graph.keySet()){
            firstVertex = v;
            break; 
        }
        DFS(graph, connectedPeople, firstVertex);

        return graph.size() == connectedPeople.size();
    }

    private static void DFS(HashMap<Integer, ArrayList<Integer>> graph, HashSet<Integer> connectedPeople,
            int firstVertex) {
                connectedPeople.add(firstVertex);
                for(int neighbors: graph.get(firstVertex)){
                    if(!connectedPeople.contains(neighbors)){
                        DFS(graph, connectedPeople, neighbors);
                    }
                }
    }
}

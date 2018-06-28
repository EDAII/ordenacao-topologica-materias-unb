/*
 * SimpleGraphView.java
 *
 * Created on March 8, 2007, 7:49 PM; Updated May 29, 2007
 *
 * Copyright March 8, 2007 Grotto Networking
 */

package grafo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class SimpleGraphView {
	
	static AdjNode adjList[];

    private static List<Integer> getNeighbors(String line){
    	line = line.substring(line.indexOf('-')+1);
		List<Integer> neighbors = new ArrayList<Integer>();
		while(line.indexOf('\t') != -1) {
			try {
				line = line.trim();
				int neighbor = Integer.parseInt(line.substring(0, line.indexOf('\t')));
				neighbors.add(neighbor);
				line = line.substring(line.indexOf('\t'));	
			}
			catch(Exception e) {
				/*do nothing, no child left*/
			}
		}
		return neighbors;
    }
    
    private static List<Node> getRightGraph(List<Node> list){
    	List<Node> graph = new ArrayList<Node>();
    	
    	for(Node node : list) {
    		graph.add(new Node(node.getNode(), new ArrayList<Integer>()));
    	}

    	for(Node node: list) {
    		for(Integer neighbor : node.getNeighbors()) {
    			int index = Node.indexOf(list, neighbor);
    			if(index != -1) {
    				graph.get(index).addNeighbor(node.getNode());
    			}
    		}
    	}
    	
    	return graph;
    }
    
    private static List<Node> readNodesFromFile(){
    	List<String> relationLines = null;
    	List<Node> graph = new ArrayList<Node>();
		try {
			relationLines = Files.readAllLines(Paths.get("relacao.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for (String line : relationLines) {
    		String lineWithoutSpaces = line.trim();
    		int nodeValue = Integer.parseInt(lineWithoutSpaces.substring(0, lineWithoutSpaces.indexOf('-')));
    		List<Integer> nodeNeighbors = getNeighbors(lineWithoutSpaces);

    		Node node = new Node(nodeValue, nodeNeighbors);
    		graph.add(node);
		}
    	return graph;
    }
    
    private static void swap(int a, int b)
    {
    	AdjNode tmp = adjList[a];
    	adjList[a] = adjList[b];
    	adjList[b] = tmp;
    }

    private static void heapify(int i, int n)
    {
        int lft = i * 2;
        int rgt = lft + 1;
        int grt = i;

        if (lft <= n && adjList[lft].compareTo(adjList[grt]) < 0) grt = lft;
        if (rgt <= n && adjList[rgt].compareTo(adjList[grt]) < 0) grt = rgt;
        if (grt != i) {
            swap(i, grt);
            heapify(grt, n);
        }
    }
 

    public static void main(String[] args) throws Exception {
    	//reading reverse graph from file
    	List<Node> reverseGraph = readNodesFromFile();
    	List<Node> graph = getRightGraph(reverseGraph);
    	List<Node> TopologicGraph = new ArrayList<Node>();
    	adjList = new AdjNode[reverseGraph.size()];
    	
    	int count = 0;
    	
    	//building adj vector (using position in original vector and number of nodes that point for it)
    	for (Node node : reverseGraph) {
    		AdjNode adjNode = new AdjNode(reverseGraph.indexOf(node), node.getNeighbors().size());
    		adjList[count] = adjNode;
    		count++;
		}
    	
    	//build heap
    	int n = adjList.length-1;
    	while(graph.size() != 0) {
    		//faz o heapfy
	    	for (int i =  n/2-1; i >= 0; i--)
	    		heapify(i, n);
	    	
	    	//se tem +1 apontando pra ele e ele é o que tem menos apontamentos, há algo errado!
	    	if(adjList[0].getNeighbors() != 0) {
	    		System.out.println("HÁ UMA INCOSISTÊNCIA NAS MATÉRIAS!!");
	    		throw new Exception();
	    	}
	    	//se não tem tá ok, remove ele da lista e subtrai 1 da lista de controle de todos os nós q ele apontava
	    	else {
	    		Node currNode = graph.get(Node.indexOf(graph, reverseGraph.get(adjList[0].getNode()).getNode()));
	    		TopologicGraph.add(currNode);
	    		for(Integer value : currNode.getNeighbors()) {
	    			int indexInReverseList = Node.indexOf(reverseGraph, value);
	    			if(indexInReverseList != -1) {
	    				int indexInAdjList = AdjNode.indexOf(adjList, indexInReverseList);
	    				adjList[indexInAdjList].substractNeighbor();
	    			}
	    		}
	    		graph.remove(Node.indexOf(graph, reverseGraph.get(adjList[0].getNode()).getNode()));
	    		swap(0, n);
	    		n--;
	    	}
    	}
    	int aux = 0;
    	while(aux < adjList.length) {
    		System.out.println(adjList[aux].toString());
    		aux++;
    	}
    	
    }
    
}
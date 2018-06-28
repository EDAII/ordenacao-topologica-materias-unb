package grafo;

import java.util.List;

public class Node{
    	private List<Integer> neighbor;
    	private int value;
    	public Node(int value, List<Integer> neighbor) {
    		this.neighbor = neighbor;
    		this.value = value;
    	}
    	public int getNode() {
    		return value;
    	}
    	public List<Integer> getNeighbors(){
    		return neighbor;
    	}
    	
    	public void setNeighbors(List<Integer> neighbor) {
    		this.neighbor = neighbor;
    	}
    	
    	public void addNeighbor(Integer neighbor) {
    		this.neighbor.add(neighbor);
    	}
    	
    	@Override
    	public String toString() {
    		int isFirst = 1;
    		String returnedValue = this.value + " -> ";
    		for (Integer neighbor : neighbor) {
    			if(isFirst != 1) {
    				returnedValue += " -> ";
    			}
    			else {
    				isFirst = 0;
    			}
    			returnedValue += neighbor;
			}
    		if(returnedValue.length() != 10) {
    			returnedValue += " -> ";
    		}
    		returnedValue += "NULL";
    		return returnedValue;
    	}
    	
    	public static int indexOf(List<Node> list, int value) {
    		//System.out.printf("searching %d in an vector with %d elements\n", value, list.size());
    		for(Node node : list) {
    			if(node.value == value) {
    				return list.indexOf(node);
    			}
    		}
    		return -1;
    	}
    }
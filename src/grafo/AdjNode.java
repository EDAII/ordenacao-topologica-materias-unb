package grafo;


public class AdjNode {
	private int neighbor;
	private int value;
	public AdjNode(int value, int neighbor) {
		this.neighbor = neighbor;
		this.value = value;
	}
	public int getNode() {
		return value;
	}
	public int getNeighbors(){
		return neighbor;
	}
	
	public void setNeighbors(int neighbor) {
		this.neighbor = neighbor;
	}
	
	public void substractNeighbor() {
		this.neighbor--;
	}
	
	public String toString() {
		return this.value + " has " + this.neighbor + "neighbors";
	}
	
	public int compareTo(AdjNode node) {
	  if(neighbor < node.neighbor) {
		  return -1;
	  }
	  else if(neighbor > node.neighbor) {
		  return 1;
	  }
	  else {
		  return 0;
	  }
	}
	
	
	public static int indexOf(AdjNode[] list, int value) {
		int count = 0;
		//System.out.printf("searching %d in an vector with %d elements\n", value, list.size());
		for(AdjNode node : list) {
			if(node.value == value) {
				return count;
			}
			count++;
		}
		return -1;
	}
}

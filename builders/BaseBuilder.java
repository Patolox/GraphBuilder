package builders;


import java.util.HashMap;
import java.util.Map;

import objects.Edge;
import objects.Node;

public abstract class BaseBuilder{
	protected Map<Integer, Node> nodes = new HashMap<Integer, Node>();
	protected Map<Integer, Edge> edges = new HashMap<Integer, Edge>();

	public void buildAdjacencyLists() {
		
		for(Map.Entry<Integer, Node> n: nodes.entrySet()) {
			for(Map.Entry<Integer, Edge> e: edges.entrySet()) {
				if(e.getValue().getOrigin().getId() == n.getValue().getId()) {
					n.getValue().addNeighbor(e.getValue().getDestiny());
					if(e.getValue().isDirected() == false) {
						e.getValue().getDestiny().addNeighbor(n.getValue());
					}
				}
			}
		}
		
	}
	
	
	public void deleteAll() {
		nodes = new HashMap<Integer, Node>();
		edges = new HashMap<Integer, Edge>(); 
	}


}

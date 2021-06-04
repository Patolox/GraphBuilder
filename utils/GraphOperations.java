package utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import gui.GraphVisualizer;
import objects.Node;

public abstract class GraphOperations {


	public static void BFS(int start, int finish, HashMap<Integer, Node> nodes, GraphVisualizer gv)
    {
        Node fNode = nodes.get(finish);
        Node aNode = nodes.get(start);
		Node nNode;

        LinkedList<Node> queue = new LinkedList<Node>();

        aNode.setVisited(true);
        aNode.setDrawColor(Color.green);
        queue.add(aNode);

        while (queue.size() != 0)
        {
            nNode = queue.poll();
            if(nNode.getId() == fNode.getId()) {
            	break;
            }
            Iterator<Node> i = nNode.getAdjacencyList().listIterator();
            while (i.hasNext())
            {

                Node auxn = i.next();
                if (!auxn.isVisited())
                {
                    auxn.setParent(nNode);
                    auxn.setVisited(true);
                    auxn.setDrawColor(Color.green);
                    queue.add(auxn);
                    gv.repaint();                  
                }
               
            }
        }

        fNode.setDrawColor(Color.RED);
        Node parent = fNode.getParent();
        while(parent != null) {
        	parent.setDrawColor(Color.RED);
        	parent = parent.getParent();
        	gv.repaint();
        }
    }

// this should be done with the EDGES, not the NODES...
	public static void DjikstraAlgorithm(int start, int finish, HashMap<Integer, Node> nodes, GraphVisualizer gv) {
		Node fNode = nodes.get(finish);
		Node aNode = nodes.get(start);
		LinkedList<Node> queue = new LinkedList<Node>();

		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).setDistance(Integer.MAX_VALUE);
			queue.add(nodes.get(i));
		}

		aNode.setDistance(0);

		while(!queue.isEmpty()) {
			Node minNode = getMinDistanceNode(queue);
			minNode.setDrawColor(Color.green);
			if(minNode.getId() == fNode.getId()) {
				break;
			}
			queue.remove(minNode);
			if(minNode != null && minNode.getAdjacencyList() != null) {
				for(Node adj : minNode.getAdjacencyList()) {
					if(adj.getDistance() > minNode.getDistance() + adj.getCost()) {
						adj.setDistance(minNode.getDistance() + adj.getCost());
						adj.setParent(minNode);
					}
				}
			}else {
				System.out.println("Não há caminhos disponiveis a partir do nó selecionado");
				break;
			}
		}

        fNode.setDrawColor(Color.RED);
        Node parent = fNode.getParent();
        while(parent != null) {
        	parent.setDrawColor(Color.RED);
        	parent = parent.getParent();
        }
        gv.repaint();
	}


	private static Node getMinDistanceNode(LinkedList<Node> queue) {
		int minDistance = Integer.MAX_VALUE;
		Node minNode = null;

		for(int i = 0; i < queue.size(); i++) {
			Node n = queue.get(i);
			int nDistance = n.getDistance();
			
			if(nDistance < minDistance) {
				minDistance = nDistance;
				minNode = n;
			}
		}
		return minNode;
	}

}

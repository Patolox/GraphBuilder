package objects;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Comparator<Node>, Serializable{

	private int id;
	private String name;
	private String color;
	private Color drawColor;
	private ArrayList<Node> adjacencyList = new ArrayList<Node>();
	private int x;
	private int y;
	private boolean visited;
	private Node parent;
	private int cost;
	private int distance;
	
	public Node(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}

	
	public Node(int id, int x, int y, String color, Color drawColor) {
		this.id = id;
		this.color = color;
		this.setDrawColor(drawColor);
		this.x = x;
		this.y = y;
		this.setVisited(false);
	}
	
	
	public ArrayList<Node> getAdjacencyList(){
		return adjacencyList;
	}
	
	
	public void addNeighbor(Node n) {
		adjacencyList.add(n);
	}
	
	
	public void setColor(String color) {
		this.color = color;
	}
	
	
	public String getColor() {
		return this.color;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	
	public int getId() {
		return this.id;
	}
	
	
	public int getX() {
		return this.x;
	}
	
	
	public int getY() {
		return this.y;
	}
	
	
	public String toString() {
		return (
				"Node {\n"
				+"id: " + this.id + ",\n"
				+"name: " + this.name + ",\n"
				+"color: " + this.color + ",\n"
				+"}\n\n"
				);
	}


	public Color getDrawColor() {
		return drawColor;
	}


	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}


	public boolean isVisited() {
		return visited;
	}


	public void setVisited(boolean visited) {
		this.visited = visited;
	}


	public Node getParent() {
		return parent;
	}


	public void setParent(Node parent) {
		this.parent = parent;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}


  @Override
    public int compare(Node node1, Node node2){
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }


	public int getDistance() {
		return distance;
	}
	
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
}

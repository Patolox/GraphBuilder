package objects;

import java.awt.Color;
import java.io.Serializable;

public class Edge implements Serializable{

	private int id;
	private Node origin;
	private Node destiny;
	private boolean directed;
	private Color drawColor;
	private int weight;
	private int distance;
	
	public Edge(int id, Node origin, Node destiny, boolean directed, int weight) {
		this.id = id;
		this.weight = weight;
		this.origin = origin;
		this.destiny = destiny;
		this.directed = directed;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Node getOrigin() {
		return this.origin;
	}
	
	public Node getDestiny() {
		return this.destiny;
	}
	
	public boolean isDirected() {
		return this.directed;
	}
	
	public String toString() {
		return (
				"Edge {\n"
				+"id: " + this.id + ",\n"
				+"origin: " + this.origin.getId() + ",\n"
				+"destiny: " + this.destiny.getId() + ",\n"
				+"directed: " + this.directed + ",\n"
				+"weight: " + this.getWeight() + ",\n"
				+"}\n\n"
				);
	}

	public Color getDrawColor() {
		return drawColor;
	}

	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
}

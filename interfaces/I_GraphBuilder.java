package interfaces;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import objects.Edge;
import objects.Node;

public interface I_GraphBuilder {

	public void buildNodes() throws IOException;
	public void buildEdges() throws IOException;
	public BufferedImage getImg();
	public HashMap<Integer, Node> getNodesHashTable();
	public HashMap<Integer, Edge> getEdgesHashTable();
	
}

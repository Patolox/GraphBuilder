package interfaces;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import objects.Edge;
import objects.Node;

public interface I_imageGraphBuilder {
	public BufferedImage getImg();
	public HashMap<Integer, Node> getNodesHashTable();
	public HashMap<Integer, Edge> getEdgesHashTable();
	public void buildEdges(int piecesSize, Node selectedNode, Node n);
	public void buildNodes(int piecesSize, int mouse_x, int mouse_y);
	public void saveRepresentationToFile(String path);
	public void saveToFile(String path);
}

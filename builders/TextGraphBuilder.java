package builders;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import interfaces.I_GraphBuilder;
import objects.Edge;
import objects.Node;


public class TextGraphBuilder extends BaseBuilder implements I_GraphBuilder{

	private FileReader nodeReader;
	private FileReader edgeReader;
	
	
	public TextGraphBuilder(File nodeFile, File edgeFile) throws FileNotFoundException {
		this.nodeReader = new FileReader(nodeFile);
		this.edgeReader = new FileReader(edgeFile);
	}
	
	
	// no error handler...
	// assuming that the text file has correct values
	public void buildNodes() throws IOException {
		BufferedReader brNode = new BufferedReader(this.nodeReader);
		String line = brNode.readLine();
		int nodeId = 0;
		String nodeName = null;
		String nodeColor = null;
		
		while(line != null) {
			
			if(line.contains("id")) {
				nodeId = parseIntegers(line);
			}else if(line.contains("name")) {
				nodeName = parseStrings(line);
			}else if(line.contains("color")) {
				nodeColor = parseStrings(line);
			}else if(line.contains("}")) {
				nodes.put(nodeId, new Node(nodeId, nodeName, nodeColor));
//				System.out.println(nodes.get(nodeId)); // for testing purposes
			}
			line = brNode.readLine();
		}
	}
	
	
	// no error handler...
	// assuming that the text file has correct values
	public void buildEdges() throws IOException {
		BufferedReader brEdge = new BufferedReader(this.edgeReader);
		String line = brEdge.readLine();
		int edgeId = 0;
		int originId = 0;
		int destinyId = 0;
		int weight = 0;
		boolean directed = false;
		
		while(line != null) {
			
			if(line.contains("id")) {
				edgeId = parseIntegers(line);
			}else if(line.contains("origin")) {
				originId = parseIntegers(line);
			}else if(line.contains("destiny")) {
				destinyId = parseIntegers(line);
			}else if(line.contains("directed")) {
				directed = Boolean.parseBoolean(parseStrings(line));
			}else if(line.contains("weight")){
				weight = parseIntegers(line);
			}else if(line.contains("}")) {
				edges.put(edgeId, new Edge(edgeId, nodes.get(originId), nodes.get(destinyId), directed, weight));
//				System.out.println(edges.get(edgeId)); // for testing purposes
			}
			line = brEdge.readLine();
		}
		
	}
	
	
	public HashMap<Integer, Node> getNodesHashTable() {
		return (HashMap<Integer, Node>) nodes;
	}
	
	
	public HashMap<Integer, Edge> getEdgesHashTable() {
		return (HashMap<Integer, Edge>) edges;
	}
	
	
	private String parseStrings(String line) {
		String l = line.split(":")[1];
		l = l.replace(",", "");
		l = l.trim();
		return l;
	}
	
	
	private int parseIntegers(String line) {
		String l = line.split(":")[1];
		l = l.replace(",", "");
		l = l.trim();
		return Integer.parseInt(l);
	}


	@Override
	public BufferedImage getImg() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

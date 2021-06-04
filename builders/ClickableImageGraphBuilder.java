package builders;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import interfaces.I_imageGraphBuilder;
import objects.Edge;
import objects.Node;
import utils.ImageParsing;

public class ClickableImageGraphBuilder extends BaseBuilder implements I_imageGraphBuilder, Serializable{

	private static int nodeId = 0;
	private static int edgeId = 0;
	private BufferedImage img;
	private static ClickableImageGraphBuilder instance = null;


	public static ClickableImageGraphBuilder getInstance() {
		return instance;
	}


	public ClickableImageGraphBuilder(File image, int upscaleFactor) throws IOException{
		img = ImageIO.read(image);
		img =ImageParsing.upScaleImage(upscaleFactor, img);
		instance = this;
	}


	public ClickableImageGraphBuilder(String path) throws IOException{
		FileInputStream file = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(file);

        try {
        	nodes = (Map<Integer, Node>) in.readObject();
        	edges = (Map<Integer, Edge>) in.readObject();
        	img = ImageIO.read(in);
			nodeId = nodes.size();
			edgeId = edges.size();

			for(int i = 0; i < nodes.size(); i++) {
				nodes.get(i).setDrawColor(Color.BLACK);
			}

			System.out.println("Informações carregadas com sucesso!");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        in.close();
        file.close();
        instance = this;
	}


	public void buildNodes(int pieces, int mouse_x, int mouse_y) {
		Node nNode = new Node(nodeId, mouse_x-pieces/2, mouse_y-pieces/2, "black", Color.BLACK);
		nodes.put(nodeId, nNode);
		nodeId++;
	}
	
	

	public void buildEdges(int pieces, Node nodeA, Node nodeB) {
		nodeB.setCost(1);
		Edge nEdge = new Edge(edgeId, nodeA, nodeB, true, 0);
		nEdge.setDrawColor(Color.BLACK);
		nEdge.setWeight(1);
		edges.put(edgeId, nEdge);
		edgeId++;		
	}
	
	
	public HashMap<Integer, Node> getNodesHashTable() {
		return (HashMap<Integer, Node>) nodes;
	}
	
	
	public HashMap<Integer, Edge> getEdgesHashTable() {
		return (HashMap<Integer, Edge>) edges;
	}



	public BufferedImage getImg() {
		return this.img;
	}


	public void saveToFile(String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(nodes);
			objectOut.writeObject(edges);
			ImageIO.write(img, "png", objectOut);
			objectOut.flush();
			objectOut.close();
			System.out.println("Informações foram salvas com Sucesso!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveRepresentationToFile(String path) {
		try {
			FileWriter nodesFW = new FileWriter(path + "\\nodes.txt");
			FileWriter edgesFW = new FileWriter(path + "\\edges.txt");

			writeFromHashmap(nodesFW, (HashMap) nodes);
			writeFromHashmap(edgesFW, (HashMap) edges);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Informações foram salvas com Sucesso!");
	}


	private void writeFromHashmap(FileWriter fw, HashMap hm) throws IOException {
		for(int i = 0; i < hm.size(); i++) {
			fw.write(hm.get(i).toString());
		}
		fw.close();
	}


}

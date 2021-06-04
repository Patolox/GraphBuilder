package builders;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import javax.imageio.ImageIO;

import interfaces.I_imageGraphBuilder;
import objects.Edge;
import objects.Node;
import utils.ImageParsing;

public class MazeGraphBuilder extends BaseBuilder implements I_imageGraphBuilder, Serializable{

	private BufferedImage img;
	private static MazeGraphBuilder instance = null;
	

	public static MazeGraphBuilder getInstance() {
		return instance;
	}

	
	public MazeGraphBuilder(File image, int upScaleFactor)	throws IOException{
		img = ImageIO.read(image);
		img =ImageParsing.upScaleImage(upScaleFactor, img);
		instance = this;
	}


	public void buildNodes(int pieces) {
		int nodeId = 0;
		double colorPercentage = 0;
		int imgfactorX = img.getWidth()%pieces;
		int imgfactorY = img.getHeight()%pieces;
		Color c;
		Color c2 = Color.LIGHT_GRAY;
		for(int x = 0; x < img.getWidth()-imgfactorX; x = x+pieces) {
			for(int y = 0; y < img.getHeight()-imgfactorY; y = y+pieces) {
				for(int i = 0; i < pieces; i++) {
					for(int j = 0; j < pieces; j++) {
						c = new Color(img.getRGB(x+i, y+j));
						if(!(c.getRGB() > c2.getRGB())) {
							colorPercentage++;
						}
					}
				}
				colorPercentage = (colorPercentage/(pieces*pieces))*100;
				if(colorPercentage <= 20) { // if gray color percentage <= 20, add node
					nodes.put(nodeId, new Node(nodeId, x, y, "black", Color.BLACK));
					nodeId++;
				}
				colorPercentage = 0;

			}
		}
	}
	
	//not the optimal way of doing this...
	public void buildEdges(int pieces) {
		int nodeOriginX;
		int nodeOriginY;
		int nodeDestinyX;
		int nodeDestinyY;
		int edgeId = 0;
		
		boolean nbor = false;
//		boolean left = false;
//		boolean right = false;
//		boolean upL = false;
//		boolean upR = false;
//		boolean down = false;
//		boolean downL = false;
//		boolean downR = false;
		
		Node origin = null;
		Node destiny = null;
		
//		Color c;
//		Color c2 = Color.LIGHT_GRAY;
		
		for(int id = 0; id < nodes.size(); id++) {
			origin = nodes.get(id);
			for(int idS = 0; idS < nodes.size(); idS++) {
				if(id == idS) {
					continue;
				}
				destiny = nodes.get(idS);
				nodeOriginX = origin.getX();
				nodeOriginY = origin.getY();
				nodeDestinyX = destiny.getX();
				nodeDestinyY = destiny.getY();
				
//				c = new Color(img.getRGB(nodeDestinyX, nodeDestinyY));
				
				if(nodeOriginX + pieces == nodeDestinyX && nodeOriginY == nodeDestinyY) {
						nbor = true;
//						right = true;
				}else if(nodeOriginX - pieces == nodeDestinyX && nodeOriginY == nodeDestinyY) {
						nbor = true;
//						left = true;
				}else if(nodeOriginY + pieces == nodeDestinyY && nodeOriginX == nodeDestinyX) {
						nbor = true;
//						down = true;
				}else if(nodeOriginY - pieces == nodeDestinyY && nodeOriginX + pieces == nodeDestinyX) {
						nbor = true;
//						upR = true;
				}else if(nodeOriginX + pieces == nodeDestinyX && nodeOriginY + pieces == nodeDestinyY) {
						nbor = true;
//						downR = true;
				}else if(nodeOriginX - pieces == nodeDestinyX && nodeOriginY - pieces == nodeDestinyY) {
						nbor = true;
//						upL = true;
				}else if(nodeOriginX - pieces == nodeDestinyX && nodeOriginY + pieces == nodeDestinyY) {
						nbor = true;
//						downL = true;
				}else {
					nbor = false;
				}
				// not checking for walls...
				if (nbor == true) {
//					if(!(c.getRGB() > c2.getRGB())) {
						Edge nEdge = new Edge(edgeId, nodes.get(id), nodes.get(idS), false, 0);
						nEdge.setDrawColor(Color.BLACK);
						edges.put(edgeId, nEdge);
						edgeId++;
//					}
				}
				
			}
		}
		
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


	public HashMap<Integer, Node> getNodesHashTable() {
		return (HashMap<Integer, Node>) nodes;
	}
	
	
	public HashMap<Integer, Edge> getEdgesHashTable() {
		return (HashMap<Integer, Edge>) edges;
	}
	
	
	// may be interesting for scalability...
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	
	public BufferedImage getImg() {
		return this.img;
	}


	@Override
	public void buildEdges(int piecesSize, Node selectedNode, Node n) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void buildNodes(int piecesSize, int mouse_x, int mouse_y) {
		// TODO Auto-generated method stub
		
	}

	
}

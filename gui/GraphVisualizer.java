package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;

import interfaces.I_imageGraphBuilder;
import objects.Edge;
import objects.Node;

public class GraphVisualizer extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private I_imageGraphBuilder gb;
	private int pieces;
	
	public GraphVisualizer(I_imageGraphBuilder gb, int pieces) {	
		image = gb.getImg();
		this.gb = gb;
		this.pieces = pieces;
		this.setSize(image.getWidth(), image.getHeight());
		setFocusable(true);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
//		drawLines(g);
		drawNodes(g);
		drawEdges(g);
	}

	
	private void drawNodes(Graphics g) {
		int centerFactor = (int) ((pieces/10)*2.5);
		if (pieces == 10) {
			centerFactor = 2;
		}
		for(Map.Entry<Integer, Node> n: gb.getNodesHashTable().entrySet()) {
			g.setColor(n.getValue().getDrawColor());
			g.fillOval(n.getValue().getX()+centerFactor, n.getValue().getY()+centerFactor, pieces/2, pieces/2);
		}
	}
	
	
	private void drawEdges(Graphics g) {
		for(Map.Entry<Integer, Edge> e: gb.getEdgesHashTable().entrySet()) {
			g.setColor(e.getValue().getDrawColor());
			g.drawLine(e.getValue().getOrigin().getX() + (pieces/2), e.getValue().getOrigin().getY() + (pieces/2), e.getValue().getDestiny().getX() + (pieces/2), e.getValue().getDestiny().getY() + (pieces/2));
//			g.drawLine(e.getValue().getOrigin().getX() + (pieces/2) - 1, e.getValue().getOrigin().getY() + (pieces/2), e.getValue().getDestiny().getX() + (pieces/2) - 1, e.getValue().getDestiny().getY() + (pieces/2));
//			g.drawLine(e.getValue().getOrigin().getX() + (pieces/2) - 2, e.getValue().getOrigin().getY() + (pieces/2), e.getValue().getDestiny().getX() + (pieces/2) - 2, e.getValue().getDestiny().getY() + (pieces/2));
//			g.drawLine(e.getValue().getOrigin().getX() + (pieces/2) - 3, e.getValue().getOrigin().getY() + (pieces/2), e.getValue().getDestiny().getX() + (pieces/2) - 3, e.getValue().getDestiny().getY() + (pieces/2));
		}
	}
	
	
	private void drawLines(Graphics g) {
		g.setColor(Color.black);
		
		for(int i = 0; i < image.getWidth(); i++) {
			g.drawLine(i*pieces, 0, i*pieces, image.getHeight());
		}
		for(int i = 0; i < image.getHeight(); i++) {
			g.drawLine(0, i*pieces, image.getWidth(), i*pieces);
		}
	}
	
}

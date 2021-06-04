package handlers;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import builders.BaseBuilder;
import gui.GraphVisualizer;
import interfaces.I_imageGraphBuilder;
import objects.Node;
import utils.GraphOperations;

public class InputHandler implements KeyListener, EventListener, MouseListener, MouseMotionListener{

	private GraphVisualizer gv;
	private Map<Integer, Node> nodes;
	private int piecesSize;
	private I_imageGraphBuilder gb;
	private Node selectedNode;
	private Node startNode;
	private Node endNode;
	private boolean djikstra;


	public InputHandler(JPanel jpanel, I_imageGraphBuilder gb, int piecesSize) {
		this.gv = (GraphVisualizer) jpanel;
		this.gb = gb;
		this.nodes = gb.getNodesHashTable();
		this.djikstra = false;
		this.piecesSize = piecesSize;
		jpanel.addKeyListener(this);
		jpanel.addMouseListener(this);
		jpanel.addMouseMotionListener(this);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == '1') { // set start node
			if(this.selectedNode != null) {
				this.startNode = this.selectedNode;
				this.startNode.setDrawColor(Color.MAGENTA);
				gv.repaint();
			}
		}else if(e.getKeyChar() == '2') { // set end node
			if(this.selectedNode != null) {
				this.endNode = this.selectedNode;
				this.endNode.setDrawColor(Color.orange);
				gv.repaint();
			}
		}else if(e.getKeyChar() == 'r') { // run BFS
			((BaseBuilder) gb).buildAdjacencyLists();
			if(djikstra == true) { // run Djikstra
				GraphOperations.DjikstraAlgorithm(startNode.getId(), endNode.getId(), gb.getNodesHashTable(), gv);
			}else {
				GraphOperations.BFS(startNode.getId(), endNode.getId(), gb.getNodesHashTable(), gv);
			}
			gv.repaint();
		}else if(e.getKeyChar() == 'q' || e.getKeyChar() == 'Q') { // Reset
			djikstra = false;
			Node n;
			if(e.getKeyChar() == 'Q') {
//				((BaseBuilder) gb).deleteAll();
//				this.nodes = gb.getNodesHashTable();
			}else {
				if(!nodes.isEmpty()) {
					for (int i = 0; i < nodes.size(); i++) {
						n = nodes.get(i);
						n.setVisited(false);
						n.setParent(null);
						n.setDrawColor(Color.black);
					}
				}
				gv.repaint();
			}
		}else if(e.getKeyChar() == 'd') {
			djikstra = true;
		}else if(e.getKeyChar() == 's') {
			String input = JOptionPane.showInputDialog("Diga o novo custo: ");
			if(input != null) {
				int newCost = Integer.parseInt(input);
				this.selectedNode.setCost(newCost);
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.print("X: " + e.getX());
//		System.out.println(" Y: " + e.getY());
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Node n;
		boolean check = false;
		int mouse_x = e.getX();
		int mouse_y = e.getY();
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			
			for (int i = 0; i < nodes.size(); i++) {
				n = nodes.get(i);
				int obj_x = n.getX();
				int obj_y = n.getY();
				
					
				if((mouse_x >= obj_x && mouse_x <= obj_x + piecesSize) && (mouse_y >= obj_y && mouse_y <= obj_y + piecesSize)) {
					Node aux = this.selectedNode;
					this.selectedNode = n;
					this.selectedNode.setDrawColor(Color.BLUE);
					check = true;
					if(aux != null && aux.getId() != this.selectedNode.getId()) {
						aux.setDrawColor(Color.BLACK);
					}
					break;
				}
			}

			if (!check) {
				gb.buildNodes(piecesSize, mouse_x, mouse_y);		
			}
			gv.repaint();
			break;
		case MouseEvent.BUTTON2:
			if(selectedNode != null) {
				selectedNode.setCost(selectedNode.getCost() + 1);
				System.out.println(selectedNode.getCost());
			}
			break;

		case MouseEvent.BUTTON3:
				if(selectedNode != null) {
					for (int i = 0; i < nodes.size(); i++) {
						n = nodes.get(i);
						int obj_x = n.getX();
						int obj_y = n.getY();
							
						if((mouse_x >= obj_x && mouse_x <= obj_x + piecesSize) && (mouse_y >= obj_y && mouse_y <= obj_y + piecesSize)) {
							gb.buildEdges(piecesSize, selectedNode, n);
							gv.repaint();
						}
				}
				break;
			}
		
		}
	}
}

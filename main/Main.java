package main;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import builders.ClickableImageGraphBuilder;
import builders.GoogleMapsBuilder;
import builders.MazeGraphBuilder;
import gui.GraphVisualizer;
import handlers.InputHandler;
import interfaces.I_imageGraphBuilder;

public class Main {

	private static GraphVisualizer gv = null;
	private static int pieces = 30; // pieces size, lower values means more pieces on GB, also means more memory!
	private static I_imageGraphBuilder imgb;
	private static InputHandler ih;
	private static int upScale = 1;
	private static final String aboutText = "Github:\n https://github.com/Patolox"
											+ "";
	private static final String commandsText = "Commands: \n\n"
										 + " Left Mouse Button - Select Node\n\n"
	 									 + " Right Mouse Button - Build edge between nodes\n\n"
	 									 + " 1 - Make the selected node the start node\n\n"
	 									 + " 2 - Make the selected node the end node\n\n"
	 									 + " r - Run Algorithm (BFS by default)\n\n"
	 									 + " d - Change algorithm to Djikstra\n\n"
	 									 + " s - Set cost to selected Node\n\n"
	 									 + " q - Reset\n\n";

	public static void main(String[] args) {
		JFrame jf = new JFrame("Grafos");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setSize(400, 400);
		MenuBar menuBar = new MenuBar();
		jf.setMenuBar(menuBar);

		{
			Menu subMenu = new Menu("New Graph");
			Menu menu = new Menu("File");
			Menu operations = new Menu("Operations");
			Menu help = new Menu("Help");
			
			menu.add(subMenu);
			{
				MenuItem item = new MenuItem("Commands");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						helpDialogs(jf, commandsText);
					}
				});
				help.add(item);
			}
			{
				MenuItem item = new MenuItem("About");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						helpDialogs(jf, aboutText);
					}
				});
				help.add(item);
			}
			{
				MenuItem item = new MenuItem("Set pieces size");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String value = JOptionPane.showInputDialog("What is the new value? PS: It cant be 0!");
						if (value != null) {
							int val = Integer.parseInt(value);
							if(val != 0) {
								pieces = val;
							}else {
								JOptionPane.showConfirmDialog(jf,"The value is not acceptable.");
							}
						}
					}
				});
				operations.add(item);
			}
			{
				MenuItem item = new MenuItem("Set upscale factor");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String value = JOptionPane.showInputDialog("What is the new value? PS: It cant be 0!");
						if (value != null) {
							int val = Integer.parseInt(value);
							if(val != 0) {
								upScale = val;
							}else {
								JOptionPane.showConfirmDialog(jf,"The value is not acceptable.");
							}
						}
					}
				});
				operations.add(item);
			}
			{
				MenuItem item = new MenuItem("Load from file");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							onLoad(jf);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				menu.add(item);
			}
			{
				MenuItem item = new MenuItem("Save to file");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							onSave(jf);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				menu.add(item);
			}
			{
				MenuItem item = new MenuItem("Save graph representation to file");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							onRepresentationSave(jf);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				menu.add(item);
			}
			{
				MenuItem item = new MenuItem("MazeGraphBuilder");
				MenuItem item2 = new MenuItem("ClickableImageGraphBuilder");
				MenuItem item3 = new MenuItem("TextGraphBuilder");
				item3.setEnabled(false);
				MenuItem item4 = new MenuItem("GoogleMapsBuilder");
				
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							onMazeGraph(jf);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
				item2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							onImageGraph(jf);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
				item3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						onTextGraph(jf);
					}
				});
				
				item4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						onGoogleGraph(jf);
					}
				});
				
				subMenu.add(item);
				subMenu.add(item2);
				subMenu.add(item3);
				subMenu.add(item4);
				
			}
			
			menuBar.add(menu);
			menuBar.add(operations);
			menuBar.add(help);
			
		}

		jf.setVisible(true);
	}

	private static void onSave(JFrame jf) throws IOException {
		FileDialog d = new FileDialog(jf, "Save file", FileDialog.SAVE);
		d.setVisible(true);
		String dir = d.getDirectory();
		String file = d.getFile();
		if(file != null && file.endsWith(".cigb")) {
			try {
				if(imgb instanceof ClickableImageGraphBuilder) {
					((ClickableImageGraphBuilder) imgb).getInstance().saveToFile(dir+file);
				}else if(imgb instanceof MazeGraphBuilder) {
					((MazeGraphBuilder) imgb).getInstance().saveToFile(dir+file);
				}else {
					JOptionPane.showConfirmDialog(jf, "Operação não suportada.", null, JOptionPane.DEFAULT_OPTION);
					return;
				}

				JOptionPane.showConfirmDialog(jf, "O arquivo foi salvo com sucesso!", null, JOptionPane.DEFAULT_OPTION);
			}catch(Exception ex) {
				System.out.println(dir);
				System.out.println(file);
				JOptionPane.showConfirmDialog(jf, "Não foi possível salvar", null, JOptionPane.DEFAULT_OPTION);
			}
		}else if(!file.endsWith(".cigb")){
			JOptionPane.showConfirmDialog(jf, "O arquivo deve conter a extensão .cigb", null, JOptionPane.DEFAULT_OPTION);
		}
	}


	private static void onRepresentationSave(JFrame jf) throws IOException {
		JFileChooser fchooser = new JFileChooser("Choose the folder to save the edges and nodes");
		fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int option = fchooser.showOpenDialog(jf);
		try {
			if(option == JFileChooser.APPROVE_OPTION){
				String dir = fchooser.getSelectedFile().getAbsolutePath();
				if(imgb instanceof ClickableImageGraphBuilder) {
					ClickableImageGraphBuilder.getInstance().saveRepresentationToFile(dir);
				}else if(imgb instanceof MazeGraphBuilder) {
					MazeGraphBuilder.getInstance().saveRepresentationToFile(dir);
				}else {
					JOptionPane.showConfirmDialog(jf, "Operação não suportada.", null, JOptionPane.DEFAULT_OPTION);
					return;
				}
				JOptionPane.showConfirmDialog(jf, "O arquivo foi salvo com sucesso!", null, JOptionPane.DEFAULT_OPTION);
			}
		}catch(Exception ex) {
			JOptionPane.showConfirmDialog(jf, "Não foi possível salvar", null, JOptionPane.DEFAULT_OPTION);
		}
	}


	private static void onLoad(JFrame jf) throws IOException {
		FileDialog d = new FileDialog(jf, "Select file", FileDialog.LOAD);
		d.setVisible(true);
		String dir = d.getDirectory();
		String file = d.getFile();
		File fp = new File(dir + file);
		try {
			if(fp.exists()) {
				ClickableImageGraphBuilder gb = new ClickableImageGraphBuilder(dir+file);
				imgb = gb;
				if(gv != null) {
					jf.remove(gv);
					jf.removeKeyListener(ih);
				}
				gv = new GraphVisualizer(gb, pieces);
				jf.repaint();
				ih = new InputHandler(gv, gb, pieces);
				jf.addKeyListener(ih);
				jf.setSize(gv.getWidth() + 17, gv.getHeight() + 45);
				jf.add(gv);
				jf.setLocationRelativeTo(null);
			}
		}catch(Exception e) {
			JOptionPane.showConfirmDialog(jf, "Não foi possível carregar o arquivo", null, JOptionPane.DEFAULT_OPTION);
		}
	}


	private static void onMazeGraph(JFrame jf) throws IOException {
		FileDialog d = new FileDialog(jf, "Select Image", FileDialog.LOAD);
		d.setVisible(true);
		String dir = d.getDirectory();
		String file = d.getFile();
		File fp = new File(dir + file);
		if(fp.exists()) {
			MazeGraphBuilder mgb;
			mgb = new MazeGraphBuilder(new File(dir + file), upScale);
			mgb.buildNodes(pieces);
			mgb.buildEdges(pieces);
			mgb.buildAdjacencyLists();
			imgb = mgb;
			if(gv != null) {
				jf.remove(gv);
				jf.removeKeyListener(ih);
			}
			gv = new GraphVisualizer(mgb, pieces);
			ih = new InputHandler(gv, mgb, pieces);
			jf.addKeyListener(ih);
			jf.setSize(gv.getWidth() + 17, gv.getHeight() + 70);
			jf.add(gv);
			jf.setLocationRelativeTo(null);	
			jf.repaint();
		
		}
	}
	
	
	private static void onImageGraph(JFrame jf) throws IOException {
		FileDialog d = new FileDialog(jf, "Select Image", FileDialog.LOAD);
		d.setVisible(true);
		String dir = d.getDirectory();
		String file = d.getFile();
		File fp = new File(dir + file);
		if(fp.exists()) {
			ClickableImageGraphBuilder gb = new ClickableImageGraphBuilder(new File(dir + file), upScale);
			imgb = gb;
			if(gv != null) {
				jf.remove(gv);
				jf.removeKeyListener(ih);
			}
			gv = new GraphVisualizer(gb, pieces);
			ih = new InputHandler(gv, gb, pieces);
			jf.addKeyListener(ih);
			jf.setSize(gv.getWidth() + 17, gv.getHeight() + 45);
			jf.add(gv);
			jf.setLocationRelativeTo(null);
		}
	}
	

	private static void onTextGraph(JFrame jf) {

	}


	private static void onGoogleGraph(JFrame jf) {
		FileDialog d = new FileDialog(jf, "Select Image", FileDialog.LOAD);
		d.setVisible(true);
		String dir = d.getDirectory();
		String file = d.getFile();
		File fp = new File(dir + file);
		if(fp.exists()) {
			try {
				GoogleMapsBuilder gb = new GoogleMapsBuilder(new File(dir+file), upScale);
				gb.buildNodes(pieces);
				gb.buildEdges(pieces);
				gb.buildAdjacencyLists();
				imgb = gb;
				if(gv != null) {
					jf.remove(gv);
				}
				gv = new GraphVisualizer(gb, pieces);
				jf.setSize(gv.getWidth() + 17, gv.getHeight() + 45);
				jf.add(gv);
				jf.setLocationRelativeTo(null);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private static void helpDialogs(JFrame jf, String text) {
		JDialog jd = new JDialog(jf, "About", Dialog.ModalityType.MODELESS);
		JTextArea doc = new JTextArea(text);
        doc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        doc.setEditable(false);
        doc.setLineWrap(true);
        jd.setSize(320, 350);
        jd.add(doc);
        jd.setLocationRelativeTo(null);
        
        jd.setVisible(true);
	}
}

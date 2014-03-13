package RocketUML.ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Menu extends JFrame implements ActionListener {
		
	JMenuBar menuBar = new JMenuBar();
	
  	JMenuItem   newMenuItem, openMenuItem, saveMenuItem, undoMenuItem,
  				redoMenuItem, aboutMenuItem, addClassItem, addRelationshipItem,
  				clearScreenItem;
  
  	private Main gui;
    
  	public Menu(Main in)
	{
		gui = in; 
		//gui.frame;
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenu optionMenu = new JMenu("Option");
        menuBar.add(optionMenu);

        newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(this);
        fileMenu.add(newMenuItem);
        
        openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);
        
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);
        
        undoMenuItem = new JMenuItem("Undo");
        //menuItem.addActionListener(new clearScreen());
        editMenu.add(undoMenuItem);
        
        redoMenuItem = new JMenuItem("Redo");
        //menuItem.addActionListener(new openFile());
        editMenu.add(redoMenuItem);

        aboutMenuItem = new JMenuItem("About GED");
        aboutMenuItem.addActionListener(this);
        editMenu.add(aboutMenuItem);
        
        addClassItem = new JMenuItem("Add Class");
        addClassItem.addActionListener(this);
        optionMenu.add(addClassItem);
        
        addRelationshipItem = new JMenuItem("Add Relationship");
        addRelationshipItem.addActionListener(this);
        optionMenu.add(addRelationshipItem);
        
        clearScreenItem = new JMenuItem("Clear");
        clearScreenItem.addActionListener(this);
        optionMenu.add(clearScreenItem);
        
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == newMenuItem)
		{
			gui.frame.getContentPane().removeAll();
			//gui.frame.revalidate();
			gui.frame.repaint();
		}
		else if(e.getSource() == openMenuItem)
		{
			JFileChooser openChooser = new JFileChooser();
			int val = openChooser.showOpenDialog(Menu.this);
			if(val == JFileChooser.APPROVE_OPTION)
			{
				File file = openChooser.getSelectedFile();
			}	
		}
		else if (e.getSource() == saveMenuItem)
		{
			JFileChooser openChooser = new JFileChooser();
			int val = openChooser.showSaveDialog(Menu.this);	
		}
		else if (e.getSource() == addClassItem)
		{
			Graphics g = gui.frame.getGraphics();
			String class_x_coord = JOptionPane.showInputDialog("Please Enter X coordinate for class");
			if(class_x_coord != null)
			{
				String class_y_coord = JOptionPane.showInputDialog("Please Enter Y coordinate for class");
				String className = JOptionPane.showInputDialog("Please Enter Name of Class");
				
				int x = Integer.parseInt(class_x_coord);
				int y = Integer.parseInt(class_y_coord);
	
				Element classElement = Flyweight.getElement("Class");
				classElement.Draw(g, x,y,100,100,className);
			}
		}
		else if (e.getSource() == addRelationshipItem)
		{
			Graphics g = gui.frame.getGraphics();
			String relationship_x_coord = JOptionPane.showInputDialog("Please Enter X coordinate for class");
			if(relationship_x_coord != null)
			{
				String relationship_y_coord = JOptionPane.showInputDialog("Please Enter Y coordinate for class");
				String className = JOptionPane.showInputDialog("Please Enter Name of Class");
				
				int x = Integer.parseInt(relationship_x_coord);
				int y = Integer.parseInt(relationship_y_coord);
	
				Element relationshipElement = Flyweight.getElement("Relationship");
				relationshipElement.Draw(g, x, y, 100, 2, null);
			}
		}
		else if (e.getSource() == aboutMenuItem)
		{
			JFrame myFrame = new JFrame("Help");
			JOptionPane.showMessageDialog(myFrame, "Graphical UML Editor - 2014 \n             Sean Crane\n             Richard Dutt\n             Jonathan Eason\n             Luke Scheffler\n             Dave Weber");
			myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			myFrame.pack();
		}
		else if (e.getSource() == clearScreenItem)
		{
			gui.frame.getContentPane().removeAll();
		//	gui.frame.revalidate();
			gui.frame.repaint();
		}
	}
}
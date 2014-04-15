package RocketUML.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
		
	JMenuBar menuBar = new JMenuBar();
	
  	JMenuItem   newMenuItem, openMenuItem, saveMenuItem, undoMenuItem,
  				redoMenuItem, aboutMenuItem, addClassItem, addRelationshipItem,
  				clearScreenItem, exitProgramItem;
  
  	private Main gui;
    private String pathName;

    ModelViewController controller = ModelViewController.getInstance();
	
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

        exitProgramItem = new JMenuItem("Exit");
        exitProgramItem.addActionListener(this);
        fileMenu.add(exitProgramItem);
        
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
			gui.getContentPane().removeAll();
			gui.repaint();
		}
		else if(e.getSource() == openMenuItem)
		{
			JFileChooser openChooser = new JFileChooser();
			int val = openChooser.showOpenDialog(Menu.this);
			if(val == JFileChooser.APPROVE_OPTION)
			{
				pathName = openChooser.getSelectedFile().getPath();
                controller.deserializeElements(pathName);
                gui.loadDiagrams();
                gui.repaint();
			}
		}
		else if (e.getSource() == saveMenuItem)
		{
			JFileChooser openChooser = new JFileChooser();
			int val = openChooser.showSaveDialog(Menu.this);	
			if(val == JFileChooser.APPROVE_OPTION)
			{
				pathName = openChooser.getSelectedFile().getPath();
                controller.serializeElements(pathName);
			}
		}
		else if (e.getSource() == addClassItem)
		{
			Graphics g = gui.getGraphics();
			String class_x_coord = JOptionPane.showInputDialog("Please Enter X coordinate for class");
			if(class_x_coord != null)
			{
				String class_y_coord = JOptionPane.showInputDialog("Please Enter Y coordinate for class");
				String className = JOptionPane.showInputDialog("Please Enter Name of Class");
				
				int x = Integer.parseInt(class_x_coord);
				int y = Integer.parseInt(class_y_coord);
	
				Element classElement = AbstractFactory.getElement("Class");
				classElement.init(x,y,className, "Class");
			}
		}
        else if (e.getSource() == exitProgramItem)
        {
            System.exit(0);
        }
		else if (e.getSource() == addRelationshipItem)
		{
			Graphics g = gui.getGraphics();
			String relationship_x_coord = JOptionPane.showInputDialog("Please Enter X coordinate for class");
			if(relationship_x_coord != null)
			{
				String relationship_y_coord = JOptionPane.showInputDialog("Please Enter Y coordinate for class");
				String className = JOptionPane.showInputDialog("Please Enter Name of Class");
				
				int x = Integer.parseInt(relationship_x_coord);
				int y = Integer.parseInt(relationship_y_coord);
	
				Element relationshipElement = AbstractFactory.getElement("Relationship");
				relationshipElement.init(x, y, null, "Relationship");
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
			gui.getContentPane().removeAll();
			gui.repaint();
		}
	}
}

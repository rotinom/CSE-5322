package RocketUML.ui;

import RocketUML.model.ElementCreator;
import RocketUML.model.ProjectElement;
import RocketUML.visitor.CodeGenerationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
		
	JMenuBar menuBar = new JMenuBar();
	
  	JMenuItem   newMenuItem, openMenuItem, saveMenuItem, undoMenuItem,
  				redoMenuItem, aboutMenuItem, addClassItem, addRelationshipItem,
  				clearScreenItem, exitProgramItem, generateCodeItem, generateCppItem, generateJavaItem;
  
  	private Main gui;
    private String pathName;

    ProjectElement projectElement = ProjectElement.getInstance();
    ModelViewController controller = ModelViewController.getInstance();
	
  	public Menu(Main in)
	{
		gui = in; 
		//gui.frame;
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        JMenu optionMenu = new JMenu("Option");
        menuBar.add(optionMenu);

        // Right-align help
        menuBar.add(Box.createHorizontalGlue());
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);


        JMenu generateMenu = new JMenu("Generate");

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
        editMenu.add(undoMenuItem);
        
        redoMenuItem = new JMenuItem("Redo");
        editMenu.add(redoMenuItem);

        aboutMenuItem = new JMenuItem("About GED");
        aboutMenuItem.addActionListener(this);
        helpMenu.add(aboutMenuItem);
        
        addClassItem = new JMenuItem("Add Class");
        addClassItem.addActionListener(this);
        optionMenu.add(addClassItem);
        
        addRelationshipItem = new JMenuItem("Add Relationship");
        addRelationshipItem.addActionListener(this);
        optionMenu.add(addRelationshipItem);
        
        clearScreenItem = new JMenuItem("Clear");
        clearScreenItem.addActionListener(this);
        optionMenu.add(clearScreenItem);
        optionMenu.add(generateMenu);

        generateCppItem = new JMenuItem("C++");
        generateCppItem.addActionListener(this);
        generateMenu.add(generateCppItem);

        generateJavaItem = new JMenuItem("Java");
        generateJavaItem.addActionListener(this);
        generateMenu.add(generateJavaItem);
        
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == newMenuItem)
		{
            projectElement.resetProject();
            gui.loadDiagrams();
            controller.resetController();
			gui.repaint();
		}
		else if(e.getSource() == openMenuItem)
		{
			JFileChooser openChooser = new JFileChooser();
			int val = openChooser.showOpenDialog(Menu.this);
			if(val == JFileChooser.APPROVE_OPTION)
			{
				pathName = openChooser.getSelectedFile().getPath();
                Serialization.getInstance().deserialize(pathName);
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
                Serialization.getInstance().serialize(pathName);
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

                ElementCreator.getElement("Class", x, y);
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

                ElementCreator.getElement("Relationship", x, y);
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
        else if (e.getSource() == generateCppItem)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int val = chooser.showSaveDialog(Menu.this);
            if(val == JFileChooser.APPROVE_OPTION)
            {
                pathName = chooser.getSelectedFile().getPath();
                CodeGenerationController cgc = CodeGenerationController.create();
                cgc.generateCppCode(ProjectElement.getInstance(), pathName);
            }
        }
        else if (e.getSource() == generateJavaItem)
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int val = chooser.showSaveDialog(Menu.this);
            if(val == JFileChooser.APPROVE_OPTION)
            {
                pathName = chooser.getSelectedFile().getPath();
                CodeGenerationController cgc = CodeGenerationController.create();
                cgc.generateJavaCode(ProjectElement.getInstance(), pathName);
            }
        }
	}
}

package ged;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	protected JFrame frame = new JFrame("Rocket UML");
	protected JPanel theContentPane = new JPanel();
	
	private Main()
	{
		init();
	}

	private void init() {
		
		Menu gMenu = new Menu(this);	
		Toolbar Tool = new Toolbar();
		
        //Create and set up the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth()/2);
        int height = (int) (screenSize.getHeight()/2);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               
        Tool.setSize(360,250);
        frame.add(Tool.panel);
        frame.setJMenuBar(gMenu.menuBar);

        frame.setLocation(width/2, height/2);
        
        //  frame.pack();
        frame.setVisible(true);
        
    }
	
	public static void main(String[] args) {
	      Main window = new Main();
	}
}

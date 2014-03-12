package RocketUML.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class Main extends JFrame {
	
	protected JFrame frame = new JFrame("Rocket UML");
	protected JPanel theContentPane = new JPanel();
    protected JPopupMenu popup;

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

        popup = new JPopupMenu();
        // add menu items to popup
        popup.add(new JMenuItem("Add Class"));
        popup.add(new JMenuItem("Add Relationship"));
        popup.addSeparator();
        popup.add(new JMenuItem("Clear All"));
        System.out.println("test one two");
        //Add listener to components that can bring up popup menus.
        frame.addMouseListener(new MouseListener());
    }
	
	public static void main(String[] args) {
	      Main window = new Main();
	}


    class MouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            handleMousePress(e);
        }

        public void mouseReleased(MouseEvent e) {
            handleMousePress(e);
        }

        private void handleMousePress(MouseEvent e) {

            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                        e.getX(), e.getY());
            }
        }
    }

}

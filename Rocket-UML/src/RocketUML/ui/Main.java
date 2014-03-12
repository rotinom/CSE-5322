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

        //Add mouse listener
        final MouseListener mouseHandler = new MouseListener();
        frame.addMouseListener(mouseHandler);

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
        JMenuItem menuItem = new JMenuItem("Add Class");
        popup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Element classElement = Flyweight.getElement("Class");
                classElement.Draw(frame.getGraphics(), mouseHandler.getX(), mouseHandler.getY(),100,100,"New Class");
            }
        });

        popup.add(new JMenuItem("Add Relationship"));
        popup.addSeparator();
        popup.add(new JMenuItem("Clear All"));
    }
	
	public static void main(String[] args) {
	      Main window = new Main();
	}


    class MouseListener extends MouseAdapter {
        int x;
        int y;

        public int getX(){return x;}
        public int getY(){return y;}

        public void mousePressed(MouseEvent e) {
            handleMousePress(e);
        }

        public void mouseReleased(MouseEvent e) {
            handleMousePress(e);
        }

        public void mouseDragged(MouseEvent e) {
            System.out.println("mouseDragged");
        }

        private void handleMousePress(MouseEvent e) {

            if (e.isPopupTrigger()) {
                x = e.getX();
                y = e.getY();
                popup.show(e.getComponent(),x,y);
            }
        }

    }

}

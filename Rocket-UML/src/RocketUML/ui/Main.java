package RocketUML.ui;

import java.awt.*;
import javax.swing.*;


public class Main extends JFrame {
    private JPanel workSpace;

    public Main() {
   		Menu gMenu = new Menu(this);
        //hide toolbar for now
		//Toolbar Tool = new Toolbar();
        //getContentPane().add(Tool.panel, BorderLayout.WEST);

        //Create and set up the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth()/2);
        int height = (int) (screenSize.getHeight()/2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(gMenu.menuBar);
        setLocation(width/2, height/2);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);

        workSpace = new ModelView();
        getContentPane().add(workSpace, BorderLayout.CENTER);
        pack();
    }


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
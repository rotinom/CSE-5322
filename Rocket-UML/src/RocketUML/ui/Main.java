package RocketUML.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


public class Main extends JFrame {
    int counter = 0;
    private JPanel workSpace;
    protected JPopupMenu popup;
    protected JPopupMenu classPopup;

    public ArrayList<Element> elements = new ArrayList<Element>();
    private int mouseX=0;
    private int mouseY=0;
    private int xOffset = 0;
    private int yOffset = 0;

    public Main() {
   		Menu gMenu = new Menu(this);
		Toolbar Tool = new Toolbar();

        //Create and set up the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth()/2);
        int height = (int) (screenSize.getHeight()/2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(Tool.panel, BorderLayout.WEST);
        setJMenuBar(gMenu.menuBar);
        setLocation(width/2, height/2);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);

        workSpace = new  WorkSpace();
        getContentPane().add(workSpace, BorderLayout.CENTER);
        pack();
    }

    class WorkSpace extends JPanel implements MouseMotionListener,MouseListener {

        private Element selectedElement=null;

        public WorkSpace(){
            super();
            addMouseListener(this);
            addMouseMotionListener(this);

            popup = new JPopupMenu();
            // add menu items to popup
            JMenuItem menuItem = new JMenuItem("Add Class");
            popup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Element classElement = Flyweight.getElement("Class");
                    classElement.init(mouseX, mouseY,"New Class "+counter++);
                    elements.add(classElement);
                    repaint();
                }
            });
            popup.add(new JMenuItem("Add Relationship"));
            popup.addSeparator();
            popup.add(new JMenuItem("Clear All"));

            classPopup = new JPopupMenu();
            // add menu items to popup


            menuItem = new JMenuItem("Add Attribute");
            classPopup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ((Class)selectedElement).addAttribute("void newAttribute");
                    repaint();
                }
            });

            menuItem = new JMenuItem("Add Method");
            classPopup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ((Class)selectedElement).addMethod("void newMethod()");
                    repaint();
                }
            });

            classPopup.addSeparator();
            menuItem = new JMenuItem("Remove Class");
            classPopup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    remove(selectedElement);
                    elements.remove(selectedElement);
                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Element s : elements){
                s.draw(g);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedElement!=null){
                if(selectedElement instanceof Element){
                    // Move the shape center to the mouse location
                    int width = selectedElement.getWidth();
                    int height = selectedElement.getHeight();

                    //move object to new location minus the previously recorded offset
                    selectedElement.setLocation((int)(e.getX()-xOffset), (int)(e.getY()-yOffset));
                }
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        private Element getSelectedElement(Point p){
            Element selectedElement = null;
            for (Element testShape : elements){
                if (testShape.contains(p)){
                    selectedElement = testShape;

                    //save off offset between shape and mouse
                    xOffset = mouseX - selectedElement.getX();
                    yOffset = mouseY - selectedElement.getY();
                    break;
                }
            }
            return selectedElement;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            selectedElement = getSelectedElement(e.getPoint());

            //for mac isPopupTrigger works on mouse pressed
            if (e.isPopupTrigger()) {
                if(selectedElement == null)
                    popup.show(e.getComponent(), mouseX, mouseY);
                else
                    classPopup.show(e.getComponent(), mouseX, mouseY);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            //for windows isPopupTrigger works on mouse pressed
            if (e.isPopupTrigger()) {
                if(selectedElement == null)
                    popup.show(e.getComponent(), mouseX, mouseY);
                else
                    classPopup.show(e.getComponent(), mouseX, mouseY);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
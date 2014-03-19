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
    protected JPopupMenu relationPopup;

    public ArrayList<Element> elements = new ArrayList<Element>();
    private int mouseX=0;
    private int mouseY=0;
    private int xOffset = 0;
    private int yOffset = 0;

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

        workSpace = new  WorkSpace();
        getContentPane().add(workSpace, BorderLayout.CENTER);
        pack();
    }

    class WorkSpace extends JPanel implements MouseMotionListener,MouseListener,KeyListener {
        private Element selectedElement=null;

        public WorkSpace(){
            super();
            addMouseListener(this);
            addMouseMotionListener(this);
            addKeyListener(this);
            createPopup();
            createClassPopup();
            createRelationPopup();
        }

        public void createPopup()
        {
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

            JMenu submenu = new JMenu("Add Relationship");
            JMenuItem menuAssc = new JMenuItem("Association");
            JMenuItem menuAggr = new JMenuItem("Aggregation");
            JMenuItem menuInhr = new JMenuItem("Inheritance");
            submenu.add(menuAssc);
            submenu.add(menuAggr);
            submenu.add(menuInhr);
            popup.add(submenu);

            menuAssc.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Element relationshipElement = Flyweight.getElement("Relationship");
                    relationshipElement.init(mouseX, mouseY, "New Relationship " + counter++);
                    ((Relationship) relationshipElement).setType(Relationship.Type.ASSOCIATION);
                    elements.add(relationshipElement);
                    repaint();
                }
            });
            menuAggr.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Element relationshipElement = Flyweight.getElement("Relationship");
                    relationshipElement.init(mouseX, mouseY, "New Relationship " + counter++);
                    ((Relationship) relationshipElement).setType(Relationship.Type.AGGREGATION);
                    elements.add(relationshipElement);
                    repaint();
                }
            });
            menuInhr.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Element relationshipElement = Flyweight.getElement("Relationship");
                    relationshipElement.init(mouseX, mouseY, "New Relationship " + counter++);
                    ((Relationship) relationshipElement).setType(Relationship.Type.INHERITANCE);
                    elements.add(relationshipElement);
                    repaint();
                }
            });
            popup.addSeparator();
            popup.add(new JMenuItem("Clear All"));
        }

        public void createClassPopup()
        {
            classPopup = new JPopupMenu();

            // add menu items to popup
            JMenuItem menuItem = new JMenuItem("Add Attribute");
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

            menuItem = new JMenuItem("Remove Selected Attribute/Method");
            classPopup.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (((Class) selectedElement).isPointInAttributeArea(mouseX, mouseY)) {
                        ((Class) selectedElement).removeAttribute(selectedElement.getStringAtLocation(mouseX, mouseY));
                    } else if (((Class) selectedElement).isPointInMethodArea(mouseX, mouseY)) {
                        ((Class) selectedElement).removeMethod(selectedElement.getStringAtLocation(mouseX, mouseY));
                    }
                   repaint();
                }
            });

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

        public void createRelationPopup()
        {
            relationPopup = new JPopupMenu();

            JMenu submenu = new JMenu("Change Relationship");
            JMenuItem menuAssc = new JMenuItem("Association");
            JMenuItem menuAggr = new JMenuItem("Aggregation");
            JMenuItem menuInhr = new JMenuItem("Inheritance");
            submenu.add(menuAssc);
            submenu.add(menuAggr);
            submenu.add(menuInhr);
            relationPopup.add(submenu);

            menuAssc.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Relationship relationship = (Relationship)selectedElement;
                    relationship.setType(Relationship.Type.ASSOCIATION);
                    repaint();
                }
            });
            menuAggr.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Relationship relationship = (Relationship) selectedElement;
                    relationship.setType(Relationship.Type.AGGREGATION);
                    repaint();
                }
            });
            menuInhr.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Relationship relationship = (Relationship)selectedElement;
                    relationship.setType(Relationship.Type.INHERITANCE);
                    repaint();
                }
            });

            relationPopup.addSeparator();
            JMenuItem menuItem = new JMenuItem("Remove Relationship");
            relationPopup.add(menuItem);
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

        ////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// INPUT HANDLERS //////////////////////////////
        ////////////////////////////////////////////////////////////////////////////
        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedElement!=null){
                // Move the shape center to the mouse location
                int width = selectedElement.getWidth();
                int height = selectedElement.getHeight();

                //move object to new location minus the previously recorded offset
                if(selectedElement.getClass() == Class.class)
                    selectedElement.setLocation((int)(e.getX()-xOffset), (int)(e.getY()-yOffset));
                else //relationship
                {
                    if(!((Relationship)selectedElement).getIsDragText()) {
                        for (Element element : elements){
                            if(element.getClass() == Class.class){
                                ((Class)element).drawConnectPoints(((Class)element).closeTo(e.getPoint()));
                                ((Class)element).setRelationshipDragPoint(((Relationship)selectedElement).getDragPoint());
                            }
                        }
                    }
                    selectedElement.setLocation((int) (e.getX()), (int) (e.getY()));
                }
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        private Element getSelectedElement(Point p){
            if(selectedElement != null)
                selectedElement.setSelected(false);
            Element selectedElement = null;
            for (Element testElement : elements){
                if (testElement.contains(p)){
                    selectedElement = testElement;
                    //save off offset between shape and mouse
                    xOffset = mouseX - selectedElement.getX();
                    yOffset = mouseY - selectedElement.getY();
                    selectedElement.setSelected(true);
                    repaint();
                    break;
                }
            }
            return selectedElement;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            handleMouseClick(e); //for mac isPopupTrigger works on mouse pressed
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getClickCount() != 2) //don't handle double click
                handleMouseClick(e); //for win isPopupTrigger works on mouse released
        }

        //handle mouse click for both Mac/Windows
        public void handleMouseClick(MouseEvent e ){
            mouseX = e.getX();
            mouseY = e.getY();
            selectedElement = getSelectedElement(e.getPoint());

            if (e.getClickCount() == 2 && selectedElement != null){
                selectedElement.editString(mouseX, mouseY);
                repaint();
            }
            else if (e.isPopupTrigger()) {
                if(selectedElement == null){
                    popup.show(e.getComponent(), mouseX, mouseY);
                }
                else if(selectedElement.getClass() == Class.class){
                    classPopup.show(e.getComponent(), mouseX, mouseY);
                }
                else if(selectedElement.getClass() == Relationship.class){
                    relationPopup.show(e.getComponent(), mouseX, mouseY);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}

        public void addNotify() {
            super.addNotify();
            requestFocus();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            handleKeyEvent(e);
        }
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {}

        public void handleKeyEvent(KeyEvent e){
            int c = e.getKeyCode ();
            if (c==KeyEvent.VK_UP) {
                selectedElement.setLocation(selectedElement.getX(), selectedElement.getY()-1);
            } else if(c==KeyEvent.VK_DOWN) {
                selectedElement.setLocation(selectedElement.getX(), selectedElement.getY()+1);
            } else if(c==KeyEvent.VK_LEFT) {
                selectedElement.setLocation(selectedElement.getX()-1, selectedElement.getY());
            } else if(c==KeyEvent.VK_RIGHT) {
                selectedElement.setLocation(selectedElement.getX()+1, selectedElement.getY());
            }
            repaint();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
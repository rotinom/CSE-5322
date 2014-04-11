package RocketUML.ui;

import RocketUML.model.AttributeElement;
import RocketUML.model.MethodElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class ModelView extends JPanel implements MouseMotionListener,MouseListener,KeyListener {
    protected JPopupMenu popup;
    protected JPopupMenu classPopup;
    protected JPopupMenu relationPopup;

    private int mouseX=0;
    private int mouseY=0;

    public ArrayList<MethodElement> methods = new ArrayList<MethodElement>();
    public ArrayList<AttributeElement> attributes = new ArrayList<AttributeElement>();

    ModelViewController controller;

    public ModelView(){
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        createPopup();
        createClassPopup();
        createRelationPopup();
        controller = ModelViewController.getInstance();
    }

    public void createPopup()
    {
        popup = new JPopupMenu();

        // add menu items to popup
        JMenuItem menuItem = new JMenuItem("Add Class");
        popup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createElement("", "Class", mouseX, mouseY);
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
                controller.createElement("", "Relationship", mouseX, mouseY);
                controller.changeRelationshipType(Relationship.Type.ASSOCIATION);
                repaint();
            }
        });
        menuAggr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createElement("", "Relationship", mouseX, mouseY);
                controller.changeRelationshipType(Relationship.Type.AGGREGATION);
                repaint();
            }
        });
        menuInhr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createElement("", "Relationship", mouseX, mouseY);
                controller.changeRelationshipType(Relationship.Type.INHERITANCE);
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
                controller.addClassAttribute("void newAttribute");
                repaint();
            }
        });

        menuItem = new JMenuItem("Add Method");
        classPopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.addClassMethod("void newMethod()");
                repaint();
            }
        });

        classPopup.addSeparator();

        menuItem = new JMenuItem("Remove Selected Attribute/Method");
        classPopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(controller.isPointInAttributeArea(mouseX, mouseY)){
                    controller.removeAttribute(mouseX, mouseY);
                }
                else if(controller.isPointInMethodArea(mouseX, mouseY)){
                    controller.removeMethod(mouseX, mouseY);
                }
                repaint();
            }
        });

        menuItem = new JMenuItem("Remove Class");
        classPopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.removeElement();
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
                controller.changeRelationshipType(Relationship.Type.ASSOCIATION);
                repaint();
            }
        });
        menuAggr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeRelationshipType(Relationship.Type.AGGREGATION);
                repaint();
            }
        });
        menuInhr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeRelationshipType(Relationship.Type.INHERITANCE);
                repaint();
            }
        });

        relationPopup.addSeparator();
        JMenuItem menuItem = new JMenuItem("Remove Relationship");
        relationPopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.removeElement();
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        controller.drawElement(g);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////// INPUT HANDLERS //////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void mouseDragged(MouseEvent e) {
        if (controller.isElementSelected()){
            controller.setLocation((int)(e.getX()), (int)(e.getY()));
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}



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
        controller.setSelectedElement(mouseX, mouseY);

        if (e.getClickCount() == 2 && controller.isElementSelected()){
            controller.editString(mouseX, mouseY);
            repaint();
        }
        else if (e.isPopupTrigger()) {
            if(!controller.isElementSelected()){
                popup.show(e.getComponent(), mouseX, mouseY);
            }
            else if(controller.isClassSelected()){
                classPopup.show(e.getComponent(), mouseX, mouseY);
            }
            else if(controller.isRelationshipSelected()){
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
            controller.setLocation(controller.getElementX(), controller.getElementY()-1);
        } else if(c==KeyEvent.VK_DOWN) {
            controller.setLocation(controller.getElementX(), controller.getElementY()+1);
        } else if(c==KeyEvent.VK_LEFT) {
            controller.setLocation(controller.getElementX()-1, controller.getElementY());
        } else if(c==KeyEvent.VK_RIGHT) {
            controller.setLocation(controller.getElementX()+1, controller.getElementY());
        }
        repaint();
    }

    public void drawElementsAfterOpen(ArrayList<Element> loadElements)
    {
        Element element = new Element();

        for (int i = 0; i < loadElements.size(); i++)
        {
            element = loadElements.get(i);
            attributes = ((Class)element).getAttributes();
            methods = ((Class)element).getMethods();

            controller.createElement(element.name, element.elementType, element.x, element.y);            

            for (int j = 0; j < attributes.size(); j++)
            {
                System.out.printf("Attribute Name = " + attributes.get(j).getString() + "%n");
                controller.addClassAttribute(attributes.get(j).getString());
            }

            for (int j = 0; j < methods.size(); j++)
            {
                System.out.printf("Method Name = " + methods.get(j).getString() + "%n");
                controller.addClassMethod(methods.get(j).getString());
            }
        }

        validate();
        revalidate();
        repaint();

        this.validate();
        this.revalidate();
        this.repaint();
    }
}


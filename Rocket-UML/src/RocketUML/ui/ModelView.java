package RocketUML.ui;

import RocketUML.model.AttributeElement;
import RocketUML.model.MethodElement;
import RocketUML.model.ProtectionEnum;
import RocketUML.model.RelationshipElement;

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
    private String name;

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
                controller.changeRelationshipType(RelationshipElement.Type.ASSOCIATION);
                repaint();
            }
        });
        menuAggr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createElement("", "Relationship", mouseX, mouseY);
                controller.changeRelationshipType(RelationshipElement.Type.AGGREGATION);
                repaint();
            }
        });
        menuInhr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.createElement("", "Relationship", mouseX, mouseY);
                controller.changeRelationshipType(RelationshipElement.Type.INHERITANCE);
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

        classPopup.addSeparator();

        JMenu subProtMenu = new JMenu("Change Protection");
        JMenuItem menuPub = new JMenuItem("Public");
        JMenuItem menuProt = new JMenuItem("Protected");
        JMenuItem menuPriv = new JMenuItem("Private");
        subProtMenu.add(menuPub);
        subProtMenu.add(menuProt);
        subProtMenu.add(menuPriv);
        classPopup.add(subProtMenu);

        menuPub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeProtection(mouseX, mouseY, ProtectionEnum.PUBLIC);
                repaint();
            }
        });

        menuProt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeProtection(mouseX, mouseY, ProtectionEnum.PROTECTED);
                repaint();
            }
        });

        menuPriv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeProtection(mouseX, mouseY, ProtectionEnum.PRIVATE);
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
                controller.changeRelationshipType(RelationshipElement.Type.ASSOCIATION);
                repaint();
            }
        });
        menuAggr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeRelationshipType(RelationshipElement.Type.AGGREGATION);
                repaint();
            }
        });
        menuInhr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.changeRelationshipType(RelationshipElement.Type.INHERITANCE);
                repaint();
            }
        });

        JMenuItem menuItem = new JMenuItem("Change Source Multiplicity");
        relationPopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String string = JOptionPane.showInputDialog("Please enter new source multiplicity:");
                controller.setRelationSrcMulti(string);
                repaint();
            }
        });

        menuItem = new JMenuItem("Change Destination Multiplicity");
        relationPopup.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String string = JOptionPane.showInputDialog("Please enter new destination multiplicity:");
                controller.setRelationDestMulti(string);
                repaint();
            }
        });

        relationPopup.addSeparator();
        menuItem = new JMenuItem("Remove Relationship");
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
}


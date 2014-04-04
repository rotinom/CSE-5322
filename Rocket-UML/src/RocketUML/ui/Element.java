package RocketUML.ui;

import javax.swing.*;
import java.awt.*;

public class Element extends JPanel {

    protected String name = "";
    protected String elementType = "";
    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;
    protected boolean isSelected = false;

    public void init(int xLoc, int yLoc, String n, String type){
        x = xLoc;
        y = yLoc;
        name = n;
        elementType = type;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics graphics){
        //meant to be overridden
    }

    public void setLocation(int xLoc, int yLoc){
        x = xLoc;
        y = yLoc;
    }

    public boolean contains(Point p){
        //meant to be overridden
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return elementType;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public void editString(int xLoc, int yLoc){
        String string = JOptionPane.showInputDialog("Please enter new text for: "+getStringAtLocation(xLoc,yLoc));
        if(string == null){string = "";}
        setEditedString(xLoc, yLoc, string);
    }

    public void setEditedString(int xLoc, int yLoc, String s){
        //meant to be overridden
    }

    public String getStringAtLocation(int xLoc, int yLoc){
        //meant to be overridden
        return "";
    }

}

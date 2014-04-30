package RocketUML.model;

import RocketUML.visitor.Visitor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Represents an Abstract element
 */
public abstract class AbstractElement implements Serializable {

    protected String name = "";
    protected String elementType = "";
    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;
    protected boolean isSelected = false;

    public AbstractElement(){}

    public void init(int xLoc, int yLoc, String n, String type) {
        x = xLoc;
        y = yLoc;
        name = n;
        elementType = type;
    }

    public abstract void accept(Visitor v);

    public String getName() {
        return name;
    }

    public AbstractElement setName(String n) {
        name = n;
        return this;
    }

    public void draw(Graphics graphics){}

    public void setLocation(int xLoc, int yLoc){
        x = xLoc;
        y = yLoc;
    }

    public boolean contains(Point p){
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setSelected(boolean selected){
        isSelected = selected;
    }

    public void editString(int xLoc, int yLoc){
        String string = JOptionPane.showInputDialog("Please enter new text for: " + getStringAtLocation(xLoc, yLoc));
        if(string == null){string = "";}
        setEditedString(xLoc, yLoc, string);
    }

    public void setEditedString(int xLoc, int yLoc, String s){
    }

    public String getStringAtLocation(int xLoc, int yLoc){
        return "";
    }
}

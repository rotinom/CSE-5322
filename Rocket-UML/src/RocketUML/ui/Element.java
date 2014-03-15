package RocketUML.ui;

import javax.swing.*;
import java.awt.*;

public class Element extends JPanel {

    protected String name;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public void init(int xLoc, int yLoc, int w, int h, String n){
        x = xLoc;
        y = yLoc;
        width = w;
        height = h;
        name = n;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics graphics){
        System.out.println("base draw");
    }

    public void setLocation(int xLoc, int yLoc){
        x = xLoc;
        y = yLoc;
    }

    public boolean contains(Point p){
        System.out.println("base contains");
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
}

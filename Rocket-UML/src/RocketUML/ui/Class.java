package RocketUML.ui;

import java.awt.*;

public class Class extends Element {

    @Override
    public boolean contains(Point p){
        return (x < p.getX() && y < p.getY() &&
                x+width > p.getX() && y+height > p.getY());
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, width,height/3);
        g.setColor(Color.WHITE);
        g.drawString(name,x+60,y+25);
    }
}
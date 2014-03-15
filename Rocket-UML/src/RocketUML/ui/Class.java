package RocketUML.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;

public class Class extends Element {

    public static final int TITLE_HEIGHT = 30;
    public static final int LINE_HEIGHT = 20;
    public static final String EMPTY_STRING = "<empty>";

    public ArrayList<String> attributes = new ArrayList<String>();
    public ArrayList<String> methods = new ArrayList<String>();

    @Override
    public void init(int xLoc, int yLoc, String n){
        x = xLoc;
        y = yLoc;
        width = 200;
        height = 30;
        name = n;
        attributes.add(EMPTY_STRING);
        methods.add(EMPTY_STRING);
    }

    @Override
    public boolean contains(Point p){
        return (x < p.getX() && y < p.getY() &&
                x+width > p.getX() && y+height > p.getY());
    }

    public void draw(Graphics g)
    {

        //compute width and height
        int attributeHeight = attributes.size()*LINE_HEIGHT;
        int methodHeight = methods.size()*LINE_HEIGHT;
        height = TITLE_HEIGHT + attributeHeight + methodHeight;

        // draw a shadow
        BufferedImage img = new BufferedImage(width+20, height+20, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = img.getGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        // blur the shadow
        BufferedImageOp op = getBlurredOp();
        img = op.filter(img, null);
        g.drawImage(img, x, y, null);

        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(new Color(60, 60, 150));
        g.fillRect(x, y, width, TITLE_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString(name, x + 60, y + 20);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        int count = 0;
        g.setColor(Color.GRAY);
        for (String s : attributes){
            g.drawString(s, x + 20, 5 + y + LINE_HEIGHT/2 + TITLE_HEIGHT + LINE_HEIGHT*count++);
        }
        g.setColor(Color.DARK_GRAY);
        for (String s : methods){
            g.drawString(s, x + 20, 5 + y + LINE_HEIGHT/2 + TITLE_HEIGHT + LINE_HEIGHT*count++);
        }
        g.drawLine(x, y+TITLE_HEIGHT+attributeHeight, x+width, y+TITLE_HEIGHT+attributeHeight);
    }

    private static BufferedImageOp getBlurredOp() {

        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++)
            matrix[i] = 1.0f/400.0f;

        return new ConvolveOp(new Kernel(20, 20, matrix),
                ConvolveOp.EDGE_NO_OP, null);
    }

    public void addAttribute(String attribute){
        if(attributes.contains(EMPTY_STRING))
            attributes.remove(EMPTY_STRING);
        attributes.add(attribute);
    }

    public void addMethod(String method){
        if(methods.contains(EMPTY_STRING))
            methods.remove(EMPTY_STRING);
        methods.add(method);
    }

}
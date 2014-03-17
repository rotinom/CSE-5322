package RocketUML.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Class extends Element {

    public static final int TITLE_HEIGHT = 30;
    public static final int LINE_HEIGHT = 20;
    public static final String EMPTY_STRING = "<empty>";
    public static final int CONNECT_SIZE = 6;
    public static final int CONNECT_HALF_SIZE = CONNECT_SIZE/2;
    public static final int CLOSE_DIST = 50;
    public static final int CONNECT_CLOSE_DIST = 10;

    public enum ConnectLocationType {
        TOP, BOTTOM, LEFT, RIGHT;
    }

    public Map<ConnectLocationType, Point> connectPoints = new HashMap<ConnectLocationType, Point>();
    public Map<ConnectLocationType, ArrayList<Point>> attachedPoints = new HashMap<ConnectLocationType, ArrayList<Point>>();
    public ArrayList<String> attributes = new ArrayList<String>();
    public ArrayList<String> methods = new ArrayList<String>();
    private boolean drawConnectPoints = false;
    private Point relationshipDragPoint = null;

    public void init(int xLoc, int yLoc, String n){
        width = 200;
        height = 30;
        attributes.add(EMPTY_STRING);
        methods.add(EMPTY_STRING);

        //initialize connection point data
        connectPoints.put(ConnectLocationType.TOP, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.TOP, new ArrayList<Point>());
        connectPoints.put(ConnectLocationType.BOTTOM, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.BOTTOM, new ArrayList<Point>());
        connectPoints.put(ConnectLocationType.LEFT, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.LEFT, new ArrayList<Point>());
        connectPoints.put(ConnectLocationType.RIGHT, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.RIGHT, new ArrayList<Point>());

        super.init(xLoc, yLoc, n);
    }

    @Override
    public boolean contains(Point p){
        return (x < p.getX() && y < p.getY() &&
                x+width > p.getX() && y+height > p.getY());
    }

    public boolean closeTo(Point p){
        return (x-CLOSE_DIST < p.getX() && y-CLOSE_DIST < p.getY() &&
                x+width+CLOSE_DIST > p.getX() && y+height+CLOSE_DIST > p.getY());
    }

    public void draw(Graphics g)
    {
        //compute width and height
        int attributeHeight = attributes.size()*LINE_HEIGHT;
        int methodHeight = methods.size()*LINE_HEIGHT;
        height = TITLE_HEIGHT + attributeHeight + methodHeight;

        //move connect points
        connectPoints.get(ConnectLocationType.TOP).setLocation(x + width / 2, y-CONNECT_SIZE);
        connectPoints.get(ConnectLocationType.BOTTOM).setLocation(x + width / 2, y + height + CONNECT_HALF_SIZE);
        connectPoints.get(ConnectLocationType.LEFT).setLocation(x-CONNECT_SIZE, y+height/2);
        connectPoints.get(ConnectLocationType.RIGHT).setLocation(x + width + CONNECT_HALF_SIZE, y + height / 2);

        // draw a shadow
        BufferedImage img = new BufferedImage(width+20, height+20, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = img.getGraphics();
        if(isSelected)
           g2.setColor(Color.BLUE);
        else
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

        //draw attributes and methods
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

        //draw connect points
        if(drawConnectPoints)
        {
            //for (Point point : connectPoints){
            for (Map.Entry<ConnectLocationType, Point> entry : connectPoints.entrySet()) {
                Point point = entry.getValue();
                g.setColor(Color.BLACK);
                g.drawRect(point.x, point.y, CONNECT_SIZE, CONNECT_SIZE);
                //highlight if close enough to connect
                if(relationshipDragPoint.distance(point) < CONNECT_CLOSE_DIST)
                {
                    ArrayList<Point> points = attachedPoints.get(entry.getKey());
                    g.setColor(new Color(60, 60, 150));
                    if(!points.contains(relationshipDragPoint))
                    {
                        relationshipDragPoint.setLocation(point.x+CONNECT_HALF_SIZE, point.y+CONNECT_HALF_SIZE);
                        points.add(relationshipDragPoint);
                    }
                }
                else
                    g.setColor(Color.WHITE);
                g.fillRect(point.x, point.y, CONNECT_SIZE, CONNECT_SIZE);

                if(!isSelected){ //only look for moved points if we aren't moving this class
                    //look for points that have been moved away
                    ArrayList<Point> removePoints = new ArrayList<Point>();
                    ArrayList<Point> points = attachedPoints.get(entry.getKey());
                    for(Point attachPoint : points){
                        if(Math.abs(attachPoint.x - point.x) > CONNECT_CLOSE_DIST*2 ||
                           Math.abs(attachPoint.y - point.y) > CONNECT_CLOSE_DIST*2){
                            removePoints.add(attachPoint); //cant remove from list while iterating through same list
                        }
                    }
                    for(Point removePoint : removePoints){
                        points.remove(removePoint);
                    }
                }
            }
        }

        //move any attached points
        for (Map.Entry<ConnectLocationType, ArrayList<Point>> entry : attachedPoints.entrySet()) {
            for(Point point : entry.getValue()) {
                //System.out.println("update location of attached point");
                Point centerPoint = connectPoints.get(entry.getKey());
                point.setLocation(centerPoint.x+CONNECT_HALF_SIZE, centerPoint.y+CONNECT_HALF_SIZE);
            }
        }
    }

    @Override
    public void setEditedString(int xLoc, int yLoc, String s){
        if(!contains(new Point(xLoc, yLoc)))
            return;

        int attributeHeight = attributes.size()*LINE_HEIGHT;
        int methodHeight = methods.size()*LINE_HEIGHT;
        int index = 0;
        if(yLoc < y+TITLE_HEIGHT){ //title
            name = s;
        }
        else if(yLoc < y+TITLE_HEIGHT+attributeHeight) { //attributes
            index = (yLoc-y-TITLE_HEIGHT)/LINE_HEIGHT;
            if(index >= 0 && index < attributes.size()) //make sure in range
                attributes.set(index,s);
        }
        else { //methods
            index = (yLoc-y-TITLE_HEIGHT-attributeHeight)/LINE_HEIGHT;
            if(index >= 0 && index < methods.size()) //make sure in range
                methods.set(index,s);
        }
    }

    @Override
    public String getStringAtLocation(int xLoc, int yLoc){
        String string = "";
        if(!contains(new Point(xLoc, yLoc)))
            return string;

        int attributeHeight = attributes.size()*LINE_HEIGHT;
        int methodHeight = methods.size()*LINE_HEIGHT;
        int index = 0;
        if(yLoc < y+TITLE_HEIGHT){ //title
            string = name;
        }
        else if(yLoc < y+TITLE_HEIGHT+attributeHeight) { //attributes
            index = (yLoc-y-TITLE_HEIGHT)/LINE_HEIGHT;
            if(index >= 0 && index < attributes.size()) //make sure in range
                string = attributes.get(index);
        }
        else { //methods
            index = (yLoc-y-TITLE_HEIGHT-attributeHeight)/LINE_HEIGHT;
            if(index >= 0 && index < methods.size()) //make sure in range
                string = methods.get(index);
        }
        return string;
    }

    private static BufferedImageOp getBlurredOp() {
        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++)
            matrix[i] = 1.0f/400.0f;
        return new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null);
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

    public void drawConnectPoints(boolean draw) {
        drawConnectPoints = draw;
    }

    public void setRelationshipDragPoint(Point point){
        relationshipDragPoint = point;
    }

}
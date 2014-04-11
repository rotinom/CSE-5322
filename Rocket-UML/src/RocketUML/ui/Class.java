package RocketUML.ui;

import RocketUML.model.AttributeElement;
import RocketUML.model.ClassElement;
import RocketUML.model.MethodElement;

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
        TOP, BOTTOM, LEFT, RIGHT
    }

    public Map<ConnectLocationType, Point> connectPoints = new HashMap<ConnectLocationType, Point>();
    public Map<ConnectLocationType, ArrayList<Point>> attachedPoints = new HashMap<ConnectLocationType, ArrayList<Point>>();
    private boolean drawConnectPoints = false;
    private Point relationshipDragPoint = null;
    private Color colorScheme = new Color(60, 60, 150);
    private ClassElement model;

    public void init(int xLoc, int yLoc, String n, String type){
        width = 200;
        height = 30;

        //initialize connection point data
        connectPoints.put(ConnectLocationType.TOP, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.TOP, new ArrayList<Point>());
        connectPoints.put(ConnectLocationType.BOTTOM, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.BOTTOM, new ArrayList<Point>());
        connectPoints.put(ConnectLocationType.LEFT, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.LEFT, new ArrayList<Point>());
        connectPoints.put(ConnectLocationType.RIGHT, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.RIGHT, new ArrayList<Point>());

        //create new class model for data storage
        model = new ClassElement();

        super.init(xLoc, yLoc, n, "Class");
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
        int attributeHeight = model.getAttributes().size()*LINE_HEIGHT;
        int methodHeight = model.getMethods().size()*LINE_HEIGHT;
        height = TITLE_HEIGHT + attributeHeight + methodHeight;

        //move connect points
        connectPoints.get(ConnectLocationType.TOP).setLocation(x + width / 2, y-CONNECT_SIZE);
        connectPoints.get(ConnectLocationType.BOTTOM).setLocation(x + width / 2, y + height + CONNECT_HALF_SIZE);
        connectPoints.get(ConnectLocationType.LEFT).setLocation(x-CONNECT_SIZE, y+height/2);
        connectPoints.get(ConnectLocationType.RIGHT).setLocation(x + width + CONNECT_HALF_SIZE, y + height / 2);

        // draw a shadow
        BufferedImage img = new BufferedImage(width+20, height+20, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = img.getGraphics();
        if(isSelected){g2.setColor(Color.BLUE);}
        else{g2.setColor(Color.BLACK);}

        g2.fillRect(0, 0, width, height);
        // blur the shadow
        BufferedImageOp op = getBlurredOp();
        img = op.filter(img, null);
        g.drawImage(img, x, y, null);

        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(colorScheme);
        g.fillRect(x, y, width, TITLE_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString(name, x + 60, y + 20);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        //draw attributes and methods
        int count = 0;
        g.setColor(Color.GRAY);
        ArrayList<AttributeElement> attributes = model.getAttributes();
        for (AttributeElement attr : attributes){ //todo add attribute protection (public, private, etc...)?
            g.drawString(attr.getType() + " " + attr.getName(), x + 20, 5 + y + LINE_HEIGHT/2 + TITLE_HEIGHT + LINE_HEIGHT*count++);
        }

        g.setColor(Color.DARK_GRAY);
        ArrayList<MethodElement> methods = model.getMethods();
        for (MethodElement method : methods){
            g.drawString(method.getString(), x + 20, 5 + y + LINE_HEIGHT/2 + TITLE_HEIGHT + LINE_HEIGHT*count++);
        }
        g.drawLine(x, y+TITLE_HEIGHT+attributeHeight, x+width, y+TITLE_HEIGHT+attributeHeight);

        //draw connect points
        if(drawConnectPoints)
        {
            for (Map.Entry<ConnectLocationType, Point> entry : connectPoints.entrySet()) {
                Point point = entry.getValue();
                g.setColor(Color.BLACK);
                g.drawRect(point.x, point.y, CONNECT_SIZE, CONNECT_SIZE);
                //highlight if close enough to connect
                if(relationshipDragPoint.distance(point) < CONNECT_CLOSE_DIST)
                {
                    ArrayList<Point> points = attachedPoints.get(entry.getKey());
                    g.setColor(colorScheme);
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
                Point centerPoint = connectPoints.get(entry.getKey());
                point.setLocation(centerPoint.x+CONNECT_HALF_SIZE, centerPoint.y+CONNECT_HALF_SIZE);
            }
        }
    }

    @Override
    public void setEditedString(int xLoc, int yLoc, String s){
        if(!contains(new Point(xLoc, yLoc)))
            return;

        int attributeHeight = model.getAttributes().size()*LINE_HEIGHT;
        int index = 0;
        if(yLoc < y+TITLE_HEIGHT){ //title
            name = s;
        }
        else if(yLoc < y+TITLE_HEIGHT+attributeHeight) { //attributes
            index = (yLoc-y-TITLE_HEIGHT)/LINE_HEIGHT;
            if(index >= 0 && index < model.getAttributes().size()){ //make sure in range
                model.getAttributes().get(index).setString(s);
            }
        }
        else { //methods
            index = (yLoc-y-TITLE_HEIGHT-attributeHeight)/LINE_HEIGHT;
            if(index >= 0 && index < model.getMethods().size()){ //make sure in range
                model.getMethods().get(index).setString(s);
            }
        }
    }

    @Override
    public String getStringAtLocation(int xLoc, int yLoc){
        String string = "";
        if(!contains(new Point(xLoc, yLoc))){
            return string;
        }

        int attributeHeight = model.getAttributes().size()*LINE_HEIGHT;
        int methodHeight = model.getMethods().size()*LINE_HEIGHT;
        int index = 0;
        if(yLoc < y+TITLE_HEIGHT){ //title
            string = name;
        }
        else if(yLoc < y+TITLE_HEIGHT+attributeHeight) { //attributes
            index = (yLoc-y-TITLE_HEIGHT)/LINE_HEIGHT;
            if(index >= 0 && index < model.getAttributes().size()){ //make sure in range
                string = model.getAttributes().get(index).getString();
            }
        }
        else { //methods
            index = (yLoc-y-TITLE_HEIGHT-attributeHeight)/LINE_HEIGHT;
            if(index >= 0 && index < model.getMethods().size()){ //make sure in range
                string = model.getMethods().get(index).getString();
            }
        }
        return string;
    }

    public boolean isPointInTitleArea(int xLoc, int yLoc){
        return contains(new Point(xLoc, yLoc)) && yLoc < y+TITLE_HEIGHT;
    }

    public boolean isPointInAttributeArea(int xLoc, int yLoc){
        int attributeHeight = model.getAttributes().size()*LINE_HEIGHT;
        return contains(new Point(xLoc, yLoc)) && yLoc > y+TITLE_HEIGHT && yLoc < y+TITLE_HEIGHT+attributeHeight;
    }

    public boolean isPointInMethodArea(int xLoc, int yLoc){
        int attributeHeight = model.getMethods().size()*LINE_HEIGHT;
        return contains(new Point(xLoc, yLoc)) && yLoc > y+TITLE_HEIGHT+attributeHeight;
    }

    private static BufferedImageOp getBlurredOp() {
        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++){
            matrix[i] = 1.0f/400.0f;
        }
        return new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null);
    }

    public void addAttribute(String s){
        model.createAttribute().setString(s);
    }

    public ArrayList<AttributeElement> getAttributes(){
        return model.getAttributes();
    }

    public void removeAttribute(String s){
        //todo add remove attribute based on string
        //attributes.removeMethod(s);
    }

    public void addMethod(String s){
        model.createMethod().setString(s);
    }

    public ArrayList<MethodElement> getMethods(){
        return model.getMethods();
    }

    public void removeMethod(String s){
        //todo add remove method based on string
        //model.removeMethod(s);
    }

    public void drawConnectPoints(boolean draw) {
        drawConnectPoints = draw;
    }

    public void setRelationshipDragPoint(Point point){
        relationshipDragPoint = point;
    }

}

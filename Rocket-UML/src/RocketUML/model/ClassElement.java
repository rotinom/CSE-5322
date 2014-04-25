package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassElement extends AbstractElement {

    public static final int TITLE_HEIGHT = 30;
    public static final int LINE_HEIGHT = 20;
    public static final int CONNECT_SIZE = 6;
    public static final int CONNECT_HALF_SIZE = CONNECT_SIZE/2;
    public static final int CLOSE_DIST = 50;
    public static final int CONNECT_CLOSE_DIST = 10;

    public enum ConnectLocationType {
        TOP, BOTTOM, LEFT, RIGHT
    }

    public Map<ConnectLocationType, Point> connectPoints = new HashMap<ConnectLocationType, Point>();
    public Map<ConnectLocationType, ArrayList<RelationshipPoint>> attachedPoints =
            new HashMap<ConnectLocationType, ArrayList<RelationshipPoint>>();
    private boolean drawConnectPoints = false;
    private RelationshipPoint relationshipDragPoint = null;
    private Color colorScheme = new Color(60, 60, 150);

    private ArrayList<AttributeElement> attributes =
            new ArrayList<AttributeElement>();
    private ArrayList<MethodElement> methods =
            new ArrayList<MethodElement>();

    /**
     * CTOR
     */
   public ClassElement() {}

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public void init(int xLoc, int yLoc, String n, String type){
        width = 200;
        height = 30;

        //initialize connection point data
        connectPoints.put(ConnectLocationType.TOP, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.TOP, new ArrayList<RelationshipPoint>());
        connectPoints.put(ConnectLocationType.BOTTOM, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.BOTTOM, new ArrayList<RelationshipPoint>());
        connectPoints.put(ConnectLocationType.LEFT, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.LEFT, new ArrayList<RelationshipPoint>());
        connectPoints.put(ConnectLocationType.RIGHT, new Point(0, 0));
        attachedPoints.put(ConnectLocationType.RIGHT, new ArrayList<RelationshipPoint>());

        super.init(xLoc, yLoc, n, "Class");
    }

    /**
     * Set the name of the class
     * @param n The name to set
     */
    public ClassElement setName(String n){
        super.setName(n);
        return this;
    }

    public void draw(Graphics g)
    {
        //compute width and height
        int attributeHeight = getAttributes().size()*LINE_HEIGHT;
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
        ArrayList<AttributeElement> attributes = getAttributes();
        for (AttributeElement attr : attributes){ //todo add attribute protection (public, private, etc...)?
            g.drawString(attr.getString(), x + 20, 5 + y + LINE_HEIGHT/2 + TITLE_HEIGHT + LINE_HEIGHT*count++);
        }

        g.setColor(Color.DARK_GRAY);
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
                    ArrayList<RelationshipPoint> points = attachedPoints.get(entry.getKey());
                    g.setColor(colorScheme);
                    if(!points.contains(relationshipDragPoint))
                    {
                        relationshipDragPoint.setLocation(point.x+CONNECT_HALF_SIZE, point.y+CONNECT_HALF_SIZE);
                        points.add(relationshipDragPoint);
                        //tell relationship if this is the source or dest class
                        if(relationshipDragPoint.getType() == RelationshipElement.MovePointType.SOURCE) {
                            relationshipDragPoint.getParent().setSrce(this);
                        }
                        else if(relationshipDragPoint.getType() == RelationshipElement.MovePointType.DESTINATION) {
                            relationshipDragPoint.getParent().setDest(this);
                        }
                    }
                }
                else
                    g.setColor(Color.WHITE);
                g.fillRect(point.x, point.y, CONNECT_SIZE, CONNECT_SIZE);

                if(!isSelected){ //only look for moved points if we aren't moving this class
                    //look for points that have been moved away
                    ArrayList<RelationshipPoint> removePoints = new ArrayList<RelationshipPoint>();
                    ArrayList<RelationshipPoint> points = attachedPoints.get(entry.getKey());
                    for(RelationshipPoint attachPoint : points){
                        if(Math.abs(attachPoint.x - point.x) > CONNECT_CLOSE_DIST*2 ||
                                Math.abs(attachPoint.y - point.y) > CONNECT_CLOSE_DIST*2){
                            removePoints.add(attachPoint); //cant remove from list while iterating through same list
                        }
                    }
                    for(Point removePoint : removePoints){
                        points.remove(removePoint);
                        //if removing point then clear relationship source or dest class
                        if(relationshipDragPoint.getType() == RelationshipElement.MovePointType.SOURCE) {
                            relationshipDragPoint.getParent().setSrce(null);
                        }
                        else if(relationshipDragPoint.getType() == RelationshipElement.MovePointType.DESTINATION) {
                            relationshipDragPoint.getParent().setDest(null);
                        }
                    }
                }
            }
        }

        //move any attached points
        for (Map.Entry<ConnectLocationType, ArrayList<RelationshipPoint>> entry : attachedPoints.entrySet()) {
            for(Point point : entry.getValue()) {
                Point centerPoint = connectPoints.get(entry.getKey());
                point.setLocation(centerPoint.x+CONNECT_HALF_SIZE, centerPoint.y+CONNECT_HALF_SIZE);
            }
        }
    }

    /**
     * Add an attribute to the class
     */
    public AttributeElement createAttribute(){
        AttributeElement ae = AttributeElement.create();
        attributes.add(ae);
        return ae;
    }

    /**
     * Add a method to the class
     * @return
     */
    public MethodElement createMethod(){
        MethodElement ae = MethodElement.create();
        methods.add(ae);
        return ae;
    }

    /**
     * Get the attribute HashMap
     * @return The attribute HashMap
     */
    public ArrayList<AttributeElement> getAttributes(){
        return attributes;
    }


    public void addAttribute(String s){
        createAttribute().setString(s);
    }

    public void removeAttribute(String s){
        AttributeElement removeAttribute = null;
        ArrayList<AttributeElement> attributes = getAttributes();
        for (AttributeElement attribute : attributes){
            if(attribute.getString().equals(s)) {
                removeAttribute = attribute;
                break;
            }
        }
        if(removeAttribute != null) {
            attributes.remove(removeAttribute);
        }
    }

    public void addMethod(String s){
        createMethod().setString(s);
    }

    public ArrayList<MethodElement> getMethods() {
        return methods;
    }

    public void removeMethod(String s){
        MethodElement removeMethod = null;
        for (MethodElement method : methods){
            if(method.getString().equals(s)) {
                removeMethod = method;
                break;
            }
        }
        if(removeMethod != null) {
            methods.remove(removeMethod);
        }
    }

    public void changeProtection(int xLoc, int yLoc, ProtectionEnum protection){
        AttributeElement attribute = getAttributeAtLocation(xLoc, yLoc);
        if(attribute != null) {
            attribute.setProtection(protection);
            return;
        }

        MethodElement method = getMethodAtLocation(xLoc, yLoc);
        if (method != null) {
            method.setProtection(protection);
        }
    }

    @Override
    public void setEditedString(int xLoc, int yLoc, String s){

        if(yLoc < y+TITLE_HEIGHT){
            name = s;
            return;
        }

        AttributeElement attribute = getAttributeAtLocation(xLoc, yLoc);
        if(attribute != null) {
            attribute.setString(s);
            return;
        }

        MethodElement method = getMethodAtLocation(xLoc, yLoc);
        if (method != null) {
            method.setString(s);
        }
    }

    @Override
    public String getStringAtLocation(int xLoc, int yLoc){
        String string = "";

        AttributeElement attribute = getAttributeAtLocation(xLoc, yLoc);
        MethodElement method = getMethodAtLocation(xLoc, yLoc);
        if(attribute != null) {
            string = attribute.getString();
        }
        else if (method != null) {
            string = method.getString();
        }
        return string;
    }

    public MethodElement getMethodAtLocation(int xLoc, int yLoc){
        MethodElement method = null;
        if(!contains(new Point(xLoc, yLoc))){
            return method;
        }

        int attributeHeight = getAttributes().size()*LINE_HEIGHT;
        int index = 0;
        if(yLoc > y+TITLE_HEIGHT+attributeHeight) { //attributes
            index = (yLoc-y-TITLE_HEIGHT-attributeHeight)/LINE_HEIGHT;
            if(index >= 0 && index < methods.size()){ //make sure in range
                method = methods.get(index);
            }
        }
        return method;
    }

    public AttributeElement getAttributeAtLocation(int xLoc, int yLoc){
        AttributeElement attribute = null;
        if(!contains(new Point(xLoc, yLoc))){
            return attribute;
        }

        int attributeHeight = getAttributes().size()*LINE_HEIGHT;
        int index = 0;
        if(yLoc < y+TITLE_HEIGHT+attributeHeight) { //attributes
            index = (yLoc-y-TITLE_HEIGHT)/LINE_HEIGHT;
            if(index >= 0 && index < getAttributes().size()){ //make sure in range
                attribute = getAttributes().get(index);
            }
        }
        return attribute;
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

    public boolean isPointInTitleArea(int xLoc, int yLoc){
        return contains(new Point(xLoc, yLoc)) && yLoc < y+TITLE_HEIGHT;
    }

    public boolean isPointInAttributeArea(int xLoc, int yLoc){
        int attributeHeight = getAttributes().size()*LINE_HEIGHT;
        return contains(new Point(xLoc, yLoc)) && yLoc > y+TITLE_HEIGHT && yLoc < y+TITLE_HEIGHT+attributeHeight;
    }

    public boolean isPointInMethodArea(int xLoc, int yLoc){
        int attributeHeight = getAttributes().size()*LINE_HEIGHT;
        return contains(new Point(xLoc, yLoc)) && yLoc > y+TITLE_HEIGHT+attributeHeight;
    }

    private static BufferedImageOp getBlurredOp() {
        float[] matrix = new float[400];
        for (int i = 0; i < 400; i++){
            matrix[i] = 1.0f/400.0f;
        }
        return new ConvolveOp(new Kernel(20, 20, matrix), ConvolveOp.EDGE_NO_OP, null);
    }

    public void drawConnectPoints(boolean draw) {
        drawConnectPoints = draw;
    }

    public void setRelationshipDragPoint(RelationshipPoint point){
        relationshipDragPoint = point;
    }

}

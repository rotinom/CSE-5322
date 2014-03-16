package RocketUML.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

public class Relationship extends Element {
    public static final int GRAB_SIZE = 6;
    public static final int END_SIZE = 4;
    public static final int HALF_END = END_SIZE/2;
    public static final int HALF_GRAB = GRAB_SIZE/2;
    public static final int SYMBOL_SIZE = 12;

    public enum Type {
        ASSOCIATION, AGGREGATION, INHERITANCE;
    }
    public enum MovePointType {
        NONE, SOURCE, INTERIM1, INTERIM2, DESTINATION;
    }

    public Map<MovePointType, Point> movePoints = new HashMap<MovePointType, Point>();
    private Type type = Type.ASSOCIATION;
    private MovePointType moveType = MovePointType.NONE;
    private Point dragPoint = null;

    public void init(int xLoc, int yLoc, String n){
        movePoints.put(MovePointType.SOURCE, new Point(xLoc-45, yLoc));
        movePoints.put(MovePointType.INTERIM1, new Point(xLoc-15, yLoc));
        movePoints.put(MovePointType.INTERIM2, new Point(xLoc+15, yLoc));
        movePoints.put(MovePointType.DESTINATION, new Point(xLoc+45, yLoc));

        super.init(xLoc, yLoc, n);
    }

	@Override
	public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawLine(movePoints.get(MovePointType.SOURCE).x, movePoints.get(MovePointType.SOURCE).y,
                   movePoints.get(MovePointType.INTERIM1).x, movePoints.get(MovePointType.INTERIM1).y);
        g.drawLine(movePoints.get(MovePointType.INTERIM1).x, movePoints.get(MovePointType.INTERIM1).y,
                   movePoints.get(MovePointType.INTERIM2).x, movePoints.get(MovePointType.INTERIM2).y);

        //draw src/dest points
        g.fillOval(movePoints.get(MovePointType.SOURCE).x - HALF_END,
                   movePoints.get(MovePointType.SOURCE).y - HALF_END, END_SIZE, END_SIZE);
        Point destPoint = movePoints.get(MovePointType.DESTINATION);
        //draw end type
        switch(type){
            case ASSOCIATION:
                g.drawLine(movePoints.get(MovePointType.INTERIM2).x, movePoints.get(MovePointType.INTERIM2).y,
                        movePoints.get(MovePointType.DESTINATION).x, movePoints.get(MovePointType.DESTINATION).y);
                break;

            case AGGREGATION:
                drawAggregation(g, movePoints.get(MovePointType.INTERIM2).x, movePoints.get(MovePointType.INTERIM2).y,
                        movePoints.get(MovePointType.DESTINATION).x, movePoints.get(MovePointType.DESTINATION).y);
                break;

            case INHERITANCE:
                drawInheritance(g, movePoints.get(MovePointType.INTERIM2).x, movePoints.get(MovePointType.INTERIM2).y,
                                   movePoints.get(MovePointType.DESTINATION).x, movePoints.get(MovePointType.DESTINATION).y);
                break;

            default:
                g.drawLine(movePoints.get(MovePointType.INTERIM2).x, movePoints.get(MovePointType.INTERIM2).y,
                        movePoints.get(MovePointType.DESTINATION).x, movePoints.get(MovePointType.DESTINATION).y);
                break;
        }

        g.fillOval(destPoint.x - HALF_END, destPoint.y - HALF_END, END_SIZE, END_SIZE);

        //draw interim points
        g.setColor(new Color(60, 60, 150));
        g.fillOval(movePoints.get(MovePointType.INTERIM1).x - HALF_GRAB,
                   movePoints.get(MovePointType.INTERIM1).y - HALF_GRAB, GRAB_SIZE, GRAB_SIZE);
        g.fillOval(movePoints.get(MovePointType.INTERIM2).x - HALF_GRAB,
                   movePoints.get(MovePointType.INTERIM2).y - HALF_GRAB, GRAB_SIZE, GRAB_SIZE);

        g.setColor(Color.BLACK);
        g.drawString(name, x - 30, y + 15);
    }
    
    @Override
    public boolean contains(Point p){
        //determine is user is selecting a grab point, go ahead and give them a little
        //more area to grab (x2)
        boolean contains = false;
        moveType = MovePointType.NONE;
        dragPoint = null;

        for (Map.Entry<MovePointType, Point> entry : movePoints.entrySet()) {
            if(entry.getValue().x-END_SIZE < p.getX() && entry.getValue().y-END_SIZE < p.getY() &&
               entry.getValue().x+END_SIZE > p.getX() && entry.getValue().y+END_SIZE > p.getY())
            {
                contains = true;
                moveType = entry.getKey();
                dragPoint = entry.getValue();
                break;
            }
        }
        //System.out.println("contains=" + contains + " type="+moveType);
        return contains;
    }

    @Override
    public void setLocation(int xLoc, int yLoc){
        movePoints.get(moveType).setLocation(xLoc, yLoc);
    }

    public Point getDragPoint(){
        return dragPoint;
    }

    public void setType(Type relationshipType){
        type = relationshipType;
    }

    private void drawInheritance( Graphics g, int x1, int y1, int x2, int y2 )
    {
        double phi;
        phi = Math.PI/6; //30 degree angle
        double theta = Math.atan2(y1 - y2, x1 - x2);

        int endX1 = (int)(x2 + SYMBOL_SIZE * Math.cos(theta + phi));
        int endY1 = (int)(y2 + SYMBOL_SIZE * Math.sin(theta + phi));
        int endX2 = (int)(x2 + SYMBOL_SIZE * Math.cos(theta - phi));
        int endY2 = (int)(y2 + SYMBOL_SIZE * Math.sin(theta - phi));

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

        int[] xPoints = {endX1, endX2, x2};
        int[] yPoints = {endY1, endY2, y2};
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, xPoints.length);

        g.setColor(Color.BLACK);
        g.drawLine(x2, y2, endX1, endY1);
        g.drawLine(x2, y2, endX2, endY2);
        g.drawLine(endX1, endY1, endX2, endY2);
    }

    private void drawAggregation( Graphics g, int x1, int y1, int x2, int y2 )
    {
        double phi;
        phi = Math.PI/6; //30 degree angle
        double theta = Math.atan2(y1 - y2, x1 - x2);

        int endX1 = (int)(x2 + SYMBOL_SIZE * Math.cos(theta + phi));
        int endY1 = (int)(y2 + SYMBOL_SIZE * Math.sin(theta + phi));
        int endX2 = (int)(x2 + SYMBOL_SIZE * Math.cos(theta - phi));
        int endY2 = (int)(y2 + SYMBOL_SIZE * Math.sin(theta - phi));
        int endX3 = (int)(endX2 + SYMBOL_SIZE * Math.cos(theta + phi));
        int endY3 = (int)(endY2 + SYMBOL_SIZE * Math.sin(theta + phi));

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);

        int[] xPoints = {endX1, endX3, endX2, x2};
        int[] yPoints = {endY1, endY3, endY2, y2};
        g.setColor(Color.WHITE);
        g.fillPolygon(xPoints, yPoints, xPoints.length);

        g.setColor(Color.BLACK);
        g.drawLine(x2, y2, endX1, endY1);
        g.drawLine(x2, y2, endX2, endY2);
        g.drawLine(endX3, endY3, endX1, endY1);
        g.drawLine(endX3, endY3, endX2, endY2);
    }

}

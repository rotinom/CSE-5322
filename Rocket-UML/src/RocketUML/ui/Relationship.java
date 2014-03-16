package RocketUML.ui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Relationship extends Element {
    public static final int GRAB_SIZE = 6;
    public static final int END_SIZE = 4;
    public static final int HALF_END = END_SIZE/2;
    public static final int HALF_GRAB = GRAB_SIZE/2;
    public static final int SYMBOL_SIZE = 12;
    public static final double SYMBOL_ANGLE = Math.PI/6; //30 degree angle
    public static final int SNAP_TO_DIST = 5;

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
    private FontMetrics metrics = null;
    private boolean dragText = false;

    public void init(int xLoc, int yLoc, String n){
        movePoints.put(MovePointType.SOURCE, new Point(xLoc-45, yLoc));
        movePoints.put(MovePointType.INTERIM1, new Point(xLoc-15, yLoc));
        movePoints.put(MovePointType.INTERIM2, new Point(xLoc+15, yLoc));
        movePoints.put(MovePointType.DESTINATION, new Point(xLoc+45, yLoc));

        //go ahead and offset for text
        super.init(xLoc-45, yLoc-15, n);
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
        g.drawString(name, x, y);

        //save metrics for later
        if(metrics == null)
            metrics = g.getFontMetrics(getFont());
    }
    
    @Override
    public boolean contains(Point p){
        //determine is user is selecting a grab point, go ahead and give them a little
        //more area to grab (x2)
        boolean contains = false;
        dragText = false;
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

        //check to see if they selected the text
        int textWidth = metrics.stringWidth(name);
        int textHeight = metrics.getHeight();
        if(p.getX() >= x && p.getX() <= x+textWidth &&
           p.getY() >= y-textHeight && p.getY() <= y)
        {
            dragText = true;
            contains = true;
        }

        return contains;
    }

    @Override
    public void setLocation(int xLoc, int yLoc){

        //if we are dragging text, set local x/y
        if(dragText){
            x = xLoc;
            y = yLoc;
            return;
        }

        //otherwise this is an interim point
        Point point = movePoints.get(moveType);
        //make interim points snap to src/dest/each other
        if(moveType == MovePointType.INTERIM1 || (moveType == MovePointType.INTERIM2)) {
            Point point2, point3;
            if(moveType == MovePointType.INTERIM1){
                point2 = movePoints.get(MovePointType.INTERIM2);
                point3 = movePoints.get(MovePointType.SOURCE);
            }
            else { //INTERIM2
                point2 = movePoints.get(MovePointType.DESTINATION);
                point3 = movePoints.get(MovePointType.INTERIM1);
            }

            if(Math.abs(xLoc - point2.x) < SNAP_TO_DIST){xLoc = point2.x;}
            else if(Math.abs(yLoc - point2.y) < SNAP_TO_DIST){yLoc = point2.y;}
            else if(Math.abs(xLoc - point3.x) < SNAP_TO_DIST){xLoc = point3.x;}
            else if(Math.abs(yLoc - point3.y) < SNAP_TO_DIST){yLoc = point3.y;}
        }

        point.setLocation(xLoc, yLoc);
    }

    public Point getDragPoint(){
        return dragPoint;
    }

    public void setType(Type relationshipType){
        type = relationshipType;
    }

    private void drawInheritance( Graphics g, int x1, int y1, int x2, int y2 )
    {
        double arcTangent = Math.atan2(y1 - y2, x1 - x2);
        int endX1 = (int)(x2 + SYMBOL_SIZE * Math.cos(arcTangent + SYMBOL_ANGLE));
        int endY1 = (int)(y2 + SYMBOL_SIZE * Math.sin(arcTangent + SYMBOL_ANGLE));
        int endX2 = (int)(x2 + SYMBOL_SIZE * Math.cos(arcTangent - SYMBOL_ANGLE));
        int endY2 = (int)(y2 + SYMBOL_SIZE * Math.sin(arcTangent - SYMBOL_ANGLE));

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
        double arcTangent = Math.atan2(y1 - y2, x1 - x2);
        int endX1 = (int)(x2 + SYMBOL_SIZE * Math.cos(arcTangent + SYMBOL_ANGLE));
        int endY1 = (int)(y2 + SYMBOL_SIZE * Math.sin(arcTangent + SYMBOL_ANGLE));
        int endX2 = (int)(x2 + SYMBOL_SIZE * Math.cos(arcTangent - SYMBOL_ANGLE));
        int endY2 = (int)(y2 + SYMBOL_SIZE * Math.sin(arcTangent - SYMBOL_ANGLE));
        int endX3 = (int)(endX2 + SYMBOL_SIZE * Math.cos(arcTangent + SYMBOL_ANGLE));
        int endY3 = (int)(endY2 + SYMBOL_SIZE * Math.sin(arcTangent + SYMBOL_ANGLE));

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

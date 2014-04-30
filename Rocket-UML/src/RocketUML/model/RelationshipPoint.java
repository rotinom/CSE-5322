package RocketUML.model;

import java.awt.*;

/**
 * Represents a Relationship Point to help associate a Class/Relationship
 */
public class RelationshipPoint extends Point {
    RelationshipElement parent;
    RelationshipElement.MovePointType type;

    public RelationshipPoint(int x, int y, RelationshipElement.MovePointType type, RelationshipElement parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.type = type;
    }

    public void setParent(RelationshipElement parent) {this.parent = parent;}
    public RelationshipElement getParent(){return this.parent;}
    public void setType(RelationshipElement.MovePointType type) {this.type = type;}
    public RelationshipElement.MovePointType getType() {return this.type;}
}

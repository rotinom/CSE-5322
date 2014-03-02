package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rotinom on 2/19/14.
 */

enum DiagramType{
    RELATIONSHIP,
    CLASS
}

/**
 * Represents an abstract element
 */
public abstract class AbstractElement {

    protected AbstractElement(){}

    /**
     * Abstract factory to create diagram elements
     * @param type The type of element to create
     * @return The element created
     * @throws Exception if invalid type specified
     */
    public static AbstractElement create(DiagramType type) throws Exception {
        if(DiagramType.CLASS == type){
            return ClassElement.create();
        }
        else if(DiagramType.RELATIONSHIP == type){
            return RelationshipElement.create();
        }
        throw new Exception("Invalid type specified");
    }

    public abstract void accept(Visitor v);
}

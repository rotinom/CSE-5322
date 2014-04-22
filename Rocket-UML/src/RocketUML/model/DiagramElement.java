package RocketUML.model;

import RocketUML.visitor.Visitor;

import javax.management.relation.Relation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DiagramElement extends AbstractElement implements Serializable {

    private static final long serialVersionUID = -2470735693019794460L;
    private ProjectElement parent;
    private ArrayList<ClassElement> classes = new ArrayList<ClassElement>();
    private ArrayList<RelationshipElement> relationships = new ArrayList<RelationshipElement>();

    public DiagramElement(ProjectElement p) {
        parent = p;
    }

    public ArrayList<ClassElement> getClasses(){
        return classes;
    }
    public Collection<RelationshipElement> getRelationships(){
        return relationships;
    }

    public void addElement(AbstractElement element) {
        if(element.getClass() == ClassElement.class) {
            classes.add((ClassElement)element);
        }
        else if(element.getClass() == RelationshipElement.class) {
            relationships.add((RelationshipElement)element);
        }
    }

    public void removeElement(AbstractElement element) {
        if(element.getClass() == ClassElement.class) {
            classes.remove((ClassElement) element);
        }
        else if(element.getClass() == RelationshipElement.class) {
            relationships.remove((RelationshipElement) element);
        }
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    public DiagramElement setName(String n) {
        super.setName(n);
        return this;
    }
}

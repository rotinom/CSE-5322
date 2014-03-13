package RocketUML.model;

import RocketUML.visitor.Visitor;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by rotinom on 3/4/14.
 */
public class DiagramElement extends AbstractElement{

    private ProjectElement parent;
    private ArrayList<ClassElement> classes = new ArrayList<ClassElement>();
    private ArrayList<RelationshipElement> relationships = new ArrayList<RelationshipElement>();

    public DiagramElement(ProjectElement p) {
        parent = p;
    }

    public ClassElement createClass(){
        ClassElement ret = new ClassElement();
        classes.add(ret);
        return ret;
    }

    public ArrayList<ClassElement> getClasses(){
        return classes;
    }

    public RelationshipElement createRelationship(){
        RelationshipElement ret = new RelationshipElement();
        relationships.add(ret);
        return ret;
    }

    public Collection<RelationshipElement> getRelationships(){
        return relationships;
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

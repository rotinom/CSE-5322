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

    public ClassElement createClass(String name){
        ClassElement ret = new ClassElement(name);
        classes.add(ret);
        return ret;
    }

    public ArrayList<ClassElement> getClasses(){
        return classes;
    }

    public RelationshipElement createRelationship(RelationshipType type){
        RelationshipElement ret = new RelationshipElement(type);
        relationships.add(ret);
        return ret;
    }

    public Collection<RelationshipElement> getRelationships(){
        return relationships;
    }

    @Override
    public void accept(Visitor v) {
        // Visit ourselves first
        v.visit(this);

        // Visit our child classes
        for(Iterator<ClassElement> iter = classes.iterator();
            iter.hasNext();)
        {
            v.visit(iter.next());
        }

        // Visit our child relationships
        for(Iterator<RelationshipElement> iter = relationships.iterator();
            iter.hasNext();)
        {
            v.visit(iter.next());
        }
    }
}

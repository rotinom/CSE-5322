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
    private String name;

    public DiagramElement(ProjectElement p) {
        parent = p;
        name = "Diagram1"; // @todo Randomize this
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
        v.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rotinom on 2/19/14.
 */
public class ProjectElement extends AbstractElement{

    private static ProjectElement instance = new ProjectElement();
    private List<DiagramElement> diagramList = new ArrayList<DiagramElement>();
    private String name;

    /**
     * Private constructor to enforce the singleton pattern
     */
    private ProjectElement() {
        name = "proj1"; /// @todo Make this random
    }

    /**
     * Singleton create method
     * @return The ProjectElement singleton
     */
    public static ProjectElement create(){return instance;}

    /**
     * Create a new DiagramElement and add it to the project
     * @return The new DiagramElement
     */
    public DiagramElement createDiagram(){
        DiagramElement ret = new DiagramElement(this);
        diagramList.add(ret);
        return ret;
    }

    /**
     * Get a list of the diagrams in the project
     * @return The diagram list
     */
    public List<DiagramElement> getDiagrams(){
        return diagramList;
    }

    /**
     * Accept method for the visitor pattern
     * @param v The visitor to visit
     */
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

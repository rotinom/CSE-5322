package RocketUML.model;

import RocketUML.visitor.Visitor;

import java.util.HashMap;

public class ProjectElement extends AbstractElement {
    private static ProjectElement instance = new ProjectElement();
    private HashMap<String,DiagramElement> diagramList = new HashMap<String,DiagramElement>();

    /**
     * Private constructor to enforce the singleton pattern
     */
    private ProjectElement() {}

    /**
     * Singleton create method
     * @return The ProjectElement singleton
     */
    public static ProjectElement create(){return instance;}
    public static ProjectElement getInstance(){return instance;}

    /**
     * Create a new DiagramElement and add it to the project
     * @return The new DiagramElement
     */
    public DiagramElement createDiagram(String name){
        DiagramElement ret = new DiagramElement();
        ret.setName(name);
        diagramList.put(name, ret);
        return ret;
    }

    public DiagramElement getDiagram(String name) {
        DiagramElement diagram = null;
        if(diagramList.containsKey(name)){
            diagram = diagramList.get(name);
        }
        return diagram;
    }

    public void removeDiagram(String name) {
        diagramList.remove(name);
    }

    public void changeName(String oldName, String newName) {
        DiagramElement diagram  = diagramList.remove(oldName);
        diagramList.put(newName, diagram);
        diagram.setName(newName);
    }

    /**
     * Get a list of the diagrams in the project
     * @return The diagram list
     */
    public HashMap<String,DiagramElement> getDiagrams(){
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

    public ProjectElement setName(String n) {
        super.setName(n);
        return this;
    }

    public void resetDiagramForOpen()
    {
        diagramList.clear();
    }

    public void rebuildDiagrams(HashMap<String,DiagramElement> diagramsIn)
    {
        diagramList = diagramsIn;
    }
}

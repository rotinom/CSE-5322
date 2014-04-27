package RocketUML.memento;


import RocketUML.model.DiagramElement;

import java.util.HashMap;

public class Originator {

    private static Originator instance = new Originator();
    private Originator() {}

    public static Originator getInstance(){return instance;}

    private HashMap<String,DiagramElement> diagramElement;

    public void setState(HashMap element)
    {
        this.diagramElement = (HashMap)Memento.CopyObject(element);
    }

    public HashMap getState()
    {
        return diagramElement;
    }

    public Memento createMemento()
    {
        return new Memento(diagramElement);
    }

    public void setMemento(Memento memento)
    {
        diagramElement = memento.getState();
    }
}

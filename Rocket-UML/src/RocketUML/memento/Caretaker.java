package RocketUML.memento;

import java.util.ArrayList;

public class Caretaker {

    protected ArrayList<Memento> savedStates = new ArrayList();

    private static int maxObjectCounter;
    private static int undoCounter;

    private static Caretaker instance = new Caretaker();

    private Caretaker()
    {
        maxObjectCounter = 0;
        undoCounter = maxObjectCounter;
    }

    public static Caretaker getInstance(){return instance;}

    public void setState(Memento memento)
    {
     //   HashMap<String,DiagramElement> diagramList = ProjectElement.getInstance().getDiagrams();
        maxObjectCounter++;
        if(maxObjectCounter <= 9) {
            savedStates.add(memento);
            undoCounter = maxObjectCounter;
        }
        else
        {
            savedStates.remove(0);
            savedStates.add(memento);
            maxObjectCounter = 10;
            undoCounter = maxObjectCounter;
        }
    }

    public Memento getUndoState()
    {
        if(undoCounter >= 0)
        {
            undoCounter--;
        }
        else {
            return savedStates.get(0);
        }
        return savedStates.get(undoCounter);
    }

    public Memento getRedoState()
    {
        if(undoCounter <= 9)
        {
            undoCounter++;
        }
        else
        {
            return savedStates.get(9);
        }
        return savedStates.get(undoCounter);
    }
}

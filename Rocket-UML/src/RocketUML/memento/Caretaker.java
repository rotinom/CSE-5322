package RocketUML.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class Caretaker {

    protected Deque<Memento> undoStates = new ArrayDeque<Memento>();
    protected Deque<Memento> redoStates = new ArrayDeque<Memento>();

    private static Caretaker instance = new Caretaker();



    private Caretaker()
    {
    }

    public static Caretaker getInstance(){return instance;}

    public void setState(Memento memento)
    {
     //   HashMap<String,DiagramElement> diagramList = ProjectElement.getInstance().getDiagrams();
        undoStates.push(memento);
    }

    public Memento getUndoState()
    {
        Memento undo = null;
        if(undoStates.size() != 0) {
           undo = undoStates.pop();
            redoStates.push(undo);
        }
        return undo;
    }

    public Memento getRedoState()
    {

        Memento redo = null;
        if(redoStates.size() != 0) {
           redo = redoStates.pop();
            undoStates.push(redo);
        };
        return redo;
    }
}

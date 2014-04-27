package RocketUML.memento;

import RocketUML.model.DiagramElement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Memento {

    private HashMap<String,DiagramElement> diagramElement;

    public Memento(HashMap element)
    {
        this.diagramElement = (HashMap)CopyObject(element);
    }

    public HashMap getState()
    {
        return diagramElement;
    }

    public static Object CopyObject(Object object) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(outStream);
            objectStream.writeObject(object);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(outStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(byteInputStream);
            return objInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

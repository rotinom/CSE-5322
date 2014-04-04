package RocketUML.ui;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Luke on 3/17/14.
 */
public class Serialization
{
    ModelViewController controller = ModelViewController.getInstance();
    public ArrayList<Element> elementsIn = new ArrayList<Element>();

    public void Serialize (String fileName, ArrayList<Element> elementsOut)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(elementsOut);
            out.close();
            fileOut.close();
            System.out.printf("File saved to " + fileName + "%n");
            System.out.printf("Number of elements saved =  " + elementsOut.size() + "%n");
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void Deserialize (String fileName)
    {
        Element element = new Element();

        try
        {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            elementsIn = (ArrayList<Element>) in.readObject();
            System.out.printf("File opened from " + fileName + "%n");
            System.out.printf("Number of elements recovered =  " + elementsIn.size() + "%n");
            in.close();
            fileIn.close();
        }
        catch(IOException i)
        {
            i.printStackTrace();
            return;
        }
        catch(ClassNotFoundException c)
        {
            System.out.println("Element class not found");
            c.printStackTrace();
            return;
        }

        for (int i = 0; i < elementsIn.size(); i++)
        {
            element = elementsIn.get(i);
            System.out.printf("Element name loaded =   " + element.name + "%n");
            System.out.printf("Element type =   " + element.elementType + "%n");
            System.out.printf("X coordinate =   " + element.x + "%n");
            System.out.printf("Y coordinate =   " + element.y + "%n");
            controller.createElement(element.name, element.elementType, element.x, element.y);
        }
    }
}

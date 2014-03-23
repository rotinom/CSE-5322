package RocketUML.ui;
import java.io.*;

/**
 * Created by Luke on 3/17/14.
 */
public class Serialization
{
    public void Serialize (String fileName)
    {
        Element e = new Element();
        e.height = 2;
        e.name = "bob";
        e.width = 2;
        e.x = 2;
        e.y = 2;

        try
        {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
            System.out.printf("File saved to " + fileName);
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void Deserialize (String fileName)
    {
        Element e = null;

        try
        {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Element) in.readObject();
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
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.printf("File opened from " + fileName);
    }
}

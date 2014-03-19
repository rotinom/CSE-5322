package RocketUML.serialization;
import java.io.*;

/**
 * Created by Luke on 3/17/14.
 */
public class Serialization
{
    public void Serialize (String fileName)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            //out.writeObject(diagramObject);
            //out.close();
            //fileOut.close();
            System.out.printf("File saved to " + fileName);
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void Deserialize ()
    {

    }
}

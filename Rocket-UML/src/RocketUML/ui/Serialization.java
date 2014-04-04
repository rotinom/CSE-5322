package RocketUML.ui;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Luke on 3/17/14.
 */
public class Serialization
{
    ModelView view = new ModelView();
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
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void Deserialize (String fileName)
    {
        File checkFile = new File(fileName);
        if (checkFile.exists())
        {
            controller.resetDiagramForOpen();

            try
            {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                elementsIn = (ArrayList<Element>) in.readObject();
                System.out.printf("File opened from " + fileName + "%n");
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

            view.drawElementsAfterOpen(elementsIn);
        }
        else
        {
            System.out.printf("File " + fileName + " does not exist. Verify your path and file name. %n");
        }
    }
}

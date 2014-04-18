package RocketUML.ui;
import RocketUML.model.AbstractElement;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Serialization
{
    ModelViewController controller = ModelViewController.getInstance();

    public void Serialize (String fileName, HashMap<String, ArrayList<AbstractElement>> elementsOut)
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
        HashMap<String, ArrayList<AbstractElement>> elementsIn;
        File checkFile = new File(fileName);

        if (checkFile.exists())
        {
            controller.resetDiagramForOpen();

            try
            {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                elementsIn = (HashMap<String, ArrayList<AbstractElement>>) in.readObject();
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
                System.out.println("AbstractElement class not found");
                c.printStackTrace();
                return;
            }

            controller.rebuildElementsArray(elementsIn);
        }
        else
        {
            System.out.printf("File " + fileName + " does not exist. Verify your path and file name. %n");
        }
    }
}

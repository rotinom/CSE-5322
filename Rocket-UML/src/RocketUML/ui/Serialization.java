package RocketUML.ui;
import RocketUML.model.AbstractElement;
import RocketUML.model.ProjectElement;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Serialization
{
    ModelViewController controller = ModelViewController.getInstance();

    public void Serialize (String fileName, ProjectElement projectOut)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(projectOut);
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
        ProjectElement projectIn;
        File checkFile = new File(fileName);

        if (checkFile.exists())
        {
            controller.resetDiagramForOpen();

            try
            {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                projectIn = (ProjectElement)in.readObject();
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

            controller.rebuildProject(projectIn);
        }
        else
        {
            System.out.printf("File " + fileName + " does not exist. Verify your path and file name. %n");
        }
    }
}

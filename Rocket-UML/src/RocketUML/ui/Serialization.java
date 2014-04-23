package RocketUML.ui;
import RocketUML.model.AbstractElement;
import RocketUML.model.DiagramElement;
import RocketUML.model.ProjectElement;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Serialization
{
    ProjectElement projectElement = ProjectElement.getInstance();

    public void Serialize (String fileName, HashMap<String,DiagramElement> diagramsOut)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(diagramsOut);
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
        HashMap<String,DiagramElement> diagramsIn;
        File checkFile = new File(fileName);

        if (checkFile.exists())
        {
            projectElement.resetDiagramForOpen();

            try
            {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                diagramsIn = (HashMap<String,DiagramElement>)in.readObject();
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

            projectElement.rebuildDiagrams(diagramsIn);
        }
        else
        {
            System.out.printf("File " + fileName + " does not exist. Verify your path and file name. %n");
        }
    }
}

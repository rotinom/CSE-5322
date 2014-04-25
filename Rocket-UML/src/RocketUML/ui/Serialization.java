package RocketUML.ui;
import RocketUML.model.*;

import java.io.*;
import java.util.HashMap;

public class Serialization
{
    ProjectElement projectElement = ProjectElement.getInstance();

    private static Serialization instance_ = null;

    private Serialization() {	}

    public static synchronized Serialization getInstance() {
        if (instance_ == null) {
            instance_ = new Serialization ();
        }
        return instance_;
    }

    public void serialize (String fileName)
    {
        HashMap<String,DiagramElement> diagramsOut = projectElement.getDiagrams();
        try
        {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(diagramsOut);
            out.flush();
            out.close();
            fileOut.flush();
            fileOut.close();
            System.out.printf("File saved to " + fileName + "%n");
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void deserialize (String fileName)
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

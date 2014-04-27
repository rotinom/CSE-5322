package RocketUML.model;

import RocketUML.model.AbstractElement;
import RocketUML.model.ClassElement;
import RocketUML.model.RelationshipElement;

import java.util.HashMap;

public class AbstractFactory {
    private static int classCounter = 0;
    private static int relationshipCounter = 0;

	private static final HashMap elements = new HashMap();
	
	public static AbstractElement getElement(String objName)
	{
        AbstractElement containerElement = (AbstractElement) elements.get(objName);

		if (containerElement == null)
		{
			if(objName.equals("Class"))
			{
				containerElement = new ClassElement();
                containerElement.init(0, 0, "New Class " + classCounter++, "");
			}
			if(objName.equals("Relationship"))
			{
				containerElement = new RelationshipElement();
                containerElement.init(0, 0, "New Relationship " + relationshipCounter++, "");
			}
		}
		return containerElement;
	}

    public static void reset() {
        classCounter = 0;
        relationshipCounter = 0;
    }
}

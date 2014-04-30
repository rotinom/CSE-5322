package RocketUML.model;

import java.util.HashMap;

/**
 * Factory for creating specific element types
 */
public class ElementCreator {
    private static int classCounter = 0;
    private static int relationshipCounter = 0;

	private static final HashMap elements = new HashMap();
	
	public static AbstractElement getElement(String objName, int initX, int initY)
	{
        AbstractElement containerElement = (AbstractElement) elements.get(objName);

		if (containerElement == null)
		{
			if(objName.equals("Class"))
			{
				containerElement = new ClassElement();
                containerElement.init(initX, initY, "New Class " + classCounter++, "");
			}
			if(objName.equals("Relationship"))
			{
				containerElement = new RelationshipElement();
                containerElement.init(initX, initY, "New Relationship " + relationshipCounter++, "");
			}
		}
		return containerElement;
	}

    public static void reset() {
        classCounter = 0;
        relationshipCounter = 0;
    }
}

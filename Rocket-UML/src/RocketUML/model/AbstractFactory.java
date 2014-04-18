package RocketUML.model;

import RocketUML.model.AbstractElement;
import RocketUML.model.ClassElement;
import RocketUML.model.RelationshipElement;

import java.util.HashMap;

public class AbstractFactory {
	private static final HashMap elements = new HashMap();
	
	public static AbstractElement getElement(String objName)
	{
        AbstractElement containerElement = (AbstractElement) elements.get(objName);

		if (containerElement == null)
		{
			if(objName.equals("Class"))
			{
				containerElement = new ClassElement();
			}
			if(objName.equals("Relationship"))
			{
				containerElement = new RelationshipElement();
			}
		}
		return containerElement;
	}
}

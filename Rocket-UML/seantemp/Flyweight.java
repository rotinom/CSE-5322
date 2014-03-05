package ged;

import java.util.HashMap;

public class Flyweight {
	private static final HashMap elements = new HashMap();
	
	public static Element getElement(String objName)
	{
		Element containerElement = (Element) elements.get(objName);

		if (containerElement == null)
		{
			if(objName.equals("Class"))
			{
				containerElement = new Class();
			}
			if(objName.equals("Relationship"))
			{
				containerElement = new Relationship();
			}
		}
		return containerElement;
	}
}

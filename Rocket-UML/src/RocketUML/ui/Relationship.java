package RocketUML.ui;

import java.awt.Color;
import java.awt.Graphics;

public class Relationship implements Element {

		private String name;
		
		@Override
		public void Draw(Graphics graphics, int x, int y, int width, int height, String objName) 
		{
			graphics.setColor(Color.GRAY);
			graphics.fillRect(x, y, width+(width/2), height-10);
			graphics.setColor(Color.DARK_GRAY);
			graphics.fillRect(x, y-25, width+(width/2),height/3);

			graphics.setColor(Color.WHITE);
			if(objName != null)
			{
				this.name = objName;
				graphics.drawString(name,x+65,y+0);
			}	
		}
	
}

package ged;

import java.awt.Color;
import java.awt.Graphics;

public class Class implements Element {

	private String name;
	
	@Override
	public void Draw(Graphics graphics, int x, int y, int width, int height, String className) 
	{
		this.name = className;

		graphics.setColor(Color.GRAY);
		graphics.fillRect(x, y, width+(width/2), height-10);
		graphics.setColor(Color.DARK_GRAY);
		graphics.fillRect(x, y-25, width+(width/2),height/3);

		graphics.setColor(Color.WHITE);
		graphics.drawString(name,x+65,y+0);
		
	}

}

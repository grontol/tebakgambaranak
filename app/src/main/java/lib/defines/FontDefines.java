package lib.defines;

import org.andengine.util.color.Color;

import lib.element.ElementFont;

public interface FontDefines 
{	
	int FONT_LEVEL_1 = 0;
	int FONT_LEVEL_2 = 1;
	int FONT_LEVEL_3 = 2;
	int FONT_LEVEL_4 = 3;
	int FONT_LEVEL_5 = 4;

	int FONT_LEVEL_UNLOCK = 5;
	
	final static ElementFont CONTAINER[] = 
	{	
		new ElementFont("font/font.otf", 24, new Color(0.2f, 0.4f, 0)),
		new ElementFont("font/font.otf", 24, new Color(0.1f, 0.37f, 0.33f)),
		new ElementFont("font/font.otf", 24, new Color(0.26f, 0.12f, 0.36f)),
		new ElementFont("font/font.otf", 24, new Color(0.34f, 0.05f, 0.23f)),
		new ElementFont("font/font.otf", 24, new Color(0.4f, 0.4f, 0)),
		
		new ElementFont("font/font.otf", 40, new Color(0.1f, 0.156f, 0.38f)),
	};
}

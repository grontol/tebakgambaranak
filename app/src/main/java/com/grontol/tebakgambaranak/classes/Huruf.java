package com.grontol.tebakgambaranak.classes;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;

public class Huruf 
{
	static GameEngine engine;
	ElementSprite elementSprite;
	GameSprite sprite;
	
	String huruf;
	
	public Huruf(String huruf, GameEngine engine)
	{
		if (Huruf.engine == null)
			Huruf.engine = engine;
			
		this.elementSprite = new ElementSprite(50, 50, "gfx/font/" + huruf + ".png");
		this.sprite = new GameSprite(elementSprite, engine);
		
		this.huruf = huruf;
	}
	
	public String getHuruf()
	{
		return huruf;
	}
	
	public ElementSprite getElementSprite()
	{
		return elementSprite;
	}
	
	public GameSprite getSprite()
	{
		return sprite;
	}
}

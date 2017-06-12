package com.grontol.tebakgambaranak.classes;

import java.util.Random;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;

public class Benda 
{
	static Random r = new Random();
	
	GameEngine engine;
	
	int jmlKata;
	String nama;
	
	ElementSprite elementSprite;
	GameSprite sprite;
	
	Huruf[] hurufs;
	
	boolean hasUsed = false;
	
	public Benda(String nama, GameEngine engine)
	{
		this.engine = engine;
		
		this.jmlKata = nama.length();
		this.nama = nama;
		
		this.elementSprite = new ElementSprite(270, 270, "gfx/imgs/" + nama + ".jpg");
	}
	
	public int getJmlKata()
	{
		return jmlKata;
	}
	
	public String getNama()
	{
		return nama;
	}
	
	public ElementSprite getElementSprite()
	{
		return elementSprite;
	}
	
	public void setHasUsed(boolean hasUsed)
	{
		this.hasUsed = hasUsed;
	}
	
	public boolean getHasUsed()
	{
		return hasUsed;
	}
	
	public GameSprite getSprite()
	{
		if (sprite == null)
			sprite = new GameSprite(elementSprite, engine, 1);
		
		return sprite;
	}
	
	public Huruf[] getHuruf()
	{
		if (hurufs == null)
		{
			hurufs = new Huruf[nama.length() + 2];
			
			for (int a = 0; a < nama.length() + 2; a++)
			{
				if (a >= 2)
					hurufs[a] = new Huruf(String.valueOf(nama.charAt(a - 2)), engine);
				else
				{
					char c;
					
					while (nama.contains(String.valueOf(c = (char)(((int)'a') + r.nextInt(26))))){}
					
					hurufs[a] = new Huruf(String.valueOf(c), engine);
				}
			}
		}
		
		return hurufs;
	}
}

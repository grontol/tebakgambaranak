package com.grontol.tebakgambaranak.classes;

import com.grontol.tebakgambaranak.utility.Button;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;

public class AssetHelper 
{
	static GameSprite backs[];
	static GameSprite bingkais[];
	static GameSprite hitam;
	
	public static GameSprite getBackground(GameEngine engine)
	{
		if (backs == null)
			backs = new GameSprite[5];
		
		int i = G.curLevel;
		
		if (backs[i - 1] == null)
			backs[i - 1] = new GameSprite(new ElementSprite(480, 800, "gfx/play/lv" + i + "/back.png"), engine);
		
		return backs[i - 1];
	}
	
	public static GameSprite getBackgroundTutorial(GameEngine engine)
	{
		if (backs == null)
			backs = new GameSprite[5];
		
		int i = 2;
		
		if (backs[i - 1] == null)
			backs[i - 1] = new GameSprite(new ElementSprite(480, 800, "gfx/play/lv" + i + "/back.png"), engine);
		
		return backs[i - 1];
	}
	
	public static GameSprite getBingkai(GameEngine engine)
	{
		if (bingkais == null)
			bingkais = new GameSprite[5];
		
		int i = G.curLevel;
		
		if (bingkais[i - 1] == null)
			bingkais[i - 1] = new GameSprite(new ElementSprite(284, 284, "gfx/play/lv" + i + "/bingkai.png"), engine);
		
		return bingkais[i - 1];
	}
	
	public static GameSprite getHitam(GameEngine engine)
	{
		if (hitam == null)
			hitam = new GameSprite(new ElementSprite(270, 270, "gfx/play/hitam.png"), engine);
		
		return hitam;
	}
	
	public static Button getCek(GameEngine engine)
	{
		int i = G.curLevel;
		
		return new Button(new ElementSprite(125, 54, "gfx/play/lv" + i + "/cek.png"), 
					new ElementSprite(125, 54, "gfx/play/lv" + i + "/cek_down.png"), engine);
	}
	
	public static Button getCekTutorial(GameEngine engine)
	{
		int i = 2;
		
		return new Button(new ElementSprite(125, 54, "gfx/play/lv" + i + "/cek.png"), 
					new ElementSprite(125, 54, "gfx/play/lv" + i + "/cek_down.png"), engine);
	}
	
	public static GameSprite getCangkang(GameEngine engine)
	{
		int i = G.curLevel;
		
		return new GameSprite(new ElementSprite(58, 58, "gfx/play/lv" + i + "/cangkang.png"), engine);
	}
	
	public static GameSprite getCangkangTutorial(GameEngine engine)
	{
		return new GameSprite(new ElementSprite(58, 58, "gfx/play/lv2/cangkang.png"), engine);
	}
}

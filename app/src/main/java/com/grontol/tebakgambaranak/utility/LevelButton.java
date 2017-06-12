package com.grontol.tebakgambaranak.utility;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;

public class LevelButton extends GameSprite
{
	GameSprite mask;
	GameSprite lock;
	
	boolean isDown;
	boolean isLocked;
	
	public LevelButton(int w, int h, String imgPath, String maskPath, String lockPath, GameEngine engine)
	{
		super(new ElementSprite(w, h, imgPath), engine);
		
		mask = new GameSprite(new ElementSprite(w, h, maskPath), engine);
		lock = new GameSprite(new ElementSprite(w, h, lockPath), engine);
		
		mask.setVisible(false);
		lock.setVisible(false);
	}
	
	@Override
	public void onAttached()
	{
		super.onAttached();
		
		this.attachChild(mask);
		this.attachChild(lock);
	}
	
	@Override
	public void onDetached()
	{
		super.onDetached();
		
		mask.detachSelf();
		lock.detachSelf();
	}
	
	public void setLocked(boolean isLocked)
	{
		lock.setVisible(isLocked);
		
		this.isLocked = isLocked;
	}
	
	public boolean isLocked()
	{
		return isLocked;
	}
	
	public void setDown(boolean isDown)
	{
		if (isLocked)
			return;
		
		mask.setVisible(isDown);
		
		this.isDown = isDown;
	}
	
	public boolean isDown()
	{
		return isDown;
	}
}

package com.grontol.tebakgambaranak.utility;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;

import lib.defines.SpriteDefines;
import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;

public class Button implements SpriteDefines
{
	GameSprite img;
	GameSprite imgDown;

	boolean isDown;
	
	public Button(int imgIndex, int imgDownIndex, GameEngine engine)
	{
		img = new GameSprite(imgIndex, engine);
		imgDown = new GameSprite(imgDownIndex, engine);
		
		imgDown.setVisible(false);
	}
	
	public Button(ElementSprite elSp, ElementSprite elSpD, GameEngine engine)
	{
		img = new GameSprite(elSp, engine);
		imgDown = new GameSprite(elSpD, engine);
		
		imgDown.setVisible(false);
	}
	
	public void attach(Entity e)
	{
		e.attachChild(img);
		e.attachChild(imgDown);
	}

	public void detach()
	{
		img.detachSelf();
		imgDown.detachSelf();
	}
	
	public void setPosition(float x, float y)
	{
		img.setPosition(x, y);
		imgDown.setPosition(x, y);
	}
	
	public void registerTouch(Scene sc)
	{
		sc.registerTouchArea(img);
		sc.registerTouchArea(imgDown);
	}
	
	public void registerEntityModifier(IEntityModifier mod)
	{
		img.registerEntityModifier(mod);
		imgDown.registerEntityModifier(mod);
	}
	
	public void unregisterEntityModifier(IEntityModifier mod)
	{
		img.unregisterEntityModifier(mod);
		imgDown.unregisterEntityModifier(mod);
		
		img.setScale(1);
		imgDown.setScale(1);
	}
	
	public void clearEntityModifier()
	{
		img.clearEntityModifiers();
		imgDown.clearEntityModifiers();
		
		img.setScale(1);
		imgDown.setScale(1);
	}
	
	public void attachChild(Entity e)
	{
		img.attachChild(e);
	}
	
	public void setVisibility(boolean b)
	{
		if (b)
		{
			if (isDown)
			{
				img.setVisible(false);
				imgDown.setVisible(true);
			}
			else
			{
				img.setVisible(true);
				imgDown.setVisible(false);
			}
		}
		else
		{
			img.setVisible(false);
			imgDown.setVisible(false);
		}
	}
	
	public boolean checkTouchArea(ITouchArea t)
	{
		if (t == img || t == imgDown)
			return true;
		else
			return false;
	}
	
	public void setDown(boolean isDown)
	{
		if (isDown)
		{
			img.setVisible(false);
			imgDown.setVisible(true);
		}
		else
		{
			img.setVisible(true);
			imgDown.setVisible(false);
		}
		
		this.isDown = isDown;
	}
	
	public boolean getDown()
	{
		return isDown;
	}
	
	public float getX()
	{
		return img.getX();
	}
	
	public float getY()
	{
		return img.getY();
	}
	
	public float getWidth()
	{
		return img.getWidth();
	}
	
	public float getHeight()
	{
		return img.getHeight();
	}
	
	public GameSprite getImage()
	{
		if (isDown)
			return imgDown;
		else
			return img;
	}
}

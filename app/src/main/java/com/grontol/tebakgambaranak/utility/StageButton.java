package com.grontol.tebakgambaranak.utility;

import com.grontol.tebakgambaranak.classes.G;
import com.grontol.tebakgambaranak.classes.GameManager;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;

public class StageButton extends GameSprite
{
	static ElementSprite[] stars = new ElementSprite[]
	{
		new ElementSprite(1, 23, "gfx/level/stage_star0.png"),
		new ElementSprite(22, 23, "gfx/level/stage_star1.png"),
		new ElementSprite(51, 23, "gfx/level/stage_star2.png"),
		new ElementSprite(79, 23, "gfx/level/stage_star3.png"),
		new ElementSprite(107, 23, "gfx/level/stage_star4.png"),
		new ElementSprite(136, 23, "gfx/level/stage_star5.png"),
	};
	
	GameSprite mask;
	GameSprite lock;
	GameSprite star;
	
	int stage;
	
	boolean isDown;
	boolean isLocked;
	
	GameEngine engine;
	
	public StageButton(int stage, GameEngine engine)
	{
		super(new ElementSprite(167, 115, "gfx/level/stage" + stage + ".png"), engine);
		
		mask = new GameSprite(new ElementSprite(167, 115, "gfx/level/stage_mask.png"), engine);
		lock = new GameSprite(new ElementSprite(167, 115, "gfx/level/stage_lock.png"), engine);
		
		this.stage = stage;
		this.engine = engine;
		
		mask.setVisible(false);
		lock.setVisible(false);
		
		initStar();
	}
	
	private void initStar()
	{
		int c = GameManager.getStarCount(G.curLevel, stage);
		
		star = new GameSprite(stars[c], engine);
	}
	
	@Override
	public void onAttached()
	{
		super.onAttached();
		
		this.attachChild(star);
		this.attachChild(mask);
		this.attachChild(lock);
		
		star.setPosition(15, 75);
	}
	
	@Override
	public void onDetached()
	{
		super.onDetached();
		
		star.detachSelf();
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

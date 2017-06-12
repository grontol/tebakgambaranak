package com.grontol.tebakgambaranak.classes;

import lib.defines.FontDefines;
import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.elementgame.GameText;
import lib.engine.GameEngine;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;

public class GameProgress extends Rectangle implements FontDefines
{
	private int level;
	private int stage;
	private int game;
	
	private GameEngine engine;

	private GameText txtLevel;
	private GameText txtProgress;
	
	private GameSprite left;
	private GameSprite right;
	private GameSprite middle;
	private GameSprite back;
	
	float perStep;
	
	public GameProgress(int level, int stage, GameEngine engine)
	{
		super(0, 0, 400, 20, engine.getVertexBufferObjectManager());
		
		this.level = level;
		this.stage = stage;
		this.game = 1;
		
		this.engine = engine;
		
		this.setColor(Color.TRANSPARENT);
		
		initChild();
	}

	private void initChild()
	{
		txtLevel = new GameText(level + " - " + stage, level - 1, engine);
		txtProgress = new GameText("0/5", level - 1, engine);
		
		left = new GameSprite(new ElementSprite(5, 9, "gfx/play/lv" + level + "/bar_left.png"), engine);
		right = new GameSprite(new ElementSprite(5, 9, "gfx/play/lv" + level + "/bar_right.png"), engine);
		middle = new GameSprite(new ElementSprite(260, 9, "gfx/play/lv" + level + "/bar.png"), engine);
		back = new GameSprite(new ElementSprite(263, 9, "gfx/play/lv" + level + "/bar_back.png"), engine);
		
		this.setHeight(txtLevel.getHeight());
		middle.setWidth(0);
		
		perStep = (back.getWidth() - left.getWidth() - right.getWidth()) / 5;
	}
	
	@Override
	public void onAttached()
	{
		attachChild(txtLevel);
		attachChild(txtProgress);
		
		attachChild(back);
		attachChild(middle);
		attachChild(left);
		attachChild(right);
		
		txtLevel.setPosition(0, 0);
		txtProgress.setPosition(this.getWidth() - txtProgress.getWidth(), this.getHeight() / 2 - txtProgress.getHeight() / 2);
		
		back.setPosition(this.getWidth() / 2 - back.getWidth() / 2, this.getHeight() / 2 - back.getHeight() / 2);
		left.setPosition(back);
		middle.setPosition(left.getX() + left.getWidth(), this.getHeight() / 2 - middle.getHeight() / 2);
		right.setPosition(middle.getX() + middle.getWidth(), this.getHeight() / 2 - right.getHeight() / 2);
		
		super.onAttached();
	}

	@Override
	public void onDetached()
	{
		txtLevel.detachSelf();
		txtProgress.detachSelf();
		
		back.detachSelf();
		middle.detachSelf();
		left.detachSelf();
		right.detachSelf();
		
		super.onDetached();
	}
	
	public void setStage(int stage)
	{
		this.stage = stage;
		
		txtLevel.setText(level + " - " + stage);
	}
	
	public void setGame(int game)
	{
		this.game = game;
		
		txtProgress.setText(this.game + "/5");
		txtProgress.setPosition(this.getWidth() - txtProgress.getWidth(), this.getHeight() / 2 - txtProgress.getHeight() / 2);
		
		middle.setWidth(perStep * this.game);
		right.setPosition(middle.getX() + middle.getWidth(), this.getHeight() / 2 - right.getHeight() / 2);
	}
}

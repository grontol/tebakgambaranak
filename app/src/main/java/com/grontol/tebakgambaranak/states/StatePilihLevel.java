package com.grontol.tebakgambaranak.states;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.elementgame.Sfx;
import lib.engine.GameEngine;
import lib.engine.GameState;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.SingleValueSpanEntityModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.ease.EaseElasticOut;

import android.view.KeyEvent;

import com.grontol.tebakgambaranak.classes.G;
import com.grontol.tebakgambaranak.classes.GameManager;
import com.grontol.tebakgambaranak.classes.SoundManager;
import com.grontol.tebakgambaranak.utility.LevelButton;

public class StatePilihLevel extends GameState 
{
	static final int kotakHeight = 568;
	
	static GameSprite back;
	
	static GameSprite level;
	static GameSprite kotak;
	
	LevelButton[] levels;
	
	LevelButton inDown;

	public StatePilihLevel(GameEngine engine) 
	{
		super(engine);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
		{
			for (int a = 0; a < 5; a++)
			{
				if (pTouchArea == levels[a])
				{
					levels[a].setDown(true);
					inDown = levels[a];
					
					Sfx.Play(SOUND_CLICK);
					break;
				}
			}
		}
		else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP)
		{
			for (int a = 0; a < 5; a++)
			{
				if (inDown == levels[a] && inDown.isDown())
				{
					levels[a].setDown(false);
					
					G.curLevel = a + 1;
					exitState(STATE_PILIH_STAGE);
				}
			}
		}
		else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE)
		{
			if (inDown != null)
			{
				if (pTouchArea == inDown && !inDown.isDown())
					inDown.setDown(true);
				else if (pTouchArea != inDown && inDown.isDown())
					inDown.setDown(false);
			}
		}
		
		return false;
	}

	@Override
	public void initComponent() {
		
		if (back == null)
		{
			if (StateMenu.back != null)
				back = StateMenu.back;
			else
				back = new GameSprite(new ElementSprite(480, 800, "gfx/main_menu/back.png"), engine);
		}
		
		if (level == null)
			level = new GameSprite(new ElementSprite(440, 92, "gfx/level/level.png"), engine);
		
		if (kotak == null)
			kotak = new GameSprite(new ElementSprite(440, 568, "gfx/level/kotak.png"), engine);
		
		levels = new LevelButton[5];
		
		for (int a = 0; a < 5; a++)
		{
			levels[a] = new LevelButton(360, 74, "gfx/level/level" + (a + 1) + ".png", "gfx/level/level_mask.png", "gfx/level/level_lock.png", engine);
			levels[a].setLocked(a >= GameManager.getUnlockedLevel());
		}
	}

	@Override
	public void onActivityLeave()
	{
		SoundManager.onActivityLeave();
	}
	
	@Override
	public void onActivityBack()
	{
		SoundManager.onActivityBack();
	}
	
	@Override
	protected void init() 
	{
		SoundManager.playAwal();
	}

	@Override
	protected void attach() {
		
		engine.scene.attachChild(back);
		
		back.attachChild(kotak);
		back.attachChild(level);
		
		for (int a = 0; a < 5; a++)
		{
			back.attachChild(levels[a]);
		}
	}

	@Override
	protected void detach() 
	{
		engine.runOnUpdateThread(new Runnable()
		{
			@Override
			public void run()
			{
				back.detachSelf();
				kotak.detachSelf();
				level.detachSelf();
				
				for (int a = 0; a < 5; a++)
				{
					levels[a].detachSelf();
				}
			}
		});
	}

	@Override
	protected void destroy() 
	{
		level.clearEntityModifiers();
		kotak.clearEntityModifiers();
	}

	@Override
	protected void setPosition() {
		
		level.setPosition(GameEngine.cameraWidth / 2 - level.getWidth() / 2, 70);
		kotak.setPosition(GameEngine.cameraWidth / 2 - kotak.getWidth() / 2, level.getY() + 78);
		
		for (int a = 0; a < 5; a++)
		{
			levels[a].setPosition(GameEngine.cameraWidth / 2 - levels[a].getWidth() / 2, 190 + a * 100);
		}
		
		level.registerEntityModifier(new SequenceEntityModifier
		(
			new ScaleModifier(0, 1, 0.05f),
			new DelayModifier(0.3f),
			new ScaleModifier(1.5f, 0.05f, 1f, EaseElasticOut.getInstance())
		));
		
		kotak.setHeight(0);
		
		kotak.registerEntityModifier(new SequenceEntityModifier
		(
			new DelayModifier(1f),
			new SingleValueSpanEntityModifier(1.3f, 0, 1, EaseElasticOut.getInstance())
			{
				
				@Override
				public IEntityModifier deepCopy() throws DeepCopyNotSupportedException
				{
					return null;
				}
				
				@Override
				protected void onSetValue(IEntity pItem, float pPercentageDone, float pValue)
				{
					kotak.setHeight(pValue * kotakHeight);
				}
				
				@Override
				protected void onSetInitialValue(IEntity pItem, float pValue)
				{
					kotak.setHeight(pValue * kotakHeight);
				}
			})
		);
		
		for (int a = 0; a < 5; a++)
		{
			levels[a].registerEntityModifier(new SequenceEntityModifier
			(
				new MoveModifier(0, levels[a].getX(), -200, levels[a].getY(), levels[a].getY()),
				new DelayModifier(1.2f + 0.2f * a),
				new MoveModifier(1.2f, -200, levels[a].getX(), levels[a].getY(), levels[a].getY(), EaseElasticOut.getInstance())
			));
		}
	}

	@Override
	protected void registerTouch() 
	{
		for (int a = 0; a < GameManager.getUnlockedLevel(); a++)
		{
			engine.scene.registerTouchArea(levels[a]);
		}
	}

	@Override
	protected void unregisterTouch() 
	{
		
	}

	@Override
	protected void onUpdate() 
	{
		
	}

	@Override
	protected void onPaused() 
	{
		
	}

	@Override
	protected void onResumed() 
	{
		
	}

	@Override
	public void onKeyUp(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Sfx.Play(SOUND_BACK);
			exitState(STATE_MENU);
		}
	}

}

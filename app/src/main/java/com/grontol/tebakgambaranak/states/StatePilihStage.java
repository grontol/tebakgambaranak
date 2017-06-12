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
import com.grontol.tebakgambaranak.utility.StageButton;

public class StatePilihStage extends GameState
{
	static final int kotakHeight = 568;
	
	static GameSprite back;
	
	static GameSprite stage;
	static GameSprite kotak;
	
	StageButton[] stages;
	
	StageButton inDown;
	
	public StatePilihStage(GameEngine engine)
	{
		super(engine);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
		{
			for (int a = 0; a < 6; a++)
			{
				if (pTouchArea == stages[a])
				{
					stages[a].setDown(true);
					inDown = stages[a];
					
					Sfx.Play(SOUND_CLICK);
					break;
				}
			}
		}
		else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP)
		{
			for (int a = 0; a < 6; a++)
			{
				if (inDown == stages[a] && inDown.isDown())
				{
					stages[a].setDown(false);
					
					G.curStage = a + 1;
					G.curGame = GameManager.loadStage(G.curLevel, G.curStage);
					
					exitState(STATE_PLAY);
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
	public void initComponent()
	{
		if (back == null)
		{
			if (StateMenu.back != null)
				back = StateMenu.back;
			else
				back = new GameSprite(new ElementSprite(480, 800, "gfx/main_menu/back.png"), engine);
		}
		
		if (stage == null)
			stage = new GameSprite(new ElementSprite(440, 92, "gfx/level/stage.png"), engine);
		
		if (kotak == null)
		{
			if (StatePilihLevel.kotak != null)
				kotak = StatePilihLevel.kotak;
			else
				kotak = new GameSprite(new ElementSprite(440, 568, "gfx/level/kotak.png"), engine);
		}
		
		stages = new StageButton[6];
		
		for (int a = 0; a < 6; a++)
		{
			stages[a] = new StageButton(a + 1, engine);
			stages[a].setLocked(a >= GameManager.getUnlockedStage(G.curLevel));
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
	protected void attach()
	{
		engine.scene.attachChild(back);
		
		back.attachChild(kotak);
		back.attachChild(stage);
		
		for (int a = 0; a < 6; a++)
		{
			back.attachChild(stages[a]);
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
				stage.detachSelf();
				
				for (int a = 0; a < 6; a++)
				{
					stages[a].detachSelf();
				}
			}
		});
	}

	@Override
	protected void destroy()
	{
		stage.clearEntityModifiers();
		kotak.clearEntityModifiers();
	}

	@Override
	protected void setPosition()
	{
		stage.setPosition(GameEngine.cameraWidth / 2 - stage.getWidth() / 2, 70);
		kotak.setPosition(GameEngine.cameraWidth / 2 - kotak.getWidth() / 2, stage.getY() + 78);
		
		for (int a = 0; a < 3; a++)
		{
			stages[a * 2].setPosition(60, 210 + a * 140);
			stages[a * 2 + 1].setPosition(250, 210 + a * 140);
		}
		
		stage.registerEntityModifier(new SequenceEntityModifier
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
		
		for (int a = 0; a < 3; a++)
		{
			stages[a * 2].registerEntityModifier(new SequenceEntityModifier
			(
				new MoveModifier(0, stages[a * 2].getX(), -200, stages[a * 2].getY(), stages[a * 2].getY()),
				new DelayModifier(1.2f + 0.2f * a),
				new MoveModifier(1.2f, -200, stages[a * 2].getX(), stages[a * 2].getY(), stages[a * 2].getY(), EaseElasticOut.getInstance())
			));
			
			stages[a * 2 + 1].registerEntityModifier(new SequenceEntityModifier
			(
				new MoveModifier(0, stages[a * 2 + 1].getX(), 500, stages[a * 2 + 1].getY(), stages[a * 2 + 1].getY()),
				new DelayModifier(1.2f + 0.2f * a),
				new MoveModifier(1.2f, 500, stages[a * 2 + 1].getX(), stages[a * 2 + 1].getY(), stages[a * 2 + 1].getY(), EaseElasticOut.getInstance())
			));
		}
	}

	@Override
	protected void registerTouch()
	{
		for (int a = 0; a < GameManager.getUnlockedStage(G.curLevel); a++)
		{
			engine.scene.registerTouchArea(stages[a]);
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
	public void onKeyUp(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Sfx.Play(SOUND_BACK);
			exitState(STATE_PILIH_LEVEL);
		}
	}

}

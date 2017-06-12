package com.grontol.tebakgambaranak.states;

import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.ease.EaseElasticOut;

import com.grontol.tebakgambaranak.classes.SoundManager;
import com.grontol.tebakgambaranak.utility.Button;

import android.view.KeyEvent;
import lib.elementgame.GameSprite;
import lib.elementgame.Sfx;
import lib.engine.GameEngine;
import lib.engine.GameState;

public class StateMenu extends GameState
{
	static GameSprite back;
	static GameSprite logo;
	
	GameSprite soundOn;
	GameSprite soundOff;
	
	Button btnMain;
	Button btnPetunjuk;
	Button btnKeluar;

	Button inDown;
	
	public StateMenu(GameEngine engine) {
		super(engine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
	
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
		{
			if (pTouchArea == soundOn && soundOn.isVisible())
			{
				soundOn.setVisible(false);
				soundOff.setVisible(true);
				
				SoundManager.setSoundOn();
				
				return true;
			}
			else if (pTouchArea == soundOff && soundOff.isVisible())
			{
				soundOn.setVisible(true);
				soundOff.setVisible(false);
				
				SoundManager.setSoundOff();
				
				return true;
			}
			else if (pTouchArea == btnMain.getImage())
			{
				btnMain.setDown(true);
				inDown = btnMain;
				
				Sfx.Play(SOUND_CLICK);
			}
			else if (pTouchArea == btnPetunjuk.getImage())
			{
				btnPetunjuk.setDown(true);
				inDown = btnPetunjuk;
				
				Sfx.Play(SOUND_CLICK);
			}
			else if (pTouchArea == btnKeluar.getImage())
			{
				btnKeluar.setDown(true);
				inDown = btnKeluar;
				
				Sfx.Play(SOUND_CLICK);
			}
		}
		else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP)
		{
			if (inDown == btnMain && inDown.getDown())
			{
				btnMain.setDown(false);
				exitState(STATE_PILIH_LEVEL);
			}
			else if (inDown == btnPetunjuk && inDown.getDown())
			{
				btnPetunjuk.setDown(false);
				exitState(STATE_PETUNJUK);
			}
			else if (inDown == btnKeluar && inDown.getDown())
			{
				btnKeluar.setDown(false);
				engine.finish();
				engine.endGame();
			}
			
			inDown = null;
		}
		else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE)
		{
			if (inDown != null)
			{
				if (inDown.checkTouchArea(pTouchArea) && !inDown.getDown())
					inDown.setDown(true);
				else if (!inDown.checkTouchArea(pTouchArea) && inDown.getDown())
					inDown.setDown(false);
			}
		}
		
		return false;
	}

	@Override
	public void initComponent() 
	{
		if (back == null)
			back = new GameSprite(SPRITE_MENU_BACK, engine);
		
		if (logo == null)
			logo = new GameSprite(SPRITE_MENU_LOGO, engine);
		
		soundOn = new GameSprite(SPRITE_SOUND_ON, engine);
		soundOff = new GameSprite(SPRITE_SOUND_OFF, engine);
		
		btnMain = new Button(SPRITE_MENU_MAIN, SPRITE_MENU_MAIN_DOWN, engine);
		btnPetunjuk = new Button(SPRITE_MENU_PETUNJUK, SPRITE_MENU_PETUNJUK_DOWN, engine);
		btnKeluar = new Button(SPRITE_MENU_KELUAR, SPRITE_MENU_KELUAR_DOWN, engine);
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
		
		if (SoundManager.isSoundOn())
		{
			soundOn.setVisible(false);
			soundOff.setVisible(true);
		}
		else
		{
			soundOn.setVisible(true);
			soundOff.setVisible(false);
		}
	}

	@Override
	protected void attach() {
		
		engine.scene.attachChild(back);
		back.attachChild(logo);
		
		back.attachChild(soundOn);
		back.attachChild(soundOff);
		
		btnMain.attach(back);
		btnPetunjuk.attach(back);
		btnKeluar.attach(back);
	}

	@Override
	protected void detach() {
		
		engine.runOnUpdateThread(new Runnable()
		{
			@Override
			public void run()
			{
				back.detachSelf();
				logo.detachSelf();
				
				soundOn.detachSelf();
				soundOff.detachSelf();
				
				btnMain.detach();
				btnPetunjuk.detach();
				btnKeluar.detach();
			}
		});
	}

	@Override
	protected void destroy() {
		// TODO Auto-generated method stub
		logo.clearEntityModifiers(); 
	}

	@Override
	protected void setPosition() {
		
		logo.setPosition(GameEngine.cameraWidth / 2 - logo.getWidth() / 2, 220 - logo.getHeight() / 2);
		
		soundOn.setPosition(GameEngine.cameraWidth - soundOn.getWidth() - 15, 15);
		soundOff.setPosition(soundOn);
		
		btnMain.setPosition(GameEngine.cameraWidth / 2 - btnMain.getWidth() / 2, 440);
		btnPetunjuk.setPosition(GameEngine.cameraWidth / 2 - btnPetunjuk.getWidth() / 2, 530);
		btnKeluar.setPosition(GameEngine.cameraWidth / 2 - btnKeluar.getWidth() / 2, 620);
		
		soundOn.registerEntityModifier(new SequenceEntityModifier
		(
			new ScaleModifier(0, 1, 0.5f),
			new DelayModifier(0.5f),
			new ScaleModifier(1.5f, 0.3f, 1, EaseElasticOut.getInstance())		
		));
		
		soundOff.registerEntityModifier(new SequenceEntityModifier
		(
			new ScaleModifier(0, 1, 0.5f),
			new DelayModifier(0.5f),
			new ScaleModifier(1.5f, 0.3f, 1, EaseElasticOut.getInstance())		
		));
		
		logo.registerEntityModifier(new SequenceEntityModifier
		(
			new ScaleModifier(0, 1, 0.5f),
			new DelayModifier(0.5f),
			new ScaleModifier(1.5f, 0.3f, 1, EaseElasticOut.getInstance())		
		));
		
		btnMain.registerEntityModifier(new SequenceEntityModifier
		(
			new MoveModifier(0, btnMain.getX(), btnMain.getX(), btnMain.getY(), 1000),
			new DelayModifier(0.5f),
			new MoveModifier(3.5f, btnMain.getX(), btnMain.getX(), 1000, btnMain.getY(), EaseElasticOut.getInstance())
		));
		
		btnPetunjuk.registerEntityModifier(new SequenceEntityModifier
		(
			new MoveModifier(0, btnPetunjuk.getX(), btnPetunjuk.getX(), btnPetunjuk.getY(), 1000),
			new DelayModifier(1.3f),
			new MoveModifier(3.5f, btnPetunjuk.getX(), btnPetunjuk.getX(), 1000, btnPetunjuk.getY(), EaseElasticOut.getInstance())
		));
		
		btnKeluar.registerEntityModifier(new SequenceEntityModifier
		(
			new MoveModifier(0, btnKeluar.getX(), btnKeluar.getX(), btnKeluar.getY(), 1000),
			new DelayModifier(2.1f),
			new MoveModifier(3.5f, btnKeluar.getX(), btnKeluar.getX(), 1000, btnKeluar.getY(), EaseElasticOut.getInstance())
		));
	}

	@Override
	protected void registerTouch() {
		
		engine.scene.registerTouchArea(back);
		
		engine.scene.registerTouchArea(soundOn);
		engine.scene.registerTouchArea(soundOff);
		
		btnMain.registerTouch(engine.scene);
		btnPetunjuk.registerTouch(engine.scene);
		btnKeluar.registerTouch(engine.scene);
	}

	@Override
	protected void unregisterTouch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResumed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onKeyUp(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Sfx.Play(SOUND_BACK);
			
			engine.finish();
			engine.endGame();
		}
		
	}

}

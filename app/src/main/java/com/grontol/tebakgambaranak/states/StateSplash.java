package com.grontol.tebakgambaranak.states;

import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;
import lib.elementgame.GameSprite;
import lib.engine.GameEngine;
import lib.engine.GameState;

public class StateSplash extends GameState
{
	long curTime;
	float alpha;
	
	GameSprite back;
	
	public StateSplash(GameEngine engine) {
		super(engine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initComponent() {
		
		curTime = System.currentTimeMillis();
		
		back = new GameSprite(SPRITE_ABABIL, engine, 1);
	}

	@Override
	public void onActivityLeave()
	{
	}
	
	@Override
	public void onActivityBack()
	{
	}
	
	@Override
	protected void init() {
		
		alpha = 0f;
	}

	@Override
	protected void attach() {

		engine.scene.attachChild(back);
	}

	@Override
	protected void detach() {

		back.detachSelf();
	}

	@Override
	protected void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setPosition() {
		
		back.setPosition(GameEngine.cameraWidth / 2 - back.getWidth() / 2, GameEngine.cameraHeight / 2 - back.getHeight() / 2);
		
	}

	@Override
	protected void registerTouch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void unregisterTouch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUpdate() {
		
		if (System.currentTimeMillis() - curTime < 3000)
		{
			if (alpha < 0.99)
			{
				alpha += 0.01;			
				back.setAlpha(alpha);
			}
		}
		else
		{
			if (alpha > 0)
			{
				alpha -= 0.02;			
				back.setAlpha(alpha);
			}
			
			if (alpha <= 0)
				exitState(STATE_MENU);
		}
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
			engine.finish();
			engine.endGame();
		}
	}
	
}

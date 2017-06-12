package com.grontol.tebakgambaranak.states;

import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;
import lib.engine.GameEngine;
import lib.engine.GameState;

public class LoadingState extends GameState
{

	public LoadingState(GameEngine engine)
	{
		super(engine);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		return false;
	}

	@Override
	public void initComponent()
	{
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
	protected void init()
	{
	}

	@Override
	protected void attach()
	{
	}

	@Override
	protected void detach()
	{
	}

	@Override
	protected void destroy()
	{
	}

	@Override
	protected void setPosition()
	{
	}

	@Override
	protected void registerTouch()
	{
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
	}

}

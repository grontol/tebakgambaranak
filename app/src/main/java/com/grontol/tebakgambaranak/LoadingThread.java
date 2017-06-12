package com.grontol.tebakgambaranak;

import lib.defines.GameDefines;
import lib.engine.GameEngine;
import android.os.AsyncTask;

public class LoadingThread extends AsyncTask<Integer, GameEngine, Void> implements GameDefines{

	GameEngine engine;
	
	public LoadingThread(GameEngine engine)
	{
		this.engine = engine;
	}
	
	@Override
	protected Void doInBackground(Integer... params) {

		/*if (params[0] == STATE_STICKMAN_PLAY)
			StV.Init(engine);
		else if (params[0] == STATE_BEAR_PLAY)
			BrV.Init(engine);
		else if (params[0] == STATE_CHARCHOOSE)
			CcV.Init(engine);
		
		EvVar.isFinishedLoading = true;*/
		return null;
	}
}

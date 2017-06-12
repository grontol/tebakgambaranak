package com.grontol.tebakgambaranak;

import com.grontol.tebakgambaranak.classes.BendaHelper;
import com.grontol.tebakgambaranak.states.StateMenu;
import com.grontol.tebakgambaranak.states.StatePetunjuk;
import com.grontol.tebakgambaranak.states.StatePilihLevel;
import com.grontol.tebakgambaranak.states.StatePilihStage;
import com.grontol.tebakgambaranak.states.StatePlay;
import com.grontol.tebakgambaranak.states.StateSplash;

import lib.defines.GameDefines;
import lib.engine.GameEngine;
import lib.engine.GameState;

public class MainMenu extends GameEngine implements GameDefines{

	GameState curState;
	
	@Override
	protected void gameInit() {
		
		BendaHelper.loadBenda(this);
		
		curState = new StateMenu(this);
	}
	
	@Override
	protected GameState getCurState()
	{
		return curState;
	}

	@Override
	public void changeState(int nextState) {
		
		if (nextState == STATE_SPLASH)
			curState = new StateSplash(this);
		else if (nextState == STATE_MENU)
			curState = new StateMenu(this);
		else if (nextState == STATE_PLAY)
			curState = new StatePlay(this);
		else if (nextState == STATE_PILIH_LEVEL)
			curState = new StatePilihLevel(this);
		else if (nextState == STATE_PETUNJUK)
			curState = new StatePetunjuk(this);
		else if (nextState == STATE_PILIH_STAGE)
			curState = new StatePilihStage(this);
		
		super.changeState(nextState);
	}
}


















package com.grontol.tebakgambaranak.classes;

import lib.engine.GameEngine;

public class ProgressMaker
{
	private static GameProgress[] progresses;
	
	public static GameProgress getProgress(GameEngine engine)
	{
		if (progresses == null)
			progresses = new GameProgress[5];
		
		int i = G.curLevel;
		int s = G.curStage;
		
		if (progresses[i - 1] == null)
			progresses[i - 1] = new GameProgress(i, s, engine);
		
		progresses[i - 1].setStage(s);
		progresses[i - 1].setGame(GameManager.loadStage(i, s));
		
		return progresses[i - 1];
	}
}

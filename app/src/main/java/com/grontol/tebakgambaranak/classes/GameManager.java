package com.grontol.tebakgambaranak.classes;

import lib.defines.TableDefine;
import lib.elementgame.GameDatabase;
import lib.engine.GameEngine;

public class GameManager implements TableDefine
{
	static GameDatabase db;
	
	public static void Init(GameEngine engine)
	{
		db = new GameDatabase(engine, CONTAINER);
	}
	
	// Writing Data =======================================
	public static void saveStage()
	{
		if (G.curGame < 5)
		{
			G.curGame++;
			
			db.updateData("stage", new String[] {"value"}, new String[] { String.valueOf(G.curGame) }, 
				"WHERE level = '" + G.curLevel + "' AND stage = '" + G.curStage + "'");
		}
		else
		{
			db.updateData("stage", new String[] {"value", "complete"}, new String[] { "1", "1" }, 
					"WHERE level = '" + G.curLevel + "' AND stage = '" + G.curStage + "'");
			
			if (G.curStage < 6)
			{
				unlockStage(G.curLevel, G.curStage + 1);
			}
			
			if (G.curStage == 5 && G.curLevel < 5)
			{
				unlockLevel(G.curLevel + 1);
			}
		}
	}
	
	private static void unlockStage(int level, int stage)
	{
		db.updateData("stage", new String[] { "unlock" }, new String[] { "1" }, 
				"WHERE level = '" + level + "' AND stage = '" + stage + "'");
	}
	
	private static void unlockLevel(int level)
	{
		db.updateData("level", new String[] { "unlock" }, new String[] { "1" }, 
				"WHERE level = '" + level + "'");
	}
	// ====================================================
	
	// Reading Data ========================================
	public static int getUnlockedLevel()
	{
		String q = "SELECT MAX(level) FROM level WHERE unlock = '1'";
		
		String[][] s = db.getDataQuery(q);
		
		return Integer.parseInt(s[0][0]);
	}
	
	public static int getUnlockedStage(int level)
	{
		String q = "SELECT MAX(stage) FROM stage WHERE unlock = '1' AND level = '" + level + "'";
		
		String[][] s = db.getDataQuery(q);
		
		return Integer.parseInt(s[0][0]);
	}
	
	public static int loadStage(int level, int stage)
	{
		String q = "SELECT value FROM stage WHERE level = '" + level + "' AND stage = '" + stage + "'";
		
		String[][] s = db.getDataQuery(q);
		
		return Integer.parseInt(s[0][0]);
	}
	
	public static boolean isLevelCompleted(int level)
	{
		String q = "SELECT complete FROM level WHERE level = '" + level + "'";
		
		String[][] s = db.getDataQuery(q);
		
		return Integer.parseInt(s[0][0]) == 1;
	}
	
	public static boolean isStageCompleted(int level, int stage)
	{
		String q = "SELECT complete FROM stage WHERE level = '" + level + "' AND stage = '" + stage + "'";
		
		String[][] s = db.getDataQuery(q);
		
		return Integer.parseInt(s[0][0]) == 1;
	}
	
	public static int getStarCount(int level, int stage)
	{
		if (isStageCompleted(level, stage))
		{
			return 5;
		}
		else
		{
			return loadStage(level, stage) - 1;
		}
	}
	
	public static boolean isToShowLevelUnlocked()
	{
		if (G.curLevel == 5)
			return false;
		
		if (G.curStage != 5)
			return false;
		
		if (G.curGame != 5)
			return false;
		
		String q = "SELECT unlock FROM level WHERE level = '" + (G.curLevel + 1) + "'";
		
		String[][] s = db.getDataQuery(q);
		
		return s[0][0].equals("0");
	}
	
	public static boolean isToShowStageUnlocked()
	{
		if (G.curStage == 6)
			return false;
		
		if (G.curGame != 5)
			return false;
		
		String q = "SELECT unlock FROM stage WHERE level = '" + G.curLevel + "' AND stage = '" + (G.curStage + 1) + "'";
		
		String[][] s = db.getDataQuery(q);
		
		return s[0][0].equals("0");
	}
	// =================================================
}

package com.grontol.tebakgambaranak.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.engine.GameEngine;

import android.content.res.AssetManager;

public class BendaHelper 
{
	static Random random = new Random();
	
	static List<String> tigas = new ArrayList<String>();
	static List<String> empats = new ArrayList<String>();
	static List<String> limas = new ArrayList<String>();
	static List<String> enams = new ArrayList<String>();
	static List<String> tujuhs = new ArrayList<String>();
	
	static GameEngine engine;
	
	static public Benda getBenda()
	{
		Benda res = null;
		
		int length = G.curLevel + 2;
		int val = (G.curGame - 1) + (G.curStage - 1) * 5;
		
		if (length == 3)
		{
			res = new Benda(tigas.get(val), engine);
		}
		else if (length == 4)
		{
			res = new Benda(empats.get(val), engine);
		}
		else if (length == 5)
		{
			res = new Benda(limas.get(val), engine);
		}
		else if (length == 6)
		{
			res = new Benda(enams.get(val), engine);
		}
		else if (length == 7)
		{
			res = new Benda(tujuhs.get(val), engine);
		}
		
		return res;
	}

	public static Benda getPetunjukBenda()
	{
		return new Benda(empats.get(0), engine);
	}
	
	static public void loadBenda(GameEngine context)
	{
		GameManager.Init(context);
		BendaHelper.engine = context;
		
		AssetManager am = context.getAssets();
		
		try 
		{
			InputStream is = am.open("benda.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String s;
			
			while((s = br.readLine()) != null)
			{
				if (s.length() == 3)
				{
					tigas.add(s);
				}
				else if (s.length() == 4)
				{
					empats.add(s);
				}
				else if (s.length() == 5)
				{
					limas.add(s);
				}
				else if (s.length() == 6)
				{
					enams.add(s);
				}
				else if (s.length() == 7)
				{
					tujuhs.add(s);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}

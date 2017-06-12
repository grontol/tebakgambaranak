package com.grontol.tebakgambaranak.classes;

import java.util.Random;

import lib.defines.MfxDefines;
import lib.defines.SfxDefines;
import lib.elementgame.Mfx;
import lib.elementgame.Sfx;

public class SoundManager implements MfxDefines, SfxDefines
{
	private static Random r = new Random();
	
	private static int[] benars;
	private static int[] salahs;
	
	private static boolean soundOn = true;
	private static boolean soundPause = false;
	
	private static int curPlaying = -1;
	
	static
	{
		benars = new int[4];
		salahs = new int[2];
		
		benars[0] = SOUND_BENAR1;
		benars[1] = SOUND_BENAR2;
		benars[2] = SOUND_BENAR3;
		benars[3] = SOUND_BENAR4;
		
		salahs[0] = SOUND_SALAH1;
		salahs[1] = SOUND_SALAH2;
	}
	
	public static void playBenar()
	{
		int n = r.nextInt(benars.length);
		
		Sfx.Play(benars[n]);
	}
	
	public static void playSalah()
	{
		int n = r.nextInt(salahs.length);
		
		Sfx.Play(salahs[n]);
	}
	
	public static void playAwal()
	{
		if (soundOn)
		{
			if (curPlaying != MFX_AWAL)
			{
				if (curPlaying != -1)
				{
					Mfx.Pause(curPlaying);
				}
				
				Mfx.seekTo(MFX_AWAL, 0);
				Mfx.setVolume(MFX_AWAL, 0.6f);
				Mfx.Play(MFX_AWAL);
			}
		}
		
		curPlaying = MFX_AWAL;
	}
	
	public static void playMain()
	{
		if (soundOn)
		{
			if (curPlaying != MFX_MAIN)
			{
				if (curPlaying != -1)
				{
					Mfx.Pause(curPlaying);
				}
				
				Mfx.seekTo(MFX_MAIN, 0);
				Mfx.setVolume(MFX_MAIN, 0.6f);
				Mfx.Play(MFX_MAIN);
			}
		}
		
		curPlaying = MFX_MAIN;
	}
	
	public static void setSoundOff()
	{
		if (curPlaying != -1)
		{
			Mfx.Pause(curPlaying);
		}
		
		soundOn = false;
	}
	
	public static void setSoundOn()
	{
		if (curPlaying != -1)
		{
			Mfx.seekTo(curPlaying, 0);
			Mfx.Play(curPlaying);
		}
		
		soundOn = true;
	}
	
	public static boolean isSoundOn()
	{
		return soundOn;
	}
	
	public static void onActivityLeave()
	{
		if (curPlaying != -1)
		{
			Mfx.Pause(curPlaying);
		}
		
		soundPause = true;
	}
	
	public static void onActivityBack()
	{
		if (curPlaying != -1 && soundPause)
		{
			Mfx.Play(curPlaying);
		}
		
		soundPause = false;
	}
}

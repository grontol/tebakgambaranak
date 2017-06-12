package com.grontol.tebakgambaranak.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.element.ElementSprite;
import lib.elementgame.GameAnim;
import lib.elementgame.GameSprite;
import lib.elementgame.Sfx;
import lib.engine.GameEngine;
import lib.engine.GameState;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseElasticOut;

import android.view.KeyEvent;

import com.grontol.tebakgambaranak.classes.AssetHelper;
import com.grontol.tebakgambaranak.classes.Benda;
import com.grontol.tebakgambaranak.classes.BendaHelper;
import com.grontol.tebakgambaranak.classes.G;
import com.grontol.tebakgambaranak.classes.GameManager;
import com.grontol.tebakgambaranak.classes.GameProgress;
import com.grontol.tebakgambaranak.classes.Huruf;
import com.grontol.tebakgambaranak.classes.ProgressMaker;
import com.grontol.tebakgambaranak.classes.SoundManager;
import com.grontol.tebakgambaranak.utility.Button;
import com.grontol.tebakgambaranak.utility.LevelUnlock;

public class StatePlay extends GameState
{
	Random r = new Random();

	GameSprite rects[];

	Benda benda;

	GameSprite back;
	GameSprite gambar;
	GameSprite bingkai;
	GameSprite hitam;

	Button cek;

	Huruf[] hurufs;

	GameSprite benar;
	GameSprite salah;

	// Stage Complete =======
	GameSprite scBack;
	GameAnim scBintang;

	Button inDown;

	// Level unlock =====
	LevelUnlock levelUnlock;
	
	// Movement Vars =========
	Huruf[] jawab;

	Huruf inMove;
	float difX;
	float difY;

	float souX;
	float souY;

	boolean isOver = false;
	boolean isLevelCompleted = false;

	long lastTime;

	// Cek Completion =======
	IEntityModifier mod;
	boolean isAnimating = false;

	// Progress Game =======
	GameProgress progress;

	public StatePlay(GameEngine engine)
	{
		super(engine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{

		if (isLevelCompleted)
		{
			
		}
		else if (!isOver)
		{
			if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
			{
				if (pTouchArea == cek.getImage())
				{
					cek.setDown(true);
					inDown = cek;

					Sfx.Play(SOUND_CLICK);
				}

				for (Huruf s : hurufs)
				{
					if (pTouchArea == s.getSprite())
					{
						inMove = s;

						difX = pSceneTouchEvent.getX() - s.getSprite().getX();
						difY = pSceneTouchEvent.getY() - s.getSprite().getY();

						souX = s.getSprite().getX();
						souY = s.getSprite().getY();

						// Playing Sound ==
						Sfx.Play(SOUND_PICK);

						break;
					}
				}
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

				if (inMove != null)
				{
					float x = pSceneTouchEvent.getX() - difX;
					float y = pSceneTouchEvent.getY() - difY;

					if (x < 28)
						x = 28;
					else if (x + inMove.getSprite().getWidth() > 454)
						x = 454 - inMove.getSprite().getWidth();

					if (y < 465)
						y = 465;
					else if (y + inMove.getSprite().getHeight() > 697)
						y = 697 - inMove.getSprite().getHeight();

					inMove.getSprite().setPosition(x, y);
				}

			}
			else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP)
			{
				if (inDown == cek && inDown.getDown())
				{
					cek.setDown(false);

					checkBenar();
					isOver = true;

					lastTime = System.currentTimeMillis();
				}

				if (inMove != null)
				{
					boolean isDrop = false;

					for (int a = 0; a < rects.length; a++)
					{
						if (inMove.getSprite().collidesWith(rects[a]))
						{
							int moveTo = -1;

							if (a < rects.length - 1 && inMove.getSprite().collidesWith(rects[a + 1]))
							{
								float div1 = rects[a].getX() + rects[a].getWidth() - inMove.getSprite().getX();
								float div2 = inMove.getSprite().getX() + inMove.getSprite().getWidth() - rects[a + 1].getX();

								if (div1 >= div2 && (jawab[a] == null || jawab[a] == inMove))
									moveTo = a;
								else if (jawab[a + 1] == null || jawab[a + 1] == inMove)
									moveTo = a + 1;
							}
							else if (jawab[a] == null)
								moveTo = a;

							if (moveTo >= 0)
								setHurufPosition(moveTo);
							else
								inMove.getSprite().setPosition(souX, souY);

							// Play Sound ==
							Sfx.Play(SOUND_DROP);
							isDrop = true;

							break;
						}
					}

					// Play throw sound ==
					if (!isDrop)
						Sfx.Play(SOUND_THROW);

					for (int a = 0; a < rects.length; a++)
					{
						if (jawab[a] != null && !jawab[a].getSprite().collidesWith(rects[a]))
							jawab[a] = null;
					}

					inMove = null;

					cekComplete();
				}
			}
		}

		return false;
	}

	private void setHurufPosition(int i)
	{
		inMove.getSprite().setPosition(rects[i].getX() + 4, rects[i].getY() + 4);

		jawab[i] = inMove;
	}

	private void cekComplete()
	{
		if (mod == null)
		{
			mod = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.6f, 1.1f, 0.9f), new ScaleModifier(0.6f, 0.9f, 1.1f)));
		}

		boolean b = true;

		for (int a = 0; a < jawab.length; a++)
		{
			if (jawab[a] == null)
			{
				b = false;
				break;
			}
		}

		if (b)
		{
			if (!isAnimating)
			{
				cek.registerEntityModifier(mod);
				isAnimating = true;
			}
		}
		else
		{
			cek.unregisterEntityModifier(mod);
			isAnimating = false;
		}
	}

	private void checkBenar()
	{
		if (mod != null)
			cek.unregisterEntityModifier(mod);

		StringBuilder sb = new StringBuilder();

		for (int a = 0; a < jawab.length; a++)
		{
			if (jawab[a] != null)
				sb.append(jawab[a].getHuruf());

		}

		// Kalau Benar jawabannya ---
		if (sb.toString().equals(benda.getNama()))
		{
			final boolean showStageUnlocked = GameManager.isToShowStageUnlocked();
			final boolean showLevelUnlocked = GameManager.isToShowLevelUnlocked();

			// Play Win Sound ==
			hitam.setVisible(true);
			benar.setVisible(true);

			benar.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(1f, .05f, 1, EaseElasticOut.getInstance()), new DelayModifier(1f, new IEntityModifierListener()
			{
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
				{
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
				{
//					if (!showLevelUnlocked && !showStageUnlocked)
//					{
						if (G.curGame < 5)
							exitState(STATE_PLAY);
						else if (G.curStage < 6)
							exitState(STATE_PILIH_STAGE);
						else
							exitState(STATE_PILIH_LEVEL);
//					}
						
						GameManager.saveStage();
				}
			})));

			SoundManager.playBenar();

			// Animasi Stage Unlocked
			if (showStageUnlocked)
			{
				isLevelCompleted = true;

//				hitam.setVisible(true);
//				scBack.setVisible(true);
//				scBintang.setVisible(true);
//
//				scBack.registerEntityModifier(new SequenceEntityModifier
//				(
//					new ScaleModifier(1.5f, 0.05f, 1f, EaseElasticOut.getInstance()), 
//					new DelayModifier(3f, new IEntityModifierListener()
//					{
//						@Override
//						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
//						{
//						}
//	
//						@Override
//						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
//						{
//							if (!showLevelUnlocked)
//								exitState(STATE_PILIH_STAGE);
//						}
//					}
//				)));
//
//				scBintang.registerEntityModifier(new DelayModifier(1, new IEntityModifier.IEntityModifierListener()
//				{
//
//					@Override
//					public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
//					{
//					}
//
//					@Override
//					public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
//					{
//						scBintang.animate(250, false);
//					}
//				}));

				// Animasi Level kebuka ========
				if (showLevelUnlocked)
				{
//					levelUnlock = new LevelUnlock(G.curLevel + 1, engine);
//					back.attachChild(levelUnlock);
//					levelUnlock.setPosition(back.getWidth() / 2 - levelUnlock.getWidth() / 2, 
//							back.getHeight() / 2 - levelUnlock.getHeight() / 2);
//					
//					levelUnlock.registerEntityModifier(new SequenceEntityModifier
//					(
//						new ScaleModifier(1.5f, 0.05f, 1f, EaseElasticOut.getInstance()), 
//						new DelayModifier(3f, new IEntityModifierListener()
//						{
//							@Override
//							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
//							{
//							}
//		
//							@Override
//							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
//							{
//								exitState(STATE_PILIH_STAGE);
//							}
//						}
//					)));
				}
			}

			
		}
		// Kalau Salah ===
		else
		{
			hitam.setVisible(true);
			salah.setVisible(true);

			salah.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(1f, .05f, 1, EaseElasticOut.getInstance()), new DelayModifier(1f, new IEntityModifierListener()
			{

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
				{
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
				{
					exitState(STATE_PLAY);
				}
			})));

			// Play False Sound ==
			SoundManager.playSalah();
		}
	}

	@Override
	public void initComponent()
	{
		// Level COmpleted =======
		scBack = new GameSprite(new ElementSprite(432, 377, "gfx/play/sc_back.png"), engine);
		scBintang = new GameAnim(ANIM_BINTANG, engine);

		// Main ================
		back = AssetHelper.getBackground(engine);
		benda = BendaHelper.getBenda();
		gambar = benda.getSprite();
		bingkai = AssetHelper.getBingkai(engine);
		hitam = AssetHelper.getHitam(engine);

		cek = AssetHelper.getCek(engine);

		hurufs = new Huruf[benda.getNama().length() + 2];
		jawab = new Huruf[benda.getNama().length()];

		for (int a = 0; a < hurufs.length; a++)
		{
			hurufs[a] = benda.getHuruf()[a];
		}

		rects = new GameSprite[benda.getNama().length()];

		for (int a = 0; a < rects.length; a++)
		{
			rects[a] = AssetHelper.getCangkang(engine);
		}

		benar = new GameSprite(SPRITE_BENAR, engine);
		salah = new GameSprite(SPRITE_SALAH, engine);

		progress = ProgressMaker.getProgress(engine);
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
		SoundManager.playMain();

		hitam.setVisible(false);
		benar.setVisible(false);
		salah.setVisible(false);

		scBack.setVisible(false);
		scBintang.setVisible(false);
	}

	@Override
	protected void attach()
	{
		engine.scene.attachChild(back);
		back.attachChild(gambar);

		gambar.attachChild(bingkai);
		gambar.attachChild(hitam);
		gambar.attachChild(benar);
		gambar.attachChild(salah);

		cek.attach(back);

		for (int a = 0; a < rects.length; a++)
		{
			back.attachChild(rects[a]);
		}

		for (int a = 0; a < hurufs.length; a++)
		{
			back.attachChild(hurufs[a].getSprite());
		}

		engine.scene.attachChild(scBack);
		scBack.attachChild(scBintang);

		back.attachChild(progress);
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
				gambar.detachSelf();
				bingkai.detachSelf();
				hitam.detachSelf();

				benar.detachSelf();
				salah.detachSelf();

				cek.detach();

				for (int a = 0; a < rects.length; a++)
				{
					rects[a].detachSelf();
				}

				for (int a = 0; a < hurufs.length; a++)
				{
					hurufs[a].getSprite().detachSelf();
				}

				if (scBack != null)
				{
					scBack.detachSelf();
					scBintang.detachSelf();
				}

				progress.detachSelf();
				
				if (levelUnlock != null)
					levelUnlock.detachSelf();
			}
		});
	}

	@Override
	protected void destroy()
	{
		
	}

	@Override
	protected void setPosition()
	{
		gambar.setPosition(GameEngine.cameraWidth / 2 - gambar.getWidth() / 2, 80);
		bingkai.setPosition(-7, -7);

		gambar.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(1.5f, 0.1f, 1, EaseElasticOut.getInstance())));

		cek.setPosition(GameEngine.cameraWidth / 2 - cek.getWidth() / 2, 365);

		for (int a = 0; a < rects.length; a++)
		{
			float x = 40 + ((GameEngine.cameraWidth - 80 - rects[a].getWidth()) / (rects.length - 1)) * a;

			rects[a].setPosition(x, 625);

			rects[a].registerEntityModifier(new SequenceEntityModifier(new MoveModifier(0, rects[a].getX(), 600, rects[a].getY(), rects[a].getY()), new DelayModifier(a * 0.2f), new MoveModifier(1f,
					600, rects[a].getX(), rects[a].getY(), rects[a].getY(), EaseBackOut.getInstance())));
		}

		Huruf[] h = new Huruf[hurufs.length];
		List<Integer> li = new ArrayList<Integer>();

		for (int a = 0; a < hurufs.length; a++)
		{
			li.add(a);
		}

		for (int a = 0; a < h.length; a++)
		{
			int i = r.nextInt(li.size());

			h[a] = hurufs[li.get(i)];
			li.remove(i);
		}

		if (h.length <= 6)
		{
			for (int a = 0; a < h.length; a++)
			{
				int x = (int) (50 + ((GameEngine.cameraWidth - 100 - hurufs[a].getSprite().getWidth()) / (hurufs.length - 1)) * a);

				h[a].getSprite().setPosition(x, 510);
			}
		}
		else
		{
			for (int a = 0; a < h.length; a++)
			{
				if (a < h.length / 2)
				{
					int x = (int) (50 + ((GameEngine.cameraWidth - 100 - hurufs[a].getSprite().getWidth()) / (hurufs.length / 2 - 1)) * a);
					h[a].getSprite().setPosition(x, 480);
				}
				else
				{
					int x = (int) (50 + ((GameEngine.cameraWidth - 100 - hurufs[a].getSprite().getWidth()) / ((hurufs.length - (hurufs.length / 2) - 1))) * (a - (hurufs.length / 2)));
					h[a].getSprite().setPosition(x, 550);
				}
			}
		}

		Random r = new Random();

		for (int a = 0; a < h.length; a++)
		{
			double divx = 240 - h[a].getSprite().getX();
			double divy = 1000 - h[a].getSprite().getY();

			// double ratio = divy / divx;
			double sudut = Math.atan2(divy, divx);

			double x = h[a].getSprite().getX() - Math.cos(sudut) * 1000;
			double y = h[a].getSprite().getY() - Math.sin(sudut) * 1000;

			h[a].getSprite().registerEntityModifier(
					new SequenceEntityModifier(new MoveModifier(0, h[a].getSprite().getX(), (float) x, h[a].getSprite().getY(), (float) y), new DelayModifier(0.5f * r.nextFloat()), new MoveModifier(
							1.5f, (float) x, h[a].getSprite().getX(), (float) y, h[a].getSprite().getY(), EaseBounceOut.getInstance())));
		}

		benar.setPosition(gambar.getWidth() / 2 - benar.getWidth() / 2, gambar.getHeight() / 2 - benar.getHeight() / 2);
		salah.setPosition(gambar.getWidth() / 2 - salah.getWidth() / 2, gambar.getHeight() / 2 - salah.getHeight() / 2);

		scBack.setPosition(GameEngine.cameraWidth / 2 - scBack.getWidth() / 2, GameEngine.cameraHeight / 2 - scBack.getHeight() / 2);
		scBintang.setPosition(scBack.getWidth() / 2 - scBintang.getWidth() / 2, 180);

		progress.setPosition(back.getWidth() / 2 - progress.getWidth() / 2, 34);
	}

	@Override
	protected void registerTouch()
	{

		engine.scene.registerTouchArea(gambar);

		cek.registerTouch(engine.scene);

		for (int a = 0; a < hurufs.length; a++)
		{
			engine.scene.registerTouchArea(hurufs[a].getSprite());
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

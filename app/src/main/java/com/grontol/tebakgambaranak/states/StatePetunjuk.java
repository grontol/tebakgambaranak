package com.grontol.tebakgambaranak.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lib.element.ElementSprite;
import lib.elementgame.GameSprite;
import lib.elementgame.Sfx;
import lib.engine.GameEngine;
import lib.engine.GameState;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
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
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseElasticOut;

import android.view.KeyEvent;

import com.grontol.tebakgambaranak.classes.AssetHelper;
import com.grontol.tebakgambaranak.classes.Benda;
import com.grontol.tebakgambaranak.classes.BendaHelper;
import com.grontol.tebakgambaranak.classes.Huruf;
import com.grontol.tebakgambaranak.classes.SoundManager;
import com.grontol.tebakgambaranak.utility.Button;

public class StatePetunjuk extends GameState
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

	// Movement Vars =========
	Huruf[] jawab;

	Huruf inMove;
	float difX;
	float difY;

	float souX;
	float souY;

	boolean isImgDown = false;

	// Pointer ===
	GameSprite pointer;
	float targetX;
	float targetY;
	int curStep = 0;
	long lastTime;

	public StatePetunjuk(GameEngine engine)
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

		back = AssetHelper.getBackgroundTutorial(engine);
		benda = BendaHelper.getPetunjukBenda();
		gambar = benda.getSprite();
		bingkai = AssetHelper.getBingkai(engine);
		hitam = AssetHelper.getHitam(engine);

		cek = AssetHelper.getCekTutorial(engine);

		hurufs = new Huruf[benda.getNama().length() + 2];
		jawab = new Huruf[benda.getNama().length()];

		for (int a = 0; a < hurufs.length; a++)
		{
			hurufs[a] = benda.getHuruf()[a];
		}

		rects = new GameSprite[benda.getNama().length()];

		for (int a = 0; a < rects.length; a++)
		{
			rects[a] = AssetHelper.getCangkangTutorial(engine);
		}

		benar = new GameSprite(SPRITE_BENAR, engine);
		salah = new GameSprite(SPRITE_SALAH, engine);

		pointer = new GameSprite(new ElementSprite("gfx/pointer.png"), engine);
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
		hitam.setVisible(false);
		benar.setVisible(false);
		salah.setVisible(false);
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

		back.attachChild(pointer);
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

				pointer.detachSelf();
			}
		});
	}

	@Override
	protected void destroy()
	{
		// TODO Auto-generated method stub

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

		h[0] = hurufs[5];
		h[1] = hurufs[1];
		h[2] = hurufs[0];
		h[3] = hurufs[2];
		h[4] = hurufs[4];
		h[5] = hurufs[3];

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

		pointer.setPosition(220, 400);

		pointer.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(2), new MoveModifier(0.5f, pointer.getX(), hurufs[2].getSprite().getX(), pointer.getY(), hurufs[2].getSprite()
				.getY() + 15, EaseCubicInOut.getInstance()), new MoveModifier(0.9f, hurufs[2].getSprite().getX(), rects[0].getX() + 4, hurufs[2].getSprite().getY() + 15, rects[0].getY() + 19,
				EaseCubicInOut.getInstance()), new MoveModifier(1.2f, rects[0].getX() + 4, hurufs[3].getSprite().getX(), rects[0].getY() + 19, hurufs[3].getSprite().getY() + 15, EaseCubicInOut
				.getInstance()), new MoveModifier(0.9f, hurufs[3].getSprite().getX(), rects[1].getX() + 4, hurufs[3].getSprite().getY() + 15, rects[1].getY() + 19, EaseCubicInOut.getInstance()),
				new MoveModifier(0.6f, rects[1].getX() + 4, hurufs[4].getSprite().getX(), rects[1].getY() + 19, hurufs[4].getSprite().getY() + 15, EaseCubicInOut.getInstance()), new MoveModifier(
						0.5f, hurufs[4].getSprite().getX(), rects[2].getX() + 4, hurufs[4].getSprite().getY() + 15, rects[2].getY() + 19, EaseCubicInOut.getInstance()), new MoveModifier(0.9f,
						rects[2].getX() + 4, hurufs[5].getSprite().getX(), rects[2].getY() + 19, hurufs[5].getSprite().getY() + 15, EaseCubicInOut.getInstance()), new MoveModifier(1.2f, hurufs[5]
						.getSprite().getX(), rects[3].getX() + 4, hurufs[5].getSprite().getY() + 15, rects[3].getY() + 19, EaseCubicInOut.getInstance()), new MoveModifier(0.9f, rects[3].getX() + 4,
						cek.getX() + cek.getWidth() / 2 - pointer.getWidth() / 2, rects[3].getY() + 19, cek.getY() + 15, new IEntityModifierListener()
						{

							@Override
							public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
							{
								cek.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.6f, 1.1f, 0.9f), new ScaleModifier(0.6f, 0.9f, 1.1f))));
							}

							@Override
							public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
							{
								cek.clearEntityModifier();

								hitam.setVisible(true);
								benar.setVisible(true);
								benar.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(1f, .05f, 1, EaseElasticOut.getInstance()), new DelayModifier(1f,
										new IEntityModifierListener()
										{

											@Override
											public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem)
											{
											}

											@Override
											public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem)
											{
												exitState(STATE_MENU);
											}
										})));

								SoundManager.playBenar();
							}
						}, EaseCubicInOut.getInstance())));

		hurufs[2].getSprite().registerEntityModifier(
				new SequenceEntityModifier(new DelayModifier(2.5f), new MoveModifier(0.9f, hurufs[2].getSprite().getX(), rects[0].getX() + 4, hurufs[2].getSprite().getY(), rects[0].getY() + 4,
						EaseCubicInOut.getInstance())));

		hurufs[3].getSprite().registerEntityModifier(
				new SequenceEntityModifier(new DelayModifier(4.6f), new MoveModifier(0.9f, hurufs[3].getSprite().getX(), rects[1].getX() + 4, hurufs[3].getSprite().getY(), rects[1].getY() + 4,
						EaseCubicInOut.getInstance())));

		hurufs[4].getSprite().registerEntityModifier(
				new SequenceEntityModifier(new DelayModifier(6.1f), new MoveModifier(0.5f, hurufs[4].getSprite().getX(), rects[2].getX() + 4, hurufs[4].getSprite().getY(), rects[2].getY() + 4,
						EaseCubicInOut.getInstance())));

		hurufs[5].getSprite().registerEntityModifier(
				new SequenceEntityModifier(new DelayModifier(7.5f), new MoveModifier(1.2f, hurufs[5].getSprite().getX(), rects[3].getX() + 4, hurufs[5].getSprite().getY(), rects[3].getY() + 4,
						EaseCubicInOut.getInstance())));
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

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Sfx.Play(SOUND_BACK);
			exitState(STATE_MENU);
		}
	}

}

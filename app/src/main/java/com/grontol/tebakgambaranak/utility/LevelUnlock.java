package com.grontol.tebakgambaranak.utility;

import lib.defines.AnimDefines;
import lib.defines.FontDefines;
import lib.element.ElementSprite;
import lib.elementgame.GameAnim;
import lib.elementgame.GameSprite;
import lib.elementgame.GameText;
import lib.engine.GameEngine;

public class LevelUnlock extends GameSprite implements FontDefines, AnimDefines
{
	GameEngine engine;
	
	GameAnim jempol;
	GameText tulisan;
	
	public LevelUnlock(int level, GameEngine engine)
	{
		super(new ElementSprite(432, 335, "gfx/play/lc_back.png"), engine);
		
		this.engine = engine;
		
		jempol = new GameAnim(ANIM_JEMPOL, engine);
		tulisan = new GameText("Level " + level + " Terbuka", FONT_LEVEL_UNLOCK, engine);
	}

	@Override
	public void onAttached()
	{
		super.onAttached();
		
		this.attachChild(jempol);
		this.attachChild(tulisan);
		
		jempol.setPosition(this.getWidth() / 2 - jempol.getWidth() / 2, 0);
		tulisan.setPosition(this.getWidth() / 2 - tulisan.getWidth() / 2, 100);
	}
	
	@Override
	public void onDetached()
	{
		super.onDetached();
		
		jempol.detachSelf();
		tulisan.detachSelf();
	}
}

package lib.defines;

import lib.element.ElementSprite;

public interface SpriteDefines
{
	//Character sprite ================================================
	int SPRITE_ABABIL = 0;
	
	int SPRITE_BENAR = SPRITE_ABABIL + 1;
	int SPRITE_SALAH = SPRITE_BENAR + 1;
	
	// Menu Sprite =====
	int SPRITE_MENU_LOGO = SPRITE_SALAH + 1;
	int SPRITE_MENU_BACK = SPRITE_MENU_LOGO + 1;
	int SPRITE_MENU_MAIN = SPRITE_MENU_BACK + 1;
	int SPRITE_MENU_MAIN_DOWN = SPRITE_MENU_MAIN + 1;
	int SPRITE_MENU_PETUNJUK = SPRITE_MENU_MAIN_DOWN + 1;
	int SPRITE_MENU_PETUNJUK_DOWN = SPRITE_MENU_PETUNJUK + 1;
	int SPRITE_MENU_KELUAR = SPRITE_MENU_PETUNJUK_DOWN + 1;
	int SPRITE_MENU_KELUAR_DOWN = SPRITE_MENU_KELUAR + 1;
	
	int SPRITE_SOUND_ON = SPRITE_MENU_KELUAR_DOWN + 1;
	int SPRITE_SOUND_OFF = SPRITE_SOUND_ON + 1;
	
	public final static ElementSprite CONTAINER[] = 
	{
		new ElementSprite(400, 89, "gfx/ababil.png"),
		
		new ElementSprite(158, 158, "gfx/play/benar.png"),
		new ElementSprite(158, 158, "gfx/play/salah.png"),
		
		// Menu Sprite ======
		new ElementSprite(400, 254, "gfx/main_menu/logo.png"),
		new ElementSprite(480, 800, "gfx/main_menu/back.png"),
		new ElementSprite(340, 76, "gfx/main_menu/main.png"),
		new ElementSprite(340, 76, "gfx/main_menu/main_down.png"),
		new ElementSprite(340, 76, "gfx/main_menu/petunjuk.png"),
		new ElementSprite(340, 76, "gfx/main_menu/petunjuk_down.png"),
		new ElementSprite(340, 76, "gfx/main_menu/keluar.png"),
		new ElementSprite(340, 76, "gfx/main_menu/keluar_down.png"),
		
		new ElementSprite(80, 80, "gfx/main_menu/sound_on.png"),
		new ElementSprite(80, 80, "gfx/main_menu/sound_off.png"),
	};
}
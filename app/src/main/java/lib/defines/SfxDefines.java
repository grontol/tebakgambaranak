package lib.defines;

import lib.element.ElementSound;

public interface SfxDefines 
{ 
	int SOUND_PICK = 0;
	int SOUND_DROP = 1;
	int SOUND_THROW = 2;
	int SOUND_CLICK = 3;
	int SOUND_BACK = 4;
	
	int SOUND_BENAR1 = 5;
	int SOUND_BENAR2 = 6;
	int SOUND_BENAR3 = 7;
	int SOUND_BENAR4 = 8;
	
	int SOUND_SALAH1 = 9;
	int SOUND_SALAH2 = 10;
	
	public final static ElementSound CONTAINER[] = 
	{
		new ElementSound("sfx/pick.mp3", false),
		new ElementSound("sfx/drop.mp3", false),
		new ElementSound("sfx/throw.mp3", false),
		new ElementSound("sfx/click.mp3", false),
		new ElementSound("sfx/back.mp3", false),
		
		new ElementSound("sfx/pintar.mp3", false),
		new ElementSound("sfx/hebat.mp3", false),
		new ElementSound("sfx/berhasil.mp3", false),
		new ElementSound("sfx/benar.mp3", false),
		
		new ElementSound("sfx/salah.mp3", false),
		new ElementSound("sfx/coba.mp3", false),
	};
}

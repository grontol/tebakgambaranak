package lib.defines;

import lib.element.ElementSound;

public interface MfxDefines 
{
	int MFX_AWAL = 0;
	int MFX_MAIN = 1;

	public final static ElementSound CONTAINER[] = 
	{
		new ElementSound("mfx/awal.wav", true),
		new ElementSound("mfx/main.wav", true),
	};
}

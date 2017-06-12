package lib.defines;

import lib.element.ElementAnim;

public interface AnimDefines 
{	
	int ANIM_BINTANG = 0;
	int ANIM_JEMPOL = 1;
	
	public final static ElementAnim CONTAINER[] = 
	{
		new ElementAnim(1, 6, "gfx/play/sc_bintang.png"),
		new ElementAnim(2, 1, "gfx/play/jempol.png"),
	};
}

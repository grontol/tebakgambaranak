package lib.elementgame;

import java.io.IOException;
import java.io.InputStream;

import lib.defines.SpriteDefines;
import lib.element.ElementSprite;
import lib.engine.Anchor;
import lib.engine.GameEngine;
import lib.engine.Utils;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;

public class GameSprite extends Sprite implements SpriteDefines {

	GameEngine engine;
	private boolean isBlurred = false;
	
	public GameSprite(int index, GameEngine engine) {
		super(0, 0, CONTAINER[index].getW() != 0 ? CONTAINER[index].getW()
				: getBitmapWidth(engine, index),
				CONTAINER[index].getH() != 0 ? CONTAINER[index].getH()
						: getBitmapHeight(engine, index), getTextureRegion(
						engine, index).deepCopy(), engine
						.getVertexBufferObjectManager());
		//this.detachSelf(engine);
	}
	
	// My Method ===================================
	public GameSprite(ElementSprite elSp, GameEngine engine) {
		super(0, 0, elSp.getW() != 0 ? elSp.getW()
				: getBitmapWidth(engine, elSp),
				elSp.getH() != 0 ? elSp.getH()
						: getBitmapHeight(engine, elSp), getTextureRegion(
						engine, elSp).deepCopy(), engine
						.getVertexBufferObjectManager());
		//this.detachSelf(engine);
	}	
	
	// My Method ===================================
	public GameSprite(int index, GameEngine engine, float scale) {
		super(0, 0, CONTAINER[index].getW() != 0 ? CONTAINER[index].getW() * scale
				: getBitmapWidth(engine, index),
				CONTAINER[index].getH() != 0 ? CONTAINER[index].getH() * scale
						: getBitmapHeight(engine, index), getTextureRegion(
						engine, index).deepCopy(), engine
						.getVertexBufferObjectManager());
		//this.detachSelf(engine);
	}
	
	// My Method ===================================
		public GameSprite(ElementSprite elSp, GameEngine engine, float scale) {
			super(0, 0, elSp.getW() != 0 ? elSp.getW() * scale
					: getBitmapWidth(engine, elSp),
					elSp.getH() != 0 ? elSp.getH() * scale
							: getBitmapHeight(engine, elSp), getTextureRegion(
							engine, elSp).deepCopy(), engine
							.getVertexBufferObjectManager());
			//this.detachSelf(engine);
		}

	// My Method ===================================
	public GameSprite(int index, GameEngine engine, float scale, boolean isBlurred) {
		super(0, 0, CONTAINER[index].getW() != 0 ? CONTAINER[index].getW() * scale
				: getBitmapWidth(engine, index),
				CONTAINER[index].getH() != 0 ? CONTAINER[index].getH() * scale
						: getBitmapHeight(engine, index), getTextureRegion(
						engine, index).deepCopy(), engine
						.getVertexBufferObjectManager());
		
		this.engine = engine;
		this.isBlurred = isBlurred;
		
		//this.detachSelf(engine);
	}
	
	// My Method ======================================
	public GameSprite(TextureRegion tReg, GameEngine engine, int index)
	{
		super(getBitmapWidth(engine, index), getBitmapHeight(engine, index), tReg, engine.getVertexBufferObjectManager());
		//this.detachSelf(engine);
	}
	
	// My Method =========================
	public GameSprite(int px, int py, int width, int height, int finalWidth,
			int finalHeight, int index, GameEngine engine) {
		
		super(0, 0, finalWidth, finalHeight, getCroppedRegion(engine, index, px, py, width,
				height).deepCopy(), engine.getVertexBufferObjectManager());

		//this.detachSelf(engine);
	}
	
	// My Method ==============================
	public GameSprite(int px, int py, int width, int height, int index, GameEngine engine)
	{
		this(px, py, width, height, width, height, index, engine);
	}

	@SuppressLint("WrongCall")
	@Override
	protected void draw(GLState pGLState, Camera pCamera) {		
		
		if (isBlurred)
		{
			engine.mRenderTexture1.begin(pGLState, Color.TRANSPARENT);
			{
				super.draw(pGLState, engine.camera);
			}
			engine.mRenderTexture1.end(pGLState);
			
			engine.mRenderTexture2.begin(pGLState, false, true, Color.TRANSPARENT);
			{
				engine.mRenderTextureSprite1.onDraw(pGLState, engine.camera);
			}
			engine.mRenderTexture2.end(pGLState);
			
			pGLState.pushProjectionGLMatrix();
			pGLState.orthoProjectionGLMatrixf(this.getX(), this.getX() + 300, this.getY(), this.getY() + 300, -1, 1);
			{
				engine.mRenderTextureSprite2.onDraw(pGLState, engine.camera);
			}
			pGLState.popProjectionGLMatrix();
		}
		else
		{
			super.draw(pGLState, pCamera);
		}
	}
	
	private static ITextureRegion getTextureRegion(final GameEngine engine,
			final ElementSprite elSp) {
		ITexture texture = null;
		try {
			texture = new BitmapTexture(engine.getTextureManager(),
					new IInputStreamOpener() {

						public InputStream open() throws IOException {
							return engine.getAssets().open(
									elSp.getPath());
						}
					}, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

			texture.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return TextureRegionFactory.extractFromTexture(texture);
	}
	
	private static ITextureRegion getTextureRegion(final GameEngine engine,
			final int index) {
		ITexture texture = null;
		try {
			texture = new BitmapTexture(engine.getTextureManager(),
					new IInputStreamOpener() {

						public InputStream open() throws IOException {
							return engine.getAssets().open(
									SpriteDefines.CONTAINER[index].getPath());
						}
					}, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

			texture.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return TextureRegionFactory.extractFromTexture(texture);
	}

	// Edited Baru ====
	private static TextureRegion getTextureRegion(final BitmapTextureAtlas texture, final GameEngine engine, ElementSprite elSp, int x, int y)
	{
		return BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, engine.getAssets(), elSp.getPath(), x, y);
	}
	
	private static TextureRegion getTextureRegion(final BitmapTextureAtlas texture, final GameEngine engine, final int index, int x, int y)
	{
		return BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, engine.getAssets(), CONTAINER[index].getPath(), x, y);
	}
	
	@SuppressWarnings("unused")
	private static TextureRegion getTextureRegionFromAtlas(GameEngine engine, ElementSprite elSp)
	{
		int tWidth = 1;
		int tHeight = 1;
		
		int bWidth = getBitmapWidth(engine, elSp);
		int bHeight = getBitmapHeight(engine, elSp);
		
		while (true)
		{
			if (tWidth >= bWidth)
				break;
			else
				tWidth *= 2;
		}
		
		while (true)
		{
			if (tHeight >= bHeight)
				break;
			else
				tHeight *= 2;
		}
		
		BitmapTextureAtlas txt = new BitmapTextureAtlas(engine.getTextureManager(), tWidth, tHeight, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion reg = getTextureRegion(txt, engine, elSp, 0, 0);
		txt.load();
		
		return reg;
	}
	
	@SuppressWarnings("unused")
	private static TextureRegion getTextureRegionFromAtlas(GameEngine engine, int index)
	{
		int tWidth = 1;
		int tHeight = 1;
		
		int bWidth = getBitmapWidth(engine, index);
		int bHeight = getBitmapHeight(engine, index);
		
		while (true)
		{
			if (tWidth >= bWidth)
				break;
			else
				tWidth *= 2;
		}
		
		while (true)
		{
			if (tHeight >= bHeight)
				break;
			else
				tHeight *= 2;
		}
		
		BitmapTextureAtlas txt = new BitmapTextureAtlas(engine.getTextureManager(), tWidth, tHeight, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion reg = getTextureRegion(txt, engine, index, 0, 0);
		txt.load();
		
		return reg;
	}
	// ================
	
	// My Method =============================================
	private static ITextureRegion getCroppedRegion(final GameEngine engine,
			final int index, int x, int y, int width, int height) {
		ITextureRegion textureReg = null;

		ITexture texture = null;
		try {
			texture = new BitmapTexture(engine.getTextureManager(),
					new IInputStreamOpener() {

						public InputStream open() throws IOException {
							return engine.getAssets().open(
									SpriteDefines.CONTAINER[index].getPath());
						}
					});

			texture.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		textureReg = new TextureRegion(texture, x, y, width, height);
		return textureReg;
	}

	private static int getBitmapWidth(GameEngine engine, ElementSprite elSp) {
		try {
			return BitmapFactory.decodeStream(
					engine.getAssets().open(elSp.getPath()))
					.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private static int getBitmapWidth(GameEngine engine, int index) {
		try {
			return BitmapFactory.decodeStream(
					engine.getAssets().open(CONTAINER[index].getPath()))
					.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static int getBitmapHeight(GameEngine engine, ElementSprite elSp) {
		try {
			return BitmapFactory.decodeStream(
					engine.getAssets().open(elSp.getPath()))
					.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private static int getBitmapHeight(GameEngine engine, int index) {
		try {
			return BitmapFactory.decodeStream(
					engine.getAssets().open(CONTAINER[index].getPath()))
					.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void setPosition(Anchor anchor) {
		setPosition(0, 0, anchor);
	}

	public void setPosition(float pX, float pY, Anchor anchor) {
		if (this.getParent() == null) {
			Utils.debug("please attach first...!");
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		float pW = 0f, pH = 0f;
		if (this.getParent().getClass().equals(Scene.class)
				|| this.getParent().getClass().equals(HUD.class)) {
			pW = Utils.getXAnchor(this, GameEngine.cameraWidth, anchor);
			pH = Utils.getYAnchor(this, GameEngine.cameraHeight, anchor);
		} else {
			pW = Utils.getXAnchor(this, anchor);
			pH = Utils.getYAnchor(this, anchor);
		}

		super.setPosition(pW + pX, pH + pY);
	}

	public void detachSelf(final GameEngine engine) {
		engine.runOnUpdateThread(new Runnable() {
			public void run() {
				GameSprite.this.detachSelf();
			}
		});
	}
	
	@Override
	protected void preDraw(GLState pGLState, Camera pCamera) {
		// TODO Auto-generated method stub
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
		pGLState.enableBlend();
	}
}

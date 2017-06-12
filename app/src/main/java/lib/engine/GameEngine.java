package lib.engine;

import java.text.DecimalFormat;

import lib.defines.FontDefines;
import lib.defines.GameEngineConfiguration;
import lib.defines.TableDefine;
import lib.element.ElementTable;
import lib.elementgame.GameDatabase;
import lib.elementgame.GameFont;
import lib.elementgame.GameText;
import lib.elementgame.Mfx;
import lib.elementgame.Sfx;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSCounter;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.render.RenderTexture;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public abstract class GameEngine extends SimpleBaseGameActivity implements IUpdateHandler, IOnSceneTouchListener
{
	// SHADER FIELD ====================
	
	protected boolean mRenderTexturesInitialized = false;
	public RenderTexture mRenderTexture1;
	public Sprite mRenderTextureSprite1;
	public RenderTexture mRenderTexture2;
	public Sprite mRenderTextureSprite2;
	
	// =====================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	public static float cameraWidth, cameraHeight;
	
	public BoundCamera boundCamera;
	public Camera camera;
	public HUD hud;
	
	public Scene scene;
	
	boolean gamePaused;
	
	private boolean gameInitialized;
	private boolean stateInitialized = false;
	
	public Font font;
	
	private GameFont gameFont[] = new GameFont[FontDefines.CONTAINER.length];
	
	public final Handler handler = new Handler();
	
	private FPSCounter fps	= new FPSCounter();
	
	private GameDatabase dbase;
	
	public boolean isAdVisible = false;
	
//	@SuppressLint("NewApi")
//	@Override
//	protected void onSetContentView()
//	{
//		super.onSetContentView();
//		
//		// CREATING the parent FrameLayout //
//        final FrameLayout frameLayout = new FrameLayout(this);
// 
//        // CREATING the layout parameters, fill the screen //
//        final LayoutParams frameLayoutLayoutParams =
//                new LayoutParams(LayoutParams.MATCH_PARENT,
//                                             LayoutParams.MATCH_PARENT);
// 
// 
//        // ADVIEW layout, show at the bottom of the screen //
//        final LayoutParams adViewLayoutParams =
//                new LayoutParams(LayoutParams.WRAP_CONTENT,
//                                             LayoutParams.WRAP_CONTENT,
//                                             Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
// 
// 
//        // RENDER the add on top of the scene //
//        this.mRenderSurfaceView = new RenderSurfaceView(this);
//        mRenderSurfaceView.setRenderer(mEngine, this);
// 
//        // SURFACE layout ? //
//        final LayoutParams surfaceViewLayoutParams =
//        		new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
//        surfaceViewLayoutParams.gravity = Gravity.CENTER;
// 
//        // ADD the surface view and adView to the frame //
//        frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
// 
//        // SHOW AD //
//        this.setContentView(frameLayout, frameLayoutLayoutParams);
//	}
	
	public EngineOptions onCreateEngineOptions() 
	{
		processScreenOrientation();
		
		this.boundCamera = new BoundCamera(0, 0, cameraWidth, cameraHeight);
		this.camera = new Camera(0, 0, cameraWidth, cameraHeight);
		hud = new HUD();
		final EngineOptions engineOptions = new EngineOptions(GameEngineConfiguration.isFullScreen, GameEngineConfiguration.screenOrientation, new FillResolutionPolicy(), GameEngineConfiguration.useUsualCamera?camera:boundCamera);
		
		if(GameEngineConfiguration.useUsualCamera)
		{
			camera.setHUD(hud);
		}
		else
		{
			boundCamera.setHUD(hud);
		}
		
		
		engineOptions.getAudioOptions().setNeedsSound(GameEngineConfiguration.useSound);
		engineOptions.getAudioOptions().setNeedsMusic(GameEngineConfiguration.useMusic);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getRenderOptions().setMultiSampling(true);
		onCreateEngine(engineOptions);
		
		return engineOptions;
	}

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
		if(GameEngineConfiguration.useFpsLimiter)
			return new LimitedFPSEngine(pEngineOptions, GameEngineConfiguration.fpsLimit)
		{
			
		};
		
		return super.onCreateEngine(pEngineOptions);
	}
	
    @Override
    protected synchronized void onCreateGame() 
    {
    	if(GameEngineConfiguration.useVibration)
    		mEngine.enableVibrator(this);
    	
    	super.onCreateGame();
    }
	
	@Override
	protected void onCreateResources() 
	{
		gameInitialized = false;
		
		loadAllFont();
		Sfx.loadAllSfx(this);
		Mfx.loadAllMfx(this);
		
		createSystemFont();
		
		ElementTable[] tables = TableDefine.CONTAINER;
		if(tables != null)
		{
			dbase = new GameDatabase(this, tables);
		}
	}
	
	@Override
	protected Scene onCreateScene() 
	{
		scene = new Scene();
		
		if(GameEngineConfiguration.useGameUpdate)
		{
			scene.registerUpdateHandler(this);
		}
		
		if(GameEngineConfiguration.useTouchScene)
		{
			scene.setOnSceneTouchListener(this);
		}
		
		if(GameEngineConfiguration.showFpsCounter)
		{
			final GameText txtFps = new GameText("", 20, font, this);
			
			mEngine.registerUpdateHandler(fps);
			
			hud.attachChild(txtFps);
			
			hud.registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() 
			{	
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					txtFps.setText(new DecimalFormat("##.##").format(fps.getFPS()) + " fps");
				}
			}));
		}
		
		return scene;
	}
	
	@Override
	public synchronized void onPauseGame() 
	{
		//getCurState().pause();
		gamePaused = true;
		
		super.onPauseGame();
	}
	
	@Override
	public synchronized void onResumeGame()
	{
		//getCurState().resume();
		super.onResumeGame();
		
	}
	
	void processScreenOrientation()
	{
		cameraWidth = GameEngineConfiguration.masterWidth;
		cameraHeight = GameEngineConfiguration.masterHeight;
	}
	
	void createSystemFont()
	{
		font = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28, Color.ARGB_PACKED_GREEN_CLEAR);
		font.load();
	}
	
	public void onUpdate(float pSecondsElapsed) 
	{
		if(!gameInitialized)
		{
			gameInit();
			gameInitialized = true;
						
			getCurState().initComponent();
			stateInitialized = true;
			
			return;
		}
		else if (!stateInitialized)
		{
			getCurState().initComponent();
			stateInitialized = true;
			
			return;
		}
		
		getCurState().update();
	}
	
	
	
	public void reset() {}
	
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		getCurState().onSceneTouched(pScene, pSceneTouchEvent);
		return false;
	}
	
	public void endGame()
	{
		System.exit(0);
	}
	
	private void loadAllFont()
	{
		for(int i = 0; i < gameFont.length; i++)
		{
			gameFont[i] = new GameFont(i, this);
			gameFont[i].load();
		}
	}
	
	public void unregisterSceneTouch(final ITouchArea touchArea)
	{
		runOnUpdateThread(new Runnable() 
		{
			@Override
			public void run() 
			{
				scene.unregisterTouchArea(touchArea);
			}
		});
	}
	
	public void unregisterHudTouch(final ITouchArea touchArea)
	{
		runOnUpdateThread(new Runnable() 
		{
			@Override
			public void run() 
			{
				hud.unregisterTouchArea(touchArea);
			}
		});
	}
	
	public void vibrate(long milis)
	{
		if(!GameEngineConfiguration.useVibration) return;
			mEngine.vibrate(milis);
	}
	
	public void vibrate(long[] pPattern, int pRepeat) 
	{
		if(!GameEngineConfiguration.useVibration) return;
			mEngine.vibrate(pPattern, pRepeat);
	}

	public void changeState(int nextState) 
	{
		stateInitialized = false;
	}
	
	public GameFont getFont(int index)
	{
		try 
		{
			return gameFont[index];
		} catch (ArrayIndexOutOfBoundsException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public FPSCounter getFps() 
	{
		return fps;
	}
	
	public GameDatabase getDatabase() 
	{
		return dbase;
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		if (getCurState() != null)		
			getCurState().onActivityLeave();
	}
	
	@Override
	protected synchronized void onResume()
	{
		super.onResume();
		
		if (getCurState() != null)		
			getCurState().onActivityBack();
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		getCurState().onKeyUp(keyCode, event);
		
		return false;
	}

	abstract protected void gameInit();
	abstract protected GameState getCurState();

	public RenderTexture getmRenderTexture1() {
		return mRenderTexture1;
	}
}

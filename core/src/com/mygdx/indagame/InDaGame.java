package com.mygdx.indagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.indagame.screens.GameScreenManager;
import com.mygdx.indagame.screens.MenuScreen;

public class InDaGame extends Game {

	public static final int HEIGHT = 720;
	public static final int WIDTH = 1280;



	/*I get a bit anxious when I see an object that can change as a public variable, perhaps we should just add a
        getBatch method /S
         */
	private Sound bkgmusic;
	private Sound playSound;
	private Sound aboutSound;
	private Sound aboutBkg;
	private Sound lossMusic;
	private Sound failSound;
	private Sound boostSound;
	private Sound jumpSound;


	private Array<Sound> gpm;
	private Preferences highScore, currentScore;

	private Array<Texture> bkgs;
	private Array<Texture> bkgsGrad;
	private Array<Texture> bkgsFull;
	private Array<Texture> bkgsBack;



	public SpriteBatch batch;



	@Override
	public void create () {

		gpm = new Array<Sound>();
		bkgs = new Array<Texture>();
		bkgsGrad = new Array<Texture>();
		bkgsFull = new Array<Texture>();
		bkgsBack = new Array<Texture>();

		//load sounds
		bkgmusic = Gdx.audio.newSound(Gdx.files.internal("sounds/guitarsynth.ogg"));
		playSound = Gdx.audio.newSound(Gdx.files.internal("sounds/playbass.ogg"));
		aboutSound = Gdx.audio.newSound(Gdx.files.internal("sounds/aboutclick.ogg"));
		aboutBkg = Gdx.audio.newSound(Gdx.files.internal("sounds/aboutmenu.ogg"));
		lossMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/sadmando.ogg"));
		failSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sadtromb.ogg"));
		boostSound = Gdx.audio.newSound(Gdx.files.internal("sounds/boostSound.ogg"));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jumpSound.ogg"));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm1.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm2.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm3.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm4.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm5.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm6.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm7.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm8.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm9.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm10.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm11.ogg")));
		gpm.add(Gdx.audio.newSound(Gdx.files.internal("sounds/gpm/gpm12.ogg")));



		//load backgrounds
		bkgs.add(new Texture("PNG/PNG/lfpe/fence.png"));
		bkgs.add(new Texture("parallax_forest_pack/layers/fence.png"));
		bkgs.add(new Texture("parallax-industrial-pack/layers/fence.png"));
		bkgs.add(new Texture("parallax_mountain_pack/parallax_mountain_pack/layers/fence.png"));

		bkgsGrad.add(new Texture("PNG/PNG/lfpe/fence.png"));
		bkgsGrad.add(new Texture("parallax_forest_pack/layers/grad.png"));
		bkgsGrad.add(new Texture("parallax-industrial-pack/layers/grad.png"));
		bkgsGrad.add(new Texture("parallax_mountain_pack/parallax_mountain_pack/layers/grad.png"));

		bkgsFull.add(new Texture("PNG/PNG/full-bg.png"));
		bkgsFull.add(new Texture("parallax_forest_pack/layers/fullbkg.png"));
		bkgsFull.add(new Texture("parallax-industrial-pack/layers/fullbkg.png"));
		bkgsFull.add(new Texture("parallax_mountain_pack/parallax_mountain_pack/layers/fullbkg.png"));

		bkgsBack.add(new Texture("PNG/PNG/lfpe/backback.png"));
		bkgsBack.add(new Texture("parallax_forest_pack/layers/backback.png"));
		bkgsBack.add(new Texture("parallax-industrial-pack/layers/backback.png"));
		bkgsBack.add(new Texture("parallax_mountain_pack/parallax_mountain_pack/layers/backback.png"));




		//highscoredata
		highScore = Gdx.app.getPreferences("highScore");
		currentScore = Gdx.app.getPreferences("currentScore");

		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		//delegate the render method to the active screen
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public Sound getBkgmusic() {
		return bkgmusic;
	}

	public Sound getPlaySound() {
		return playSound;
	}

	public Sound getAboutSound() {
		return aboutSound;
	}

	public Sound getAboutBkg() {
		return aboutBkg;
	}

	public Sound getLossMusic() {
		return lossMusic;
	}

	public Sound getFailSound() {
		return failSound;
	}

	public Sound getBoostSound() {
		return boostSound;
	}

	public Sound getJumpSound(){
		return jumpSound;
	}

	public Array<Sound> getGpm() {
		return gpm;
	}

	public Array<Texture> getBkgs() {
		return bkgs;
	}

	public Array<Texture> getBkgsGrad() {
		return bkgsGrad;
	}

	public Array<Texture> getBkgsFull() {
		return bkgsFull;
	}

	public Array<Texture> getBkgsBack() {
		return bkgsBack;
	}

	public Preferences getHighScore() {
		return highScore;
	}

	public Preferences getCurrentScore() {
		return currentScore;
	}

}

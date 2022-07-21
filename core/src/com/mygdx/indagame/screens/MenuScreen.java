package com.mygdx.indagame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.indagame.InDaGame;

import java.util.Random;

/**
 * Created by Maeve Lockwall Rhodin on 05/05/2017.
 */
public class MenuScreen implements Screen {
    private InDaGame game;

    //implement camera to fit screen size
    private OrthographicCamera menuCam;

    private int counter;

    private Sound playSound, aboutSound;
    private Music bkgmusic;

    //background texture
    private Texture bkg;

    //variables for playbutton
    private Texture play1;
    private Texture play2;
    private int playButtonHeight;
    private int playButtonWidth;

    //variables for about button
    private Texture about;
    private int aboutHeight;
    private int aboutWidth;

    //variables for cursor position
    private int xPos;
    private int yPos;


    public MenuScreen(InDaGame game) {
        play1 = new Texture("playbutton.png");
        play2 = new Texture("playbuttonmark.png");

        counter = 0;

        bkgmusic = game.getBkgmusic();
        aboutSound = game.getAboutSound();
        playSound = game.getPlaySound();

        Random random = new Random();
        int bkgsInt = random.nextInt(game.getBkgs().size);
        bkg = game.getBkgsFull().get(bkgsInt);

        playButtonHeight = 300;
        playButtonWidth = 400;

        bkgmusic.setLooping(true);
        bkgmusic.setVolume(0.5f);
        bkgmusic.play();
        
        about = new Texture("AboutButton.png");
        aboutHeight = 100;
        aboutWidth = 100;

        xPos = 0;
        yPos = 0;

        menuCam = new OrthographicCamera(InDaGame.WIDTH, InDaGame.HEIGHT);
        menuCam.position.set(InDaGame.WIDTH/2, InDaGame.HEIGHT/2, 0);

        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clear the screen with near white
        Gdx.gl.glClearColor(254, 254, 254, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //get cursor x position
        xPos = Gdx.input.getX();

        //get cursor y position
        yPos = Gdx.input.getY();

        //recognize where the camera is and only render what the camera can see
        game.batch.setProjectionMatrix(menuCam.combined);

        //open the "batch" and begin drawing
        game.batch.begin();

        //draw background
        game.batch.draw(bkg, -InDaGame.WIDTH/2, -InDaGame.HEIGHT/2, InDaGame.WIDTH, InDaGame.HEIGHT);

        //make sure that sound starts playing
        if (counter == 0){
            bkgmusic.setLooping(true);
            bkgmusic.setVolume(0.5f);
            bkgmusic.play();
            counter++;
        }

        //draw play button and mark it if hovered over with cursor

        if (yPos > (InDaGame.HEIGHT/2 - playButtonHeight/2) && yPos < (InDaGame.HEIGHT/2 + playButtonHeight/2)
                && xPos > (InDaGame.WIDTH/2 - playButtonWidth/2) && xPos < InDaGame.WIDTH/2 + playButtonWidth/2) {

            game.batch.draw(play2, (-playButtonWidth/3), (-playButtonHeight/3-30));
            //launch GameScreen
            if (Gdx.input.justTouched()){
                System.out.println("I am touched");
                playSound.play(0.5f);
                //set gameScreen
                game.setScreen(new GameScreen(game));
                bkgmusic.stop();
            }
        }
        else{
            game.batch.draw(play1, (-playButtonWidth/3), (-playButtonHeight/3-30));
        }

        //draw about button
        game.batch.draw(about, -InDaGame.WIDTH/2, InDaGame.HEIGHT/2-aboutHeight/2);


        //launch AboutScreen
        if (yPos < aboutHeight && xPos < aboutWidth
                && Gdx.input.justTouched()){
            System.out.println("Testing about");
            aboutSound.play(0.5f);
            game.setScreen(new AboutScreen(game));
            bkgmusic.stop();
        }


        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

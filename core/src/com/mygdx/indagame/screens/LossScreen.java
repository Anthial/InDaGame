package com.mygdx.indagame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.indagame.InDaGame;


/**
 * Created by Maeve Lockwall Rhodin on 08/05/2017.
 */
public class LossScreen implements Screen {
    private InDaGame game;

    //implement camera to fit screen size
    private OrthographicCamera menuCam;

    private int counter;

    //font
    private BitmapFont bitmapFont;

    //sounds
    private Sound lossMusic, playSound, aboutSound;

    //scorehandling
    private long currentScore, highScore;

    //background texture
    private Texture bkg;

    //variables for playbutton
    private Texture play1;
    private Texture play2;
    private int playButtonHeight;
    private int playButtonWidth;

    //variables for scoreboard
    private Texture board;
    private int boardHeight;
    private int boardWidth;

    //variables for about button
    private Texture about;
    private int aboutHeight;
    private int aboutWidth;

    //variables for cursor position
    private int xPos;
    private int yPos;


    public LossScreen(InDaGame game, int bkgsInt) {
        play1 = new Texture("playbutton.png");
        play2 = new Texture("playbuttonmark.png");


        board = new Texture("scoreboardbkg.png");
        boardHeight = board.getHeight();
        boardWidth = board.getWidth();


        counter = 0;

        lossMusic = game.getLossMusic();
        aboutSound = game.getAboutSound();
        playSound = game.getPlaySound();

        bkg = game.getBkgsFull().get(bkgsInt);

        playButtonHeight = 300;
        playButtonWidth = 400;

        //read the score
        highScore = game.getHighScore().getLong("highScore");
        currentScore = game.getCurrentScore().getLong("currentScore");
        if(highScore < currentScore){
               game.getHighScore().putLong("highScore", currentScore);
               game.getHighScore().flush();
        }


        //initialise the fontgenerator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //set the parameters for the font
        parameter.size = 50;
        parameter.characters = "1234567890! Score: High HIGH SCORE high s" ;
        bitmapFont = generator.generateFont(parameter);
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

        //set sounds to play at loss
        game.getFailSound().play(0.5f);
        game.getLossMusic().loop(0.3f);

        //draw button to return to main screen
        about = new Texture("backButton.png");
        aboutHeight = 100;
        aboutWidth = 100;

        //get cursor position
        xPos = 0;
        yPos = 0;

        //set up camera
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
        /*if (counter == 0){
            lossMusic.loop(0.3f);
            counter++;
        }
        */

        //draw play button and mark it if hovered over with cursor

        if (yPos > (InDaGame.HEIGHT/2) && yPos < (InDaGame.HEIGHT/2 + playButtonHeight)
                && xPos > (InDaGame.WIDTH/2 - playButtonWidth/2) && xPos < InDaGame.WIDTH/2 + playButtonWidth/2) {

            game.batch.draw(play2, (-playButtonWidth/3), (-InDaGame.HEIGHT + playButtonHeight*1.5f));
            //launch GameScreen
            if (Gdx.input.justTouched()){
                System.out.println("I am touched");
                playSound.play(0.5f);
                //set gameScreen
                game.setScreen(new GameScreen(game));

                lossMusic.stop();
            }
        }
        else{
            game.batch.draw(play1, (-playButtonWidth/3), (-InDaGame.HEIGHT + playButtonHeight*1.5f));
        }

        //draw back button
        game.batch.draw(about, -InDaGame.WIDTH/2, InDaGame.HEIGHT/2-aboutHeight/2);


        //launch MenuScreen
        if (yPos < aboutHeight && xPos < aboutWidth
                && Gdx.input.justTouched()){
            System.out.println("Testing about");
            aboutSound.play(0.5f);
            game.setScreen(new MenuScreen(game));
            lossMusic.stop();
        }


        //draw Scoreboard
        game.batch.draw(board, (-boardWidth-20), (InDaGame.HEIGHT/2 - boardHeight*1.7f), boardWidth*3.2f,
                boardHeight);

        //DRAW TEXT
        bitmapFont.draw(game.batch, "HIGHSCORE: " + highScore+"\n\nSCORE: "+ currentScore
                , -300, 150);

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



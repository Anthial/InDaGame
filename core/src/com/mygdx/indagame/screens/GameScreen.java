package com.mygdx.indagame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.indagame.InDaGame;
import com.mygdx.indagame.sprites.Ball;
import com.mygdx.indagame.sprites.Brick;

import java.util.Random;
import java.util.concurrent.TimeUnit;



/**
 * Created by Maeve Lockwall Rhodin on 05/05/2017.
 */
public class GameScreen implements Screen {

    private static final int MIN_SPACE = 100;
    private static final int MAX_SPACe = 400;
    private static final int OBJECTS = 7;
    private static final int ABOVE_GROUND = 50;
    private static final int GROUND_Y_OFFSET = 0;

    private OrthographicCamera gameCam;
    private InDaGame game;

    private int bkgsInt;
    private Texture bkg;
    private Texture bkgGrad;
    private Texture bkgFull;
    private Texture bkgback;
    private Vector2 bkgPos1, bkgPos2;
    private Vector2 bkgbackPos1, bkgbackPos2;
    private int q;
    private int r;

    private Texture pause;
    private boolean gamePaused;
    private long pauseTime;
    private long pauseTimeTemp;
    private int pauseTimeCounter;
    private Texture play;

    private Ball ball;

    private Brick brick;

    private BitmapFont bitmapFont;

    private Music gpm;

    private float gravStore;

    private Random random;

    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    //array to hold the bricks
    private Array<Brick> bricks;

    //scorecounter
    private long scoreCounter;
    private long time;

    //variables for cursor position
    private int xPos;
    private int yPos;

    //ball animation
    private TextureAtlas ballAtlas;
    private Animation<TextureRegion> animation;
    private float timePassed = 0;


    public GameScreen(InDaGame game){
        this.game = game;

        scoreCounter = 0;
        time = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

        gameCam = new OrthographicCamera();
        //y-axis starts at the bottom. only display /1.5 of the window
        gameCam.setToOrtho(false, InDaGame.WIDTH/1.5f, InDaGame.HEIGHT/1.5f);

        Random bkgRandom = new Random();
        bkgsInt = bkgRandom.nextInt(game.getBkgs().size);
        bkg = game.getBkgs().get(bkgsInt);
        bkgGrad = game.getBkgsGrad().get(bkgsInt);
        bkgFull = game.getBkgsFull().get(bkgsInt);
        bkgback = game.getBkgsBack().get(bkgsInt);

        ball = new Ball(0, 100);

        //to allow the background to move along with the ball
        bkgPos1 = new Vector2(ball.getPosition().x - InDaGame.WIDTH/2, 0);
        bkgPos2 = new Vector2((ball.getPosition().x + InDaGame.WIDTH/2), 0);

        //to allow the background to move along with the ball
        q = 0;
        r = 0;
        bkgbackPos1 = new Vector2(ball.getPosition().x -InDaGame.WIDTH - q, 0);
        bkgbackPos2 = new Vector2(ball.getPosition().x + InDaGame.WIDTH - q, 0);

        //pausebutton texture
        pause = new Texture("grayPause.png");

        gamePaused = false;

        pauseTime = 0;
        pauseTimeTemp = 0;
        pauseTimeCounter = 0;

        //playbutton texture
        play = new Texture("redPlay.png");

        //cursorposition
        xPos = 0;
        yPos = 0;

        random = new Random();

        gravStore = ball.getGRAVITY();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 50;
        parameter.characters = "1234567890! Score: High HIGH SCORE high s INF" ;
        bitmapFont = generator.generateFont(parameter);
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        bitmapFont.setColor(Color.RED);

        ground = new Texture("PNG/PNG/lfpe/grass2.png");
        groundPos1 = new Vector2(ball.getPosition().x - InDaGame.WIDTH/2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(groundPos1.x + ground.getWidth(), GROUND_Y_OFFSET);

        bricks = new Array<Brick>();

        for (int x = 1; x <= OBJECTS; x++){
            int a = x*(random.nextInt((MAX_SPACe) )+ MIN_SPACE);
            if (a < ball.getPosition().x + 300){
                a = a + 300;
            }
            bricks.add(new Brick(a));
        }

        gpm = game.getGpm().get(random.nextInt(game.getGpm().size));
        gpm.setLooping(true);
        gpm.setVolume(0.5f);
        gpm.play();

        //ball animations
        ballAtlas = new TextureAtlas("ballStates/balls.atlas");
        animation = new Animation<TextureRegion>(1/30f, ballAtlas.getRegions());


    }

    @Override
    public void show() {


    }

    public void handleInput(float dt){
        if(Gdx.input.justTouched()){
            ball.jump();
            game.getJumpSound().play(0.4f);
        }
    }

    public void update(float dt){
        if (gamePaused == false) {
            bkgbackPos1 = new Vector2(ball.getPosition().x -InDaGame.WIDTH/2 - q, 0);
            bkgbackPos2 = new Vector2(ball.getPosition().x + InDaGame.WIDTH/2 - q, 0);
            handleInput(dt);
            ball.update(dt);
            scoreCounter = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) - time - pauseTime;

            gameCam.position.x = ball.getPosition().x + 100;

            updateBkg();
            updateGround();


            for (int x = 0; x < bricks.size; x++) {
                Brick brick = bricks.get(x);

                if (ball.getPosition().x - (InDaGame.WIDTH / 2) > brick.getPosBrick().x + brick.getTexture().getWidth()) {
                    brick.reposition(ball.getPosition().x + random.nextInt(600) + InDaGame.WIDTH / 2);
                }

                if (brick.collides(ball.getBounds())) {
                    game.getCurrentScore().putLong("currentScore", scoreCounter);
                    game.getCurrentScore().flush();
                    game.setScreen(new LossScreen(this.game, bkgsInt));
                    gpm.stop();

                }
            }

            //keep the ball from falling through the ground
            if (ball.getPosition().y <= GROUND_Y_OFFSET + ground.getHeight())
                ball.getPosition().y = GROUND_Y_OFFSET + ground.getHeight();

            //get cursor x position
            xPos = Gdx.input.getX();

            //get cursor y position
            yPos = Gdx.input.getY();

            //Pause Game
            if (yPos < pause.getHeight()*2 && xPos < (pause.getWidth()*2)
                    && Gdx.input.justTouched()) {
                if (pauseTimeCounter == 0) {
                    pauseTimeTemp = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
                    pauseTimeCounter++;
                }
                System.out.println("Pause pushed");
                game.getAboutSound().play(0.5f);
                gpm.stop();
                gamePaused = true;
            }

            gameCam.update();
        }
        else{
            if (Gdx.input.justTouched()){
                pauseTimeTemp = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS) - pauseTimeTemp;
                pauseTime = pauseTime + pauseTimeTemp;
                gamePaused = false;
                pauseTimeCounter = 0;
                gpm.setLooping(true);
                gpm.setVolume(0.5f);
                gpm.play();
            }
        }
    }

    @Override
    public void render(float delta) {

        update(delta);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        if (gamePaused == false) {
            game.batch.draw(bkgGrad, ball.getPosition().x - InDaGame.WIDTH/2, 0, InDaGame.WIDTH, InDaGame.HEIGHT);

            game.batch.draw(bkgback, bkgbackPos1.x, bkgbackPos1.y, InDaGame.WIDTH, InDaGame.HEIGHT);
            game.batch.draw(bkgback, bkgbackPos2.x, bkgbackPos2.y, InDaGame.WIDTH, InDaGame.HEIGHT);

            game.batch.draw(bkg, bkgPos1.x, bkgPos1.y, InDaGame.WIDTH, InDaGame.HEIGHT);
            game.batch.draw(bkg, bkgPos2.x, bkgPos2.y, InDaGame.WIDTH, InDaGame.HEIGHT);



            //get deltatime
            timePassed += Gdx.graphics.getDeltaTime();
            //draw the ball in its current state
            game.batch.draw(animation.getKeyFrame(timePassed, true), ball.getPosition().x, ball.getPosition().y);

            //draw bricks where they are positioned
            for (Brick brick : bricks) {
                game.batch.draw(brick.getTexture(), brick.getPosBrick().x, brick.getPosBrick().y,
                        brick.getTexture().getWidth(), brick.getFLUCTUATION());
            }

            //draw about button
            game.batch.draw(pause, ball.getPosition().x-330, InDaGame.HEIGHT - 280);


            game.batch.draw(ground, groundPos1.x, groundPos1.y, InDaGame.WIDTH+5, ground.getHeight());
            game.batch.draw(ground, groundPos2.x, groundPos2.y, InDaGame.WIDTH+5, ground.getHeight());
            if (scoreCounter < 100) {
                bitmapFont.draw(game.batch, "" + scoreCounter, ball.getPosition().x + 420, InDaGame.HEIGHT - 250);
            } else if (scoreCounter < 1000) {
                bitmapFont.draw(game.batch, "" + scoreCounter, ball.getPosition().x + 370, InDaGame.HEIGHT - 250);
            } else if (scoreCounter < 10000) {
                bitmapFont.draw(game.batch, "" + scoreCounter, ball.getPosition().x + 320, InDaGame.HEIGHT - 250);
            } else if (scoreCounter < 100000) {
                bitmapFont.draw(game.batch, "" + scoreCounter, ball.getPosition().x + 270, InDaGame.HEIGHT - 250);
            } else if (scoreCounter < 1000000) {
                bitmapFont.draw(game.batch, "" + scoreCounter, ball.getPosition().x + 220, InDaGame.HEIGHT - 250);
            } else {
                bitmapFont.draw(game.batch, "INF", ball.getPosition().x + 420, InDaGame.HEIGHT - 250);
            }

            q++;

        }
        else{
            game.batch.draw(bkgFull, ball.getPosition().x - InDaGame.WIDTH/2, 0, InDaGame.WIDTH, InDaGame.HEIGHT);
            game.batch.draw(play, ball.getPosition().x-50, InDaGame.HEIGHT/5,
                    InDaGame.WIDTH/4, InDaGame.HEIGHT/3);
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
        ballAtlas.dispose();

    }

    private void updateBkg() {
        if (ball.getPosition().x - (InDaGame.WIDTH / 2) > bkgPos1.x + InDaGame.WIDTH)
            bkgPos1.add(InDaGame.WIDTH * 2, 0);

        if (ball.getPosition().x - (InDaGame.WIDTH / 2) > bkgPos2.x + InDaGame.WIDTH)
            bkgPos2.add(InDaGame.WIDTH * 2, 0);

        if (ball.getPosition().x - (InDaGame.WIDTH / 2) > bkgbackPos1.x + InDaGame.WIDTH){
            bkgbackPos1.add(InDaGame.WIDTH * 2, 0);

        }

        if(ball.getPosition().x - (InDaGame.WIDTH/2) > bkgbackPos2.x + InDaGame.WIDTH) {
            bkgbackPos2.add(InDaGame.WIDTH * 2, 0);

        }
}

    private void updateGround(){
        if(ball.getPosition().x - (InDaGame.WIDTH/2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth()*2, 0);

        if(ball.getPosition().x - (InDaGame.WIDTH/2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth()*2, 0);

        if(ball.getPosition().y < GROUND_Y_OFFSET + ground.getHeight()+15) {
            if (ball.getJumpCounter() > 3){
                game.getBoostSound().play(0.05f);
            }
            ball.setJumpCounter(0);
        }
    }

}

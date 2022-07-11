package com.mygdx.indagame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.indagame.InDaGame;


/**
 * Created by Maeve Lockwall Rhodin on 06/05/2017.
 */
public class AboutScreen implements Screen {
    private InDaGame game;


    private Texture backbutton;

    private BitmapFont bitmapFont;
    private BitmapFont bitmapFontSmall;


    private int counter = 0;


    public AboutScreen(InDaGame game) {
        this.game = game;


        backbutton = new Texture("backButton.png");

        //Using the build in font generator to convert a TrueTypeFont to a supported BitMapFont.
        //based on the example code presented in the libGDX documentation
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/prstart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 30;
        parameter.characters = "This game was created by Keivan Matinzadeh and Maeve Lockwall Rhodin for the KTH" +
                " INDA Project Course.\n This game is quite weird but we hope that you will enjoy its contents.\n" +
                "\n Copyright Keivan Matinzadeh and Maeve Lockwall Rhodin" ;
        bitmapFont = generator.generateFont(parameter);
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;

        parameter2.size = 20;
        parameter2.characters = "© Keivan Matinzadeh and Maeve Lockwall Rhodin";
        bitmapFontSmall = generator.generateFont(parameter2);
        parameter2.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        generator.dispose();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {



        //clear the screen with near white
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //open the "batch" and begin drawing
        game.batch.begin();

        //DRAW TEXT
        bitmapFont.draw(game.batch, "This game was created by Keivan\nMatinzadeh and Maeve Lockwall \nRhodin for the KTH" +
                " INDA Project\nCourse.\n\nThis game is quite weird but we \nhope that you will enjoy its \ncontents.\n"
                , -InDaGame.WIDTH/2+4, InDaGame.HEIGHT/2-10);
        //REMEMBER THAT THE LAST HEIGHT INCLUDES THE VALUE OF THE SIZE OF THE FONT
        bitmapFontSmall.draw(game.batch, "© Keivan Matinzadeh and Maeve Lockwall Rhodin", -InDaGame.WIDTH/2+4,-InDaGame.HEIGHT/2 + 24);

        //DRAW BACKBUTTON
        game.batch.draw(backbutton, -backbutton.getWidth()*2, -backbutton.getHeight()*5, backbutton.getWidth()*4, backbutton.getHeight()*4);

        //force music loop once
        if (counter == 0){
            game.getAboutBkg().loop(0.5f);
            counter++;
        }

        //allow user to go back
        if (Gdx.input.justTouched()){
                System.out.println("Back We Go!");
                game.setScreen(new MenuScreen(game));
                game.getAboutSound().play(0.5f);
                counter = 0;
                game.getAboutBkg().stop();
                game.getBkgmusic().play(0.3f);
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

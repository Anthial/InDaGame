package com.mygdx.indagame.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * A class to manage the various screens in the game. NOT SURE IF NEEDED
 *
 * Created by keivanmatinzadeh on 2017-05-06.
 */

public class GameScreenManager {
    private Stack<Screen> screens;

    public GameScreenManager(){
        screens = new Stack<Screen>();
    }

    /**
     * Push a screen onto the screens stack.
     *
     * @param screen the screen to be used
     */
    public void push(Screen screen){
        screens.push(screen);
    }

    /**
     * Pop a screen from the screens stack.
     */
    public void pop(){
        //dispose of the state to prevent memory leaks
        screens.pop().dispose();
    }

    /**
     * Set the current screen of the game.
     *
     * @param screen The current screen.
     */
    public void set(Screen screen){
        screens.pop().dispose();
        screens.push(screen);
    }

    public void update(float dt){
        //TODO
    }


    public void render(SpriteBatch sb){
        //TODO
    }
}

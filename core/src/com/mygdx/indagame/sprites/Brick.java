package com.mygdx.indagame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by keivanmatinzadeh on 2017-05-06.
 */

public class Brick {
    private Texture brick;
    private int FLUCTUATION;
    private Vector2 posBrick;
    private Random rand;
    private Rectangle bounds;

    public Brick(float x){
        brick = new Texture("stone_wall06.png");
        rand = new Random();
        posBrick = new Vector2(x, 0);
        FLUCTUATION = rand.nextInt(30)+70;
        bounds = new Rectangle(posBrick.x, posBrick.y, brick.getWidth(), getFLUCTUATION());

    }

    public void reposition(float x){
        posBrick.set(x, 0);
        bounds.setPosition(posBrick.x, posBrick.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(bounds);
    }

    public void dispose(){
        brick.dispose();
    }

    public Texture getTexture() {
        return brick;
    }

    public Vector2 getPosBrick() {
        return posBrick;
    }

    public int getFLUCTUATION(){return FLUCTUATION;}
}

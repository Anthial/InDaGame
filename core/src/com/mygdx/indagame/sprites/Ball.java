package com.mygdx.indagame.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.indagame.InDaGame;
import com.mygdx.indagame.screens.GameScreen;

/**
 * A ball.
 *
 * Created by keivanmatinzadeh on 2017-05-06.
 */

public class Ball extends Sprite {

    private float GRAVITY = -InDaGame.HEIGHT/5;
    private static final int MOVEMENT = 230;
    private static final int VELOCITY = 5;
    //vector3 holds x,y,z axis
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;


    private int jumpCounter;

    private Texture ball;

    public Ball(int x, int y){
        position = new Vector3(x, y, 0);
        //starting speed is zero
        velocity = new Vector3(50, GRAVITY, 0);
        ball = new Texture("balls/ball_red.png");
        //set the bound around the ball
        bounds = new Rectangle(x, y, ball.getWidth(), ball.getHeight());
        //Counting the amounts of jumps allowed per ground touch.
        jumpCounter = 0;
    }


    /**
     * Update the ball's current location
     *
     * @param dt    delta time.
     */
    public void update(float dt){
        /*if(position.y > 0) {
            //add gravity to the z axis.
            velocity.add(0, GRAVITY, 0);
        }
        */
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);

        if(position.y < 0)
            position.y = 0;


        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump(){
        if (jumpCounter < 4) {
            position.y += 100;
            jumpCounter ++;
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return ball;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public float getGRAVITY(){
        return GRAVITY;
    }

    public void setGRAVITY(float value){
        GRAVITY = value;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }


    public void dispose(){
        ball.dispose();
    }


}

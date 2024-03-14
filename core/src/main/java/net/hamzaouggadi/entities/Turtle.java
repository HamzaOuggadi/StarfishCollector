package net.hamzaouggadi.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Turtle extends BaseActor {

    public Turtle(float x, float y, Stage stage) {
        super(x, y, stage);

        String[] fileNames = {"imgs/turtle-1.png", "imgs/turtle-2.png", "imgs/turtle-3.png",
                                "imgs/turtle-4.png", "imgs/turtle-5.png", "imgs/turtle-6.png"};

        loadAnimationFromFiles(fileNames, 0.1f, true);

        setBoundaryPolygon(8);

        setAcceleration(400);
        setMaxSpeed(200);
        setDeceleration(400);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            accelerateAtAngle(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            accelerateAtAngle(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelerateAtAngle(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            accelerateAtAngle(270);
        }

        applyPhysics(delta);

        setAnimationPaused(!isMoving());

        if (getSpeed() > 0) {
            setRotation(getMotionAngle());
        }
    }
}

package net.hamzaouggadi;


import net.hamzaouggadi.entities.BaseActor;
import net.hamzaouggadi.entities.GameBeta;
import net.hamzaouggadi.entities.Starfish;
import net.hamzaouggadi.entities.Turtle;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends GameBeta {

    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;

    @Override
    public void initialize() {
        ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture("imgs/water.jpg");
        ocean.setSize(800, 600);

        starfish = new Starfish(380,380, mainStage);

        turtle = new Turtle(20, 20, mainStage);
    }

    @Override
    public void update(float dt) {

    }
}

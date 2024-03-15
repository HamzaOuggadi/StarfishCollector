package net.hamzaouggadi;


import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import net.hamzaouggadi.entities.BaseActor;
import net.hamzaouggadi.entities.GameBeta;
import net.hamzaouggadi.entities.Rock;
import net.hamzaouggadi.entities.Starfish;
import net.hamzaouggadi.entities.Turtle;
import net.hamzaouggadi.entities.Whirlpool;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends GameBeta {

    private Turtle turtle;
    private Starfish starfish;
    private Rock rock;
    private BaseActor ocean;

    @Override
    public void initialize() {
        ocean = new BaseActor(0,0, mainStage);
        ocean.loadTexture("imgs/water.jpg");
        ocean.setSize(800, 600);

        starfish = new Starfish(380,380, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        rock = new Rock(200, 200, mainStage);
    }

    @Override
    public void update(float dt) {

        turtle.preventOverlap(rock);

        if (turtle.overlaps(starfish) && !starfish.isCollected()) {
            starfish.collect();

            Whirlpool whirlpool = new Whirlpool(0,0, mainStage);
            whirlpool.centerAtActor(starfish);
            whirlpool.setOpacity(0.25f);

            BaseActor youWinMessage = new BaseActor(0,0, mainStage);
            youWinMessage.loadTexture("imgs/you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}

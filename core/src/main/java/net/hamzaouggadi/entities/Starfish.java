package net.hamzaouggadi.entities;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Starfish extends BaseActor{

    public Starfish(float x, float y, Stage stage) {
        super(x, y, stage);

        loadTexture("imgs/starfish.png");

        Action spin = Actions.rotateBy(30f, 1f);
        this.addAction(Actions.forever(spin));
    }
}

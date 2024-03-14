package net.hamzaouggadi.entities;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Starfish extends BaseActor{

    private boolean collected;

    public Starfish(float x, float y, Stage stage) {
        super(x, y, stage);

        collected = false;

        loadTexture("imgs/starfish.png");

        setBoundaryPolygon(8);

        Action spin = Actions.rotateBy(30f, 1f);
        this.addAction(Actions.forever(spin));
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
        clearActions();
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }
}

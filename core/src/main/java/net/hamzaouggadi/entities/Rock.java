package net.hamzaouggadi.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Rock extends BaseActor {
    public Rock(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("imgs/rock.png");
        setBoundaryPolygon(8);
    }

}

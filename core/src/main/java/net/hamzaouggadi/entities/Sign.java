package net.hamzaouggadi.entities;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Sign extends BaseActor {

    private String text;
    private boolean viewing;

    public Sign(float x, float y, Stage stage) {
        super(x, y, stage);

        loadTexture("imgs/sign.png");

        text = " ";
        viewing = false;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setViewing(boolean viewing) {
        this.viewing = viewing;
    }

    public boolean isViewing() {
        return this.viewing;
    }
}

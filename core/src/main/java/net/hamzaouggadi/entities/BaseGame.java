package net.hamzaouggadi.entities;

import com.badlogic.gdx.Game;

public class BaseGame extends Game {

    public static BaseGame game;

    public BaseGame() {
        game = this;
    }

    public static void setActiveScreen(BaseScreen baseScreen) {
        game.setScreen(baseScreen);
    }

    @Override
    public void create() {

    }
}

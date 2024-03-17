package net.hamzaouggadi;


import net.hamzaouggadi.entities.BaseGame;
import net.hamzaouggadi.entities.MenuScreen;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class StarfishGame extends BaseGame {

    @Override
    public void create() {
        setActiveScreen(new MenuScreen());
    }
}

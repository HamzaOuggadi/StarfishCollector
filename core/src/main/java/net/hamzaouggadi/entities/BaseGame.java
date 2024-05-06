package net.hamzaouggadi.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class BaseGame extends Game {

    public static BaseGame game;
    public static LabelStyle labelStyle;
    public static TextButtonStyle textButtonStyle;

    public BaseGame() {
        game = this;
    }

    public static void setActiveScreen(BaseScreen baseScreen) {
        game.setScreen(baseScreen);
    }

    @Override
    public void create() {
        // Prepare for multiple classes stages to receive discrete input
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        labelStyle = new LabelStyle();
//        labelStyle.font = new BitmapFont(Gdx.files.internal("imgs/cooper.fnt"));

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("imgs/OpenSans.ttf"));

        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 48;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderStraight = true;
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;

        BitmapFont customFont = fontGenerator.generateFont(fontParameter);
        labelStyle.font = customFont;

        textButtonStyle = new TextButtonStyle();

        Texture buttonText = new Texture(Gdx.files.internal("imgs/button.png"));
        NinePatch buttonPatch = new NinePatch(buttonText, 24, 24, 24, 24);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);
        textButtonStyle.font = customFont;
        textButtonStyle.fontColor = Color.GRAY;



    }
}

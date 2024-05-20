package net.hamzaouggadi.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.hamzaouggadi.StarfishGame;

public class LevelScreen extends BaseScreen {

    private Turtle turtle;
    private boolean win;
    private Label starfishLabel;
    private DialogBox dialogBox;

    // Audio
    private float audioVolume;
    private Sound waterDrop;
    private Music instrumental;
    private Music oceanSurf;


    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("imgs/water-border.jpg");
        ocean.setSize(1200, 900);

        BaseActor.setWorldBounds(ocean);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        starfishLabel = new Label("Starfish Left : ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);



        ButtonStyle buttonStyle = new ButtonStyle();
        Texture buttonTexture = new Texture(Gdx.files.internal("imgs/undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTexture);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);

        ButtonStyle buttonStyle2 = new ButtonStyle();
        Texture buttonTexture2 = new Texture(Gdx.files.internal("imgs/audio.png"));
        TextureRegion buttonTextureRegion2 = new TextureRegion(buttonTexture2);
        buttonStyle2.up = new TextureRegionDrawable(buttonTextureRegion2);

        Button muteButton = new Button(buttonStyle2);
        muteButton.setColor(Color.CYAN);

        muteButton.addListener(
            (Event e) -> {
                if (!isTouchDownEvent(e)) {
                    return false;
                }

                audioVolume = 1 - audioVolume;
                instrumental.setVolume(audioVolume);
                oceanSurf.setVolume(audioVolume);
                if (muteButton.getColor().equals(Color.CYAN)) {
                    System.out.println(" 1st Block : ************** Mute Button Color : " + muteButton.getColor());
                    muteButton.setColor(Color.WHITE);
                } else {
                    System.out.println(" 2nd Block : ************** Mute Button Color : " + muteButton.getColor());
                    muteButton.setColor(Color.CYAN);
                }
                return true;
            }
        );

        uiTable.pad(10);
        uiTable.add(starfishLabel).top();
        uiTable.add().expandX().expandY();
        uiTable.add(muteButton).top();
        uiTable.add(restartButton).top();

        restartButton.addListener((Event e) -> {
            if ( !(e instanceof InputEvent) || !((InputEvent)e).getType().equals(InputEvent.Type.touchDown)) {
                return false;
            }

            // Disposing of the sound/music
            instrumental.dispose();
            oceanSurf.dispose();

            StarfishGame.setActiveScreen(new MenuScreen());
            return false;
        });


        Sign sign1 = new Sign(20, 400, mainStage);
        sign1.setText("West Starfish Bay");

        Sign sign2 = new Sign(600, 300, mainStage);
        sign2.setText("East Starfish Bay");

        dialogBox = new DialogBox(0, 0, uiStage);
        dialogBox.setBackgroundColor(Color.TAN);
        dialogBox.setFontColor(Color.BROWN);
        dialogBox.setDialogSize(600, 100);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.row();
        uiTable.add(dialogBox).colspan(4);

        waterDrop = Gdx.audio.newSound(Gdx.files.internal("audio/Water_Drop.ogg"));
        instrumental = Gdx.audio.newMusic(Gdx.files.internal("audio/Master_of_the_Feast.ogg"));
        oceanSurf = Gdx.audio.newMusic(Gdx.files.internal("audio/Ocean_Waves.ogg"));

        audioVolume = 1.00f;
        instrumental.setLooping(true);
        instrumental.setVolume(audioVolume);
        instrumental.play();
        oceanSurf.setLooping(true);
        oceanSurf.setVolume(audioVolume);
        oceanSurf.play();

        win = false;
    }

    @Override
    public void update(float dt) {

        for (BaseActor rockActor : BaseActor.getList(mainStage, Rock.class.getName())) {
            turtle.preventOverlap(rockActor);
        }

        for (BaseActor starfishActor : BaseActor.getList(mainStage, Starfish.class.getName())) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
                waterDrop.play(audioVolume);

                Whirlpool whirlpool = new Whirlpool(0, 0, mainStage);
                whirlpool.centerAtActor(starfish);
                whirlpool.setOpacity(0.25f);
            }
        }

        if (BaseActor.count(mainStage, Starfish.class.getName()) == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("imgs/you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(0.1f));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        for (BaseActor signActor : BaseActor.getList(mainStage, Sign.class.getName())) {

            Sign sign = (Sign) signActor;
            turtle.preventOverlap(sign);

            boolean nearby = turtle.isWithinDistance(4, sign);

            if (nearby && !sign.isViewing()) {
                dialogBox.setText(sign.getText());
                dialogBox.setVisible(true);
                sign.setViewing(true);
            }

            if (sign.isViewing() && !nearby) {
                dialogBox.setText(" ");
                dialogBox.setVisible(false);
                sign.setViewing(false);
            }
        }

        starfishLabel.setText("Starfish Left : " + BaseActor.count(mainStage, "net.hamzaouggadi.entities.Starfish"));
    }
}

package net.hamzaouggadi.entities;

import com.badlogic.gdx.scenes.scene2d.Action;

public class SetTextAction extends Action {

    protected String textToDisplay;


    public SetTextAction(String textToDisplay) {
        this.textToDisplay = textToDisplay;
    }

    @Override
    public boolean act(float delta) {
        DialogBox dialogBox = (DialogBox) target;
        dialogBox.setText(textToDisplay);
        return true;
    }
}

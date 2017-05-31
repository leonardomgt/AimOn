package com.aimon.game.view.game.inputprocessors;

import com.aimon.game.view.game.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Leo on 30/05/2017.
 */

public class AndroidInputProcessor extends GameInputProcessor {

    private Vector3 touchedPosition;
    private Vector3 currentAimPos;

    private Slider bar;



    public AndroidInputProcessor(GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    public void updateAim() {

//        projectAimToCamera(gameScreen.getAimPosition());
//
//        gameScreen.getAimPosition().add(Gdx.input.getDeltaX(), Gdx.input.getDeltaY(), 0);
//        gameScreen.getCamera().unproject(gameScreen.getAimPosition());
//
//        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x * gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y*gameScreen.PIXEL_TO_METER, 0);
//        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);

    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {

        gameScreen.getAimPosition().set(currentAimPos.x + (Gdx.input.getX() - touchedPosition.x), Gdx.graphics.getHeight()- currentAimPos.y + Gdx.input.getY() - touchedPosition.y, 0);
        gameScreen.getCamera().unproject(gameScreen.getAimPosition());

        gameScreen.getAimPosition().set(gameScreen.getAimPosition().x * gameScreen.PIXEL_TO_METER, gameScreen.getAimPosition().y*gameScreen.PIXEL_TO_METER, 0);
        gameScreen.getController().updateAimLocation(gameScreen.getAimPosition().x, gameScreen.getAimPosition().y);
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){

        touchedPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        currentAimPos = new Vector3(gameScreen.getAimPosition().x,gameScreen.getAimPosition().y,0) ;
        currentAimPos.set(currentAimPos.x / gameScreen.PIXEL_TO_METER, currentAimPos.y/gameScreen.PIXEL_TO_METER, 0);
        gameScreen.getCamera().project(currentAimPos);

        return true;
    }

    public void initializeUIElements() {

        super.initializeUIElements();

        float deltaToLimits = 16;

        //Zoom Slider
        TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sliderBar1.png"))));

        Slider.SliderStyle barStyle = new Slider.SliderStyle(gameScreen.game.getSkin().newDrawable("white", Color.DARK_GRAY), textureBar);
        bar = new Slider(0.5f, 1, 0.01f, true, barStyle);
        bar.setSize(textureBar.getRegion().getTexture().getWidth(), Gdx.graphics.getHeight()/2f);
        bar.setPosition(0, Gdx.graphics.getHeight()/4f);
        bar.setAnimateDuration(0.1f);
        bar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeZoomScroll(1.5f - bar.getValue());
            }
        });
        gameScreen.getGameStage().addActor(bar);


        // Reload Button
        ImageButton buttonReload = new ImageButton(gameScreen.game.getSkin());
        buttonReload.setSize(buttonReload.getHeight()/1, buttonReload.getHeight()/1);

        ImageButton.ImageButtonStyle reloadStyle = new ImageButton.ImageButtonStyle(buttonReload.getStyle());
        buttonReload.setStyle(reloadStyle);

        buttonReload.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ammo_empty.png"))));
        buttonReload.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ammo_empty.png"))));
        buttonReload.getStyle().up = buttonReload.getSkin().getDrawable("button");
        buttonReload.getStyle().down = buttonReload.getSkin().getDrawable("button-down");
        buttonReload.setPosition(Gdx.graphics.getWidth() - buttonReload.getWidth() - deltaToLimits, deltaToLimits);
        buttonReload.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                reloadGun();
            }
        });
        gameScreen.getGameStage().addActor(buttonReload);


        // Zoom Button
        ImageButton buttonFire = new ImageButton(gameScreen.game.getSkin());
        buttonFire.setSize(buttonFire.getHeight()/1, buttonFire.getHeight()/1);

        ImageButton.ImageButtonStyle fireStyle = new ImageButton.ImageButtonStyle(buttonFire.getStyle());
        buttonFire.setStyle(fireStyle);

        buttonFire.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("fire.png"))));
        buttonFire.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("fire.png"))));
        buttonFire.getStyle().up = buttonFire.getSkin().getDrawable("button"/*"button-small"*/);
        buttonFire.getStyle().down = buttonFire.getSkin().getDrawable("button-down"/*"button-small-down"*/);
        buttonFire.setPosition(deltaToLimits, deltaToLimits);
        buttonFire.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                shot();
            }
        });
        gameScreen.getGameStage().addActor(buttonFire);


    }

}

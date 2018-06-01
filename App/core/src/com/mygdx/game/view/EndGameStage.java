package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BattleShip;
import com.mygdx.game.utility.ButtonFactory;

import javax.xml.soap.Text;

public class EndGameStage extends Stage {

    private static final float VIEWPORT_WIDTH = 800;
    private float ratio;
    private BattleShip game;
    private Viewport viewport;
    private Table table;
    private Music music;

    EndGameStage(String status) {
        game = BattleShip.getInstance();

        ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        this.viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio);
        viewport.apply();
        this.setViewport(this.viewport);

        Gdx.input.setInputProcessor(this);

        table = new Table();
        table.setFillParent(true);
        this.addActor(table);

        table.row();

        TextButton statusField = ButtonFactory.createButton(status,80);
        table.add(statusField).width(VIEWPORT_WIDTH/5).height(VIEWPORT_WIDTH*ratio/5);

        table.row();

        TextButton tapInfo = ButtonFactory.createButton("TAP THE SCREEN TO PLAY AGAIN",30);
        table.add(tapInfo).width(VIEWPORT_WIDTH/5).height(VIEWPORT_WIDTH*ratio/5);

        this.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
               //what to do
            }
        });
    }

    @Override
    public Viewport getViewport() {
        return viewport;
    }
}
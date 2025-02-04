package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BattleShip;
import com.mygdx.game.controller.BoardController;
import com.mygdx.game.utility.ButtonFactory;

/**
 * Stage for the creator screen
 */
class CreatorStage extends Stage {
    private static final float VIEWPORT_WIDTH = 800;
    private float ratio;
    private BattleShip game;
    private Viewport viewport;
    private BoardController board;
    /**
     * CreatorStage constructor where the layout is set up and the table functions are called
     */
    CreatorStage() {
        game = BattleShip.getInstance();

        ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        this.viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio);
        viewport.apply();
        this.setViewport(this.viewport);

        Gdx.input.setInputProcessor(this);

        this.drawBoard();

        this.drawGui();
    }
    /**
     * Draws the user's board
     */
    private void drawBoard(){
        Table boardTable = new Table();
        boardTable.setFillParent(true);
        this.addActor(boardTable);

        board = new BoardController();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        boardTable.add().height(VIEWPORT_WIDTH*ratio/12).colspan(12);
        boardTable.row();

        for(int y = 0; y < BoardController.getDimension(); y++){
            boardTable.add().width(13*VIEWPORT_WIDTH/24);
            for(int x = 0; x < BoardController.getDimension(); x++){

                Texture cellTexture = game.getAssetManager().get("square.png");
                TextureRegion cellTextureRegion = new TextureRegion(cellTexture);
                TextureRegionDrawable cellTextureRegionDrawable = new TextureRegionDrawable(cellTextureRegion);
                Button.ButtonStyle style2 = new Button.ButtonStyle(cellTextureRegionDrawable,cellTextureRegionDrawable,cellTextureRegionDrawable);
                Button cellButton = new Button(); //Set the button up
                cellButton.setStyle(style2);


                boardTable.add(cellButton).width(VIEWPORT_WIDTH/24).height(VIEWPORT_WIDTH*ratio/12);
                board.getBoard().getMatrix()[y][x].setButton(cellButton);
            }
            boardTable.add().width(VIEWPORT_WIDTH/24);
            boardTable.row();
        }

        boardTable.add().height(VIEWPORT_WIDTH*ratio/12).colspan(12);
    }
    /**
     * Draws the GUI (buttons and ship options)
     */
    private void drawGui(){
        Table guiTable = new Table();
        guiTable.setFillParent(true);
        this.addActor(guiTable);

        TextButton title = ButtonFactory.createButton("Board Creator",30);

        guiTable.add(title).center().height(VIEWPORT_WIDTH*ratio/12).colspan(2);

        guiTable.row();

        for (int i = 0; i < 5; i++) {
            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
            ImageButton shipButton = new ImageButton(style);
            shipButton = createShipButton(i, shipButton);

            guiTable.add(shipButton).width(VIEWPORT_WIDTH/4).height(10*VIEWPORT_WIDTH * ratio / 80).center().expand();

            guiTable.add().width(VIEWPORT_WIDTH / 2).height(10*VIEWPORT_WIDTH * ratio / 108);

            guiTable.row();

            if(i != 4) {
                guiTable.add().width(VIEWPORT_WIDTH).height(10 * VIEWPORT_WIDTH * ratio / 108).colspan(2);

                guiTable.row();
            }
        }

        guiTable.add().height(VIEWPORT_WIDTH*ratio/12).colspan(2);

        Table auxTable = new Table();
        auxTable.setFillParent(true);
        this.addActor(auxTable);

        TextButton rotateButton = ButtonFactory.createButton("Rotate", 20);
        rotateButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                Sound sound = game.getAssetManager().get("buttonSound.mp3");
                sound.play(0.2f);
                if(board.getChosen() != null)
                    board.getChosen().rotate();
            }
        });

        auxTable.add(rotateButton).width(VIEWPORT_WIDTH / 4).height(VIEWPORT_WIDTH*ratio/12).expand().center().bottom();

        TextButton playButton = ButtonFactory.createButton("Start Playing", 20);
        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                Sound sound = game.getAssetManager().get("buttonSound.mp3");
                sound.play(0.2f);
                if(board.allPlaced())
                    game.setScreen(new DifficultyScreen(board));
            }
        });

        auxTable.add(playButton).width(VIEWPORT_WIDTH / 4).height(VIEWPORT_WIDTH*ratio/12).expand().center().bottom();
    }
    /**
     * Sets up the ship button
     * @param i             index of the ship
     * @param shipButton    ship button
     * @return ship button modified
     */
    private ImageButton createShipButton(int i,ImageButton shipButton) {
        switch(i){
            case 0:
                Texture myCarrier = game.getAssetManager().get("carrier.png");
                TextureRegion myCarrierRegion = new TextureRegion(myCarrier);
                TextureRegionDrawable myCarrierRegionDrawable = new TextureRegionDrawable(myCarrierRegion);

                shipButton = new ImageButton(myCarrierRegionDrawable); //Set the button up
                shipButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y){
                        board.setChosen(BoardController.Ships.carrier);
                    }
                });
                break;
            case 1:
                Texture myDreadnought = game.getAssetManager().get("dreadnought.png");
                TextureRegion myDreadnoughtRegion = new TextureRegion(myDreadnought);
                TextureRegionDrawable myDreadnoughtRegionDrawable = new TextureRegionDrawable(myDreadnoughtRegion);
                shipButton = new ImageButton(myDreadnoughtRegionDrawable); //Set the button up
                shipButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y){
                        board.setChosen(BoardController.Ships.dreadnought);
                    }
                });
                break;
            case 2:
                Texture mySubmarine = game.getAssetManager().get("submarine.png");
                TextureRegion mySubmarineRegion = new TextureRegion(mySubmarine);
                TextureRegionDrawable mySubmarineRegionDrawable = new TextureRegionDrawable(mySubmarineRegion);
                shipButton = new ImageButton(mySubmarineRegionDrawable); //Set the button up
                shipButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y){
                        board.setChosen(BoardController.Ships.submarine);
                    }
                });
                break;
            case 3:
                Texture myCruiser = game.getAssetManager().get("cruiser.png");
                TextureRegion myCruiserRegion = new TextureRegion(myCruiser);
                TextureRegionDrawable myCruiserRegionDrawable = new TextureRegionDrawable(myCruiserRegion);
                shipButton = new ImageButton(myCruiserRegionDrawable); //Set the button up
                shipButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y){
                        board.setChosen(BoardController.Ships.cruiser);
                    }
                });
                break;
            case 4:
                Texture myPatrolBoat = game.getAssetManager().get("patrolBoat.png");
                TextureRegion myPatrolBoatRegion = new TextureRegion(myPatrolBoat);
                TextureRegionDrawable myPatrolBoatRegionDrawable = new TextureRegionDrawable(myPatrolBoatRegion);

                shipButton = new ImageButton(myPatrolBoatRegionDrawable); //Set the button up
                shipButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y){
                        board.setChosen(BoardController.Ships.patrolBoat);
                    }
                });

                break;
        }
        return shipButton;
    }
    /**
     * Getter for the viewport
     * @return viewport
     */
    @Override
    public Viewport getViewport() {
        return viewport;
    }
}



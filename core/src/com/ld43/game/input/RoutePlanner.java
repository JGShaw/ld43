package com.ld43.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.ld43.game.map.TileMap;
import com.ld43.game.map.tiles.LandTile;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.map.tiles.WaterTile;

import java.util.List;

public class RoutePlanner extends InputAdapter {

    public static int scrolled;

    TileMap tileMap;
    private List<Tile> waypoints;

    public RoutePlanner(TileMap tileMap, List<Tile> waypoints) {
        this.tileMap = tileMap;
        this.waypoints = waypoints;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        int tileX = (int) Math.floor(screenX / Tile.getActualSize());
        int tileY = (int) Math.floor(screenY / Tile.getActualSize());
        Tile tile = new LandTile(tileX, tileY, true);
        waypoints.add(tile);
        tileMap.setTile(tileX, tileY, tile);


        return true;
    }


    @Override
    public boolean scrolled (int amount) {

        scrolled = amount;

        return false;
    }


}
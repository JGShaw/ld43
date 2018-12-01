package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.ld43.game.map.tiles.Tile;

import java.util.List;

public class RouteComponent implements Component {
    public List<Tile> tiles;
    public int tileIndex = 0;

    public RouteComponent(List<Tile> tiles){
        this.tiles = tiles;
    }

    public int[] getVector() {
        // TODO stop out of bounds
        Tile from = tiles.get(tileIndex);
        Tile to = tiles.get(tileIndex + 1);

        int[] vector = {to.getTileX() - from.getTileX(), to.getTileY() - from.getTileY()};
        return vector;
    }

    public void updateWaypoint(float x, float y) {
        // TODO stop out of bounds
        Tile tile = tiles.get(tileIndex + 1);

        double distance = Math.sqrt(Math.pow(tile.getX() - x, 2) + Math.pow(tile.getY() - y, 2));
        if(distance < 2f) {
            tileIndex += 1;
        }
    }
}

package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.math.MathUtils;

import java.util.List;

public class RouteComponent implements Component {
    public List<Tile> tiles;
    public int tileIndex = 0;

    public RouteComponent(List<Tile> tiles){
        this.tiles = tiles;
    }

    public double[] getVector(float speed) {
        if(tileIndex + 1 >= tiles.size()) { double[] a = {0, 0}; return a; }

        Tile from = tiles.get(tileIndex);
        Tile to = tiles.get(tileIndex + 1);

        double angle = MathUtils.angleBetweenPoints(from.getX(), from.getY(), to.getX(), to.getY());
        return MathUtils.movementVectors(speed, angle);
    }

    public void updateWaypoint(float x, float y) {
        if(tileIndex + 1 >= tiles.size()) { return; }

        Tile tile = tiles.get(tileIndex + 1);

        double distance = Math.sqrt(Math.pow(tile.getX() - x, 2) + Math.pow(tile.getY() - y, 2));
        if(distance < 2f) {
            tileIndex += 1;
        }
    }
}

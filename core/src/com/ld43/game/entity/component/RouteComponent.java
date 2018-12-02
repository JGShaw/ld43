package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.math.MathUtils;

import java.util.List;

public class RouteComponent implements Component {
    public List<Tile> route;
    public int tileIndex = 0;
    public boolean complete = false;

    public RouteComponent(List<Tile> route){
        this.route = route;
    }

    public double getAngle() {
        Tile from = route.get(tileIndex);
        Tile to = route.get(tileIndex + 1);

        return MathUtils.angleBetweenPoints(from.getX(), from.getY(), to.getX(), to.getY());
    }

    public void updateWaypoint(float x, float y) {
        if(complete) { return; }
        Tile tile = route.get(tileIndex + 1);

        double distance = Math.sqrt(Math.pow(tile.getX() - x, 2) + Math.pow(tile.getY() - y, 2));

        if(distance < 2f) {
            tileIndex += 1;
            complete = tileIndex + 1 >= route.size();
        }
    }

    public void addToPath(Tile tile) {
        route.add(tile);
        complete = false;
    }
}

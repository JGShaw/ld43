package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.math.MathUtils;

import java.util.List;

public class RouteComponent implements Component {
    public List<Tile> route;
    public int tileIndex = 0;
    public boolean complete = true;

    public RouteComponent(List<Tile> route){
        this.route = route;
    }

    public double getAngle(float x, float y) {
        Tile to = route.get(tileIndex);
        return MathUtils.angleBetweenPoints(x, y, to.getX(), to.getY());
    }

    public void updateWaypoint(float x, float y) {
        if(complete) { return; }
        Tile tile = route.get(tileIndex);

        double distance = Math.sqrt(Math.pow(tile.getX() - x, 2) + Math.pow(tile.getY() - y, 2));

        if(distance < 2f) {
            tileIndex += 1;
            complete = tileIndex >= route.size();
        }
    }

    public void addToPath(Tile tile) {
        route.add(tile);
        complete = false;
    }

    public float[] polylinePoints() {
        float[] points = new float[route.size() * 2];
        for(int i = 0; i < route.size() * 2; i+=2) {
            Tile t = route.get(i / 2);
            points[i] = t.getX();
            points[i + 1] = t.getY();
        }
        return points;
    }

    public void renderRoute(ShapeRenderer renderer) {
        float[] polyline = polylinePoints();

        if (polyline.length < 4) { return; }

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(1, 1, 1, 0.5f);
        renderer.polyline(polyline);
        renderer.end();
    }
}

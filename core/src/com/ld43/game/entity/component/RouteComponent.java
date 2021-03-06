package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ld43.game.map.tiles.Tile;
import com.ld43.game.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteComponent implements Component {
    public List<Tile> route;
    public Tile from;
    public int maxRouteSize;

    public RouteComponent(List<Tile> route, int maxRouteSize){
        this.route = route;
        this.maxRouteSize = maxRouteSize;
    }

    public double getAngle(float x, float y) {
        Tile to = route.get(0);
        return MathUtils.angleBetweenPoints(x, y, to.getX(), to.getY());
    }

    public void updateWaypoint(float x, float y) {
        if(isComplete()) { return; }
        Tile tile = route.get(0);

        double distance = Math.sqrt(Math.pow(tile.getX() - x, 2) + Math.pow(tile.getY() - y, 2));

        if(distance < 2f) {
            from = route.get(0);
            route.remove(0);
        }
    }

    public boolean isComplete(){
        return route.isEmpty();
    }

    public void addToPath(Tile tile) {
        if(isValidPath(tile)) { route.add(tile); }
    }

    private boolean isValidPath(Tile tile) {
        if (tile.isSolid()) {return false; }
        if(route.isEmpty()) { return true; }
        if(route.size() >= maxRouteSize) { return false; }

        Tile last = route.get(route.size() - 1);
        if(tile == last) { return false; }

        double distance = Math.sqrt(Math.pow(tile.getTileX() - last.getTileX(), 2) + Math.pow(tile.getTileY() - last.getTileY(), 2));
        return distance < 2f;
    }


    public void renderRoute(ShapeRenderer renderer) {
        float[] polyline = polylinePoints();

        if (polyline.length < 4) { return; }

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(1, 1, 1, 0.5f);
        renderer.polyline(polyline);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0,1,0,1);
        Tile last = route.get(route.size() - 1);
        renderer.circle(last.getX(), last.getY(), 10);
        renderer.end();
    }

    private float[] polylinePoints() {
        List<Integer> points = new ArrayList<Integer>();

        if(from != null) {
            points.add(from.getX());
            points.add(from.getY());
        }

        for(Tile tile : route) {
            points.add(tile.getX());
            points.add(tile.getY());
        }

        float[] floats = new float[points.size()];
        for(int i = 0; i < points.size(); i++) {
            floats[i] = points.get(i).floatValue();
        }
        return floats;
    }
}

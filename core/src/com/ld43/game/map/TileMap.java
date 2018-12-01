package com.ld43.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.ld43.game.map.tiles.LandTile;
import com.ld43.game.map.tiles.*;

public class TileMap {


    private int widthInTiles, heightInTiles;
    private Tile[] tiles;

    public TileMap(int widthInTiles, int heightInTiles, Tile[] tiles) {

        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.tiles = tiles;

    }

    public void render(Batch batch){

        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                Texture img = tiles[x + y * widthInTiles].getTexture();
                batch.draw(img, x * 32, y * 32, 32, 32);
            }
        }

    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < heightInTiles; y++) {
            for (int x = 0; x < widthInTiles; x++) {
                sb.append(tiles[x + y * widthInTiles].isSolid() ? "X" : " ");
            }
            sb.append("\n");
        }

        return sb.toString();

    }

    public static TileMap borderMap(int widthInTiles, int heightInTiles) {
        int numOfTiles = widthInTiles * heightInTiles;
        Tile[] tiles = new Tile[numOfTiles];

        for (int x = 0; x < widthInTiles; x++) {
            for (int y = 0; y < heightInTiles; y++) {
                boolean solid = x == 0 || x == widthInTiles - 1 || y == 0 || y == heightInTiles - 1;
                tiles[x + y * widthInTiles] = solid ? new LandTile(x, y, solid) : new WaterTile(x, y, solid);
            }
        }

        return new TileMap(widthInTiles, heightInTiles, tiles);
    }

    public static TileMap circleMap(int widthInTiles, int heightInTiles, int radius, int islandSize) {
        int numOfTiles = widthInTiles * heightInTiles;
        Tile[] tiles = new Tile[numOfTiles];

        int centerX = widthInTiles / 2;
        int centerY = heightInTiles / 2;

        for (int x = 0; x < widthInTiles; x++) {
            for (int y = 0; y < heightInTiles; y++) {
                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

                Tile t;
                if(distance < islandSize) {
                    t = new LandTile(x, y, true);
                } else {
                    t = new WaterTile(x, y, distance > radius);
                }
                tiles[x + y * widthInTiles] = t;
            }
        }

        return new TileMap(widthInTiles, heightInTiles, tiles);
    }
}

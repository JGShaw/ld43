package com.ld43.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ld43.game.graphics.TextureRegistry;
import com.ld43.game.map.beans.TileBean;
import com.ld43.game.map.beans.TileMapBean;
import com.ld43.game.map.tiles.LandTile;
import com.ld43.game.map.tiles.*;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

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
                Tile tile = tiles[x + y * widthInTiles];
                TextureRegion img = tile.getTexture();
                batch.draw(img, tile.getX() - Tile.TILE_WIDTH / 2, tile.getY() - Tile.TILE_WIDTH / 2, Tile.TILE_WIDTH, Tile.TILE_WIDTH);
            }
        }

    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        if(x < 0 || x >= widthInTiles || y < 0 || y >= heightInTiles) {
            return null;
        }

        return tiles[x + y * widthInTiles];
    }

    public void setTile(int tileX, int tileY, Tile tile) {
        tiles[tileX + tileY* widthInTiles] = tile;
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

    public static TileMap fromFile(String fileName){

        TileMapBean map = json.fromJson(TileMapBean.class,
                Gdx.files.internal(fileName));

        int widthInTiles = map.getTileswide();
        int heightInTiles = map.getTileshigh();

        Tile[] tiles = new Tile[widthInTiles * heightInTiles];

        for(TileBean tile: map.getLayers().get(0).getTiles()) {

            int x = tile.getX();
            int y = heightInTiles - tile.getY() - 1;

            WaterTile newTile = new WaterTile(x, y, tile.getTile() > 0);
            if(tile.getTile() <= 13){
                newTile.setTexture(TextureRegistry.getTexture("tile-water--" + tile.getTile()));
            }else {
                newTile.setTexture(TextureRegistry.getTexture("tile-land--" + (tile.getTile() - 18)));
            }
            tiles[x + y * widthInTiles] = newTile;
        }

        return new TileMap(widthInTiles, heightInTiles, tiles);

    }

}

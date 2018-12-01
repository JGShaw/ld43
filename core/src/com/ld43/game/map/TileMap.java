package com.ld43.game.map;

import com.ld43.game.map.tiles.Tile;

import java.util.Random;

public class TileMap {

    private int widthInTiles, heightInTiles;
    private Tile[] tiles;

    public TileMap(int widthInTiles, int heightInTiles, Tile[] tiles) {

        this.widthInTiles = widthInTiles;
        this.heightInTiles = heightInTiles;
        this.tiles = tiles;

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

    public static TileMap emptyMap(int widthInTiles, int heightInTiles) {

        int numOfTiles = widthInTiles * heightInTiles;
        Tile[] tiles = new Tile[numOfTiles];

        for (int x = 0; x < widthInTiles; x++) {
            for (int y = 0; y < heightInTiles; y++) {
                tiles[x + y * widthInTiles] = new Tile(x, y, false);
            }
        }

        return new TileMap(widthInTiles, heightInTiles, tiles);

    }

    public static TileMap borderMap(int widthInTiles, int heightInTiles) {

        int numOfTiles = widthInTiles * heightInTiles;
        Tile[] tiles = new Tile[numOfTiles];

        for (int x = 0; x < widthInTiles; x++) {
            for (int y = 0; y < heightInTiles; y++) {
                boolean solid = x == 0 || x == widthInTiles - 1 || y == 0 || y == heightInTiles - 1;
                tiles[x + y * widthInTiles] = new Tile(x, y, solid);
            }
        }

        return new TileMap(widthInTiles, heightInTiles, tiles);

    }

    public static TileMap randomMap(int widthInTiles, int heightInTiles) {

        int numOfTiles = widthInTiles * heightInTiles;
        Tile[] tiles = new Tile[numOfTiles];

        Random random = new Random();

        for (int x = 0; x < widthInTiles; x++) {
            for (int y = 0; y < heightInTiles; y++) {
                tiles[x + y * widthInTiles] = new Tile(x, y, random.nextBoolean());
            }
        }

        return new TileMap(widthInTiles, heightInTiles, tiles);

    }

}

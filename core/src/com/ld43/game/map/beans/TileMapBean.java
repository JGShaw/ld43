package com.ld43.game.map.beans;

import java.util.List;

public class TileMapBean {

    private Integer tileswide;
    private Integer tileheight;
    private Integer tileshigh;
    private List<LayerBean> layers = null;
    private Integer tilewidth;

    public Integer getTileswide() {
        return tileswide;
    }

    public void setTileswide(Integer tileswide) {
        this.tileswide = tileswide;
    }

    public Integer getTileheight() {
        return tileheight;
    }

    public void setTileheight(Integer tileheight) {
        this.tileheight = tileheight;
    }

    public Integer getTileshigh() {
        return tileshigh;
    }

    public void setTileshigh(Integer tileshigh) {
        this.tileshigh = tileshigh;
    }

    public List<LayerBean> getLayers() {
        return layers;
    }

    public void setLayers(List<LayerBean> layers) {
        this.layers = layers;
    }

    public Integer getTilewidth() {
        return tilewidth;
    }

    public void setTilewidth(Integer tilewidth) {
        this.tilewidth = tilewidth;
    }

}



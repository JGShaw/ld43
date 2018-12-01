package com.ld43.game.map.beans;

import java.util.List;

public class LayerBean {

    private String name;
    private List<TileBean> tiles = null;
    private Integer number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TileBean> getTiles() {
        return tiles;
    }

    public void setTiles(List<TileBean> tiles) {
        this.tiles = tiles;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}

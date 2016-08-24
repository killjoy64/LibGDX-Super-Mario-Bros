package com.stacktracemusic.mariobros.world.tiles;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.ItemManager;

/**
 * Created by kylef_000 on 4/19/2016.
 */
public abstract class Tile {

    protected TiledMap map;
    protected MapObject object;
    protected World world;
    protected TiledMapTileSet tileSet;
    protected ItemManager itemManager;

    protected Body tileBody;
    protected Rectangle objectBox;
    protected Fixture tileFixture;


    public Tile(TiledMap map, World world, MapObject object, ItemManager itemManager) {
        this.object = object;
        this.map = map;
        this.world = world;
        this.itemManager = itemManager;
        this.objectBox = ((RectangleMapObject) object).getRectangle();
        this.tileSet = map.getTileSets().getTileSet(Resources.DEFAULT_TILESET);
    }

    public abstract void onPlayerCollide(Mario mario);
    public abstract void create();

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int) (tileBody.getPosition().x * Config.SCALE / 16), (int) (tileBody.getPosition().y * Config.SCALE / 16));
    }

    public MapObject getMapObject() {
        return object;
    }

    public Body getTileBody() {
        return tileBody;
    }

}

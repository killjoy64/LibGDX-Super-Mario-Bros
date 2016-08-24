package com.stacktracemusic.mariobros.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.world.tiles.BrickTile;
import com.stacktracemusic.mariobros.world.tiles.ItemBlockTile;
import com.stacktracemusic.mariobros.world.tiles.SolidTile;
import com.stacktracemusic.mariobros.world.tiles.Tile;

/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class TileCreator {

    public static int SOLIDS_LAYER = 2;
    public static int GRAPHIC_LAYER = 1;

    private World world;
    private TiledMap tiledMap;
    private ItemManager itemManager;

    private Array<Integer> tileLayers;

    public TileCreator(World world, TiledMap tiledMap, ItemManager itemManager) {
        this.world = world;
        this.tiledMap = tiledMap;
        this.itemManager = itemManager;

        tileLayers = new Array<Integer>();
        tileLayers.add(SOLIDS_LAYER);
    }

    public void create() {

        for (int layer : tileLayers) {
            for (MapObject object : tiledMap.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)) {
                Tile tile;

                // TODO - Create if statements for properties and such to declare friction and restitution
                if (object.getProperties().containsKey("breakable")) {
                    if (object.getProperties().get("breakable").equals("true")) {
                        // TODO - declare new instance of breakable brick
                        tile = new BrickTile(tiledMap, world, object, itemManager);
                        tile.create();
                    } else {
                        if (object.getProperties().containsKey("item")) {
                            // TODO - declare new instance of coin block with item
                            tile = new ItemBlockTile(tiledMap, world, object, itemManager);
                            tile.create();
                        } else {
                            // TODO - declare new instance of coin block without item
                            tile = new ItemBlockTile(tiledMap, world, object, itemManager);
                            tile.create();
                        }
                    }
                } else {
                    tile = new SolidTile(tiledMap, world, object, itemManager);
                    tile.create();
                }

            }
        }
    }



}

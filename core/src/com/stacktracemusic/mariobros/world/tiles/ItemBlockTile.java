package com.stacktracemusic.mariobros.world.tiles;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.stacktracemusic.mariobros.entities.Mario;
import com.stacktracemusic.mariobros.items.Item;
import com.stacktracemusic.mariobros.items.Mushroom;
import com.stacktracemusic.mariobros.util.Config;
import com.stacktracemusic.mariobros.util.Resources;
import com.stacktracemusic.mariobros.world.ItemManager;

/**
 * Created by kylef_000 on 4/19/2016.
 */
public class ItemBlockTile extends Tile {
    public ItemBlockTile(TiledMap map, World world, MapObject object, ItemManager itemManager) {
        super(map, world, object, itemManager);
    }

    @Override
    public void onPlayerCollide(Mario mario) {
        if (getCell().getTile().getId() != 28) {
            if (getMapObject().getProperties().containsKey("item")) {
                // Spawn an item
                if (getMapObject().getProperties().get("item").equals("mushroom")) {
                    mario.getAssetManager().get(Resources.MUSHROOM_SPAWN, Sound.class).play();
                    if (itemManager != null) {
                        itemManager.addItem(new Mushroom(world, tileBody.getPosition().x, tileBody.getPosition().y + 16 / Config.SCALE));
                        getCell().setTile(tileSet.getTile(28));
                    }
                }
            } else {
                // Coin!
                getCell().setTile(tileSet.getTile(28));
                mario.getAssetManager().get(Resources.COIN, Sound.class).play();
                mario.getGameData().addScore(200);
                mario.getGameData().addCoin();
            }
        } else {
            mario.getAssetManager().get(Resources.TILE_BUMP, Sound.class).play();
        }
    }

    @Override
    public void create() {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((objectBox.getX() + objectBox.getWidth() / 2) / Config.SCALE, (objectBox.getY() + objectBox.getHeight() / 2) / Config.SCALE);

        tileBody = world.createBody(bodyDef);

        shape.setAsBox(objectBox.getWidth() / 2 / Config.SCALE, objectBox.getHeight() / 2 / Config.SCALE);
        fixDef.shape = shape;
        fixDef.filter.categoryBits = Config.SOLID_TILE_BIT;
        tileFixture = tileBody.createFixture(fixDef);
        tileFixture.setUserData(this);

        shape.dispose();
    }
}

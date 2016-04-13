package com.stacktracemusic.mariobros.entities.enemies;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.stacktracemusic.mariobros.entities.Entity;

/**
 * Created by Kyle Flynn on 4/8/2016.
 */
public abstract class Enemy extends Entity {

    public Enemy(World world, TiledMap map) {
        super(world, map);
    }

}

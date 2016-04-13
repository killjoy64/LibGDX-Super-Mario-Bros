package com.stacktracemusic.mariobros.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.stacktracemusic.mariobros.util.Config;

/**
 * Created by Kyle Flynn on 4/9/2016.
 */
public class TileCreator {

    public static int SOLIDS_LAYER = 2;
    public static int GRAPHIC_LAYER = 1;

    private World world;
    private TiledMap tiledMap;

    private Array<Integer> tileLayers;

    public TileCreator(World world, TiledMap tiledMap) {
        this.world = world;
        this.tiledMap = tiledMap;

        tileLayers = new Array<Integer>();
        tileLayers.add(SOLIDS_LAYER);
    }

    public void create() {

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();
        Body body;

        for (int layer : tileLayers) {
            for (MapObject object : tiledMap.getLayers().get(layer).getObjects()) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

                bodyDef.type = BodyDef.BodyType.StaticBody;
                bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / Config.SCALE, (rectangle.getY() + rectangle.getHeight() / 2) / Config.SCALE);

                body = world.createBody(bodyDef);

                shape.setAsBox(rectangle.getWidth() / 2 / Config.SCALE, rectangle.getHeight() / 2 / Config.SCALE);
                fixture.shape = shape;

                // TODO - Create if statements for properties and such to declare friction and restitution

                body.createFixture(fixture);
            }
        }
    }



}

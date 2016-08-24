package com.stacktracemusic.mariobros.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.stacktracemusic.mariobros.items.Item;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by kylef_000 on 4/26/2016.
 */
public class ItemManager {

    private static Array<Item> items;
    private LinkedBlockingQueue<Item> itemQueue;

    public ItemManager() {
        items = new Array<Item>();
        itemQueue = new LinkedBlockingQueue<Item>();
    }

    public void update(float delta) {

        if (!itemQueue.isEmpty()) {
            Item item = itemQueue.poll();
            item.createItem();
            items.add(item);
        }

        for (Item item : items) {
            item.update(delta);
        }
    }

    public void draw(Batch batch) {
        for (Item item : items) {
            item.draw(batch);
        }
    }

    public void addItem(Item item) {
        itemQueue.add(item);
    }

}

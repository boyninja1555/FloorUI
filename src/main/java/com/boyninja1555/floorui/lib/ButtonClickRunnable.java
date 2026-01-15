package com.boyninja1555.floorui.lib;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ButtonClickRunnable implements Runnable {
    private Player player;
    private InventoryClickEvent event;

    public void event(InventoryClickEvent event) {
        this.event = event;
    }

    public InventoryClickEvent event() {
        return event;
    }

    public void player(Player player) {
        this.player = player;
    }

    public Player player() {
        return player;
    }

    @Override
    abstract public void run();
}

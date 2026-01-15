package com.boyninja1555.floorui.api;

import com.boyninja1555.floorui.FloorUI;
import com.boyninja1555.floorui.lib.ButtonClickRunnable;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class InventoryUI implements InventoryHolder, Listener {
    private final Inventory inventory;
    private final Map<Vector2d, UIButton> buttons;
    private final Map<String, ButtonClickRunnable> leftClickActions;
    private final Map<String, ButtonClickRunnable> rightClickActions;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof InventoryUI ui))
            return;

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item == null)
            return;

        UIButton button = UIButton.fromItem(item);

        if (event.isLeftClick()) {
            ButtonClickRunnable onLeftClick = leftClickActions.get(button.leftClick());
            onLeftClick.event(event);
            onLeftClick.player(player);
            onLeftClick.run();
        } else if (event.isRightClick()) {
            ButtonClickRunnable onRightClick = rightClickActions.get(button.rightClick());
            onRightClick.event(event);
            onRightClick.player(player);
            onRightClick.run();
        }
    }

    private void update() {
        int rowLength = 9;

        buttons.forEach((location, button) -> {
            int x = (int) location.x();
            int y = (int) location.y();
            int slot = (y - 1) * rowLength + (x - 1);
            if (slot >= 0 && slot < inventory.getSize())
                inventory.setItem(slot, button.toItem());
        });
    }

    /**
     * Creates an inventory-based UI
     * @param variant Determines the number of slots
     * @param title Replaces the text where you see "Large Chest"
     * @param buttons List of buttons to start with
     */
    public InventoryUI(InventoryUIVariant variant, Component title, Map<Vector2d, UIButton> buttons, Map<String, ButtonClickRunnable> leftClickActions, Map<String, ButtonClickRunnable> rightClickActions) {
        this.inventory = Bukkit.createInventory(this, variant.size(), title);
        this.buttons = buttons;
        this.leftClickActions = leftClickActions;
        this.rightClickActions = rightClickActions;
        update();
        Bukkit.getPluginManager().registerEvents(this, FloorUI.INSTANCE);
    }

    /**
     * Creates an inventory-based UI
     * @param variant Determines the number of slots
     * @param title Replaces the text where you see "Large Chest"
     */
    public InventoryUI(InventoryUIVariant variant, Component title) {
        this.inventory = Bukkit.createInventory(this, variant.size(), title);
        this.buttons = new HashMap<>();
        this.leftClickActions = new HashMap<>();
        this.rightClickActions = new HashMap<>();
        update();
        Bukkit.getPluginManager().registerEvents(this, FloorUI.INSTANCE);
    }

    /**
     * <p>Creates an inventory-based UI</p>
     * <strong>WARNING:</strong> Using raw text in inventory titles is not recommended for customizability
     * @param variant Determines the number of slots
     * @param title Replaces the text where you see "Large Chest"
     * @param buttons List of buttons to start with
     */
    public InventoryUI(InventoryUIVariant variant, String title, Map<Vector2d, UIButton> buttons, Map<String, ButtonClickRunnable> leftClickActions, Map<String, ButtonClickRunnable> rightClickActions) {
        this.inventory = Bukkit.createInventory(this, variant.size(), Component.text(title));
        this.buttons = buttons;
        this.leftClickActions = leftClickActions;
        this.rightClickActions = rightClickActions;
        update();
        Bukkit.getPluginManager().registerEvents(this, FloorUI.INSTANCE);
    }

    /**
     * <p>Creates an inventory-based UI</p>
     * <strong>WARNING:</strong> Using raw text in inventory titles is not recommended for customizability
     * @param variant Determines the number of slots
     * @param title Replaces the text where you see "Large Chest"
     */
    public InventoryUI(InventoryUIVariant variant, String title) {
        this.inventory = Bukkit.createInventory(this, variant.size(), Component.text(title));
        this.buttons = new HashMap<>();
        this.leftClickActions = new HashMap<>();
        this.rightClickActions = new HashMap<>();
        update();
        Bukkit.getPluginManager().registerEvents(this, FloorUI.INSTANCE);
    }

    /**
     * Shows the UI to a player
     * @param player The player
     */
    public void show(Player player) {
        player.openInventory(inventory);
    }

    /**
     * Sets a location in the UI to a button
     * @param location Target slot represented as an XY location in the UI
     * @param button Built button
     */
    public void setButton(Vector2d location, UIButton button) {
        buttons.put(location, button);
        update();
    }

    /**
     * Sets a location in the UI to a button
     * @param x Target slot represented as an XY location in the UI (x)
     * @param y Target slot represented as an XY location in the UI (y)
     * @param button Built button
     */
    public void setButton(int x, int y, UIButton button) {
        buttons.put(new Vector2d(x, y), button);
        update();
    }

    /**
     * Adds a button to a location in the UI
     * @param location Target slot represented as an XY location in the UI
     * @param button Built button
     */
    public void addButton(Vector2d location, UIButton button) {
        setButton(location, button);
        update();
    }

    /**
     * Adds a button to a location in the UI
     * @param x Target slot represented as an XY location in the UI (x)
     * @param y Target slot represented as an XY location in the UI (y)
     * @param button Built button
     */
    public void addButton(int x, int y, UIButton button) {
        addButton(new Vector2d(x, y), button);
    }

    /**
     * Gets a button by its XY location
     * @param location Target slot represented as an XY location in the UI
     * @return UIButton - Button found, <code>null</code> if not found
     */
    public UIButton getButton(Vector2d location) {
        if (!buttons.containsKey(location))
            return null;

        return buttons.get(location);
    }

    /**
     * Gets a button by its XY location
     * @param x Target slot represented as an XY location in the UI (x)
     * @param y Target slot represented as an XY location in the UI (y)
     * @return UIButton - Button found, <code>null</code> if not found
     */
    public UIButton getButton(int x, int y) {
        return getButton(new Vector2d(x, y));
    }

    /**
     * Removes a button by its XY location
     * @param location Target slot represented as an XY location in the UI
     */
    public void removeButton(Vector2d location) {
        if (!buttons.containsKey(location))
            return;

        buttons.remove(location);
        update();
    }

    /**
     * Removes a button by its XY location
     * @param x Target slot represented as an XY location in the UI (x)
     * @param y Target slot represented as an XY location in the UI (y)
     */
    public void removeButton(int x, int y) {
        removeButton(new Vector2d(x, y));
    }

    /**
     * <strong>STRONG WARNING:</strong> Only use this when our default logic doesn't work for you! Our API is designed to be simple, which is why we do not recommend calling this method EVER
     * <p>Instead, try using <code>InventoryUI::show(Player)</code> This opens the UI for a player</p>
     * @return Inventory - The internal inventory
     */
    @ApiStatus.Internal
    @NotNull
    public Inventory getInventory() {
        return inventory;
    }
}

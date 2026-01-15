package com.boyninja1555.floorui.api;

import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Creates a button usable in any inventory-based UI, based on a chain of method calls (commonly referred to as a builder)</p>
 * <strong>Examples:</strong>
 * <pre>
 * UIButton.builder()
 *     .icon(Material.EMERALD_BLOCK)
 *     .label(Component.text("Unnamed Button"));
 * </pre>
 * <pre>
 * UIButton.builder()
 *     .icon(Material.NOTE_BLOCK)
 *     .label(Component.text("Counter"))
 *     .numberDisplay(6);
 * </pre>
 */
public class UIButton {
    private Material icon = Material.EMERALD_BLOCK;
    private Component label = Component.text("Unnamed Button");
    private int numberDisplay = 1;
    private String leftClickId = null;
    private String leftClickDescription = null;
    private String rightClickId = null;
    private String rightClickDescription = null;

    /**
     * Sets the button's icon
     * @param icon New icon
     * @return UIButton - Builder
     */
    public UIButton icon(Material icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Sets the button's label
     * @param label New label
     * @return UIButton - Builder
     */
    public UIButton label(Component label) {
        this.label = label;
        return this;
    }

    /**
     * Sets the button's number display
     * @param numberDisplay New number display
     * @return UIButton - Builder
     */
    public UIButton numberDisplay(int numberDisplay) {
        this.numberDisplay = numberDisplay;
        return this;
    }

    public UIButton leftClick(String id, String description) {
        this.leftClickId = id;
        this.leftClickDescription = description;
        return this;
    }

    public UIButton leftClick(String id) {
        this.leftClickId = id;
        return this;
    }

    public UIButton rightClick(String id, String description) {
        this.rightClickId = id;
        this.rightClickDescription = description;
        return this;
    }

    public UIButton rightClick(String id) {
        this.rightClickId = id;
        return this;
    }

    public String leftClick() {
        return leftClickId;
    }

    public String rightClick() {
        return rightClickId;
    }

    /**
     * Allows use of button in inventory UIs
     * @return ItemStack - Button as item
     */
    @ApiStatus.Internal
    public ItemStack toItem() {
        ItemStack item = ItemStack.of(icon, numberDisplay);
        item.editPersistentDataContainer(data -> {
            NamespacedKey leftClickKey = new NamespacedKey("ui", "left-click");
            data.set(leftClickKey, PersistentDataType.STRING, leftClickId);

            NamespacedKey rightClickKey = new NamespacedKey("ui", "right-click");
            data.set(rightClickKey, PersistentDataType.STRING, rightClickId);
        });

        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        meta.itemName(label);

        if (leftClickDescription != null)
            lore.add(Component.empty()
                    .append(Component
                            .text("Left click ", NamedTextColor.DARK_AQUA)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text(leftClickDescription, NamedTextColor.DARK_AQUA))
                    .decoration(TextDecoration.ITALIC, false));

        if (rightClickDescription != null)
            lore.add(Component.empty()
                    .append(Component
                            .text("Right click ", NamedTextColor.DARK_AQUA)
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text(rightClickDescription, NamedTextColor.DARK_AQUA))
                    .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Allows click actions
     * @return ItemStack - Button as item
     */
    @ApiStatus.Internal
    public static UIButton fromItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainerView data = item.getPersistentDataContainer();

        NamespacedKey leftClickKey = new NamespacedKey("ui", "left-click");
        String leftClick = data.get(leftClickKey, PersistentDataType.STRING);

        NamespacedKey rightClickKey = new NamespacedKey("ui", "right-click");
        String rightClick = data.get(rightClickKey, PersistentDataType.STRING);

        return UIButton.builder()
                .icon(item.getType())
                .label(meta.itemName())
                .numberDisplay(item.getAmount())
                .leftClick(leftClick)
                .rightClick(rightClick);
    }

    /**
     * <p>Creates a button usable in any inventory-based UI, based on a chain of method calls (commonly referred to as a builder)</p>
     * <strong>Example:</strong>
     * <pre>
     * UIButton.builder()
     *     .icon(Material.EMERALD_BLOCK)
     *     .label(Component.text("Unnamed Button"));
     * </pre>
     */
    public static UIButton builder() {
        return new UIButton();
    }
}

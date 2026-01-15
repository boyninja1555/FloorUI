package com.boyninja1555.floorui.commands;

import com.boyninja1555.floorui.FloorUI;
import com.boyninja1555.floorui.api.InventoryUI;
import com.boyninja1555.floorui.api.InventoryUIVariant;
import com.boyninja1555.floorui.api.UIButton;
import com.boyninja1555.floorui.lib.ButtonClickRunnable;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.joml.Vector2d;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public record ExampleC(FloorUI plugin, String command) implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String @NonNull [] args) {
        if (!(source.getSender() instanceof Player player))
            return;

        ButtonClickRunnable runnable = new ButtonClickRunnable() {

            @Override
            public void run() {
                event().setCancelled(true);
                event().getInventory().close();
            }
        };
        InventoryUI ui = new InventoryUI(InventoryUIVariant.CHEST, "Example", Map.of(
                new Vector2d(2, 2), UIButton.builder()
                        .icon(Material.LAVA_BUCKET)
                        .label(Component.text("Bukkit"))
                        .leftClick("bukkit", "to exit")
                        .rightClick("bukkit", "to exit")
        ), Map.of(
                "bukkit", runnable
        ), Map.of(
                "bukkit", runnable
        ));
        ui.show(player);
    }
}

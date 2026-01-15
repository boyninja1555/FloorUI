package com.boyninja1555.floorui;

import com.boyninja1555.floorui.commands.ExampleC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class FloorUI extends JavaPlugin {
    public static FloorUI INSTANCE;
    public static List<Component> banner;

    @Override
    public void onEnable() {
        INSTANCE = this;

        saveDefaultConfig();
        banner = List.of(
                Component.empty()
                        .append(Component.text("FloorUI", NamedTextColor.DARK_AQUA))
                        .append(Component.text(" v" + getPluginMeta().getVersion(), NamedTextColor.GRAY)),
                Component.empty()
                        .append(Component.text("by ", NamedTextColor.GRAY))
                        .append(Component.text(String.join(", ", getPluginMeta().getAuthors()), NamedTextColor.DARK_AQUA))
        );

        if (getConfig().getBoolean("show-banner"))
            banner.forEach(line -> Bukkit.getConsoleSender().sendMessage(line));

        ExampleC exampleC = new ExampleC(this, "floorui-example");
        registerCommand(exampleC.command(), "An example UI created with the FloorUI library", exampleC);
    }
}

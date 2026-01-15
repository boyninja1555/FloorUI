<div>
    <h1 align="center">FloorUI</h1>
    <p align="center">Inventory-based GUI library, made for beginners</p>
</div>

---

> **Note:** This library is Paper-only! However, it may also work on forks of Paper. We have tested on Paper, Folia and Purpur

## The Idea

Have trouble making UIs? You're either a beginner, or fed up with other libraries. The competition, a lot of times, create entire FRAMEWORKS for GUIs. It's definitely too much for that guide menu of yours! FloorUI is essentially just a set of helper classes you can use in your own Paper plugins. Anywhere you can access a player reference, creating UIs will work!

---

## How We Version Our Releases

Read more on this [here](version-plans.md), the Version Plans file

---

## Examples

Moderator Menu

```java
// ... (possible command code)

Player player = Bukkit.getPlayer("JohnDoe12");
ButtonClickRunnable banPlayer = new ButtonClickRunnable() {
    @Override
    public void run() {
        // event() is the InventoryClickEvent, meaning it lets you manage what happens when a button is used
        event().setCancelled(true); // Stops button's item from being taken by player
        event().getInventory().close(); // Closes the menu
        // TODO: Ban the player
    }
};
ButtonClickRunnable kickPlayer = new ButtonClickRunnable() {
    @Override
    public void run() {
        event().setCancelled(true);
        event().getInventory().close();
        // TODO: Kick the player
    }
};

InventoryUI ui = new InventoryUI(
        InventoryUIVariant.CHEST, // The inventory type, which determines it's size
        "Moderate - " + player.getName(), // Menu title (can also be an Adventure Component for complex styling)
        Map.of( // Buttons to start with
                new Vector2d(2, 2), UIButton.builder() // location on screen (x, y) -> UIButton
                        .icon(Material.MACE) // Any block/item viewable in your inventory works here
                        .label(Component.text("Ban")) // This is ALWAYS an Adventure Component for customizability
                        .leftClick("ban-player", "to ban this player") // Optional: Tells the player what left-clicking does
                        .rightClick("ban-player", "to ban this player"), // Optional: Tells the player what right-clicking does
                new Vector2d(3, 2), UIButton.builder()
                        .icon(Material.DIAMOND_SWORD)
                        .label(Component.text("Kick"))
                        .leftClick("kick-player", "to kick this player")
                        .rightClick("kick-player", "to kick this player")
        ),
        Map.of( // Left click action code
                "ban-player", banPlayer,
                "kick-player", kickPlayer
        ),
        Map.of( // Right click action code
                "ban-player", banPlayer,
                "kick-player", kickPlayer
        )
);
ui.show(player); // Shows the UI to a player

// ... (more code, possibly for a menu-opened message)
```

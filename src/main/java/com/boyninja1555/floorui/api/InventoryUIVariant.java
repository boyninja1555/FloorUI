package com.boyninja1555.floorui.api;

public enum InventoryUIVariant {
    HOTBAR(1),
    CHEST(3),
    LARGE_CHEST(6);

    private final int size;

    InventoryUIVariant(int rows) {
        this.size = rows * 9;
    }

    public int size() {
        return size;
    }
}

package com.connexal.desktopinminecraft;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DesktopMapRenderer extends MapRenderer {
    private final MapManager mapManager;
    private final Rectangle dimensions;
    private boolean done;

    public DesktopMapRenderer(MapManager mapManager, Rectangle dimensions) {
        this.mapManager = mapManager;
        this.dimensions = dimensions;
        this.done = false;
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        if (this.done) {
            return;
        }

        this.done = true;
        this.mapManager.mapCanvases.put(this.dimensions, canvas);
    }
}

package com.connexal.desktopinminecraft;

import org.bukkit.event.Listener;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class MapManager implements Listener {
    public final int mapWidth = 15;
    public final int mapHeight = 9;

    public final Map<MapPosition, MapView> maps = new HashMap<>();
    public final Map<Rectangle, MapCanvas> mapCanvases = new HashMap<>();

    public MapManager(Plugin plugin) {
        int mapId = 0;
        for (int x = 0; x < this.mapWidth; x++) {
            for (int y = 0; y < this.mapHeight; y++) {
                MapView map = plugin.getServer().getMap(mapId);
                if (map == null) {
                    map = plugin.getServer().createMap(plugin.getServer().getWorlds().get(0));
                }

                map.setTrackingPosition(false);
                map.getRenderers().clear();
                map.addRenderer(new DesktopMapRenderer(this, new Rectangle(x * 128, y * 128, 128, 128)));

                this.maps.put(new MapPosition(x, y), map);

                mapId++;
            }
        }

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::updateMaps, 0L, 2L);
    }

    private void updateMaps() {
        BufferedImage screenImg = (BufferedImage) ScreenCapture.capture(new Rectangle(0, 0, this.mapWidth * 128, this.mapHeight * 128));

        for (Map.Entry<Rectangle, MapCanvas> entry : mapCanvases.entrySet()) {
            Rectangle dimensions = entry.getKey();
            MapCanvas canvas = entry.getValue();

            canvas.drawImage(0, 0, screenImg.getSubimage(dimensions.x, dimensions.y, dimensions.width, dimensions.height));
        }
    }
}

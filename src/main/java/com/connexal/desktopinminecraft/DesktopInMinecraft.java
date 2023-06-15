package com.connexal.desktopinminecraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class DesktopInMinecraft extends JavaPlugin {
    private static Plugin instance;
    private MapManager mapManager;

    @Override
    public void onEnable() {
        instance = this;

        this.getLogger().info("DesktopInMinecraft is initialing...");

        this.mapManager = new MapManager(this);
        ScreenCapture.init();

        this.getServer().getPluginManager().registerEvents(this.mapManager, this);

        this.getLogger().info("DesktopInMinecraft is done!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("DesktopInMinecraft has been disabled.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Location basePos = player.getLocation().getBlock().getLocation();
            basePos.add(0, this.mapManager.mapHeight, 0);

            for (Map.Entry<MapPosition, MapView> entry : this.mapManager.maps.entrySet()) {
                MapPosition mapPosition = entry.getKey();
                MapView mapView = entry.getValue();

                ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
                MapMeta meta = (MapMeta) mapItem.getItemMeta();
                meta.setMapView(mapView);
                mapItem.setItemMeta(meta);

                Location pos = basePos.clone().add(mapPosition.x(), mapPosition.y() * -1, 0);
                Block behindBlock = pos.clone().subtract(0, 0, 1).getBlock();
                behindBlock.setType(Material.STONE);

                ItemFrame frame = (ItemFrame) player.getWorld().spawnEntity(pos, EntityType.GLOW_ITEM_FRAME);
                frame.setItem(mapItem);
            }
        }

        return true;
    }

    public static Plugin getPlugin() {
        return instance;
    }
}

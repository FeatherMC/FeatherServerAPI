package net.digitalingot.featherserverapi.platforms.bukkit;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

class BukkitFeatherUser extends FeatherUser {
    @NotNull
    private final Player player;
    @NotNull
    private final Plugin plugin;

    public BukkitFeatherUser(@NotNull Plugin plugin, @NotNull Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void sendRawPacket(byte[] packet) {
        player.sendPluginMessage(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME, packet);
    }

    @Override
    protected void onClientHello() {
        Bukkit.getPluginManager().callEvent(new BukkitFeatherLoginEvent(this));
    }

    @Override
    protected UUID getUUID() {
        return player.getUniqueId();
    }
}

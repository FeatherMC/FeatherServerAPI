package net.digitalingot.featherserverapi.platforms.bukkit;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

class BukkitEventListener implements Listener {

    @NotNull
    private final Plugin plugin;

    BukkitEventListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRegisterChannel(@NotNull PlayerRegisterChannelEvent event) {
        if (event.getChannel().equals(FeatherServerAPI.PLUGIN_MESSAGE_NAME)) {
            Player player = event.getPlayer();
            FeatherUser user = new BukkitFeatherUser(plugin, player);
            user.register();
        }
    }

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        FeatherUser featherUser = FeatherServerAPI.getInstance().getFeatherUser(event.getPlayer().getUniqueId());
        if (featherUser != null) {
            featherUser.unregister();
        }
    }

}

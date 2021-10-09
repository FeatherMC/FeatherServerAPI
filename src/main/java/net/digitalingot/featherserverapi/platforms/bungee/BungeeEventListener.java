package net.digitalingot.featherserverapi.platforms.bungee;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.NotNull;

class BungeeEventListener implements Listener {

    @NotNull
    private final Plugin plugin;

    public BungeeEventListener(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(@NotNull PlayerDisconnectEvent event) {
        FeatherUser featherUser = FeatherServerAPI.getInstance().getFeatherUser(event.getPlayer().getUniqueId());
        if (featherUser != null) {
            featherUser.unregister();
        }
    }

    @EventHandler
    public void onPluginMessage(@NotNull PluginMessageEvent event) {
        if (event.getSender() instanceof ProxiedPlayer && event.getTag().equals(FeatherServerAPI.PLUGIN_MESSAGE_NAME)) {
            ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
            FeatherUser featherUser = FeatherServerAPI.getInstance().getFeatherUser(sender.getUniqueId());
            if (featherUser == null) {
                featherUser = new BungeeFeatherUser(sender, plugin);
                FeatherServerAPI.getInstance().getFeatherUsers().put(sender.getUniqueId(), featherUser);
            }

            byte[] data = event.getData();
            featherUser.onReceivePacket(data);
        }
    }

}

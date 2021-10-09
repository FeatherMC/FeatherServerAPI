package net.digitalingot.featherserverapi.platforms.bungee;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

class BungeeFeatherUser extends FeatherUser {

    @NotNull
    private final ProxiedPlayer player;
    @NotNull
    private final Plugin plugin;

    public BungeeFeatherUser(@NotNull ProxiedPlayer player, @NotNull Plugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    protected void sendRawPacket(byte[] packet) {
        player.sendData(FeatherServerAPI.PLUGIN_MESSAGE_NAME, packet);
    }

    @Override
    protected void onClientHello() {
        plugin.getProxy().getPluginManager().callEvent(new BungeeFeatherLoginEvent(this));
    }

    @Override
    protected UUID getUUID() {
        return player.getUniqueId();
    }
}

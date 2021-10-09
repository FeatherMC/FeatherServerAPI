package net.digitalingot.featherserverapi.platforms.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.jetbrains.annotations.NotNull;

public class VelocityEventListener {

    @NotNull
    private final ProxyServer server;

    public VelocityEventListener(@NotNull ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onQuit(@NotNull DisconnectEvent event) {
        FeatherUser featherUser = FeatherServerAPI.getInstance().getFeatherUser(event.getPlayer().getUniqueId());
        if (featherUser != null) {
            featherUser.unregister();
        }
    }

    @Subscribe
    public void onPluginMessage(@NotNull PluginMessageEvent event) {
        if (event.getSource() instanceof Player && event.getIdentifier().getId().equals(FeatherServerAPI.PLUGIN_MESSAGE_NAME)) {
            Player sender = (Player) event.getSource();
            FeatherUser featherUser = FeatherServerAPI.getInstance().getFeatherUser(sender.getUniqueId());
            if (featherUser == null) {
                featherUser = new VelocityFeatherUser(sender, server);
                FeatherServerAPI.getInstance().getFeatherUsers().put(sender.getUniqueId(), featherUser);
            }

            byte[] data = event.getData();
            featherUser.onReceivePacket(data);
        }
    }

}

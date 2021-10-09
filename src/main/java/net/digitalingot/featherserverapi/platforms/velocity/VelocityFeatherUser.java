package net.digitalingot.featherserverapi.platforms.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class VelocityFeatherUser extends FeatherUser {

    @NotNull
    private final Player player;
    @NotNull
    private final ProxyServer server;

    public VelocityFeatherUser(@NotNull Player player, @NotNull ProxyServer server) {
        this.player = player;
        this.server = server;
    }

    @Override
    protected void sendRawPacket(byte[] packet) {
        player.sendPluginMessage(FeatherAPIVelocityHook.CHANNEL_IDENTIFIER, packet);
    }

    @Override
    protected void onClientHello() {
        server.getEventManager().fireAndForget(new VelocityFeatherLoginEvent(this));
    }

    @Override
    protected UUID getUUID() {
        return player.getUniqueId();
    }
}

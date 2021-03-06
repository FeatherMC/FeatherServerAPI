package net.digitalingot.featherserverapi.platforms.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FeatherAPIVelocityHook {

    static final ChannelIdentifier CHANNEL_IDENTIFIER = new VelocityChannelIdentifier();

    /**
     * Ensures only one plugin registers to the hook at a time
     */
    @Nullable
    private static Object plugin;

    /**
     * Should be called in the {@link com.velocitypowered.api.event.proxy.ProxyInitializeEvent}
     *
     * @param plugin the plugin instance
     * @param server the server instance
     */
    public static void onEnable(@NotNull Object plugin, @NotNull ProxyServer server) {
        if (FeatherAPIVelocityHook.plugin != null) return;
        FeatherAPIVelocityHook.plugin = plugin;

        server.getChannelRegistrar().register(CHANNEL_IDENTIFIER);
        server.getEventManager().register(server, new VelocityEventListener(server));
    }

}

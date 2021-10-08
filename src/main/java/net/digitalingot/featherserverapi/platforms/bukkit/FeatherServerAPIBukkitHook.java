package net.digitalingot.featherserverapi.platforms.bukkit;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FeatherServerAPIBukkitHook {

    /**
     * Ensures only one plugin registers to the hook at a time
     */
    @Nullable
    private static Plugin plugin;

    public static void onEnable(@NotNull Plugin plugin) {
        if (FeatherServerAPIBukkitHook.plugin != null) return;

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME);
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME, (channel, player, packet) -> {
            if (!channel.equals(FeatherServerAPI.PLUGIN_MESSAGE_NAME)) return;

            FeatherUser user = FeatherServerAPI.getInstance().getFeatherUser(player.getUniqueId());
            if (user == null)
                return; // shouldn't happen in normal circumstances because the user should've been already registered

            user.onReceivePacket(packet);
        });

        Bukkit.getPluginManager().registerEvents(new BukkitEventListener(plugin), plugin);
    }

    public static void onDisable(@NotNull Plugin plugin) {
        if (plugin != FeatherServerAPIBukkitHook.plugin) return;

        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME);
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME);
    }
}

package net.digitalingot.featherserverapi.platforms.bukkit;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FeatherAPIBukkitHook {

    /**
     * Ensures only one plugin registers to the hook at a time
     */
    @Nullable
    private static Plugin plugin;

    /**
     * Should be called in the {@link JavaPlugin#onEnable()} of the bukkit plugin.
     *
     * @param plugin the plugin instance
     */
    public static void onEnable(@NotNull Plugin plugin) {
        if (FeatherAPIBukkitHook.plugin != null) return;
        FeatherAPIBukkitHook.plugin = plugin;

        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME);
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME, (channel, player, packet) -> {
            if (!channel.equals(FeatherServerAPI.PLUGIN_MESSAGE_NAME)) return;

            FeatherUser user = FeatherServerAPI.getInstance().getFeatherUser(player.getUniqueId());

            // shouldn't happen in normal circumstances because the user should've been already registered
            if (user == null) return;

            user.onReceivePacket(packet);
        });

        Bukkit.getPluginManager().registerEvents(new BukkitEventListener(plugin), plugin);
    }

    /**
     * Should be called in the {@link JavaPlugin#onDisable()} of the bukkit plugin.
     *
     * @param plugin the plugin instance
     */
    public static void onDisable(@NotNull Plugin plugin) {
        if (plugin != FeatherAPIBukkitHook.plugin) return;

        Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME);
        Bukkit.getMessenger().unregisterIncomingPluginChannel(plugin, FeatherServerAPI.PLUGIN_MESSAGE_NAME);
    }
}

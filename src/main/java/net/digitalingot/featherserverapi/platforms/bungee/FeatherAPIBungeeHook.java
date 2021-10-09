package net.digitalingot.featherserverapi.platforms.bungee;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FeatherAPIBungeeHook {

    /**
     * Ensures only one plugin registers to the hook at a time
     */
    @Nullable
    private static Plugin plugin;

    /**
     * Should be called in the {@link Plugin#onEnable()} of the bungee plugin.
     *
     * @param plugin the plugin instance
     */
    public static void onEnable(@NotNull Plugin plugin) {
        if (FeatherAPIBungeeHook.plugin != null) return;
        FeatherAPIBungeeHook.plugin = plugin;

        plugin.getProxy().registerChannel(FeatherServerAPI.PLUGIN_MESSAGE_NAME);
        plugin.getProxy().getPluginManager().registerListener(plugin, new BungeeEventListener(plugin));
    }

    /**
     * Should be called in the {@link Plugin#onDisable()} ())} of the bukkit plugin.
     *
     * @param plugin the plugin instance
     */
    public static void onDisable(@NotNull Plugin plugin) {
        if (plugin != FeatherAPIBungeeHook.plugin) return;

        plugin.getProxy().unregisterChannel(FeatherServerAPI.PLUGIN_MESSAGE_NAME);
    }

}

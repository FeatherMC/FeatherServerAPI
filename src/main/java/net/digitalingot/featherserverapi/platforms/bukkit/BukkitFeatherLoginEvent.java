package net.digitalingot.featherserverapi.platforms.bukkit;

import net.digitalingot.featherserverapi.abstractions.FeatherLoginEvent;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BukkitFeatherLoginEvent extends Event implements FeatherLoginEvent {
    private static final HandlerList handlers = new HandlerList();

    @NotNull
    private final FeatherUser featherUser;

    public BukkitFeatherLoginEvent(@NotNull FeatherUser featherUser) {
        this.featherUser = featherUser;
    }

    @NotNull
    @Override
    public FeatherUser getUser() {
        return featherUser;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

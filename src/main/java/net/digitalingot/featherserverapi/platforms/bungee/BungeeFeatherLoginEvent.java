package net.digitalingot.featherserverapi.platforms.bungee;

import net.digitalingot.featherserverapi.abstractions.FeatherLoginEvent;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;

public class BungeeFeatherLoginEvent extends Event implements FeatherLoginEvent {

    @NotNull
    private final FeatherUser featherUser;

    public BungeeFeatherLoginEvent(@NotNull FeatherUser featherUser) {
        this.featherUser = featherUser;
    }

    @Override
    @NotNull
    public FeatherUser getUser() {
        return featherUser;
    }
}

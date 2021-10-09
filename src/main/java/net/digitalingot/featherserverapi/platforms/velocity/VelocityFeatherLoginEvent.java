package net.digitalingot.featherserverapi.platforms.velocity;

import net.digitalingot.featherserverapi.abstractions.FeatherLoginEvent;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.jetbrains.annotations.NotNull;

public class VelocityFeatherLoginEvent implements FeatherLoginEvent {

    @NotNull
    private final FeatherUser featherUser;

    public VelocityFeatherLoginEvent(@NotNull FeatherUser featherUser) {
        this.featherUser = featherUser;
    }

    @Override
    @NotNull
    public FeatherUser getUser() {
        return featherUser;
    }
}

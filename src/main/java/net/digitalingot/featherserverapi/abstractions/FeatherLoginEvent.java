package net.digitalingot.featherserverapi.abstractions;

import org.jetbrains.annotations.NotNull;

/**
 * Called when a feather user sends {@link net.digitalingot.featherserverapi.proto.ClientHello}
 **/
public interface FeatherLoginEvent {

    @NotNull
    FeatherUser getUser();

}

package net.digitalingot.featherserverapi.platforms.velocity;

import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import net.digitalingot.featherserverapi.FeatherServerAPI;

class VelocityChannelIdentifier implements ChannelIdentifier {
    @Override
    public String getId() {
        return FeatherServerAPI.PLUGIN_MESSAGE_NAME;
    }
}

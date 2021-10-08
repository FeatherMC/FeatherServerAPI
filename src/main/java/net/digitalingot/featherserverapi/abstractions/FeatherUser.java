package net.digitalingot.featherserverapi.abstractions;

import com.google.gson.Gson;
import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.proto.*;
import net.digitalingot.featherserverapi.proto.models.PacketType;
import net.digitalingot.featherserverapi.proto.models.Waypoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public abstract class FeatherUser {

    @Nullable
    private List<String> mods;

    /**
     * Returns the mods the player uses. Returns {@code null} when executed before the client has sent {@link net.digitalingot.featherserverapi.proto.ClientHello}.
     *
     * @return a list of mods the player uses
     * @see <a href="https://github.com/FeatherMC/feather-server-api/pages/mods">List of all mods</a> // todo
     */
    @Nullable
    public List<String> getMods() {
        return mods;
    }

    /**
     * Disables the provided mods.
     *
     * @param mods the slugs of the mods to be disabled
     * @see <a href="https://github.com/FeatherMC/feather-server-api/pages/mods">List of all mods</a> // todo
     */
    public void disableMods(@NotNull List<String> mods) {
        DisableMods disableMods = new DisableMods(mods);
        sendPacket(PacketType.Clientbound.DISABLE_MODS, disableMods);
    }

    /**
     * Sets waypoints for the player.
     *
     * @param waypoints the waypoints the player should see
     */
    public void setWaypoints(@NotNull List<Waypoint> waypoints) {
        SetWaypoints setWaypoints = new SetWaypoints(waypoints);
        sendPacket(PacketType.Clientbound.SET_WAYPOINTS, setWaypoints);
    }

    /**
     * Sends the provided packet to the client.<br>
     * The object is translated to JSON using Gson.
     *
     * @param type    the packet type
     * @param payload the packet to be sent to the client
     * @see FeatherUser#sendRawPacket(byte[])
     */
    private void sendPacket(@NotNull PacketType.Clientbound type, @NotNull Object payload) {
        Gson gson = FeatherServerAPI.getInstance().getGson();

        ClientboundWrapper wrapper = new ClientboundWrapper(type, gson.toJsonTree(payload));
        String serialized = gson.toJson(wrapper);
        sendRawPacket(serialized.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Sends the provided packet to the client.
     *
     * @param packet the packet to be sent to the client
     * @see FeatherUser#sendPacket(PacketType.Clientbound, Object)
     */
    protected abstract void sendRawPacket(byte[] packet);

    /**
     * Executed when the player connects.
     */
    public void register() {
        FeatherServerAPI.getInstance().getFeatherUsers().put(getUUID(), this);
    }

    /**
     * Executed when the player disconnects.
     */
    public void unregister() {
        FeatherServerAPI.getInstance().getFeatherUsers().remove(getUUID());
    }

    /**
     * Executed when a packet is sent to the server from the client.
     *
     * @param packet the packet sent to the server
     */
    public void onReceivePacket(byte[] packet) {
        try {
            String serializedJson = new String(packet, StandardCharsets.UTF_8);

            Gson gson = FeatherServerAPI.getInstance().getGson();
            ServerboundWrapper wrapper = gson.fromJson(serializedJson, ServerboundWrapper.class);

            PacketType.Serverbound packetType = wrapper.getPacketType();
            switch (packetType) {
                case CLIENT_HELLO:
                    ClientHello clientHello = gson.fromJson(wrapper.getPayload(), ClientHello.class);
                    mods = clientHello.getFeatherMods();
                    this.onClientHello();
                    break;
            }
        } catch (Exception e) {
            System.err.println("Exception during feather packet receive from " + getUUID());
            e.printStackTrace();
        }
    }

    protected void onClientHello() {
    }

    /**
     * Gets the uuid of the player.
     *
     * @return the uuid
     */
    protected abstract UUID getUUID();

}

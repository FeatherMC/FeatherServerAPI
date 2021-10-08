package net.digitalingot.featherserverapi.abstractions;

import net.digitalingot.featherserverapi.FeatherServerAPI;
import net.digitalingot.featherserverapi.proto.ClientHello;
import net.digitalingot.featherserverapi.proto.DisableMods;
import net.digitalingot.featherserverapi.proto.SetWaypoints;
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
        sendPacket(disableMods);
    }

    /**
     * Sets waypoints for the player.
     *
     * @param waypoints the waypoints the player should see
     */
    public void setWaypoints(@NotNull List<Waypoint> waypoints) {
        SetWaypoints setWaypoints = new SetWaypoints(waypoints);
        sendPacket(setWaypoints);
    }

    /**
     * Sends the provided packet to the client.<br>
     * The object is translated to JSON using Gson.
     *
     * @param packet the packet to be sent to the client
     * @see FeatherUser#sendRawPacket(byte[])
     */
    private void sendPacket(@NotNull Object packet) {
        String serialized = FeatherServerAPI.getInstance().getGson().toJson(packet);
        sendRawPacket(serialized.getBytes(StandardCharsets.UTF_8));
    }

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
        String serializedJson = new String(packet, StandardCharsets.UTF_8);

        // as we currently only have one C->S packet, we don't need more sophisticated handling yet
        ClientHello clientHello = FeatherServerAPI.getInstance().getGson().fromJson(serializedJson, ClientHello.class);

        mods = clientHello.getFeatherMods();

        this.onClientHello();
    }

    protected void onClientHello() {
    }

    /**
     * Sends the provided packet to the client.
     *
     * @param packet the packet to be sent to the client
     * @see FeatherUser#sendPacket(Object)
     */
    protected abstract void sendRawPacket(byte[] packet);

    /**
     * Gets the uuid of the player.
     *
     * @return the uuid
     */
    protected abstract UUID getUUID();

}

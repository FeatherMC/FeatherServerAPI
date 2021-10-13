package net.digitalingot.featherserverapi;

import com.google.gson.Gson;
import net.digitalingot.featherserverapi.abstractions.FeatherUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FeatherServerAPI {

    @NotNull
    public static final String PLUGIN_MESSAGE_NAME = "feather:client";
    @NotNull
    private static final FeatherServerAPI INSTANCE = new FeatherServerAPI();

    @NotNull
    public static FeatherServerAPI getInstance() {
        return INSTANCE;
    }

    @NotNull
    private final Gson gson = new Gson();
    @NotNull
    private final Map<UUID, FeatherUser> featherUsers = new HashMap<>();

    /**
     * Returns the {@link FeatherUser} instance from the {@code uuid} provided.
     * <p>
     * This only returns an instance after the client has registered the plugin channel.
     *
     * @return the feather user instance
     */
    @Nullable
    public FeatherUser getFeatherUser(@NotNull UUID uuid) {
        return featherUsers.get(uuid);
    }

    /**
     * Returns a {@link Map} of all feather user instances.
     *
     * @return a {@link Map} of all feather user instances
     */
    @NotNull
    public Map<UUID, FeatherUser> getFeatherUsers() {
        return featherUsers;
    }

    @NotNull
    public Gson getGson() {
        return gson;
    }
}

package dev.masa.masuitecore.common.config.server;

import lombok.Getter;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

@ConfigSerializable
public class CoreServerMessageConfig {

    @Getter
    @Setting("no-permission")
    private final String databaseName = "&cNo permission";

    @Getter
    @Setting("in-cooldown")
    private final String inCooldown = "&cYou can use that command after %time% seconds.";

    @Getter
    @Setting("teleportation-cancelled")
    private final String teleportationCancelled = "&cTeleportation cancelled!";

    @Getter
    @Setting("teleportation-started")
    private final String teleportationStarted = "&7You will be teleported in &a%time%&7 seconds! &cDont move!";

    private static final ObjectMapper<CoreServerMessageConfig> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.factory().get(CoreServerMessageConfig.class); // We hold on to the instance of our ObjectMapper
        } catch (final SerializationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static CoreServerMessageConfig loadFrom(final ConfigurationNode node) throws SerializationException {
        return MAPPER.load(node);
    }
}

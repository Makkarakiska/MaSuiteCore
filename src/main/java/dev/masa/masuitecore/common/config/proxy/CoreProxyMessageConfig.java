package dev.masa.masuitecore.common.config.proxy;

import lombok.Getter;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

@ConfigSerializable
public class CoreProxyMessageConfig {

    @Getter
    @Setting("player-not-online")
    private final String playerNotOnline = "&cPlayer is not online.";

    private static final ObjectMapper<CoreProxyMessageConfig> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.factory().get(CoreProxyMessageConfig.class);
        } catch (final SerializationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static CoreProxyMessageConfig loadFrom(final ConfigurationNode node) throws SerializationException {
        return MAPPER.load(node);
    }

}

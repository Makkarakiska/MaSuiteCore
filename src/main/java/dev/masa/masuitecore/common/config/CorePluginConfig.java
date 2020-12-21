package dev.masa.masuitecore.common.config;

import lombok.Getter;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import org.spongepowered.configurate.serialize.SerializationException;

@ConfigSerializable
public class CorePluginConfig {

    @Getter
    @Comment("Database settings")
    @Setting("database")
    protected final DatabaseConfig database = new DatabaseConfig();

    @Getter
    @Setting("teleportation-delay")
    @Comment("# In ms, other plugins like Homes and Warps are using this.")
    private final Integer teleportationDelay = 750;

    @ConfigSerializable
    static public class DatabaseConfig {
        @Getter
        @Setting("name")
        protected final String databaseName = "minecraft?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";

        @Getter
        @Setting("address")
        protected final String databaseAddress = "localhost";

        @Getter
        @Setting("port")
        private final Integer databasePort = 3306;

        @Getter
        @Setting("username")
        protected final String databaseUsername = "minecraft";

        @Getter
        @Setting("password")
        protected final String databasePassword = "kissaKoira123";
    }

    private static final ObjectMapper<CorePluginConfig> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.factory().get(CorePluginConfig.class);
        } catch (final SerializationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static CorePluginConfig loadFrom(final ConfigurationNode node) throws SerializationException {
        return MAPPER.load(node);
    }
}



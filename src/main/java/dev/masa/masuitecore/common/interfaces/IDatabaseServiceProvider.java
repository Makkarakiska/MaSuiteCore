package dev.masa.masuitecore.common.interfaces;

import dev.masa.masuitecore.common.services.DatabaseService;

public interface IDatabaseServiceProvider {

    DatabaseService getDatabaseService();
}

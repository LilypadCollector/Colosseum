package dev.dannychoi.colosseum;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "colosseum",
        name = "Colosseum",
        description = "A Minecraft PvP plugin where players can choose classes, skills, and more.",
        url = "https://dannychoi.dev"
)
public class Colosseum {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("[Colosseum] Beginning setup...");
        
        logger.info("[Colosseum] Setup finished.");
    }
}

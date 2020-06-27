package dev.dannychoi.colosseum;

import com.google.inject.Inject;
import dev.dannychoi.colosseum.commands.CommandSetCharge;
import dev.dannychoi.colosseum.commands.CommandSpecies;
import dev.dannychoi.colosseum.listeners.PlayerLeftClickListener;
import dev.dannychoi.colosseum.listeners.PlayerRightClickListener;
import dev.dannychoi.colosseum.listeners.TakeDamageListener;
import dev.dannychoi.colosseum.species.Species;
import dev.dannychoi.colosseum.species.SpeciesFinder;
import dev.dannychoi.colosseum.species.SpeciesType;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.util.HashMap;

@Plugin(
        id = "colosseum",
        name = "Colosseum",
        description = "A Minecraft PvP plugin where players can choose classes, skills, and more.",
        url = "https://dannychoi.dev",
        version = "0.1.0"
)
public class Colosseum {

    public static final int ATTACKTYPE_MELEE = 0;
    public static final int ATTACKTYPE_PROJECTILE = 1;

    @Inject
    private Logger logger;

    private static GameManager gameManager;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("[Colosseum] Beginning setup...");
        gameManager = new GameManager();
        registerCommands();
        registerListeners();
        logger.info("[Colosseum] Setup finished.");
    }

    private static HashMap<Player, PlayerProfile> activePlayers; // Contains list of all players who have selected a class.

    private void registerCommands() {
        CommandSpec species = CommandSpec.builder()
                .description(Text.of("Select a species."))
                .arguments(GenericArguments.string(Text.of("species")))
                .executor(new CommandSpecies())
                .build();

        CommandSpec setCharge = CommandSpec.builder()
                .description(Text.of("Set your charge."))
                .arguments(GenericArguments.integer(Text.of("charge")))
                .executor(new CommandSetCharge())
                .build();

        CommandManager cmdManager = Sponge.getCommandManager();
        cmdManager.register(this, species, "species", "sp");
        cmdManager.register(this, setCharge, "setcharge", "sc");
    }

    private void registerListeners() {
        EventManager em = Sponge.getEventManager();
        em.registerListeners(this, new PlayerLeftClickListener());
        em.registerListeners(this, new PlayerRightClickListener());
        em.registerListeners(this, new TakeDamageListener());
    }

    public static GameManager getGameManager() {
        return gameManager;
    }
}

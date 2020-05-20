package dev.dannychoi.colosseum;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.All;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.HashMap;

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

        activePlayers = new HashMap<Player, PlayerProfile>();
        createAndRegisterCommands();

        logger.info("[Colosseum] Setup finished.");
    }

    private static HashMap<Player, PlayerProfile> activePlayers; // Contains list of all players who have selected a class.

    @Listener
    public void onPlayerDamage(DamageEntityEvent e, @All Player[] playersInvolved, @First EntityDamageSource src) {
        PlayerProfile damaged = activePlayers.get(playersInvolved[0]); // Player who got damaged
        PlayerProfile damageAfflicter = activePlayers.get(playersInvolved[1]); // Player who afflicted damage

        if (damaged != null) {
            // Calling the onTakeDamage listener.
            damaged.getSpeciesObject().onTakeDamage(damaged);
            logger.info("[Colosseum] " + damaged.getPlayer().getName() + " has taken damage.");
        }

        if (damageAfflicter != null) {
            Species playerSpeciesInstance = damageAfflicter.getSpeciesObject(); // This player's set species.
            playerSpeciesInstance.onHitPlayer(damageAfflicter, damaged); // Calling onHitPlayer listener
            damaged.addCharge(playerSpeciesInstance.getCPH()); // Increasing player charge accordingly
            logger.info("[Colosseum] " + damaged.getPlayer().getName() + " has taken damage from " + damageAfflicter.getPlayer().getName());
        }
    }

    // Below listener is for calling ability (by right clicking sword)
    @Listener
    public void onPlayerRightClick(InteractEntityEvent.Secondary e, @First Player p) {
        ItemType heldItemType = p.getItemInHand(HandTypes.MAIN_HAND).get().getType();
        if (heldItemType.equals(ItemTypes.IRON_SWORD) ||
                heldItemType.equals(ItemTypes.DIAMOND_SWORD) ||
                heldItemType.equals(ItemTypes.STONE_SWORD) ||
                heldItemType.equals(ItemTypes.WOODEN_SWORD) ||
                heldItemType.equals(ItemTypes.GOLDEN_SWORD))
        {
            if (!activePlayers.containsKey(p)) {
                p.sendMessage(Text.builder("No species selected.").color(TextColors.RED).build());
                return;
            }

            getPlayerProfileOf(p).useSkill();
        }
    }

    // Below listener is for calling ability (via left click bow)
    @Listener
    public void onPlayerLeftClick(InteractEntityEvent.Primary e, @First Player p) {
        if (p.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.BOW)) {
            if (!activePlayers.containsKey(p)) {
                p.sendMessage(Text.builder("No species selected.").color(TextColors.RED).build());
                return;
            }

            getPlayerProfileOf(p).useSkill();
        }
    }

    private void createAndRegisterCommands() {
        CommandSpec species = CommandSpec.builder()
                .description(Text.of("Select a species."))
                .arguments(GenericArguments.string(Text.of("species")))
                .executor(new CommandSpecies())
                .build();

        CommandManager cmdManager = Sponge.getCommandManager();
        cmdManager.register(this, species, "species", "sp");
    }

    public static void setPlayerSpecies(Player p, SpeciesType st) {
        PlayerProfile pp = new PlayerProfile(p, st);
        activePlayers.put(p, pp);
        Species pSpecies = SpeciesFinder.getByType(st);
        pSpecies.equip(p);
    }

    // May be null!
    public static PlayerProfile getPlayerProfileOf(Player p) {
        return activePlayers.get(p);
    }
}

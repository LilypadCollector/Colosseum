package dev.dannychoi.colosseum;

import dev.dannychoi.colosseum.species.Species;
import dev.dannychoi.colosseum.species.SpeciesFinder;
import dev.dannychoi.colosseum.species.SpeciesType;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Optional;

public class GameManager {

    // 1 - 499: Status codes
    public static final int CODE_EXPLOSIVE_ARROW_FLYING = 1;

    private StatusManager statusManager;
    private CooldownManager cooldownManager;
    private HashMap<Player, PlayerProfile> activePlayers;

    public GameManager(Colosseum plugin) {
        activePlayers = new HashMap<Player, PlayerProfile>();
        statusManager = new StatusManager();
        cooldownManager = new CooldownManager(plugin);
    }

    public void setPlayerSpecies(Player p, SpeciesType st) {
        PlayerProfile pp = new PlayerProfile(p, st);
        activePlayers.put(p, pp);
        Species pSpecies = SpeciesFinder.getByType(st);
        pSpecies.equip(p);
        p.offer(Keys.MAX_HEALTH, 40D); // Set to 20 hearts.
        p.offer(Keys.HEALTH, p.maxHealth().get()); // Heal player
    }

    public Optional<PlayerProfile> getPlayerProfileOf(Player p) {
        if (isPlayerActive(p))
            return Optional.of(activePlayers.get(p));
        else
            return Optional.empty();
    }

    public boolean isPlayerActive(Player p) {
        return activePlayers.containsKey(p);
    }

    public StatusManager getStatusManager() {
        return statusManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

}

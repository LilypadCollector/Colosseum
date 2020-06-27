package dev.dannychoi.colosseum;

import dev.dannychoi.colosseum.species.Species;
import dev.dannychoi.colosseum.species.SpeciesFinder;
import dev.dannychoi.colosseum.species.SpeciesType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;

public class GameManager {

    private HashMap<Player, PlayerProfile> activePlayers;

    public GameManager() {
        activePlayers = new HashMap<Player, PlayerProfile>();
    }

    public void setPlayerSpecies(Player p, SpeciesType st) {
        PlayerProfile pp = new PlayerProfile(p, st);
        activePlayers.put(p, pp);
        Species pSpecies = SpeciesFinder.getByType(st);
        pSpecies.equip(p);
    }

    // May be null!
    public PlayerProfile getPlayerProfileOf(Player p) {
        return activePlayers.get(p);
    }

    public boolean isPlayerActive(Player p) {
        return activePlayers.containsKey(p);
    }

}

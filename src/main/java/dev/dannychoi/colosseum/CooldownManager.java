package dev.dannychoi.colosseum;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.api.scheduler.Task;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CooldownManager {
    // 1000 - 1499: Cooldown type codes
    public static final int COOLDOWN_CODE_ABILITY = 1000;
    public static final int COOLDOWN_ARCHER_PASSIVE = 1001;

    // Below HashMap stores cooldowns.
    // Example: A player who's an archer will have
    private HashMap<Pair<PlayerProfile, Integer>, Integer> cooldowns;

    public CooldownManager(Colosseum plugin) {
        cooldowns = new HashMap<Pair<PlayerProfile, Integer>, Integer>();
        Task.builder()
                .interval(1, TimeUnit.SECONDS)
                .execute(this::tickCooldowns)
                .submit(plugin);
    }

    public void setCooldown(PlayerProfile pp, int cooldownCode, int seconds) {
        Pair<PlayerProfile, Integer> ppAndCode = Pair.of(pp, cooldownCode);
        cooldowns.put(ppAndCode, seconds);
    }

    public int getCooldown(PlayerProfile pp, int cooldownCode) {
        Pair ppAndCode = Pair.of(pp, cooldownCode);
        return cooldowns.getOrDefault(ppAndCode, 0); // If there's a cooldown, return it. Otherwise, return 0.
    }

    public boolean hasCooldown(PlayerProfile pp, int cooldownCode) {
        return cooldowns.containsKey(Pair.of(pp, cooldownCode));
    }

    public void tickCooldowns() {
        cooldowns.forEach((ppAndCode, seconds) -> {
            if (seconds <= 0) {
                cooldowns.remove(ppAndCode);
                return; // This merely exits out of the forEach loop.
            }

            cooldowns.put(ppAndCode, seconds-1); // Ticking down a second
        });
    }

    public void clearCooldowns() {
        cooldowns.clear();
    }
}

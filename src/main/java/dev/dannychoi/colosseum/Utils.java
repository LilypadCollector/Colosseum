package dev.dannychoi.colosseum;

import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.World;

public class Utils {
    public static void addEnchant(ItemStack it, EnchantmentType et, int level) {
        EnchantmentData eData = it.getOrCreate(EnchantmentData.class).get();
        eData.set(eData.enchantments().add(Enchantment.of(et, level)));

        it.offer(eData);
    }

    public static void addPotionEffect(Living entity, PotionEffectType peType, int amplifier, int durationInSeconds) {
        entity.offer(
                entity.getOrCreate(PotionEffectData.class).get()
                        .effects()
                        .add(PotionEffect.of(peType, amplifier, durationInSeconds * 20))
        );
    }

    // Spawns particles above a player.
    // count: The number of particles to spawn, equally distributed in a circle pattern.
    public static void spawnParticlesAbove(Player target, ParticleType partType, int count) {
        World world = target.getWorld();
        ParticleEffect partEff = ParticleEffect.builder()
                .type(partType)
                .quantity(5)
                .build();

        for (Player p2 : world.getPlayers()) {
            int oneSlice = 360/count; // Like one slice of a pi cut equally into "counts"
            for (int i = 1; i <= count; i++) {
                // Below: the direction RELATIVE TO PLAYER to spawn the particle.
//                Vector3d dir = Vector3d.createDirectionDeg(i, 45);
//                p2.spawnParticles(partEff, target.getPosition().add());
            }
        }
    }

    // Simply returns an hp potion
    public static ItemStack hPot(int amplifier) {
        ItemStack hPot = ItemStack.of(ItemTypes.POTION);
        hPot.offer(hPot.getOrCreate(PotionEffectData.class).get().effects().add(PotionEffect.of(PotionEffectTypes.INSTANT_HEALTH, amplifier, 1)));
        return hPot;
    }

    // Simply returns an speed potion
    public static ItemStack sPot(int amplifier) {
        ItemStack hPot = ItemStack.of(ItemTypes.POTION);
        hPot.offer(hPot.getOrCreate(PotionEffectData.class).get().effects().add(PotionEffect.of(PotionEffectTypes.SPEED, amplifier, 1)));
        return hPot;
    }
}

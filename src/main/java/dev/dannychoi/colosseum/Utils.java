package dev.dannychoi.colosseum;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

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
                        .add(PotionEffect.of(peType, amplifier-1, durationInSeconds * 20))
        );
    }

    // Spawns particles above an entity.
    // count: The number of particles to spawn, equally distributed in a circle pattern.
    public static void spawnParticlesAbove(Entity target, ParticleType partType, int count) {
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

    public static void spawnParticlesAt(Entity target, ParticleType partType) {
        ParticleEffect partEff = ParticleEffect.builder()
                .type(partType)
                .quantity(5)
                .build();

        for (Player p : target.getWorld().getPlayers())
            p.spawnParticles(partEff, target.getLocation().getPosition());

    }

    public static Optional<Player> getPlayerLookingAt(Player user, int distance, boolean seeThroughBlocks) {
        BlockRay<World> blockRay = BlockRay.from(user)
                .distanceLimit(distance)
                .build();

        // First for-loop: iterates through all blocks in BlockRay.
        // Second for-loop: checks all players to see if they're inside the current iterated block.
        for (BlockRayHit<World> cur = blockRay.next();
                blockRay.hasNext();
                cur = blockRay.next()) {

            // We check current iteration block for players (and if one exists, we return it).
            Vector3i rayBlockPos = cur.getBlockPosition();
            for (Player target : user.getWorld().getPlayers()) {
                if (target.equals(user))
                    continue;

                Vector3i targetBlockPos = target.getLocation().getBlockPosition();
                if (targetBlockPos.equals(rayBlockPos) || targetBlockPos.equals(rayBlockPos.add(0, -1, 0)))
                    return Optional.of(target);
            }

            // If function caller specified no to see-through, AND BlockRay hit non-air block, end the function.
            if (!seeThroughBlocks && cur.getLocation().hasBlock())
                return Optional.empty();
        }

        return Optional.empty();
    }

    public static void shootRay(Player user, int distance, ParticleType... particleTypes) {
        BlockRay<World> ray = BlockRay.from(user)
                .distanceLimit(distance)
                .build();

        BlockRayHit<World> cur = ray.next();
        while (ray.hasNext()) {
            for (ParticleType pt : particleTypes) {
                ParticleEffect toShow = ParticleEffect.builder()
                        .type(pt)
                        .build();
                cur.getExtent().spawnParticles(toShow, cur.getPosition());
            }

            cur = ray.next(); // Iterating to next.
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

    public static void logToMe(String msg) {
        Optional<Player> me = Sponge.getServer().getPlayer("LilypadCollector");
        if (me.isPresent())
            me.get().sendMessage(Text.of(msg));
    }
}

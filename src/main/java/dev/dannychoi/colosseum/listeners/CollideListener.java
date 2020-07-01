package dev.dannychoi.colosseum.listeners;

import dev.dannychoi.colosseum.Colosseum;
import dev.dannychoi.colosseum.GameManager;
import dev.dannychoi.colosseum.StatusManager;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.Explosive;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.entity.projectile.explosive.fireball.Fireball;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.action.CollideEvent;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

import java.util.Optional;

public class CollideListener {

    @Listener
    public void onEntityCollide(CollideEvent.Impact event, @First Entity entity) {
        StatusManager statusManager = Colosseum.getGameManager().getStatusManager();

        Optional<Integer> statusOpt = statusManager.getStatus(entity);
        if (!statusOpt.isPresent())
            return; // Since it's just a regular collision that we don't care about
        if (statusOpt.get() == GameManager.CODE_EXPLOSIVE_ARROW_FLYING && (entity instanceof Snowball)) {
            Snowball arrow = (Snowball) entity;
            Location<World> impactPoint = event.getImpactPoint();

            // This is just the effect & block destroying, NOT the damage.
            Fireball base = (Fireball) arrow.getWorld().createEntity(EntityTypes.FIREBALL, impactPoint.getPosition());
            Explosion explosion = Explosion.builder()
                    .knockback(0)
                    .canCauseFire(false)
                    .shouldBreakBlocks(true)
                    .location(impactPoint)
                    .shouldPlaySmoke(false)
                    .shouldDamageEntities(false)
                    .sourceExplosive((Explosive) base)
                    .build();
            arrow.getWorld().triggerExplosion(explosion);
            for (Entity e : entity.getNearbyEntities(6)) {
                if (e.equals(arrow.getShooter()))
                    continue;

                e.damage(6, DamageSources.GENERIC);
            }
        }

    }

}

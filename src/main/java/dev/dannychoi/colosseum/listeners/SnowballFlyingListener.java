package dev.dannychoi.colosseum.listeners;

import dev.dannychoi.colosseum.Colosseum;
import dev.dannychoi.colosseum.GameManager;
import dev.dannychoi.colosseum.StatusManager;
import dev.dannychoi.colosseum.Utils;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;

import java.util.Optional;

public class SnowballFlyingListener {

    @Listener
    public void onMoveEntity(MoveEntityEvent e) {
        if (!(e.getTargetEntity() instanceof Snowball))
            return;

        StatusManager statusManager = Colosseum.getGameManager().getStatusManager();
        Snowball arrow = (Snowball) e.getTargetEntity();

        if (!statusManager.statusExistsFor(arrow))
            return;

        Optional<Integer> arrowStatusOpt = statusManager.getStatus(arrow);
        if (!arrowStatusOpt.isPresent())
            return;

        if (arrowStatusOpt.get() == GameManager.CODE_EXPLOSIVE_ARROW_FLYING) {
            Utils.spawnParticlesAt(arrow, ParticleTypes.FIRE_SMOKE);
        }
    }

}

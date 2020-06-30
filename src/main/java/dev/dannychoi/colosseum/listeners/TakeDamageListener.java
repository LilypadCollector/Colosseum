package dev.dannychoi.colosseum.listeners;

import dev.dannychoi.colosseum.Colosseum;
import dev.dannychoi.colosseum.GameManager;
import dev.dannychoi.colosseum.PlayerProfile;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

public class TakeDamageListener {

    @Listener
    public void onPlayerDamage(DamageEntityEvent e, @First Entity attacker /* Caution! May be arrow! */) {
        GameManager gameManager = Colosseum.getGameManager();

        if (!(e.getTargetEntity() instanceof Player))
            return;
        Player p = (Player) e.getTargetEntity();
        if (!gameManager.getPlayerProfileOf(p).isPresent()) // If player hasn't chosen a species yet, return.
            return;
        PlayerProfile damagedPP;
        PlayerProfile attackerPP;

        if ( (!attacker.getType().equals(EntityTypes.PLAYER) && !(attacker.getType().equals(EntityTypes.TIPPED_ARROW)))) { // If damage was not caused by player/arrow, ONLY call onTakeDamage() of damaged player.

            damagedPP = gameManager.getPlayerProfileOf(p).get();
            damagedPP.getSpeciesObject().onTakeDamage(damagedPP, null);

        } else { // If damage WAS CAUSED by another player, call BOTH (1) onTakeDamage (2) onHitPlayer.
            // NOTE: If attacker is an arrow, it will also goto else. This is because the shooter of arrow might be a person!

            // Below code says:
            // If the attacker is an arrow, see if that arrow was fired by a person! If fired by person, then the "attacker" should get set to the shooter (duh)!
            boolean isDmgTypeArrow = false;
            if (attacker instanceof Arrow) {
                Arrow arrow = (Arrow) attacker;

                if (arrow.getShooter() instanceof Player) {
                    attacker = (Player) arrow.getShooter();
                    isDmgTypeArrow = true;
                } else
                    return;

            }

            if (attacker.equals(p))
                return; // So that players damaging THEMSELVES doesn't trigger onTakeDamage or onHitPlayer

            damagedPP = gameManager.getPlayerProfileOf(p).get();
            attackerPP = gameManager.getPlayerProfileOf((Player) attacker).get();

            // To determine if damage was melee or bow:
            int damageType;
            if (isDmgTypeArrow)
                damageType = Colosseum.ATTACKTYPE_PROJECTILE;
            else
                damageType = Colosseum.ATTACKTYPE_MELEE; // TODO: Check for cases where player indirectly attacks player. Eg) they set off TNT on another player.

            int chargeToAdd = attackerPP.getSpeciesObject().getCPH();
            // This switch statement determines if charge should be given based on ChargeType
            // Eg) an archer using melee should NOT get charge (since their ChargeType is ONLY_BOW).
            switch (attackerPP.getSpeciesObject().getChargeType()) {
                case BOTH:
                    attackerPP.addCharge(chargeToAdd);
                    break;
                case ONLY_BOW:
                    if (damageType == Colosseum.ATTACKTYPE_PROJECTILE)
                        attackerPP.addCharge(chargeToAdd);
                    break;
                case ONLY_MELEE:
                    if (damageType == Colosseum.ATTACKTYPE_MELEE)
                        attackerPP.addCharge(chargeToAdd);
                    break;

            }

            attackerPP.getSpeciesObject().onHitPlayer(attackerPP, damagedPP, damageType);
            damagedPP.getSpeciesObject().onTakeDamage(damagedPP, null);
        }
    }

}

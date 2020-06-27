package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.PlayerProfile;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;

public class SpeciesArcher implements Species {
    @Override
    public void equip(Player p) {

    }

    @Override
    public int useSkill(PlayerProfile p) {
        return 0;
    }

    @Override
    public int onTakeDamage(PlayerProfile p, DamageSource dmgSrc) {
        return 0;
    }

    @Override
    public int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged, int damageType) {
        // Speed 2, regen 1 for 7 seconds. 14S cooldown.

        return 0;
    }

    @Override
    public SpeciesType getSpeciesType() {
        return SpeciesType.ARCHER;
    }

    @Override
    public int getCPH() {
        return 25;
    }

    @Override
    public int getMaxCharge() {
        return 0;
    }

    @Override
    public String getSkillName() {
        return null;
    }
}

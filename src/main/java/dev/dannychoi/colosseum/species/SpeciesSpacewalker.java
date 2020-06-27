package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.PlayerProfile;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;

public class SpeciesSpacewalker implements Species {
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
        return 0;
    }

    @Override
    public SpeciesType getSpeciesType() {
        return SpeciesType.SPACEWALKER;
    }

    @Override
    public int getCPH() {
        return 25;
    }

    @Override
    public int getMaxCharge() {
        return 100;
    }

    @Override
    public String getSkillName() {
        return "Teleport";
    }
}

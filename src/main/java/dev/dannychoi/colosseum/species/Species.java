package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.PlayerProfile;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;

public interface Species {

    int SUCCESS_CODE = 1;
    int FAIL_CODE = 0;

    void equip(Player p);
    int useSkill(PlayerProfile p);
    int onTakeDamage(PlayerProfile p, DamageSource dmgSrc);
    int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged, int damageType);
    SpeciesType getSpeciesType();
    int getCPH();
    int getMaxCharge();
    String getSkillName();

}

package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.PlayerProfile;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.DamageEntityEvent;

public interface Species {

    final int SUCCESS_CODE = 1;
    final int FAIL_CODE = 0;

    void equip(Player p);
    int useSkill(PlayerProfile p);
    int onTakeDamage(PlayerProfile p, DamageEntityEvent event);
    int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged, DamageEntityEvent event);
    SpeciesType getSpeciesType();
    int getCPH();
    int getMaxCharge();
    int getChargeNeeded();
    int getChargePerUse();
    String getSkillName();
    ChargeType getChargeType();

}

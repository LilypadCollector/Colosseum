package dev.dannychoi.colosseum;

import org.spongepowered.api.entity.living.player.Player;

public interface Species {

    int SUCCESS_CODE = 1;
    int FAIL_CODE = 0;

    void equip(Player p);
    int useSkill(PlayerProfile p);
    int onTakeDamage(PlayerProfile p);
    int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged);
    SpeciesType getSpeciesType();
    int getCPH();
    int getMaxCharge();
    String getSkillName();

}

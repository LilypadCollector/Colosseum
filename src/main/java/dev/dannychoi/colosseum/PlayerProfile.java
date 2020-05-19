package dev.dannychoi.colosseum;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class PlayerProfile {

    PlayerProfile(Player player, SpeciesType speciesType) {
        this.player = player;
        this.speciesType = speciesType;
        charge = 0;
    }

    private Player player;
    private SpeciesType speciesType;
    private int charge;

    public Player getPlayer() {
        return player;
    }

    public Species getSpeciesObject() {
        switch (speciesType) {
            // TODO Edit this list every time a new species is added.
            case DOG:
                return new SpeciesDog();
            default:
                break;
        }
        return null;
    }

    public void setSpeciesType(SpeciesType speciesType) {
        this.speciesType = speciesType;
    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }

    // Adds to the player's charge
    public void addCharge(int chargeToAdd) {
        charge += chargeToAdd;
        // If the new charge exceeds the maximum charge possible, bring it down to the max.
        if (charge > getSpeciesObject().getMaxCharge())
            charge = getSpeciesObject().getMaxCharge();
        displayCharge();
    }

    // Subtracts from the player's charge
    public void minusCharge(int chargeToSub) {
        charge -= chargeToSub;
        if (charge < 0)
            charge = 0;
        displayCharge();
    }

    // Updates player's EXP bar to display charge
    private void displayCharge() {
        player.offer(Keys.EXPERIENCE_LEVEL, charge);
        // TODO Add sound effect when fully charged.
    }

}

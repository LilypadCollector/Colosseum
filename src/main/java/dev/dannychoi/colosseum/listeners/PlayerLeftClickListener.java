package dev.dannychoi.colosseum.listeners;

import dev.dannychoi.colosseum.Colosseum;
import dev.dannychoi.colosseum.GameManager;
import dev.dannychoi.colosseum.PlayerProfile;
import dev.dannychoi.colosseum.species.Species;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PlayerLeftClickListener {
    // Below listener is for calling ability (via left click bow)
    @Listener
    public void onPlayerLeftClick(InteractItemEvent.Primary.MainHand e, @First Player p) {
        GameManager gameManager = Colosseum.getGameManager();

        if (p.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.BOW)) {
            if (!gameManager.isPlayerActive(p)) {
                p.sendMessage(Text.builder("No species selected.").color(TextColors.RED).build());
                return;
            }

            PlayerProfile pp = Colosseum.getGameManager().getPlayerProfileOf(p);
            Species pSpecies = pp.getSpeciesObject();
            if (pp.getCharge() >= pSpecies.getChargeNeeded()) {
                int result = pSpecies.useSkill(pp);
                if (result == Species.SUCCESS_CODE)
                    pp.minusCharge(pSpecies.getChargePerUse());
            } else {
                p.sendMessage(Text.builder("Not enough charge.").color(TextColors.RED).build());
            }
        }
    }
}

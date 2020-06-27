package dev.dannychoi.colosseum.listeners;

import dev.dannychoi.colosseum.Colosseum;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import static dev.dannychoi.colosseum.Colosseum.getPlayerProfileOf;

public class PlayerLeftClickListener {
    // Below listener is for calling ability (via left click bow)
    @Listener
    public void onPlayerLeftClick(InteractBlockEvent.Primary.MainHand e, @First Player p) {
        if (p.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(ItemTypes.BOW)) {
            if (!Colosseum.isPlayerActive(p)) {
                p.sendMessage(Text.builder("No species selected.").color(TextColors.RED).build());
                return;
            }

            getPlayerProfileOf(p).useSkill();
        }
    }
}

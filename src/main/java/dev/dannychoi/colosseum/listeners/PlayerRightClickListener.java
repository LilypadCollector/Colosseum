package dev.dannychoi.colosseum.listeners;

import dev.dannychoi.colosseum.Colosseum;
import dev.dannychoi.colosseum.GameManager;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PlayerRightClickListener {
    // Below listener is for calling ability (by right clicking sword)
    @Listener
    public void onPlayerRightClick(InteractItemEvent.Secondary.MainHand e, @First Player p) {
        GameManager gameManager = Colosseum.getGameManager();

        ItemType heldItemType = p.getItemInHand(HandTypes.MAIN_HAND).get().getType();

        if (heldItemType.equals(ItemTypes.IRON_SWORD) ||
                heldItemType.equals(ItemTypes.DIAMOND_SWORD) ||
                heldItemType.equals(ItemTypes.STONE_SWORD) ||
                heldItemType.equals(ItemTypes.WOODEN_SWORD) ||
                heldItemType.equals(ItemTypes.GOLDEN_SWORD))
        {
            if (!gameManager.isPlayerActive(p)) {
                p.sendMessage(Text.builder("No species selected.").color(TextColors.RED).build());
                return;
            }

            gameManager.getPlayerProfileOf(p).useSkill();
        }
    }
}

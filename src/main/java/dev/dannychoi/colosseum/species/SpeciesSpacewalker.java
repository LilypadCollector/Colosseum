package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.PlayerProfile;
import dev.dannychoi.colosseum.Utils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.MainPlayerInventory;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class SpeciesSpacewalker implements Species {
    @Override
    public void equip(Player p) {
        ItemStack helmet = ItemStack.builder()
                .itemType(ItemTypes.IRON_HELMET)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack chestplate = ItemStack.builder()
                .itemType(ItemTypes.IRON_CHESTPLATE)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack leggings = ItemStack.builder()
                .itemType(ItemTypes.IRON_LEGGINGS)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack boots = ItemStack.builder()
                .itemType(ItemTypes.DIAMOND_BOOTS)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack sword = ItemStack.builder()
                .itemType(ItemTypes.IRON_SWORD)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack pickaxe = ItemStack.builder()
                .itemType(ItemTypes.DIAMOND_PICKAXE)
                .add(Keys.UNBREAKABLE, true)
                .build();
        Utils.addEnchant(pickaxe, EnchantmentTypes.EFFICIENCY, 3);
        ItemStack bow = ItemStack.builder()
                .itemType(ItemTypes.BOW)
                .add(Keys.UNBREAKABLE, true)
                .build();
        Utils.addEnchant(bow, EnchantmentTypes.INFINITY, 1);
        ItemStack axe = ItemStack.builder()
                .itemType(ItemTypes.IRON_AXE)
                .add(Keys.UNBREAKABLE, true)
                .build();

        ItemStack woodStack = ItemStack.builder()
                .itemType(ItemTypes.PLANKS)
                .quantity(64)
                .build();
        ItemStack steak = ItemStack.of(ItemTypes.COOKED_BEEF, 64);
        ItemStack arrow = ItemStack.of(ItemTypes.ARROW);

        CarriedInventory pInv = p.getInventory();

        // Clear inv and give them everything.
        pInv.clear();
        p.setHelmet(helmet);
        p.setChestplate(chestplate);
        p.setLeggings(leggings);
        p.setBoots(boots);
        pInv.offer(sword);
        pInv.offer(pickaxe);
        pInv.offer(bow);
        pInv.offer(axe);
        pInv.offer(Utils.hPot(4));
        pInv.offer(steak);

        // Line below sets top left grid of inventory to arrow.
        ((MainPlayerInventory) p.getInventory().query(MainPlayerInventory.class)).getGrid().set(0, 0, arrow);
        for (int i = 0; i < 18; i++)
            p.getInventory().offer(ItemStack.of(ItemTypes.PLANKS, 64));
    }

    @Override
    public int useSkill(PlayerProfile p) {
        Player user = p.getPlayer();
        Player target;

        Optional<Player> optTarget = Utils.getPlayerLookingAt(user, 25, true);
        if (optTarget.isPresent()) {
            target = optTarget.get();
            Utils.shootRay(user, 25, ParticleTypes.ENDER_TELEPORT, ParticleTypes.FIREWORKS_SPARK);
            user.setLocation(target.getLocation()); // teleport
            Utils.addPotionEffect(user, PotionEffectTypes.SPEED, 3, 5);
            return SUCCESS_CODE;
        }

        user.sendMessage(Text.builder("No player in range!").color(TextColors.RED).build());
        return FAIL_CODE;
    }

    @Override
    public int onTakeDamage(PlayerProfile p, DamageEntityEvent event) {
        return 0;
    }

    @Override
    public int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged, DamageEntityEvent event) {
        if (damageAfflicter.getCharge() == 100)
            Utils.addPotionEffect(damageAfflicter.getPlayer(), PotionEffectTypes.REGENERATION, 1, 10);

        return SUCCESS_CODE;
    }

    @Override
    public SpeciesType getSpeciesType() {
        return SpeciesType.SPACEWALKER;
    }

    @Override
    public int getCPH() {
        return 20;
    }

    @Override
    public int getMaxCharge() {
        return 100;
    }

    @Override
    public int getChargeNeeded() {
        return 100;
    }

    @Override
    public int getChargePerUse() {
        return 100;
    }

    @Override
    public String getSkillName() {
        return "Warp";
    }

    @Override
    public ChargeType getChargeType() {
        return ChargeType.BOTH;
    }
}

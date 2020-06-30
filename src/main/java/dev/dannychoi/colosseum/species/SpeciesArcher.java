package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.*;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.MainPlayerInventory;
import org.spongepowered.api.item.inventory.type.CarriedInventory;

public class SpeciesArcher implements Species {
    @Override
    public void equip(Player p) {
        ItemStack helmet = ItemStack.builder()
                .itemType(ItemTypes.DIAMOND_HELMET)
                .add(Keys.UNBREAKABLE, true)
                .build();
        Utils.addEnchant(helmet, EnchantmentTypes.PROJECTILE_PROTECTION, 2);
        ItemStack chestplate = ItemStack.builder()
                .itemType(ItemTypes.IRON_CHESTPLATE)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack leggings = ItemStack.builder()
                .itemType(ItemTypes.IRON_LEGGINGS)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack boots = ItemStack.builder()
                .itemType(ItemTypes.IRON_BOOTS)
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
        ItemStack axe = ItemStack.builder()
                .itemType(ItemTypes.IRON_AXE)
                .add(Keys.UNBREAKABLE, true)
                .build();
        Utils.addEnchant(bow, EnchantmentTypes.INFINITY, 1);
        Utils.addEnchant(bow, EnchantmentTypes.POWER, 3);

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
        Snowball arrow = user.launchProjectile(Snowball.class).get();
        arrow.setShooter(user);
        user.getWorld().spawnEntity(arrow);
        Colosseum.getGameManager().getStatusManager().setStatus(arrow, GameManager.CODE_EXPLOSIVE_ARROW_FLYING); // This will let CollideListener.java know when to cause impact explosion.

        return SUCCESS_CODE;
    }

    @Override
    public int onTakeDamage(PlayerProfile p, DamageSource dmgSrc) {
        return 0;
    }

    @Override
    public int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged, int damageType) {
        CooldownManager cooldownManager = Colosseum.getGameManager().getCooldownManager();

        // Speed 2, regen 1 for 7 seconds. 14S cooldown.
        if (damageType == Colosseum.ATTACKTYPE_PROJECTILE) {
            // Checking for 14s cooldown.
            if (cooldownManager.hasCooldown(damageAfflicter, CooldownManager.COOLDOWN_ARCHER_PASSIVE))
                return FAIL_CODE;

            Utils.addPotionEffect(damageAfflicter.getPlayer(), PotionEffectTypes.SPEED, 2, 7);
            Utils.addPotionEffect(damageAfflicter.getPlayer(), PotionEffectTypes.REGENERATION, 1, 7);
            cooldownManager.setCooldown(damageAfflicter, CooldownManager.COOLDOWN_ARCHER_PASSIVE, 14);
            return SUCCESS_CODE;
        }
        return FAIL_CODE;
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
        return "Explosive Arrow";
    }

    @Override
    public ChargeType getChargeType() {
        return ChargeType.ONLY_BOW;
    }
}

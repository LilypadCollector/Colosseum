package dev.dannychoi.colosseum;

import com.sun.tools.javac.util.List;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.MainPlayerInventory;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.item.potion.PotionTypes;

import java.util.Collection;
import java.util.Random;

public class SpeciesDog implements Species {

    @Override
    public void equip(Player p) {
        ItemStack helmet = ItemStack.builder()
                .itemType(ItemTypes.IRON_HELMET)
                .add(Keys.UNBREAKABLE, true)
                .build();
        ItemStack chestplate = ItemStack.builder()
                .itemType(ItemTypes.IRON_CHESTPLATE)
                .add(Keys.UNBREAKABLE, true)
                .add(Keys.ITEM_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.PROJECTILE_PROTECTION, 4)))
                .build();
        ItemStack leggings = ItemStack.builder()
                .itemType(ItemTypes.IRON_LEGGINGS)
                .add(Keys.UNBREAKABLE, true)
                .add(Keys.ITEM_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.PROJECTILE_PROTECTION, 4)))
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
                .add(Keys.ITEM_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.EFFICIENCY, 3)))
                .build();
        ItemStack bow = ItemStack.builder()
                .itemType(ItemTypes.BOW)
                .add(Keys.UNBREAKABLE, true)
                .add(Keys.ITEM_ENCHANTMENTS, List.of(Enchantment.of(EnchantmentTypes.INFINITY, 1)))
                .build();
        ItemStack healthPots = ItemStack.builder()
                .itemType(ItemTypes.POTION)
                .quantity(2)
                .add(Keys.POTION_TYPE, PotionTypes.HEALING)
                .add(Keys.POTION_EFFECTS, List.of(PotionEffect.of(PotionEffectTypes.INSTANT_HEALTH, 4, 999999999)))
                .build();
        ItemStack woodStack = ItemStack.builder()
                .itemType(ItemTypes.PLANKS)
                .quantity(64)
                .build();
        ItemStack arrow = ItemStack.of(ItemTypes.ARROW);

        p.equip(EquipmentTypes.HEADWEAR, helmet);
        p.equip(EquipmentTypes.CHESTPLATE, chestplate);
        p.equip(EquipmentTypes.LEGGINGS, leggings);
        p.equip(EquipmentTypes.BOOTS, boots);

        p.getInventory().clear();
        p.getInventory().offer(sword);
        p.getInventory().offer(pickaxe);
        p.getInventory().offer(bow);
        p.getInventory().offer(healthPots);
        // Line below sets top left grid of inventory to arrow.
        ((MainPlayerInventory) p.getInventory().query(MainPlayerInventory.class)).getGrid().set(0, 0, arrow);
        while (p.getInventory().canFit(woodStack)) // Filling entire inventory with wood
            p.getInventory().offer(woodStack);
    }

    @Override
    public int useSkill(PlayerProfile p) {
        Player user = p.getPlayer(); // Player who uses ability
        Player receiver = null; // Soon to be the player (1) Within range (2) that the user is looking at
        Collection<Entity> playersInRange = user.getNearbyEntities(5);
        for (Entity e : playersInRange)
            if (e.getType().equals(EntityTypes.PLAYER)) {
                receiver = (Player) e;
                break; // We got our receiver, so we can end the loop.
            }

        if (receiver == null)
            return FAIL_CODE; // No player in range!
        else {
            receiver.damage(5.0D, (DamageSource) EntityDamageSource.builder().entity(user));
            PotionEffect slowness = PotionEffect.of(PotionEffectTypes.SLOWNESS, 1, 2);
            user.playSound(SoundTypes.ENTITY_WOLF_HURT, user.getPosition(), 7);
            return SUCCESS_CODE;
        }
    }

    @Override
    public int onTakeDamage(PlayerProfile p) {
        // Dog has X% chance to get angry when damaged.
        int outOf100 = (new Random()).nextInt(100);
        if (outOf100 < 15) {
            Player player = p.getPlayer();
            PotionEffect strength = PotionEffect.of(PotionEffectTypes.STRENGTH, 1, 3);
            PotionEffect speed = PotionEffect.of(PotionEffectTypes.SPEED, 2, 7);
            player.offer(Keys.POTION_EFFECTS, List.of(strength, speed));
            player.playSound(SoundTypes.ENTITY_WOLF_GROWL, player.getPosition(), 10);
        }

        return FAIL_CODE;
    }

    @Override
    public int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged) {
        return 0;
    }

    @Override
    public SpeciesType getSpeciesType() {
        return SpeciesType.DOG;
    }

    @Override
    public int getCPH() {
        return 10;
    }

    @Override
    public int getMaxCharge() {
        return 100;
    }

    @Override
    public String getSkillName() {
        return "Bite";
    }
}

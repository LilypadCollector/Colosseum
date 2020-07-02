package dev.dannychoi.colosseum.species;

import dev.dannychoi.colosseum.PlayerProfile;
import dev.dannychoi.colosseum.Utils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.MainPlayerInventory;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

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
                .itemType(ItemTypes.DIAMOND_CHESTPLATE)
                .add(Keys.UNBREAKABLE, true)
                .build();
        Utils.addEnchant(chestplate, EnchantmentTypes.PROJECTILE_PROTECTION, 4);
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
        Player user = p.getPlayer(); // Player who uses ability
        Player receiver = null; // Soon to be the player (1) Within range (2) that the user is looking at
        Collection<Entity> playersInRange = user.getNearbyEntities(5);
        if (playersInRange.isEmpty())
            return FAIL_CODE;
        for (Entity e : playersInRange)
            if (e.getType().equals(EntityTypes.PLAYER) && e != user) {
                receiver = (Player) e;
                break; // We got our receiver, so we can end the loop.
            }

        if (receiver == null) {
            p.getPlayer().sendMessage(Text.builder("No player in range.").color(TextColors.RED).build());
            return FAIL_CODE; // No player in range!
        } else {
            receiver.damage(5D, DamageSources.GENERIC);
            Utils.addPotionEffect(receiver, PotionEffectTypes.SLOWNESS, 1, 2);
            user.playSound(SoundTypes.ENTITY_WOLF_HURT, user.getPosition(), 7);

            return SUCCESS_CODE;
        }
    }


    @Override
    public int onTakeDamage(PlayerProfile pp, DamageEntityEvent event) {

        // Dog has X% chance to get angry when damaged.
        int outOf100 = (new Random()).nextInt(100);
        if (outOf100 < 15) {
            Player p = pp.getPlayer();
            Utils.addPotionEffect(p, PotionEffectTypes.STRENGTH, 1, 3);
            Utils.addPotionEffect(p, PotionEffectTypes.SPEED, 2, 7);
            p.playSound(SoundTypes.ENTITY_WOLF_GROWL, p.getPosition(), 10);
            Utils.spawnParticlesAbove(p, ParticleTypes.ANGRY_VILLAGER, 10);
            return SUCCESS_CODE;
        }

        return FAIL_CODE;
    }

    @Override
    public int onHitPlayer(PlayerProfile damageAfflicter, PlayerProfile damaged, DamageEntityEvent event) {
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
    public int getChargeNeeded() {
        return 100;
    }

    @Override
    public int getChargePerUse() {
        return 100;
    }

    @Override
    public String getSkillName() {
        return "Bite";
    }

    @Override
    public ChargeType getChargeType() {
        return ChargeType.BOTH;
    }
}

package dev.dannychoi.colosseum;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public void addEnchant(ItemStack it, EnchantmentType et, int level) {
        List<Enchantment> enchantments = it.get(Keys.ITEM_ENCHANTMENTS).orElse(new ArrayList<>());
        enchantments.add(Enchantment.of(et, level));
    }
}


package xyz.etheriamc.skyblock.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.etheriamc.skyblock.util.CC;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ItemBuilder implements Listener {
    private final ItemStack is;

    public ItemBuilder(final Material mat) {
        this.is = new ItemStack(mat);
    }

    public ItemBuilder(final ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(final Material material, final int amount) {
        this(material, amount, (byte)0);
    }

    public ItemBuilder(final Material material, final int amount, final byte data) {
        Preconditions.checkNotNull((Object)material, "Material cannot be null");
        Preconditions.checkArgument(amount > 0, "Amount must be positive");
        this.is = new ItemStack(material, amount, data);
    }

    public ItemBuilder(final Material material, final int amount, final int data) {
        Preconditions.checkNotNull((Object)material, "Material cannot be null");
        Preconditions.checkArgument(amount > 0, "Amount must be positive");
        this.is = new ItemStack(material, amount, (short)data);
    }

    public ItemBuilder amount(final int amount) {
        this.is.setAmount(amount);
        return this;
    }

    public ItemBuilder name(final String name) {
        final ItemMeta meta = this.is.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(final String name) {
        final ItemMeta meta = this.is.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(ChatColor.translateAlternateColorCodes('&', name));
        meta.setLore(lore);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addToLore(String ... parts) {
        List lore;
        ItemMeta meta = this.is.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(this.is.getType());
        }
        if ((lore = meta.getLore()) == null) {
            lore = Lists.newArrayList();
        }
        lore.addAll(Arrays.stream(parts).map(part -> ChatColor.translateAlternateColorCodes('&', part)).collect(Collectors.toList()));
        meta.setLore(lore);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(Collection<String> l) {
        ArrayList lore = new ArrayList<>();
        ItemMeta meta = this.is.getItemMeta();
        lore.addAll(l.stream().map(part -> ChatColor.translateAlternateColorCodes('&', part)).collect(Collectors.toList()));
        meta.setLore(lore);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(final List<String> lore) {
        final List<String> toSet = new ArrayList<>();
        final ItemMeta meta = this.is.getItemMeta();
        for (final String string : lore) {
            toSet.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        meta.setLore(toSet);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(final String... lore) {
        ItemMeta meta = this.is.getItemMeta();
        if (meta == null) {
            meta = this.is.getItemMeta();
        }
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder durability(final int durability) {
        this.is.setDurability((short)durability);
        return this;
    }

    public ItemBuilder enchant(final Enchantment enchantment, final int level) {
        this.is.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchant(final Enchantment enchantment) {
        this.is.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder type(final Material material) {
        this.is.setType(material);
        return this;
    }

    public ItemBuilder clearLore() {
        final ItemMeta meta = this.is.getItemMeta();
        meta.setLore(new ArrayList());
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (final Enchantment e : this.is.getEnchantments().keySet()) {
            this.is.removeEnchantment(e);
        }
        return this;
    }

    public ItemBuilder data(final short data) {
        this.is.setDurability(data);
        return this;
    }

    public ItemBuilder color(final Color color) {
        if (this.is.getType() == Material.LEATHER_BOOTS || this.is.getType() == Material.LEATHER_CHESTPLATE || this.is.getType() == Material.LEATHER_HELMET || this.is.getType() == Material.LEATHER_LEGGINGS) {
            final LeatherArmorMeta meta = (LeatherArmorMeta)this.is.getItemMeta();
            meta.setColor(color);
            this.is.setItemMeta(meta);
            return this;
        }
        throw new IllegalArgumentException("color() only applicable for leather armor!");
    }

    public ItemBuilder glow() {
        ItemMeta meta = this.is.getItemMeta();

        meta.addEnchant(new Glow(), 1, true);
        this.is.setItemMeta(meta);

        return this;
    }

    public static void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glow glow = new Glow();
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Glow extends Enchantment {

        public Glow() {
            super(25);
        }

        @Override
        public boolean canEnchantItem(ItemStack arg0) {
            return false;
        }

        @Override
        public boolean conflictsWith(Enchantment arg0) {
            return false;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return null;
        }

        @Override
        public int getMaxLevel() {
            return 2;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public int getStartLevel() {
            return 1;
        }
    }

    public ItemBuilder meta(final ItemMeta itemMeta) {
        this.is.setItemMeta(itemMeta);
        return this;
    }



    public ItemBuilder material(Material material) {
        this.is.setType(material);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        ItemMeta meta = this.is.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder replaceLoreLine(String lore, String replacedLore) {
        ItemMeta meta = this.is.getItemMeta();
        List<String> itemLore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
        for (String line : itemLore) {
            if (line.contains(CC.translate(lore)))
                itemLore.set(itemLore.indexOf(line), CC.translate(replacedLore));
        }
        meta.setLore(itemLore);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder replaceLoreLine(String lore) {
        ItemMeta meta = this.is.getItemMeta();
        List<String> itemLore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
        itemLore.removeIf(line -> line.contains(CC.translate(lore)));
        meta.setLore(itemLore);
        this.is.setItemMeta(meta);
        return this;
    }

    public static ItemBuilder copyOf(ItemBuilder builder) {
        return new ItemBuilder(builder.build());
    }

    public static ItemBuilder copyOf(ItemStack item) {
        return new ItemBuilder(item);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material, 1);
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public ItemStack build() {
        return this.is;
    }

    /*public ItemBuilder texture(String value) {
        if (!(this.is.getItemMeta() instanceof SkullMeta)) {
            return this;
        }

        final SkullMeta meta = (SkullMeta) this.is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", value));

        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        this.is.setItemMeta(meta);
        return this;
    }*/

    public ItemBuilder skull(String owner) {
        if (!(this.is.getItemMeta() instanceof SkullMeta)) {
            return this;
        }

        final SkullMeta meta = (SkullMeta) this.is.getItemMeta();
        meta.setOwner(owner);
        this.is.setItemMeta(meta);
        return this;
    }

}

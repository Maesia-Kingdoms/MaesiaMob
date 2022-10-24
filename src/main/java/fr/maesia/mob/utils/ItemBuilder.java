package fr.maesia.mob.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class ItemBuilder {

    private final ItemStack it;
    private final ItemMeta itm;

    public ItemStack getItemStack() {
        return it;
    }

    public ItemBuilder(ItemStack it){
        this.it = it;
        this.itm = it.getItemMeta();
    }

    public ItemBuilder(Material m){
        this(m, 1);
    }

    public ItemBuilder(Material m, int amount){
        it= new ItemStack(m, amount);
        this.itm = it.getItemMeta();
    }

    public ItemBuilder clone() throws CloneNotSupportedException {
        ItemBuilder clone = (ItemBuilder) super.clone();
        return new ItemBuilder(clone.it);
    }

    public @NotNull ItemBuilder setName(String name){
        assert itm != null;
        itm.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder setAmount(Integer i){
        i = Math.min(i, 64);
        it.setAmount(i);
        return this;
    }


    public @NotNull ItemBuilder setLore(String... lore){
        List<String> newLore = new ArrayList<>();
        for(String l : lore){
            newLore.add(ChatColor.translateAlternateColorCodes('&', l));
        }
        itm.setLore(newLore);
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder setLore(List<String> lore){
        ChatColor.translateAlternateColorCodes('&', String.valueOf(lore));
        itm.setLore(lore);
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder addLore(String... strings){
        List<String> lore;
        if (!itm.hasLore()){
            lore = new ArrayList<>();
        }else {
            lore = itm.getLore();
        }
        Collections.addAll(lore, strings);
        itm.setLore(lore);
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder setPersistentData(Plugin plugin, String key, String value){
        itm.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        it.setItemMeta(itm);
        return this;
    }
    public @NotNull ItemBuilder setPersistentData(Plugin plugin, String key, Integer value){
        itm.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, value);
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder setPersistentData(Plugin plugin, String key, Long value){
        itm.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.LONG, value);
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder setPlayerSkull(String name){
        SkullMeta skullMeta = (SkullMeta) itm;
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(name);
        assert skullMeta != null;
        playerProfile.update();
        skullMeta.setOwnerProfile(playerProfile);
        it.setItemMeta(skullMeta);
        return this;
    }

    public @NotNull ItemBuilder setPlayerSkull(Player player){
      setPlayerSkull(player.getUniqueId());
      return this;
    }

    public @NotNull ItemBuilder setPlayerSkull(UUID uuid){
        it.setType(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        PlayerProfile playerProfile = Objects.requireNonNull(Bukkit.getPlayer(uuid)).getPlayerProfile();
        playerProfile.update();
        assert itm != null;
        itm.setOwnerProfile(playerProfile);
        it.setItemMeta(itm);
        return this;
    }

    public @NotNull ItemBuilder setPlayerSkullUrl(String url) throws MalformedURLException {
        URL url1 = new URL("http://textures.minecraft.net/texture/"+url);
        it.setType(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        PlayerProfile playerProfile = Bukkit.createPlayerProfile("sss"+url+"sss");
        PlayerTextures playerTextures = playerProfile.getTextures();
        playerTextures.setSkin(url1);
        playerProfile.update();
        assert itm != null;
        itm.setOwnerProfile(playerProfile);
        it.setItemMeta(itm);
        return this;
    }


}

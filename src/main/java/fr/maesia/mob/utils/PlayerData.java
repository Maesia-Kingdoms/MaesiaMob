package fr.maesia.mob.utils;

import fr.maesia.mob.MaesiaMob;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PlayerData {
    private static final Plugin plugin = MaesiaMob.getInstance();

    public static void setPersistentData(Player p, String key , String value){
        p.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    public static Boolean hasPersitentDataSring(Player p, String key){
        return p.getPersistentDataContainer().has(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static String getPersistentDataString(Player p, String key){
        return p.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }
    public static void removePersistentDataString(Player p, String key){
        p.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
    }
}

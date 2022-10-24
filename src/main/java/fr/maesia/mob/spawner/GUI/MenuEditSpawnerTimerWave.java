package fr.maesia.mob.spawner.GUI;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.spawner.Spawner;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public class MenuEditSpawnerTimerWave {
    public static void menuEditSpawnerTimerWaveInteract(final InventoryClickEvent e) {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it == null) return;

        ItemStack its = e.getView().getItem(0);
        if (its == null) return;
        String key = Objects.requireNonNull(its.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "ID"), PersistentDataType.STRING);

        if (key == null) return;
        UUID uuid = UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);

        if (spawner == null) return;
        if (it.equals(MenuSpawner.onExit())) {
            new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), player,"MenuSpawnerEdit");
            return;
        }
        if (it.getType().equals(Material.CLOCK)){
            Long l = Objects.requireNonNull(it.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "TimerWave"),PersistentDataType.LONG);
            spawner.setDelay(l);
            new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), player,"MenuSpawnerEdit");
        }

    }
}

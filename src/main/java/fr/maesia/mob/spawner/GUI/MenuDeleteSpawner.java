package fr.maesia.mob.spawner.GUI;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.MaesiaMobFiles.Messages.Messages;
import fr.maesia.mob.MaesiaMobFiles.Messages.MessagesValues;
import fr.maesia.mob.spawner.Spawner;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public class MenuDeleteSpawner {

    public static void menuDeleteSpawner(final InventoryClickEvent e) {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it == null) return;

        ItemStack its = e.getView().getItem(4);
        if (its == null) return;
        String key = Objects.requireNonNull(its.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "ID"), PersistentDataType.STRING);

        if (key == null) return;
        UUID uuid = UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);

        if (spawner == null) return;

        if (it.equals(MenuSpawner.onExit())) {
            new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), player, "MenuSpawnerEdit");
            return;
        }

        if (it.getType().equals(Material.BARRIER)) {
            Spawner.getSpawnerList().remove(spawner);
            player.sendMessage(Messages.getPrefix()+Messages.REMOVE_SPAWNER_SUCCESS.get().replace(MessagesValues.MOB.toName(), spawner.getName()));
            new MenuSpawner(MenuSpawner.getSpawnerMenuMain(), player, "MenuMainSpawner");
        }
    }
}

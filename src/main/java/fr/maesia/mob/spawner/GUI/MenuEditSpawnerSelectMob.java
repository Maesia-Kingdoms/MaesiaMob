package fr.maesia.mob.spawner.GUI;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.spawner.Spawner;
import fr.maesia.mob.utils.EntityHead;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuEditSpawnerSelectMob {


    public static void menuEditSpawnerSelectMobs(final InventoryClickEvent e) throws MalformedURLException {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it == null) return;
        if (it.equals(MenuSpawner.deco()))return;
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

        if (e.getSlot() == 47) {
            int page = it.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "Page"), PersistentDataType.INTEGER);
            onUpdatePage(e.getInventory(), page, spawner);
            return;
        }
        //page suivante
        int page = e.getView().getItem(51).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "Page"), PersistentDataType.INTEGER);
        if (e.getSlot() == 51) {
            onUpdatePage(e.getInventory(), page, spawner);
            return;
        }

        String keyMobs = Objects.requireNonNull(it.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "ID"), PersistentDataType.STRING);
        if (keyMobs == null)return;
        UUID uuidMobs = UUID.fromString(keyMobs);
        Mobs mobs = Mobs.getMobs(uuidMobs);
        if (mobs == null)return;
        spawner.changeStatus(mobs);
        onUpdatePage(e.getInventory(), (page-1), spawner);
    }

    private static void onUpdatePage(Inventory inventory, Integer page, Spawner spawner) throws MalformedURLException {
        int maxPage = 26;
        for(Integer i : clearNoBorder()){
            inventory.setItem(i,new ItemStack(Material.AIR));
        }
        int turn = 0;
        int start = page * maxPage;
        int finish = (page+1) * maxPage;
        for (ItemStack it : MenuSpawner.getMobSkullList(spawner)){
            if (turn >=start && turn <= finish) inventory.addItem(it);
            turn++;
        }
        inventory.setItem(47, MenuSpawner.onLeft(page));
        inventory.setItem(51, MenuSpawner.onRight(page));
    }
    public static Integer[] clearNoBorder(){
        List<Integer> integers =new ArrayList<>();
        for (int i = 1; i < 5; i++){
            for(int x =(i*9)+1; x < (i*9)+8; x++){
                integers.add(x);
            }
        }
        return integers.toArray(new Integer[0]);
    }
}

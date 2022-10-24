package fr.maesia.mob.spawner.GUI;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.spawner.Spawner;
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

public class MenuMainSpawnerInteract {

    public static void menuMainSpawnerInteract(final InventoryClickEvent e) throws MalformedURLException {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();

        ItemStack it = e.getCurrentItem();
        if (it ==  null)return;

        if (e.getSlot() == 47) {
            int page = it.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "Page"), PersistentDataType.INTEGER);
            onUpdatePage(e.getInventory(), page);
            return;
        }
        //page suivante
        int page = e.getView().getItem(51).getItemMeta().getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "Page"), PersistentDataType.INTEGER);
        if (e.getSlot() == 51) {
            onUpdatePage(e.getInventory(), page);
            return;
        }
        if (it.getType().equals(Material.SPAWNER)){
            String key =  Objects.requireNonNull(it.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "ID"), PersistentDataType.STRING);
            if (key == null)return;
            UUID uuid =  UUID.fromString(key);
            Spawner spawner = Spawner.getId(uuid);
            if(spawner == null) return;
            player.closeInventory();
            new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), player,"MenuSpawnerEdit");
        }

    }

    private static void onUpdatePage(Inventory inventory, Integer page) throws MalformedURLException {
        int maxPage = 26;
        for(Integer i : clearNoBorder()){
            inventory.setItem(i,new ItemStack(Material.AIR));
        }
        int turn = 0;
        int start = page * maxPage;
        int finish = (page+1) * maxPage;
        for (ItemStack it : MenuSpawner.getSpawner()){
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

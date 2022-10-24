package fr.maesia.mob.spawner;

import fr.maesia.mob.spawner.GUI.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.net.MalformedURLException;

public class MenuSpawnerInteract implements Listener {

    @EventHandler
    private void onInteractMenu(InventoryClickEvent e) throws MalformedURLException {
        if(MenuSpawner.checkInventory(e.getInventory(), "MenuMainSpawner")) MenuMainSpawnerInteract.menuMainSpawnerInteract(e);
        if(MenuSpawner.checkInventory(e.getInventory(), "MenuSpawnerEdit")) MenuEditSpawner.menuEditSpawnerInteract(e);
        if(MenuSpawner.checkInventory(e.getInventory(), "MenuSpawnerEditTimerWave")) MenuEditSpawnerTimerWave.menuEditSpawnerTimerWaveInteract(e);
        if(MenuSpawner.checkInventory(e.getInventory(), "MenuSpawnerEditSelectionMob")) MenuEditSpawnerSelectMob.menuEditSpawnerSelectMobs(e);
        if(MenuSpawner.checkInventory(e.getInventory(), "MenuDeleteSpawner")) MenuDeleteSpawner.menuDeleteSpawner(e);
    }

    @EventHandler
    private void onInteractMenu(InventoryCloseEvent e){
        MenuSpawner.deleteMenu(e.getInventory());
    }


}

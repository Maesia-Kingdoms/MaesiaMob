package fr.maesia.mob.spawner.GUI;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.MaesiaMobFiles.Messages.Messages;
import fr.maesia.mob.spawner.Spawner;
import fr.maesia.mob.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.net.MalformedURLException;
import java.util.Objects;
import java.util.UUID;

public class MenuEditSpawner {
    public static void menuEditSpawnerInteract(final InventoryClickEvent e) throws MalformedURLException {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        if (it ==  null)return;
        if (it.equals(MenuSpawner.onExit())){
            new MenuSpawner(MenuSpawner.getSpawnerMenuMain() ,player, "MenuMainSpawner");
            return;
        }
        ItemStack its = e.getView().getItem(0);
        if (its == null)return;
        String key = Objects.requireNonNull(its.getItemMeta()).getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "ID"), PersistentDataType.STRING);

        if (key == null)return;
        UUID uuid =  UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);

        if(spawner == null)return;

        //teleportation spawner
        if (it.getType().equals(Material.ENDER_PEARL)){
            player.teleport(spawner.getLocation());
            return;
        }

        //Gestion du radius
        if (it.getType().equals(Material.RECOVERY_COMPASS)){
            player.closeInventory();
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_SPAWNER_SPAWN_RADIUS.get());
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
            PlayerData.setPersistentData(player, "Radius", spawner.getId().toString());
            return;
        }
        //Gestion du Limite de mobs
        if (it.getType().equals(Material.PAPER)){
            player.closeInventory();
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_SPAWNER_SPAWN_LIMIT.get());
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
            PlayerData.setPersistentData(player, "Limit", spawner.getId().toString());
            return;
        }
        //Gestion de la limite de mobs min
        if (it.getType().equals(Material.EGG)){
            player.closeInventory();
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_SPAWNER_SPAWN_MIN.get());
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
            PlayerData.setPersistentData(player, "SpawnMin", spawner.getId().toString());
            return;
        }
        //Gestion de la limite de mobs max
        if (it.getType().equals(Material.BLAZE_POWDER)){
            player.closeInventory();
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_SPAWNER_SPAWN_MAX.get());
            player.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
            PlayerData.setPersistentData(player, "SpawnMax", spawner.getId().toString());
            return;
        }

        //Gestion du temps de spawn
        if (it.getType().equals(Material.CLOCK)){
            player.closeInventory();
            new MenuSpawner(MenuSpawner.getMenuSpawnerTimerWave(spawner), player, "MenuSpawnerEditTimerWave");
            return;
        }
        if(it.getType().equals(Material.SKELETON_SKULL)){
            player.closeInventory();
            new MenuSpawner(MenuSpawner.getMenuSelectMobToSpawner(spawner), player, "MenuSpawnerEditSelectionMob");
            return;
        }
        if (it.getType().equals(Material.BARRIER)){
            player.closeInventory();
            new MenuSpawner(MenuSpawner.getMenuSpawnerDelete(spawner), player, "MenuDeleteSpawner");
        }

    }

    public static void chatEditSpawnerLimitEdit(final AsyncPlayerChatEvent e){
        e.setCancelled(true);
        String key =  PlayerData.getPersistentDataString(e.getPlayer(), "Limit");
        if (key == null)return;
        UUID uuid =  UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);
        PlayerData.removePersistentDataString(e.getPlayer(), "Limit");
        if(spawner == null)return;


        Bukkit.getScheduler().runTask(MaesiaMob.getInstance(), ()->{
            if(e.getMessage().equalsIgnoreCase("Cancel")){
                return;
            }
            int number = getStringIsInt(e.getMessage());

            if (number < 1){
                e.getPlayer().sendMessage(Messages.getPrefix()+Messages.EDIT_NUMBER_ERROR_EXACT.get());
                PlayerData.setPersistentData(e.getPlayer(), "Limit", spawner.getId().toString());
                return;
            }
            spawner.setLimit(number);
            new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
        });
    }

    public static void chatEditSpawnerRadiusEdit(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        String key =  PlayerData.getPersistentDataString(e.getPlayer(), "Radius");
        if (key == null)return;
        UUID uuid =  UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);
        PlayerData.removePersistentDataString(e.getPlayer(), "Radius");
        if(spawner == null)return;
        Bukkit.getScheduler().runTask(MaesiaMob.getInstance(),() ->{
            if(e.getMessage().equalsIgnoreCase("Cancel")){
                new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
                return;
            }
            if (getStringIsInt(e.getMessage()) > 0){
                spawner.setRadius(getStringIsInt(e.getMessage()));
                new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
            }else {
                e.getPlayer().sendMessage(Messages.getPrefix()+Messages.EDIT_NUMBER_ERROR_EXACT.get());
                PlayerData.setPersistentData(e.getPlayer(), "Radius", spawner.getId().toString());
            }
        });
    }

    public static void chatEditSpawnMinEdit(final AsyncPlayerChatEvent e){
        e.setCancelled(true);
        String key =  PlayerData.getPersistentDataString(e.getPlayer(), "SpawnMin");
        if (key == null)return;
        UUID uuid =  UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);
        PlayerData.removePersistentDataString(e.getPlayer(), "SpawnMin");
        if(spawner == null)return;

        Bukkit.getScheduler().runTask(MaesiaMob.getInstance(),() ->{

            if(e.getMessage().equalsIgnoreCase("Cancel")){
                new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
                return;
            }
            int number =getStringIsInt(e.getMessage());
            if (number > 0){
                number = Math.min(number, spawner.getSpawnMax());
                spawner.setSpawnMin(number);
                new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
            }else {
                e.getPlayer().sendMessage(Messages.getPrefix()+Messages.EDIT_NUMBER_ERROR_EXACT.get());
                PlayerData.setPersistentData(e.getPlayer(), "SpawnMin", spawner.getId().toString());
            }
        });
    }

    public static void chatEditSpawnMaxEdit(final AsyncPlayerChatEvent e){
        e.setCancelled(true);
        String key =  PlayerData.getPersistentDataString(e.getPlayer(), "SpawnMax");
        if (key == null)return;
        UUID uuid =  UUID.fromString(key);
        Spawner spawner = Spawner.getId(uuid);
        PlayerData.removePersistentDataString(e.getPlayer(), "SpawnMax");
        if(spawner == null)return;
        Bukkit.getScheduler().runTask(MaesiaMob.getInstance(),() ->{
            if(e.getMessage().equalsIgnoreCase("Cancel")){
                new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
                return;
            }
            int number =getStringIsInt(e.getMessage());
            if (number > 0){
                number = Math.max(number, spawner.getSpawnMin());
                spawner.setSpawnMax(number);
                new MenuSpawner(MenuSpawner.getSpawnerMenuEdit(spawner), e.getPlayer(),"MenuSpawnerEdit");
            }else {
                e.getPlayer().sendMessage(Messages.getPrefix()+Messages.EDIT_NUMBER_ERROR_EXACT.get());
                PlayerData.setPersistentData(e.getPlayer(), "SpawnMax", spawner.getId().toString());
            }
        });
    }

    private static int getStringIsInt(String message){

        try {
            return Integer.parseInt(message);
        }catch (NumberFormatException e){
            return -1;
        }
    }

}

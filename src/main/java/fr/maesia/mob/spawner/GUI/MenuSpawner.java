package fr.maesia.mob.spawner.GUI;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.spawner.Spawner;
import fr.maesia.mob.utils.EntityHead;
import fr.maesia.mob.utils.ItemBuilder;
import fr.maesia.mob.utils.MenuBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MenuSpawner {

    private final Inventory menu;
    private final String key;
    private final Player player;

    public static List<MenuSpawner> menus = new ArrayList<>();

    public MenuSpawner(Inventory menu,Player player ,String key){
        this.menu = menu;
        this.key = key;
        this.player = player;
        menus.add(this);
        openMenu(player, key);
    }

    private static void openMenu(Player player, String key){
        Inventory inventory = getMenu(player, key);
        if (inventory != null) player.openInventory(inventory);
    }

    private static Inventory getMenu(Player player, String key){
        for(MenuSpawner menu :menus){
            if (menu.player.equals(player) && menu.key.equalsIgnoreCase(key)) return menu.menu;
        }
        return null;
    }

    public static boolean checkInventory(Inventory inventory, String key){

        for (MenuSpawner menu :menus){
            if(menu.menu.equals(inventory) && menu.key.equalsIgnoreCase(key)) return true;

        }
        return false;
    }

    public static void deleteMenu(Inventory inventory){
        for (MenuSpawner menu : menus){
            if (menu.menu.equals(inventory)) {
                menus.remove(menu);
                break;
            }
        }
    }

    public static void deleteMenu(Player inventory){
        for (MenuSpawner menu : menus){
            if (menu.player.equals(inventory)) {
                menus.remove(menu);
                break;
            }
        }
    }

    //Menu Principal
    public static Inventory getSpawnerMenuMain(){
        return new MenuBuilder()
                .setRow(6)
                .setTitle(ChatColor.BOLD+"Menu Selection Spawner")
                .paternFrame(deco())
                .addItemStack(0, 26, getSpawner())
                .setItems(onLeft(0), 47)
                .setItems(onRight(0), 51)
                .getInv();
    }


    //Menu Edition
    public static Inventory getSpawnerMenuEdit(Spawner spawner){
        return new MenuBuilder()
                .setRow(5)
                .setTitle(ChatColor.BOLD+"Menu Edit Spawner")
                .paternAll(deco())
                .setItems(getItemSpawnerView(spawner), 0)
                .setItems(onExit(), 36)
                .setItems(onDelete(spawner), 44)
                .setItems(getMobs(spawner), 10)
                .setItems(onLimit(spawner), 12)
                .setItems(onDelay(spawner), 14)
                .setItems(onRadius(spawner), 16)
                .setItems(onSpawnMin(spawner), 29)
                .setItems(onSpawnMax(spawner), 31)
                .setItems(onTeleport(), 33)
                .getInv();
    }

    public static Inventory getMenuSpawnerTimerWave(Spawner spawner){
        return new MenuBuilder()
                .setRow(3)
                .setTitle(ChatColor.BOLD+"Menu edit Cooldown "+spawner.getName())
                .paternFrame(deco())
                .setItems(getItemSpawnerView(spawner), 0)
                .setItems(onExit(), 18)
                .addItemStack(
                        getClock(1),
                        getClock(5),
                        getClock(10),
                        getClock(15),
                        getClock(30),
                        getClock(60),
                        getClock(120)
                        )
                .getInv();
    }

    public static Inventory getMenuSelectMobToSpawner(Spawner spawner) throws MalformedURLException {
        return new MenuBuilder()
                .setRow(6)
                .setTitle(ChatColor.BOLD+"Selection Des Mobs Spawner "+ChatColor.GOLD+spawner.getName())
                .paternFrame(deco())
                .addItemStack(0, 26, getMobSkullList(spawner))
                .setItems(getItemSpawnerView(spawner), 0)
                .setItems(onExit(), 45)
                .setItems(onLeft(0), 47)
                .setItems(onRight(0), 51)
                .getInv();
    }


    public static Inventory getMenuSpawnerDelete(Spawner spawner){
        return new MenuBuilder()
                .setRow(1)
                .setTitle(ChatColor.BOLD+""+ChatColor.RED+spawner.getName()+"Suppression du spawner")
                .paternAll(deco())
                .setItems(onDelete(spawner), 3, 5)
                .setItems(onExit(), 0)
                .setItems(getItemSpawnerView(spawner), 4)
                .getInv();

    }

    //ItemStack
    public static ItemStack[] getMobSkullList(Spawner spawner) throws MalformedURLException {

        List<ItemStack> skullList = new ArrayList<>();
        if (Mobs.mobsList.isEmpty()) return  skullList.toArray(new ItemStack[0]);
        for(Mobs mobs: Mobs.mobsList){
            skullList.add(getSpawnerSkullMob(spawner, mobs));
        }

        return skullList.toArray(new ItemStack[0]);

    }

    public static ItemStack getSpawnerSkullMob(Spawner spawner, Mobs mobs) throws MalformedURLException {
        String has =ChatColor.GRAY +"Mob Actif "+ ChatColor.RED+"✘";
        if (spawner.getMobs().contains(mobs.getId())) has =ChatColor.GRAY +"Mob Actif "+ ChatColor.GREEN+"✔";
     return new ItemBuilder(Material.PLAYER_HEAD)
                .setName(ChatColor.GOLD+mobs.getName())
                .setLore(" ",
                        ChatColor.GRAY+" Dégats: "+ChatColor.RED+""+mobs.getDamage(),
                        ChatColor.GRAY+" Point de vie: "+ChatColor.GREEN+""+mobs.getHealth(),
                        ChatColor.GRAY+" Speed: "+ChatColor.GOLD+""+mobs.getSpeed(),
                        ChatColor.GRAY+" World spawn: "+ChatColor.YELLOW+""+mobs.getWorldspawn(),
                        ChatColor.GRAY+" Biome: "+ChatColor.DARK_GREEN+""+mobs.getBiomespawn(),
                        " ",
                        has
                        )
             .setPersistentData(MaesiaMob.getInstance(), "ID", mobs.getId().toString())
             .setPlayerSkullUrl(Objects.requireNonNull(EntityHead.getEntity(mobs.getEntityType())).getTexture())
             .getItemStack();
    }
    private static ItemStack getMobs(Spawner spawner){
        return new ItemBuilder(Material.SKELETON_SKULL)
                .setName(ChatColor.GOLD+ "Mobs liée au spawner")
                .setLore(" ",
                        ChatColor.GRAY+"List des mobs",
                        ChatColor.YELLOW+""+spawner.getNameMob(),
                        " ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour editer les mobs du spawner.")
                .getItemStack();
    }


    private static ItemStack getClock(int i){
        return new ItemBuilder(Material.CLOCK)
                .setName(ChatColor.GOLD+ Spawner.getDelayTemps(1000L* 60*i))
                .setPersistentData(MaesiaMob.getInstance(), "TimerWave", 1000L* 60*i)
                .setLore(" ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier le délais")
                .getItemStack();
    }

    public static ItemStack deco(){
        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ")
                .getItemStack();
    }

    public static ItemStack onExit(){
        return new ItemBuilder(Material.OAK_DOOR)
                .setName(ChatColor.GOLD+"Revenir au menu précédent")
                .getItemStack();
    }

    public static ItemStack onDelete(Spawner spawner){
        return new ItemBuilder(Material.BARRIER)
                .setName(ChatColor.RED+"Supprimer le mob spawner "+ChatColor.YELLOW+spawner.getName())
                .getItemStack();
    }

    public static ItemStack onLimit(Spawner spawner){
        return new ItemBuilder(Material.PAPER)
                .setName(ChatColor.GOLD+"Limite de mob générer")
                .setLore(" ",
                        ChatColor.GRAY+"Actuels: "+ChatColor.YELLOW+spawner.getLimit(),
                        ChatColor.GRAY+"Mobs Spawner Actuellement: "+ChatColor.YELLOW+spawner.getMobsSpawn().size(),
                        ChatColor.GRAY+"Mobs générer par ce mobs spawner: "+ChatColor.YELLOW+spawner.getCountSpawn(),
                        " ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour éditer la limite"
                )
                .getItemStack();
    }


    public static ItemStack onDelay(Spawner spawner){
        return  new ItemBuilder(Material.CLOCK)
                .setName(ChatColor.GOLD+"Le temps entre chaque vague")
                .setLore(" ",
                        ChatColor.GRAY+"Actuels: "+ChatColor.YELLOW+ Spawner.getDelayTemps(spawner.getDelay()),
                        " ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour éditer le Temps,",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"entre chaque vague de spawn"
                )
                .getItemStack();
    }

    public static ItemStack onRadius(Spawner spawner){
        return new ItemBuilder(Material.RECOVERY_COMPASS)
                .setName(ChatColor.GOLD+"Rayon de spawn")
                .setLore(" ",
                        ChatColor.GRAY+"Actuels: "+ChatColor.YELLOW+spawner.getRadius(),
                        " ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour éditer le Rayon de spawn"
                )
                .getItemStack();
    }

    public static ItemStack onSpawnMin(Spawner spawner){
        return new ItemBuilder(Material.EGG)
                .setName(ChatColor.GOLD+"Nombre de mob qui spawn au minimun")
                .setLore(" ",
                        ChatColor.GRAY+"Actuels: "+ChatColor.YELLOW+spawner.getSpawnMin(),
                        " ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour éditer le nombre,",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"de spawn au minimun de mob"
                )
                .getItemStack();
    }

    public static ItemStack onSpawnMax(Spawner spawner){
        return new ItemBuilder(Material.BLAZE_POWDER)
                .setName(ChatColor.GOLD+"Nombre de mob qui spawn au maximun")
                .setLore(" ",
                        ChatColor.GRAY+"Actuels: "+ChatColor.YELLOW+spawner.getSpawnMax(),
                        " ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour éditer le nombre,",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"de spawn au maximun de mob"
                )
                .getItemStack();
    }


    public static ItemStack onTeleport(){
        return new ItemBuilder(Material.ENDER_PEARL)
                .setName(ChatColor.GOLD+"Téléportation au spawner")
                .setLore(" ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour te téléporter au spawner"
                        )
                .getItemStack();
    }

    public static ItemStack[] getSpawner(){
        List<ItemStack> spawnerList =  new ArrayList<>();

        if(Spawner.getSpawnerList().isEmpty())  return spawnerList.toArray(new ItemStack[0]);

        for(Spawner spawner : Spawner.getSpawnerList()){
            spawnerList.add(getItemSpawnerView(spawner));
        }
        return spawnerList.toArray(new ItemStack[0]);
    }

    private static ItemStack getItemSpawnerView(Spawner spawner){
        return new ItemBuilder(Material.SPAWNER)
                .setName(ChatColor.GOLD+ spawner.getName())
                .setLore(" ",
                        ChatColor.GRAY+"Coordonée "+ChatColor.YELLOW+" X "+spawner.getLocation().getBlockX()+" Y "+spawner.getLocation().getBlockY()+" Z "+spawner.getLocation().getBlockZ(),
                        ChatColor.GRAY+"Monde "+ChatColor.YELLOW+ Objects.requireNonNull(spawner.getLocation().getWorld()).getName(),
                        ChatColor.GRAY+"Rayon de spawn "+ChatColor.YELLOW+""+spawner.getRadius(),
                        ChatColor.GRAY+"Limite de spawn "+ChatColor.YELLOW+""+spawner.getLimit(),
                        ChatColor.GRAY+"List des mobs:"
                )
                .addLore(ChatColor.GRAY+""+spawner.getNameMob())
                .setPersistentData(MaesiaMob.getInstance(), "ID", spawner.getId().toString())
                .getItemStack();
    }
    public static ItemStack onLeft(int page){

        int result = Math.max(page-1, 0);
        String pageString = ChatColor.GREEN +"Page "+result;
        if (result <1) pageString = ChatColor.RED+"Première page";
        if (page-1 == 0) pageString = ChatColor.GREEN +"Page "+result;
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName(pageString)
                .setLore(" ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour passer a la page précédente.")
                .setPersistentData(MaesiaMob.getInstance(), "Page", result)
                .setPlayerSkull("MHF_ArrowLeft")
                .getItemStack();
    }
    public static ItemStack onRight(int page){
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName(ChatColor.GREEN+"Page "+(page+1))
                .setLore(" ",
                        ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour passer a la page suivante.")
                .setPersistentData(MaesiaMob.getInstance(), "Page", page+1)
                .setPlayerSkull("MHF_ArrowRight")
                .getItemStack();
    }
}

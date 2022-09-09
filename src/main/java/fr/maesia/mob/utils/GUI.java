package fr.maesia.mob.utils;


import fr.maesia.mob.MaesiaMobFiles.Messages.Messages;
import fr.maesia.mob.listener.InteractMenu;
import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.Rang;
import fr.maesia.mob.mob.rangs.RangsLoots;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class GUI {

    private final static int maxpage = 45;
    private final static int onMaxPageDeathSpawn = 36;

    private final static HashMap<Player, Integer> page = new HashMap<>();

    public static HashMap<Player, Integer> getPage() {
        return page;
    }



    //Update des menus édition & bestaires
    public static void onUpdate(Player p, Inventory inv, boolean edit) throws MalformedURLException {
        if (Mobs.mobsList.isEmpty()) {
            p.closeInventory();
            return;
        }

        //calcul de la page

        int started = ((maxpage * page.get(p)) - maxpage);
        int finish = maxpage * page.get(p);

        ItemStack it = inv.getItem(0);
        //check du rank
        if (it == null) return;
        String name = Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", "");
        String rank = null;

        for(Mobs mobs : Mobs.mobsList){
            if (name.equalsIgnoreCase(mobs.getName())){
                rank = mobs.getRank().name();
            }
        }
        if (rank == null) return;
        int countmob = 0;

        for(Mobs mobs : Mobs.mobsList){
            if (mobs.getRank().name().equalsIgnoreCase(rank)){
                countmob++;
            }
        }

        if (countmob < started) return;
        //clear de l'ancienne page

        inv.clear();

        //rendu
        for (int i = maxpage; i <maxpage+9; i++){
            inv.setItem(i, onGlass());
        }

        inv.setItem(maxpage, onLeft(page.get(p)));
        inv.setItem(maxpage+4, onExit());
        inv.setItem(maxpage+8, onRight(page.get(p)));


        int count = 0;
        for(Mobs mobs : Mobs.mobsList){
            if (count == finish) return;
            if(count >= started && count < finish){
                if (mobs.getRank().name().equalsIgnoreCase(rank)){
                    inv.addItem(onSkull(mobs, edit));
                    count++;
                }

            }else{
                count++;
            }


        }

    }
    //Update du menu de selection DeathSpawn
    public static void onUpdateDeathGui(Player p, Inventory inv) throws MalformedURLException {
        if (Mobs.mobsList.isEmpty()) {
            p.closeInventory();
            return;
        }
        //calcul de la page

        int started = ((onMaxPageDeathSpawn * page.get(p)) - onMaxPageDeathSpawn);
        int finish = onMaxPageDeathSpawn * page.get(p);


        ItemStack it = inv.getItem(0);
        //check du rank
        if (it == null) return;
        String name = Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", "");
        Mobs target = Mobs.getMobs(name);



        if (Mobs.mobsList.size() < started) return;
        //clear de l'ancienne page

        inv.clear();

        //rendu
        for (int i = 0; i <9; i++){
            inv.setItem(i, onglassStuff());
        }
        for (int i = maxpage; i <maxpage+9; i++){
            inv.setItem(i, onglassStuff());
        }
        assert target != null;
        inv.setItem(0, onSkull(target, false));
        inv.setItem(maxpage, onLeft(page.get(p)));
        inv.setItem(maxpage+4, onExit());
        inv.setItem(maxpage+8, onRight(page.get(p)));


        int count = 0;
        for(Mobs mobs : Mobs.mobsList){
            if (count == finish) return;
            if(count >= started && count < finish){
                inv.addItem(onSkull(mobs, false));
            }
            count++;
        }
    }
    //Menu joueur
    public Inventory onGuiBestiare(){
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix());
        inv.setItem(10, onRank("F"));
        inv.setItem(12, onRank("E"));
        inv.setItem(14, onRank("D"));
        inv.setItem(16, onRank("C"));
        inv.setItem(29, onRank("B"));
        inv.setItem(31, onRank("A"));
        inv.setItem(33, onRank("S"));

        return inv;
    }

    //Menu selection du rangs du mob a éditer
    public Inventory onGuiEdit(){
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + ChatColor.GOLD +"édition");

            inv.setItem(10, onRank("F"));
            inv.setItem(12, onRank("E"));
            inv.setItem(14, onRank("D"));
            inv.setItem(16, onRank("C"));
            inv.setItem(29, onRank("B"));
            inv.setItem(31, onRank("A"));
            inv.setItem(33, onRank("S"));

        return inv;
    }

    //Menu confirmation de la supression du mob
    public Inventory onDeleteMobsConfirm(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.RED+"Suppression");

        inv.setItem(1, onRemove());
        inv.setItem(2, onSkull(mobs, false));
        inv.setItem(3, onAnnulle());

        return inv;
    }

    private static ItemStack onRemove(){
        ItemStack it = new ItemStack(Material.RED_CONCRETE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.RED+"Supression");
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onAnnulle(){
        ItemStack it = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+"Anuller");
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onVide(){
        return new ItemStack(Material.AIR);
    }

    private static ItemStack onAdd(int count){
       ItemStack it = new  ItemStack(Material.LIME_STAINED_GLASS_PANE);
       ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+"Ajouter "+ChatColor.GOLD +count);
        it.setItemMeta(itm);
        it.setAmount(count);
        return it;

    }
    private static ItemStack onRemove(int count){
        ItemStack it = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.RED+"Enlever "+ChatColor.GOLD +count);
        it.setItemMeta(itm);
        it.setAmount(count);
        return it ;
    }

    //Meny Selection de l'édition de la hauteur min ou max
    public Inventory onGuiEditMobsSpawnHeightMaxOrMin(Mobs mobs, boolean max) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*5, Messages.getPrefix() + "Hauteur de Spawn");

        for (int i = 0; i  < inv.getSize(); i++ ){
            inv.setItem(i, onglassStuff());
        }
        for (int i = 10; i  < 17; i++ ){
            inv.setItem(i, onVide());
        }

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(8, onHeight(mobs,false));

        inv.setItem(19, onRemove(30));
        inv.setItem(20, onRemove(15));
        inv.setItem(21, onRemove(1));
        if (max){
            inv.setItem(22, onHeightmax(mobs));
        }else {
            inv.setItem(22, onHeightmin(mobs));
        }
        inv.setItem(23, onAdd(1));
        inv.setItem(24, onAdd(15));
        inv.setItem(25, onAdd(30));

        for (int i = 28; i  < 35; i++ ){
            inv.setItem(i, onVide());
        }

        inv.setItem(36, onExit());
        return inv;
    }

    //Meny Selection de l'édition de la hauteur min ou max
    public Inventory onGuiEditMobsSpawnHeight(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*3, Messages.getPrefix() + "Hauteur de Spawn");

        for (int i = 0; i  < inv.getSize(); i++ ){
            inv.setItem(i, onglassStuff());
        }

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(8, onHeight(mobs,false));

        inv.setItem(11, onHeightmin(mobs));
        inv.setItem(15, onHeightmax(mobs));

        inv.setItem(18, onExit());
        return inv;
    }

    //Meny Selection de l'édition de la hauteur min ou max
    public Inventory onGuiEditMobsSpawnWorld(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix()+ net.md_5.bungee.api.ChatColor.GRAY + "Selection des Mondes");

        inv.setItem(0, onSkull(mobs, false));

        inv.setItem(3, onWorldSelectAll(mobs));
        inv.setItem(5, onBiome("Désactiver", Material.BARRIER));

        inv.setItem(8, onWorld(mobs,false));
        inv.setItem(45, onExit());
        int i = 9;
        for (World world: Bukkit.getWorlds()){
           if ( world.getEnvironment().name().equalsIgnoreCase(String.valueOf(World.Environment.NORMAL))){
               inv.setItem(i, onWorldHead(world.getName()));
           }else
           if ( world.getEnvironment().name().equalsIgnoreCase(String.valueOf(World.Environment.NETHER))){
               inv.setItem(i, onNetherHead(world.getName()));
           }else

           if ( world.getEnvironment().name().equalsIgnoreCase(String.valueOf(World.Environment.THE_END))) {
               inv.setItem(i, onEndHead(world.getName()));
           }
           i++;
           if (i == 45) return inv;
        }



        return inv;
    }


    public static ItemStack onWorldSelectAll(Mobs mobs){
        ItemStack it = new ItemStack(Material.NETHER_STAR);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+ InteractMenu.getNextSelectWorldAll(mobs.getWorldspawn()));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"Click pour selectionner le mode suivant");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }


    private static ItemStack onWorldHead(String worldname){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        PlayerProfile playerProfile =  Bukkit.createPlayerProfile("BlockminersTV");
        itm.setOwnerProfile(playerProfile);
        itm.setDisplayName(worldname);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"World type: "+ChatColor.YELLOW+"OverWolrd");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onNetherHead(String worldname){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        PlayerProfile playerProfile =  Bukkit.createPlayerProfile("haohanklliu");
        itm.setOwnerProfile(playerProfile);
        itm.setDisplayName(worldname);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"World type: "+ChatColor.YELLOW+"Nether");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onEndHead(String worldname){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        PlayerProfile playerProfile =  Bukkit.createPlayerProfile("gumballuke");
        itm.setOwnerProfile(playerProfile);
        itm.setDisplayName(worldname);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"World type: "+ChatColor.YELLOW+"End");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Menu édition mobs
    public Inventory onGuiEditMobs(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + mobs.getName());

        inv.setItem(0, onSkull(mobs, false));

        inv.setItem(11, onStat(mobs, true));
        inv.setItem(13, onLootStuff(true));
        inv.setItem(15, onSpawn(true));

        inv.setItem(30, onEffect(true));
        inv.setItem(32, onAvenir());

        inv.setItem(45, onExit());
        inv.setItem(53, onDelete(mobs));
        return inv;
    }

    public Inventory onGuiEditMobsStats(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + "Stats");

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(8, onStat(mobs,false));


        inv.setItem(19, onPointVie(mobs));
        inv.setItem(21, onDamage(mobs));
        inv.setItem(23, onSpeed(mobs));
        inv.setItem(25, onAttackSpeed(mobs));

        inv.setItem(45, onExit());
        return inv;
    }

    public Inventory onGuiEditMobsLootStuff(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + "Loots & Stuff");

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(8, onLootStuff(false));

        inv.setItem(20, onStuff());
        inv.setItem(22, onMonture(mobs,true));
        inv.setItem(24, onLoot());

        inv.setItem(45, onExit());
        return inv;
    }

    public  Inventory onGuiEditMobsMonture(Player p, Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + "Monture");

        for(int i = 0; i< 9 ; i++){
            inv.setItem(i, onglassStuff());
        }
        for(int i = 45; i< inv.getSize() ; i++){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(3, onMonture(mobs,false));
        inv.setItem(5, onBiome("Désactiver", Material.BARRIER));


        page.put(p, 1);

        inv.setItem(45,onLeft(page.get(p)));
        inv.setItem(49, onExit());
        inv.setItem(53,onRight(page.get(p)));

        int turn = 0;
        for(Mobs mobs1 : Mobs.mobsList){
            if (mobs1 != mobs){
                inv.addItem(onSkull(mobs1, false));
                turn++;
                if (turn == onMaxPageDeathSpawn) return inv;
            }

        }
        return inv;
    }

    public static void onUpdateMonture(Player p, Inventory inv, Mobs mobs) throws MalformedURLException {
        inv.clear();
        for(int i = 0; i< 9 ; i++){
            inv.setItem(i, onglassStuff());
        }
        for(int i = 45; i< inv.getSize(); i++){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(3, onMonture(mobs,false));
        inv.setItem(5, onBiome("Désactiver", Material.BARRIER));

        inv.setItem(45,onLeft(page.get(p)));
        inv.setItem(49, onExit());
        inv.setItem(53,onRight(page.get(p)));

        int started = ((onMaxPageDeathSpawn * page.get(p)) - onMaxPageDeathSpawn);
        int finish = onMaxPageDeathSpawn * page.get(p);

        int count = 0;
        for(Mobs mobs1 : Mobs.mobsList){
            if (count == finish) return;
            if(count >= started && count < finish && mobs1 != mobs){
                inv.addItem(onSkull(mobs1, false));
            }
            count++;
        }


    }

    public Inventory onGuiEditMobsSpawn(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + "Spawn");

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(8, onSpawn(false));

        inv.setItem(19, onRang(mobs));
        inv.setItem(21, onBiome(mobs));
        inv.setItem(23, onHeight(mobs, true));
        inv.setItem(25, onWorld(mobs, true));

        inv.setItem(45, onExit());
        return inv;
    }

    private static ItemStack onWorld(Mobs mobs, boolean lores){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        PlayerProfile playerProfile =  Bukkit.createPlayerProfile("BlockminersTV");
        itm.setOwnerProfile(playerProfile);
        itm.setDisplayName(ChatColor.AQUA+"Le(s) Monde(s)");
        if (lores){
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY+"World: "+ChatColor.YELLOW+mobs.getWorldspawn());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
            itm.setLore(lore);
        }
        it.setItemMeta(itm);
        return it;
    }


    public Inventory onGuiEditMobsEffect(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9*6, Messages.getPrefix() + "Effect");

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(8, onEffect(false));

        inv.setItem(20, onEffectMob());
        inv.setItem(22, onDamageEffectPotion());
        inv.setItem(24, onDeathEffect());

        inv.setItem(45, onExit());
        return inv;
    }

    private static ItemStack onAvenir(){
        ItemStack it = new ItemStack(Material.BEDROCK);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.DARK_GRAY+"Avenir");
        it.setItemMeta(itm);
        return it;
    }


    private static ItemStack onHeight(Mobs mobs, boolean lores){
        ItemStack it = new ItemStack(Material.LADDER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.DARK_PURPLE+"Hauteur du monde");
        if (lores){
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY+"Hauteur max: "+ChatColor.YELLOW+mobs.getHeight_max());
            lore.add(ChatColor.GRAY+"Hauteur min: "+ChatColor.YELLOW+mobs.getHeight_min());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
            itm.setLore(lore);
        }
        it.setItemMeta(itm);
        return it;
    }
    public static ItemStack onHeightmax(Mobs mobs){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        PlayerProfile playerProfile =  Bukkit.createPlayerProfile("MHF_ArrowUp");

        itm.setOwnerProfile(playerProfile);
        itm.setDisplayName(ChatColor.LIGHT_PURPLE+"Hauteur maximun");

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"Hauteur max: "+ChatColor.YELLOW+mobs.getHeight_max());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
        itm.setLore(lore);

        it.setItemMeta(itm);
        return it;
    }

    public static ItemStack onHeightmin(Mobs mobs){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        PlayerProfile playerProfile =  Bukkit.createPlayerProfile("MHF_ArrowDown");


        itm.setOwnerProfile(playerProfile);
        itm.setDisplayName(ChatColor.LIGHT_PURPLE+"Hauteur minimun");

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"Hauteur min: "+ChatColor.YELLOW+mobs.getHeight_min());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
        itm.setLore(lore);

        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onEffect(Boolean lores){
        ItemStack it = new ItemStack(Material.POTION);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.DARK_PURPLE+"Effect de mobs");
        if (lores){
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
            itm.setLore(lore);
        }
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onSpawn(Boolean spawn){
        ItemStack it = new ItemStack(Material.EGG);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Spawn");
        if (spawn){
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
            itm.setLore(lore);
        }
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onLootStuff(Boolean ls){
        ItemStack it = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GRAY+"Loots & Stuff");
        if (ls){
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
            itm.setLore(lore);
        }
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onStat(Mobs mobs, Boolean lores){
        ItemStack it = new ItemStack(Material.PAPER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+"Statistiques");
        if (lores){
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GRAY+"Dégars: "+ChatColor.YELLOW+mobs.getDamage());
            lore.add(ChatColor.GRAY+ "Point de vie: "+ChatColor.YELLOW+mobs.getHealth());
            lore.add(ChatColor.GRAY+ "Speed: "+ChatColor.YELLOW+mobs.getSpeed());
            lore.add(ChatColor.GRAY+ "Vitesse d'attack: "+ChatColor.YELLOW+mobs.getAttackspeed());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
            itm.setLore(lore);
        }
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onLoot(){
        ItemStack it = new ItemStack(Material.CHEST);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Loots");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onStuff(){
        ItemStack it = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.AQUA+"Stuff");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    public static ItemStack onMonture(Mobs mobs, boolean lores){
        ItemStack it = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.LIGHT_PURPLE+"Monture");
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (mobs.getPassager() !=null){

            lore.add(ChatColor.GRAY+"Montures actuels: "+ChatColor.YELLOW+ Objects.requireNonNull(Mobs.getMobs(mobs.getPassager())).getName());
        }else {
            lore.add(ChatColor.GRAY+"Montures actuels: "+ChatColor.YELLOW+ "Aucun");
        }
        if(lores){
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour accedez au menu.");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onDamage(Mobs mobs){
        ItemStack it = new ItemStack(Material.IRON_SWORD);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.RED+"Dégats");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Dégats actuels: "+ ChatColor.YELLOW+mobs.getDamage());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier les dégats.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onSpeed(Mobs mobs){
        ItemStack it = new ItemStack(Material.IRON_BOOTS);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Speed");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Vitesse actuels: "+ ChatColor.YELLOW+mobs.getSpeed());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier la vitesse de déplacement.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onAttackSpeed(Mobs mobs){
        ItemStack it = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.AQUA+"Attack Speed");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Vitesse d'attack actuels: "+ ChatColor.YELLOW+mobs.getAttackspeed());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier la vitesse d'attack.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onDeathEffect(){
        ItemStack it = new ItemStack(Material.SKELETON_SKULL);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.LIGHT_PURPLE+"Effets de mort");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier l'effets de mort.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onEffectMob(){
        ItemStack it = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Effet de potion");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier l'effet de potion.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onBiome(Mobs mobs){
        ItemStack it = new ItemStack(Material.OAK_SAPLING);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.DARK_GREEN+"Biome");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Biome: "+ChatColor.YELLOW +mobs.getBiomespawn());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier le biome d'apparition du mobs.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onPointVie(Mobs mobs){
        ItemStack it = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+"Point de vie");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Point de vie actuels: "+ ChatColor.YELLOW+mobs.getHealth());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier les points de vie du mob.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onDamageEffectPotion(){
        ItemStack it = new ItemStack(Material.SPLASH_POTION);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Effet de potion appliquer lors des damages");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier l'effet de potion.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    /*/
    private static ItemStack onAge() {
        ItemStack it = new ItemStack(Material.TURTLE_EGG);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Age");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier l'age du mob.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

     */

    private static ItemStack onRang(Mobs mobs) {
        ItemStack it = new ItemStack(Material.SPAWNER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Rang");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Rang actuel: "+ ChatColor.YELLOW+mobs.getRank());
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier le rang du mob.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onDelete(Mobs mobs){
        ItemStack it = new ItemStack(Material.BARRIER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.RED+"Suppression du mob");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Suppression du mob "+mobs.getName());
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    /////////////////////////
    /////////////////////////
    /////////////////////////

    //Menu des loots

    public Inventory onMenuLootMobs(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Loots");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(18, onExit());
        inv.setItem( 22, onLootAdd());
        // inv.setItem( 23, onLootDelete());
        for(int i = 10; i < 17; i++  ){
            inv.setItem(i, new ItemStack(Material.AIR));
        }
        if (mobs.getLoots().isEmpty()) return inv;

        int slot =10;
        for (Map.Entry<ItemStack, RangsLoots> item : mobs.getLoots().entrySet()){
            inv.setItem(slot, onLootItem(item.getKey(), item.getValue()));
            slot++;
        }
        return inv;
    }

    private static ItemStack onLootItem(ItemStack itemStack, RangsLoots rangsLoots){
        ItemStack it = itemStack.clone();
        ItemMeta itm = itemStack.getItemMeta();
        assert itm != null;
        List<String> lore;
        if (itm.hasLore()){
           lore = itm.getLore();
        }else {
            lore = new ArrayList<>();
        }
        assert lore != null;
        lore.add("");
        lore.add(ChatColor.GRAY+"Loots " +rangsLoots.getColor()+rangsLoots.name()+": "+ChatColor.YELLOW+rangsLoots.getProba()+"%");
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC +"Click pour éditer le loots");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onLootAdd(){
        ItemStack it = new ItemStack(Material.HOPPER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+"Ajouter un items");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Déposer l'item dans le hooper pour l'ajouter.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }




    public Inventory onMenuLootMobsEdits(Mobs mobs, ItemStack it) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Loots édits");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(18, onExit());
        inv.setItem( 22, onLootDelete());
        inv.setItem(10, it);

        if (mobs.getLoots().isEmpty()) return inv;

        int slot =11;
        for (RangsLoots rangsLoots: RangsLoots.values()){
            inv.setItem(slot, onLootEditsRangs(rangsLoots));
            slot++;
        }
        return inv;
    }

    private static ItemStack onLootDelete(){
        ItemStack it = new ItemStack(Material.BARRIER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.RED+"Supprimer un items");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour supprimer le loots.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private static ItemStack onLootEditsRangs(RangsLoots rangsLoots){
        ItemStack it = new ItemStack(Material.PAPER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(rangsLoots.getColor()+rangsLoots.name());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"Chance de drops:"+ ChatColor.YELLOW+""+rangsLoots.getProba()+"%");
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour selection sont taux de drops.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Menu supression du loots
    public Inventory onMenuLootMobsRemove(Mobs mobs, ItemStack it) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, Messages.getPrefix() + ChatColor.DARK_GRAY + "Loots Remove");
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(1, onLootRemoves());
        inv.setItem(2, it);
        inv.setItem(3, onLootRemoves());
        inv.setItem(4, onExit());
        return inv;
    }
    private static ItemStack onLootRemoves(){
        ItemStack it = new ItemStack(Material.RED_CONCRETE);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.RED+"Suppresion du loots");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour confirmer la suppression.");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    /////////////////////////
    /////////////////////////
    /////////////////////////

    //Menu édition deathEffect

    public Inventory onDeathMenuEdit(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.GRAY + "DeathMenu");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onDeathSpawn(mobs));
        inv.setItem(13, onDeathExplosion(mobs));
        inv.setItem(15, onDeathPotion(mobs));
        inv.setItem(18, onExit());
        return inv;
    }



    //item edition death spawn
    private static ItemStack onDeathSpawn(Mobs mobs){
        ItemStack it = new ItemStack(Material.EGG);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.DARK_PURPLE+"Spawn de mobs");
        List<String> lore = new ArrayList<>();
        lore.add("");
        if(mobs.getDeathEffect().getDeathSpawn().isActif()){
            lore.add(ChatColor.GRAY+"Mobs "+ChatColor.YELLOW+mobs.getDeathEffect().getDeathSpawn().getMobs().getName());
            lore.add(ChatColor.GRAY+"Quantiter "+ChatColor.YELLOW+mobs.getDeathEffect().getDeathSpawn().getCount());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir un nouveau mobs custom");
        }else {
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir un mobs custom");
            if (mobs.getDeathEffect().getDeathExplotion().isActif() || mobs.getDeathEffect().getDeathPotionEffect().isActif()){
                lore.add(ChatColor.RED+""+ChatColor.ITALIC+"En activant cette effet vous désactiver les autres");
            }
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //item edition death Explosion
    private static ItemStack onDeathExplosion(Mobs mobs){
        ItemStack it = new ItemStack(Material.TNT);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Explosion lors de la mort");
        List<String> lore = new ArrayList<>();
        if(mobs.getDeathEffect().getDeathExplotion().isActif()){
            lore.add("");
            lore.add(ChatColor.GRAY+"Taille de l'explosion: "+ChatColor.YELLOW +mobs.getDeathEffect().getDeathExplotion().getPower());
            if (mobs.getDeathEffect().getDeathExplotion().isFire()){
                lore.add(ChatColor.GRAY+"Propagation du feu "+ChatColor.GREEN+"Oui");
            }else {
                lore.add(ChatColor.GRAY+"Propagation du feu "+ChatColor.RED+"Non");
            }
           if (mobs.getDeathEffect().getDeathExplotion().isDestroy()){
               lore.add(ChatColor.GRAY+"Destruction du terrain "+ChatColor.GREEN+"Oui");
           }else {
               lore.add(ChatColor.GRAY+"Destruction du terrain "+ChatColor.RED+"Non");
           }
        }else {
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir la taille de l'explostion");
            if (mobs.getDeathEffect().getDeathSpawn().isActif() || mobs.getDeathEffect().getDeathPotionEffect().isActif()){
                lore.add(ChatColor.RED+""+ChatColor.ITALIC+"En activant cette effet vous désactiver les autres");
            }
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item edition death Potion
    private static ItemStack onDeathPotion(Mobs mobs){
        ItemStack it = new ItemStack(Material.SPLASH_POTION);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.LIGHT_PURPLE+"Applique un effet de potion");
        List<String> lore = new ArrayList<>();
        if(mobs.getDeathEffect().getDeathPotionEffect().isActif()){
            lore.add("");
            lore.add(ChatColor.GRAY+"Effets de potions: "+ChatColor.YELLOW +mobs.getDeathEffect().getDeathPotionEffect().getPotionEffect().getName());
            lore.add(ChatColor.GRAY+"Niveau de potion: "+ChatColor.YELLOW +mobs.getDeathEffect().getDeathPotionEffect().getPower());
            lore.add(ChatColor.GRAY+"Durer: "+ChatColor.YELLOW +mobs.getDeathEffect().getDeathPotionEffect().getDuration()+" Sec");
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour modifier l'effets de potion");
        }else {
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir un effets de potion");
            if (mobs.getDeathEffect().getDeathSpawn().isActif() || mobs.getDeathEffect().getDeathExplotion().isActif()){
                lore.add(ChatColor.RED+""+ChatColor.ITALIC+"En activant cette effet vous désactiver les autres");
            }
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Gui onDeath Spawn Custom Edit

    public Inventory onDeathMenuSpawnEdit(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY+ "DeathMenu Spawn");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(12, onDeathMobsEgg(mobs));
        inv.setItem(14, onDeathMobsCount(mobs));
        inv.setItem(18, onExit());
        return inv;
    }
    //item edition death spawn
    private static ItemStack onDeathMobsEgg(Mobs mobs) throws MalformedURLException {
        List<String> lore = new ArrayList<>();
        ItemStack it;
        if(mobs.getDeathEffect().getDeathSpawn().isActif()){
            it = onSkull(mobs.getDeathEffect().getDeathSpawn().getMobs(), false);
            ItemMeta itm = it.getItemMeta();
            assert itm != null;
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir un nouveau mob.");
            itm.setLore(lore);
            it.setItemMeta(itm);
        }else {
            it = new ItemStack(Material.SKELETON_SKULL);
            ItemMeta itm = it.getItemMeta();
            assert itm != null;
            itm.setDisplayName(ChatColor.GOLD+"Selectioner un mobs");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir un mob.");
            itm.setLore(lore);
            it.setItemMeta(itm);
        }
        return it;
    }
    //Item edition combien de mobs?
    private static ItemStack onDeathMobsCount(Mobs mobs){
        ItemStack it =  new ItemStack(Material.EGG);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        List<String> lore = new ArrayList<>();
        itm.setDisplayName(ChatColor.GOLD+"Combien de mobs vont spawn ?");
        if(mobs.getDeathEffect().getDeathSpawn().isActif()){
            lore.add("");
            lore.add(ChatColor.GRAY+"Quantité acutels: "+ChatColor.YELLOW+mobs.getDeathEffect().getDeathSpawn().getCount());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour définir une quantité.");
        }else {
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Selectionner un mobs avants de définirs une quantité.");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Gui Sous-Sous-Menu spawn mob
    public Inventory onDeathMenuSpawnEditSelectMob(Player p, Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *6, Messages.getPrefix() + ChatColor.DARK_GRAY + "Selection");
        for (int i = 0; i < 9; i++ ){
            inv.setItem(i, onglassStuff());
        }
        for (int i = 45; i < 9*6; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        page.put(p, 1);
        inv.setItem(45, onLeft(page.get(p)));
        inv.setItem(53, onRight(page.get(p)));
        inv.setItem(49, onExit());
        int count = 0;
        for(Mobs m : Mobs.mobsList){
            if (m != mobs){
                if (count > onMaxPageDeathSpawn){
                    return inv;
                }
                inv.addItem(onSkull(m, false));
                count++;
            }

        }
        return inv;
    }
    //Gui Sous-Sous-Menu spawn quantité
    public Inventory onDeathMenuSpawnEditCountMob(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Quantité");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onCountEgg(1));
        inv.setItem(12, onCountEgg(2));
        inv.setItem(13, onCountEgg(3));
        inv.setItem(14, onCountEgg(4));
        inv.setItem(15, onCountEgg(5));
        inv.setItem(18, onExit());

        return inv;
    }

    public static ItemStack onCountEgg(int count){
        ItemStack it = new ItemStack(Material.EGG);
        it.setAmount(count);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Quantité "+count);
        it.setItemMeta(itm);
        return it;
    }
    //Gui édition DeathExplosion

    public Inventory onDeathMenuExplosion(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Explosion edit");
            for (int i = 0; i < 27; i++ ){
                inv.setItem(i, onglassStuff());
            }
            inv.setItem(0, onSkull(mobs, false));
            inv.setItem(11, onItemEffectPower(mobs));
            inv.setItem(13, onItemEffectFire(mobs));
            inv.setItem(15, onItemEffectDestroy(mobs));
            inv.setItem(18, onExit());
        return inv;
        }
        //Fire item
        public static ItemStack onItemEffectFire(Mobs mobs){
            ItemStack it = new ItemStack(Material.FLINT_AND_STEEL);
            ItemMeta itm =  it.getItemMeta();
            assert itm != null;
            List<String> lore = new ArrayList<>();
            if (mobs.getDeathEffect().getDeathExplotion().isActif()){
                if (mobs.getDeathEffect().getDeathExplotion().isFire()){
                    itm.setDisplayName(ChatColor.DARK_GRAY+"Brule le terrain :"+ChatColor.GREEN+"Oui");
                }else {
                    itm.setDisplayName(ChatColor.DARK_GRAY+"Brule le terrain :"+ChatColor.RED+"Non");
                }
                lore.add("");
                lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour activer.");
                lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click droit pour désactiver.");
            }else {
                itm.setDisplayName(ChatColor.RED+"Désactiver");
            }

            itm.setLore(lore);
            it.setItemMeta(itm);
            return it;
        }
        //destroy item
    public static ItemStack onItemEffectDestroy(Mobs mobs){
        ItemStack it = new ItemStack(Material.CRACKED_STONE_BRICKS);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        List<String> lore = new ArrayList<>();
        if (mobs.getDeathEffect().getDeathExplotion().isActif()){
            if (mobs.getDeathEffect().getDeathExplotion().isDestroy()){
                itm.setDisplayName(ChatColor.DARK_GRAY+"Destruction du terrain :"+ChatColor.GREEN+"Oui");
            }else {
                itm.setDisplayName(ChatColor.DARK_GRAY+"Destruction du terrain :"+ChatColor.RED+"Non");
            }
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour activer.");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click droit pour désactiver.");
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
        }

        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Power item
    public static ItemStack onItemEffectPower(Mobs mobs){
        ItemStack it = new ItemStack(Material.TNT);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (mobs.getDeathEffect().getDeathExplotion().isActif()){
            itm.setDisplayName(ChatColor.GOLD+"Explosion lors de la mort :"+ChatColor.GREEN+"Oui");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour modifier la porte d'explosion.");
            lore.add(ChatColor.GRAY+"Porter actuel: "+ChatColor.YELLOW +mobs.getDeathEffect().getDeathExplotion().getPower());
        }else {
            itm.setDisplayName(ChatColor.GOLD+"Explosion lors de la mort :"+ChatColor.RED+"Non");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour activer.");
        }
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click droit pour désactiver.");
        lore.add("");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Sous Menu du Gui édition DeathExplosion
    public Inventory onDeathMenuPower(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Taille d'Explosion");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onSetPorwer(mobs, 1.0F));
        inv.setItem(12, onSetPorwer(mobs, 2.0F));
        inv.setItem(13, onSetPorwer(mobs, 3.0F));
        inv.setItem(14, onSetPorwer(mobs, 4.0F));
        inv.setItem(15, onSetPorwer(mobs, 5.0F));
        inv.setItem(18, onExit());
        return inv;
    }

    private ItemStack onSetPorwer(Mobs mobs, float power){
        ItemStack it = new ItemStack(Material.GUNPOWDER);
        ItemMeta itm =  it.getItemMeta();
        it.setAmount( (int)power);
        List<String> lore = new ArrayList<>();

        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Puissance d'explosion: "+(int) power);
        lore.add("");

        if (mobs.getDeathEffect().getDeathExplotion().isActif()){
            lore.add(ChatColor.GRAY+"Puissance d'explosion actuels: "+mobs.getDeathEffect().getDeathExplotion().getPower());
        }else {
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Click pour activer l'effets de mort d'explosion");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Gui édition DeathPotionEffect

    public Inventory onDeathMenuPotion(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "DeathPotion edit");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onDeathEffectItemPotion(mobs));
        inv.setItem(13, onItemPotionPorwerEffect(mobs));
        inv.setItem(15, onItemPotionTimeEffect(mobs));
        inv.setItem(18, onExit());
        return inv;
    }


    //Item Select Effect
    public static ItemStack onDeathEffectItemPotion(Mobs mobs){
        ItemStack it = new ItemStack(Material.POTION);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;
        lore.add("");
        if (mobs.getDeathEffect().getDeathPotionEffect().isActif()){
            itm.setDisplayName(ChatColor.GOLD+"Effect de Potion lors de la mort ? "+ChatColor.GREEN+"Oui");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer l'effets de potion");
            lore.add(ChatColor.GRAY+"Effects actuels: "+ChatColor.YELLOW +mobs.getDeathEffect().getDeathPotionEffect().getPotionEffect().getName());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click droit pour désactiver l'effets de potion");
        }else {
            itm.setDisplayName(ChatColor.GOLD+"Effect de Potion lors de la mort ? "+ChatColor.RED+"Non");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour activer l'effets de potion");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item power
    public static ItemStack onItemPotionPorwerEffect(Mobs mobs){
        ItemStack it = new ItemStack(Material.GUNPOWDER);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;

        lore.add("");

        if (mobs.getDeathEffect().getDeathPotionEffect().isActif()){
            itm.setDisplayName(ChatColor.DARK_GRAY+"Puissance de l'effet: "+ChatColor.YELLOW+mobs.getDeathEffect().getDeathPotionEffect().getPower());
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer puissance actuels");
            lore.add(ChatColor.GRAY+"Puissance de l'effet actuels: "+ChatColor.YELLOW+mobs.getDeathEffect().getDeathPotionEffect().getPower());
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Tu dois déjà selection effets de potions");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item Durer
    public static ItemStack onItemPotionTimeEffect(Mobs mobs){
        ItemStack it = new ItemStack(Material.CLOCK);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;

        lore.add("");

        if (mobs.getDeathEffect().getDeathPotionEffect().isActif()){
            int min = 0;
            int sec;
            int time = mobs.getDeathEffect().getDeathPotionEffect().getDuration();
            while (time >=60){
                min++;
                time -= 60;
            }
            sec =time;
            itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: "+ChatColor.YELLOW+min+":"+sec);
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer la durée actuels");
            lore.add(ChatColor.GRAY+"Durée de l'effet actuels: "+ChatColor.YELLOW+mobs.getDeathEffect().getDeathPotionEffect().getDuration()+"(Sec)");
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Tu dois déjà selection effets de potions");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Sous menu DeathPotion selection de l'effets
    public Inventory onMenuPotionSelect(Mobs mobs, String name) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *6, Messages.getPrefix() + name);
        for (int i = 0; i < 9; i++ ){
            inv.setItem(i, onglassStuff());
        }
        for (int i = 45; i < 9*6; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.addItem(onItemPotionEffect(Material.HONEY_BOTTLE, "Confusion"));
        inv.addItem(onItemPotionEffect(Material.ENDER_PEARL, "Blindness"));
        inv.addItem(onItemPotionEffect(Material.SCUTE, "Slowness"));
        inv.addItem(onItemPotionEffect(Material.SPIDER_EYE, "Poison"));
        inv.addItem(onItemPotionEffect(Material.FERMENTED_SPIDER_EYE, "Instant damage"));
        inv.addItem(onItemPotionEffect(Material.ROTTEN_FLESH, "Hunger"));
        inv.addItem(onItemPotionEffect(Material.WOODEN_SWORD, "Weakness"));
        inv.addItem(onItemPotionEffect(Material.WITHER_ROSE, "Wither Poison"));
        inv.addItem(onItemPotionEffect(Material.LEATHER_BOOTS, "Speed"));
        inv.addItem(onItemPotionEffect(Material.DIAMOND_SWORD, "Strength"));
        inv.addItem(onItemPotionEffect(Material.IRON_CHESTPLATE, "Resistance"));
        inv.addItem(onItemPotionEffect(Material.GLISTERING_MELON_SLICE, "Instant Heal"));
        inv.addItem(onItemPotionEffect(Material.GHAST_TEAR, "Regeneration"));
        inv.addItem(onItemPotionEffect(Material.MAGMA_CREAM, "Fire resistance"));
        inv.addItem(onItemPotionEffect(Material.GLASS, "Invisibility"));
        inv.addItem(onItemPotionEffect(Material.GOLDEN_CARROT, "Night Vision"));
        inv.addItem(onItemPotionEffect(Material.RABBIT_FOOT, "Jumps Boost"));
        inv.setItem(45, onExit());
        return inv;
    }

    //Création de l'item pour le menu deathpotion selection de l'effets
    private static ItemStack onItemPotionEffect(Material material, String name){
        ItemStack it = new ItemStack(material);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+name);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour selectione cette effets");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Menu DeathPotion Power

    public Inventory onMenuPotionPower(Mobs mobs, String name) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix()  +name);
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onItemPotionPower(1));
        inv.setItem(12, onItemPotionPower(2));
        inv.setItem(13, onItemPotionPower(3));
        inv.setItem(14, onItemPotionPower(4));
        inv.setItem(15, onItemPotionPower(5));
        inv.setItem(18, onExit());
        return inv;
    }
    //Item pour le Menu Potion power
    private static ItemStack onItemPotionPower(int power){
        ItemStack it = new ItemStack(Material.GUNPOWDER);
        ItemMeta itm =  it.getItemMeta();
        it.setAmount(power);
        assert itm != null;
        itm.setDisplayName(ChatColor.DARK_GRAY+"Puissance de l'effets: "+ChatColor.YELLOW+power);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour selectione cette puissance d'effets");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Menu DeathPotion Durer

    public Inventory onDeathMenuPotionTime(Mobs mobs, String name) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *4, Messages.getPrefix()  +name);
        for (int i = 0; i < 9*4; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(10, onItemPotionTime(15));
        inv.setItem(11, onItemPotionTime(30));
        inv.setItem(12, onItemPotionTime(45));
        inv.setItem(13, onItemPotionTime(60));
        inv.setItem(14, onItemPotionTime(75));
        inv.setItem(15, onItemPotionTime(90));
        inv.setItem(16, onItemPotionTime(105));
        inv.setItem(19, onItemPotionTime(120));
        inv.setItem(20, onItemPotionTime(135));
        inv.setItem(21, onItemPotionTime(150));
        inv.setItem(22, onItemPotionTime(165));
        inv.setItem(23, onItemPotionTime(180));
        inv.setItem(24, onItemPotionTime(195));
        inv.setItem(25, onItemPotionTime(210));
        inv.setItem(27, onExit());
        return inv;
    }
    private static ItemStack onItemPotionTime(int time){
        List<String> lore = new ArrayList<>();
        ItemStack it = new ItemStack(Material.CLOCK);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        lore.add("");
        lore.add(ChatColor.GRAY +"Temps en seconds: "+ChatColor.YELLOW+time);
        if (time > 0){
            int min = 0;
            int sec;
            while (time >=60){
                min++;
                time -= 60;
            }
            sec =time;
            itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: "+ChatColor.YELLOW+min+":"+sec);
        }else {
            itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: INFINI");
        }
        lore.add("");
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour selectione cette durée d'effets");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item Potion Durer


    //Menu édition du stuff
    public Inventory onStuff(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.GRAY + "Stuff");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }

        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, new ItemStack(Material.IRON_HELMET));
        inv.setItem(12, new ItemStack(Material.IRON_CHESTPLATE));
        inv.setItem(13, new ItemStack(Material.IRON_LEGGINGS));
        inv.setItem(14, new ItemStack(Material.IRON_BOOTS));
        inv.setItem(15, new ItemStack(Material.IRON_SWORD));
        inv.setItem(16, new ItemStack(Material.SHIELD));
        inv.setItem(20, mobs.getHelmet());
        inv.setItem(21, mobs.getChestplate());
        inv.setItem(22, mobs.getLeggings());
        inv.setItem(23, mobs.getBoots());
        inv.setItem(24, mobs.getHainMand());
        inv.setItem(25, mobs.getOffMand());
        inv.setItem(18, onExit());
        return inv;
    }



    private static ItemStack onglassStuff(){
        ItemStack it = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(" ");
        it.setItemMeta(itm);
        return it;
    }


    //Gui édition SpawnEffectPotionMobs

    public Inventory onSpawnPotionMobs(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Spawn Effect");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onSpawnItemPotion(mobs));
        inv.setItem(13, onSpawnItemPotionPorwerEffect(mobs));
        inv.setItem(15, onSpawnItemPotionTimeEffect(mobs));
        inv.setItem(18, onExit());
        return inv;
    }


    //Item Select Effect
    public static ItemStack onSpawnItemPotion(Mobs mobs){
        ItemStack it = new ItemStack(Material.POTION);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;
        lore.add("");
        if (mobs.getEffectMobsSpawn().isActif()){
            itm.setDisplayName(ChatColor.GOLD+"Effect de Potion du mobs ? "+ChatColor.GREEN+"Oui");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer l'effets de potion");
            lore.add(ChatColor.GRAY+"Effects actuels: "+ChatColor.YELLOW +mobs.getEffectMobsSpawn().getPotionEffect().getName());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click droit pour désactiver l'effets de potion");
        }else {
            itm.setDisplayName(ChatColor.GOLD+"Effect de Potion du mobs ? "+ChatColor.RED+"Non");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour activer l'effets de potion");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item power
    public static ItemStack onSpawnItemPotionPorwerEffect(Mobs mobs){
        ItemStack it = new ItemStack(Material.GUNPOWDER);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;

        lore.add("");

        if (mobs.getEffectMobsSpawn().isActif()){
            itm.setDisplayName(ChatColor.DARK_GRAY+"Puissance de l'effet: "+ChatColor.YELLOW+mobs.getEffectMobsSpawn().getPower());
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer puissance actuels");
            lore.add(ChatColor.GRAY+"Puissance de l'effet actuels: "+ChatColor.YELLOW+mobs.getEffectMobsSpawn().getPower());
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Tu dois déjà selection effets de potions");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item Durer
    public static ItemStack onSpawnItemPotionTimeEffect(Mobs mobs){
        ItemStack it = new ItemStack(Material.CLOCK);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;

        lore.add("");

        if (mobs.getEffectMobsSpawn().isActif()){
            if(mobs.getEffectMobsSpawn().getDuration() >0){
                int min = 0;
                int sec;
                int time = mobs.getEffectMobsSpawn().getDuration();
                while (time >=60){
                    min++;
                    time -= 60;
                }
                sec =time;
                itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: "+ChatColor.YELLOW+min+":"+sec);
                lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer la durée actuels");

            }else {
                itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: "+ChatColor.YELLOW+"Infinie");
            }
            lore.add(ChatColor.GRAY+"Durée de l'effet actuels: "+ChatColor.YELLOW+mobs.getEffectMobsSpawn().getDuration()+"(Sec)");
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Tu dois déjà selection effets de potions");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Sous Menu SpawnEffectPotion Mobs
    public Inventory onSpawnMenuPotionTime(Mobs mobs, String name) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *4, Messages.getPrefix()  +name);
        for (int i = 0; i < 9*4; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(10, onItemPotionTime(5));
        inv.setItem(11, onItemPotionTime(15));
        inv.setItem(12, onItemPotionTime(30));
        inv.setItem(13, onItemPotionTime(45));
        inv.setItem(14, onItemPotionTime(60));
        inv.setItem(15, onItemPotionTime(75));
        inv.setItem(16, onItemPotionTime(90));
        inv.setItem(19, onItemPotionTime(105));
        inv.setItem(20, onItemPotionTime(120));
        inv.setItem(21, onItemPotionTime(135));
        inv.setItem(22, onItemPotionTime(150));
        inv.setItem(23, onItemPotionTime(165));
        inv.setItem(24, onItemPotionTime(180));
        inv.setItem(25, onItemPotionTime(-1));
        inv.setItem(27, onExit());
        return inv;
    }

    //Menu édition du biome
    public Inventory onMobBiome(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *6, Messages.getPrefix() + ChatColor.GRAY + "Biome");
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(3, onBiomeSelectAll(mobs));
        inv.setItem(5, onBiome("Désactiver", Material.BARRIER));

        inv.setItem(9, onBiome("Forêt", Material.OAK_SAPLING));
        inv.setItem(10, onBiome("Sombre forêt", Material.DARK_OAK_SAPLING));
        inv.setItem(11, onBiome("Savanne", Material.ACACIA_SAPLING));
        inv.setItem(12, onBiome("Forêt de bouleau", Material.BIRCH_SAPLING));
        inv.setItem(13, onBiome("Desert", Material.SAND));
        inv.setItem(14, onBiome("Messa", Material.TERRACOTTA));
        inv.setItem(15, onBiome("Jungle", Material.VINE));
        inv.setItem(16, onBiome("Taiga", Material.PODZOL));
        inv.setItem(17, onBiome("Plaine", Material.GRASS_BLOCK));

        inv.setItem(18, onBiome("Plaine enneigée", Material.SNOW_BLOCK));

        inv.setItem(19, onBiome("Prairie", Material.HONEYCOMB));
        inv.setItem(20, onBiome("Montagnes", Material.STONE));
        inv.setItem(21, onBiome("Foret Montagneux", Material.SPRUCE_LEAVES));
        inv.setItem(22, onBiome("Marrais", Material.LILY_PAD));

        inv.setItem(23, onBiome("Nether", Material.NETHERRACK));
        inv.setItem(24, onBiome("Soul sand vallée", Material.SOUL_SAND));
        inv.setItem(25, onBiome("Biome delta", Material.BASALT));
        inv.setItem(26, onBiome("Forêt carmin", Material.CRIMSON_FUNGUS));
        inv.setItem(27, onBiome("Forêt biscornue", Material.WARPED_FUNGUS));
        inv.setItem(28, onBiome("End", Material.END_STONE));

        inv.setItem(45, onExit());
        return inv;
    }


    private static ItemStack onBiome(String name, Material material){
        ItemStack it = new ItemStack(material);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+ name);
        it.setItemMeta(itm);
        return it;
    }

    public static ItemStack onBiomeSelectAll(Mobs mobs){
        ItemStack it = new ItemStack(Material.NETHER_STAR);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+ InteractMenu.getNextSelectBiomeAll(mobs.getBiomespawn()));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY+"Click pour selectionner le mode suivant");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Gui édition SpawnEffectPotionMobs

    public Inventory onDamagePotionMobs(Mobs mobs) throws MalformedURLException {
        Inventory inv = Bukkit.createInventory(null, 9 *3, Messages.getPrefix() + ChatColor.DARK_GRAY + "Damage Effect");
        for (int i = 0; i < 27; i++ ){
            inv.setItem(i, onglassStuff());
        }
        inv.setItem(0, onSkull(mobs, false));
        inv.setItem(11, onDamageItemPotion(mobs));
        inv.setItem(13, onDamageItemPotionPorwerEffect(mobs));
        inv.setItem(15, onDamageItemPotionTimeEffect(mobs));
        inv.setItem(18, onExit());
        return inv;
    }
    //Item Select Effect
    public static ItemStack onDamageItemPotion(Mobs mobs){
        ItemStack it = new ItemStack(Material.POTION);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;
        lore.add("");
        if (mobs.getEffectMobsDamage().isActif()){
            itm.setDisplayName(ChatColor.GOLD+"Effect de Potion du mobs quand il attaque? "+ChatColor.GREEN+"Oui");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer l'effets de potion");
            lore.add(ChatColor.GRAY+"Effects actuels: "+ChatColor.YELLOW +mobs.getEffectMobsDamage().getPotionEffect().getName());
            lore.add("");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click droit pour désactiver l'effets de potion");
        }else {
            itm.setDisplayName(ChatColor.GOLD+"Effect de Potion du mobs ? "+ChatColor.RED+"Non");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour activer l'effets de potion");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Item power
    public static ItemStack onDamageItemPotionPorwerEffect(Mobs mobs){
        ItemStack it = new ItemStack(Material.GUNPOWDER);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;

        lore.add("");

        if (mobs.getEffectMobsDamage().isActif()){
            itm.setDisplayName(ChatColor.DARK_GRAY+"Puissance de l'effet: "+ChatColor.YELLOW+mobs.getEffectMobsDamage().getPower());
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer puissance actuels");
            lore.add(ChatColor.GRAY+"Puissance de l'effet actuels: "+ChatColor.YELLOW+mobs.getEffectMobsDamage().getPower());
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Tu dois déjà selection effets de potions");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    //Item Durer
    public static ItemStack onDamageItemPotionTimeEffect(Mobs mobs){
        ItemStack it = new ItemStack(Material.CLOCK);
        ItemMeta itm =  it.getItemMeta();
        List<String> lore = new ArrayList<>();

        assert itm != null;

        lore.add("");

        if (mobs.getEffectMobsDamage().isActif()){
            if(mobs.getEffectMobsDamage().getDuration() > 0){
                int min = 0;
                int sec;
                int time = mobs.getEffectMobsDamage().getDuration();
                while (time >=60){
                    min++;
                    time -= 60;
                }
                sec =time;
                itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: "+ChatColor.YELLOW+min+":"+sec);
                lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click gauche pour changer la durée actuels");

            }else {
                itm.setDisplayName(ChatColor.GREEN+"Durer de l'effets: "+ChatColor.YELLOW+"Infinie");
            }
            lore.add(ChatColor.GRAY+"Durée de l'effet actuels: "+ChatColor.YELLOW+mobs.getEffectMobsDamage().getDuration()+"(Sec)");
        }else {
            itm.setDisplayName(ChatColor.RED+"Désactiver");
            lore.add(ChatColor.GRAY+ ""+ChatColor.ITALIC +"Tu dois déjà selection effets de potions");
        }
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    //Menu selection du mobs a éditer
    public Inventory onMobMoreGUI(Player p, Rang rank, boolean edit) throws MalformedURLException {
        String name;
        if (edit){
          name = ""+ Messages.getPrefix()+ChatColor.GOLD +"Rang "+rank+" édition";
        }else {
           name =""+ Messages.getPrefix()+ChatColor.GOLD +"Rang "+rank;
        }

        Inventory inv = Bukkit.createInventory(null, 9*6, name);

        for (int i = maxpage; i <maxpage+9; i++){
            inv.setItem(i, onGlass());
        }
        page.put(p, 1);
        inv.setItem(maxpage, onLeft(page.get(p)));
        inv.setItem(maxpage+4, onExit());
        inv.setItem(maxpage+8, onRight(page.get(p)));
        if(Mobs.mobsList.isEmpty()){
            return inv;
        }

        int count = 0;
        for(Mobs mobs : Mobs.mobsList){
            if (count > maxpage){
                return  inv;
            }
          if(mobs.getRank().equals(rank)){
                inv.addItem(onSkull(mobs, edit));
                count++;
          }

        }
        return inv;
    }



    //tete des mobs
    public static ItemStack onSkull(Mobs mobs, Boolean edit) throws MalformedURLException {
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN+ mobs.getName());
        PlayerProfile playerProfile = Bukkit.createPlayerProfile("j");
        playerProfile.setTextures(onSkinSkull(mobs.getEntityType(), playerProfile));
        playerProfile.update();
        itm.setOwnerProfile(playerProfile);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+" Dégats: "+ChatColor.RED+""+mobs.getDamage());
        lore.add(ChatColor.GRAY+" Point de vie: "+ChatColor.GREEN+""+mobs.getHealth());
        lore.add(ChatColor.GRAY+" Speed: "+ChatColor.GOLD+""+mobs.getSpeed());
        lore.add(ChatColor.GRAY+" World spawn: "+ChatColor.YELLOW+""+mobs.getWorldspawn());
        lore.add(ChatColor.GRAY+" Biome: "+ChatColor.DARK_GREEN+""+mobs.getBiomespawn());
        if (mobs.getEntityType().equals(EntityType.ZOMBIE)){
            lore.add(ChatColor.GRAY+"Age: ");
        }
        if(edit){
            lore.add(" ");
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Click pour éditer le mob.");
        }

        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }

    private ItemStack onRank(String rank){
        ItemStack it = new ItemStack(Material.PAPER);
        ItemMeta itm = it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD+"Rang "+ rank);
        it.setItemMeta(itm);
        return it;
    }


    private static ItemStack onLeft(int page){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        if (page > 1){
            itm.setDisplayName(ChatColor.GREEN +"Page "+(page-1));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Précédent");
            itm.setLore(lore);
        }else {
            itm.setDisplayName(ChatColor.RED +"Page 0");
        }

        PlayerProfile playerProfile = Bukkit.createPlayerProfile("MHF_ArrowLeft");
        itm.setOwnerProfile(playerProfile);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onExit(){
        ItemStack it = new ItemStack(Material.OAK_DOOR);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD +"Menu principal");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Retour au menu principal");
        itm.setLore(lore);
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onGlass(){
        ItemStack it = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta itm =  it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GOLD +" ");
        it.setItemMeta(itm);
        return it;
    }
    private static ItemStack onRight(int page){
        ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itm = (SkullMeta) it.getItemMeta();
        assert itm != null;
        itm.setDisplayName(ChatColor.GREEN +"Page "+(page+1));
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+""+ChatColor.ITALIC+"Suivant");
        itm.setLore(lore);
        PlayerProfile playerProfile = Bukkit.createPlayerProfile("MHF_ArrowRight");
        itm.setOwnerProfile(playerProfile);
        it.setItemMeta(itm);
        return it;
    }

    public static PlayerTextures onSkinSkull(EntityType entityType, PlayerProfile playerProfile) throws MalformedURLException {
        PlayerTextures playerTextures = playerProfile.getTextures();
        URL url;
        String part = "http://textures.minecraft.net/texture/";
        if(EntityType.MAGMA_CUBE.equals(entityType)){
            url = new URL(part+ "a1c97a06efde04d00287bf20416404ab2103e10f08623087e1b0c1264a1c0f0c");
            playerTextures.setSkin(url);
        }else if(EntityType.STRAY.equals(entityType)){
            url = new URL(part+ "78ddf76e555dd5c4aa8a0a5fc584520cd63d489c253de969f7f22f85a9a2d56");
            playerTextures.setSkin(url);
        }
        else if(EntityType.SPIDER.equals(entityType)){
            url = new URL(part+ "5f7e82446fab1e41577ba70ab40e290ef841c245233011f39459ac6f852c8331");
            playerTextures.setSkin(url);
        }
        else if(EntityType.SLIME.equals(entityType)){
            url = new URL(part+ "bb13133a8fb4ef00b71ef9bab639a66fbc7d5cffcc190c1df74bf2161dfd3ec7");
            playerTextures.setSkin(url);
        }
        else if(EntityType.SKELETON.equals(entityType)){
            url = new URL(part+ "301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2");
            playerTextures.setSkin(url);
        }
        else if(EntityType.BLAZE.equals(entityType)){
            url = new URL(part+ "b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0");
            playerTextures.setSkin(url);
        }
        else if(EntityType.ZOMBIE.equals(entityType)){
            url = new URL(part+ "64528b3229660f3dfab42414f59ee8fd01e80081dd3df30869536ba9b414e089");
            playerTextures.setSkin(url);
        }
        else if(EntityType.ENDERMAN.equals(entityType)){
            url = new URL(part+ "96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951");
            playerTextures.setSkin(url);
        }
        else if(EntityType.ENDERMITE.equals(entityType)){
            url = new URL(part+ "5a1a0831aa03afb4212adcbb24e5dfaa7f476a1173fce259ef75a85855");
            playerTextures.setSkin(url);
        }
        else if(EntityType.EVOKER.equals(entityType)){
            url = new URL(part+ "d954135dc82213978db478778ae1213591b93d228d36dd54f1ea1da48e7cba6");
            playerTextures.setSkin(url);
        }
        else if(EntityType.WITCH.equals(entityType)){
            url = new URL(part+ "fce6604157fc4ab5591e4bcf507a749918ee9c41e357d47376e0ee7342074c90");
            playerTextures.setSkin(url);
        }
        else if(EntityType.WITHER_SKELETON.equals(entityType)){
            url = new URL(part+ "f5ec964645a8efac76be2f160d7c9956362f32b6517390c59c3085034f050cff");
            playerTextures.setSkin(url);
        }
        else if(EntityType.HUSK.equals(entityType)){
            url = new URL(part+ "9b9da6b8d06cd28d441398b96766c3b4f370de85c7898205e5c429f178a24597");
            playerTextures.setSkin(url);
        }
        else if(EntityType.PHANTOM.equals(entityType)){
            url = new URL(part+ "7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982");
            playerTextures.setSkin(url);
        }
        else if(EntityType.CAVE_SPIDER.equals(entityType)){
            url = new URL(part+ "41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224");
            playerTextures.setSkin(url);
        }
        else if(EntityType.PILLAGER.equals(entityType)){
            url = new URL(part+ "4aee6bb37cbfc92b0d86db5ada4790c64ff4468d68b84942fde04405e8ef5333");
            playerTextures.setSkin(url);
        }
        else if(EntityType.PIGLIN.equals(entityType)){
            url = new URL(part+ "9f18107d275f1cb3a9f973e5928d5879fa40328ff3258054db6dd3e7c0ca6330");
            playerTextures.setSkin(url);
        }
        else if(EntityType.PIGLIN_BRUTE.equals(entityType)){
            url = new URL(part + "3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf");
            playerTextures.setSkin(url);
        }
        else if(EntityType.DROWNED.equals(entityType)){
            url = new URL(part +"c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c");
            playerTextures.setSkin(url);
        }
        else if(EntityType.GIANT.equals(entityType)){
            url = new URL( part + "c98547c90ea11c31e9baba500c27bb2c6f5aa0c3c96bf0942549c04b87911869");
            playerTextures.setSkin(url);
        }
        else if(EntityType.CREEPER.equals(entityType)){
            url = new URL( part + "f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952");
            playerTextures.setSkin(url);
        }
        else if(EntityType.GHAST.equals(entityType)){
            url = new URL( part + "8b6a72138d69fbbd2fea3fa251cabd87152e4f1c97e5f986bf685571db3cc0");
            playerTextures.setSkin(url);
        }
        else if(EntityType.ZOMBIFIED_PIGLIN.equals(entityType)){
            url = new URL( part + "7eabaecc5fae5a8a49c8863ff4831aaa284198f1a2398890c765e0a8de18da8c");
            playerTextures.setSkin(url);
        }
        else if(EntityType.HORSE.equals(entityType)){
            url = new URL( part + "397a3c20da5eea01c4132c3ff45ea80a364ad42f74dbf797cca4f8ab2d42adb3");
            playerTextures.setSkin(url);
        }
        else if(EntityType.ZOMBIE_HORSE.equals(entityType)){
            url = new URL( part + "d22950f2d3efddb18de86f8f55ac518dce73f12a6e0f8636d551d8eb480ceec");
            playerTextures.setSkin(url);
        }
        else if(EntityType.SKELETON_HORSE.equals(entityType)){
            url = new URL( part + "47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a");
            playerTextures.setSkin(url);
        }
        return playerTextures;
    }
}

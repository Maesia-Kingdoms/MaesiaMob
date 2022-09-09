package fr.maesia.mob.listener;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.MaesiaMobFiles.Messages.Messages;
import fr.maesia.mob.mob.DeathEffect;
import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.Rang;
import fr.maesia.mob.mob.rangs.RangsLoots;
import fr.maesia.mob.utils.CustomEvents.RemoveMobEvent;
import fr.maesia.mob.utils.GUI;
import fr.maesia.mob.utils.PotionEffectItem.ItemtoPotion;
import fr.maesia.mob.utils.TchatInteract.TchatInteract;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


import static fr.maesia.mob.utils.GUI.onSkull;

public class InteractMenu implements Listener {

    @EventHandler
    private void onInteractMenu(InventoryClickEvent e) throws MalformedURLException {

        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix())){

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) return;
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 10) {
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(10)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p, Rang.valueOf(rank), false));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 12){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(12)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p, Rang.valueOf(rank), false));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 14){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(14)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), false));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 16){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(16)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank),false));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 29){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(29)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), false));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 31){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(31)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), false));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 33){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(33)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), false));
                return;
            }
        }

        //Gui suppression du mobs
        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.RED+"Suppression") && Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.HOPPER)) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) return;
            String name = Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(2)).getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            Mobs mobs = Mobs.getMobs(name);
            if(mobs == null)return;
            if (it.getType().equals(Material.RED_CONCRETE)) {
                RemoveMobEvent removeMobEvent = new RemoveMobEvent(mobs);
                Bukkit.getServer().getPluginManager().callEvent(removeMobEvent);
                if(removeMobEvent.isCancelled()) return;
                p.closeInventory();
                if (!Mobs.mobsListUuid.isEmpty()){
                    for(UUID target : Mobs.mobsListUuid){

                        if(Bukkit.getEntity(target) == null) continue;
                        Entity entity = Bukkit.getEntity(target);

                        assert entity != null;
                        if(!entity.getPersistentDataContainer().has(new NamespacedKey(MaesiaMob.getInstance(), "idMob"), PersistentDataType.STRING)) return;
                        String key = entity.getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "idMob"), PersistentDataType.STRING);
                        if(key == null) return;
                        UUID uuid = UUID.fromString(key);

                        if (uuid.equals(mobs.getId())) entity.remove();

                    }
                }
                /*
                if (Bukkit.getPluginManager().getPlugin("Skils") != null){
                    if (!FEC.hunters.isEmpty() || !FEC.monster.isEmpty()){
                        FEC fec = FEC.getFECMobs(mobs, FEC.hunters);
                        if (fec != null){
                            FEC.hunters.remove(fec);
                        }
                        fec = FEC.getFECMobs(mobs, FEC.monster);
                        if (fec != null){
                            FEC.monster.remove(fec);
                        }
                    }
                }
                 */
                Mobs.removeMobs(mobs);
                p.openInventory(gui.onMobMoreGUI(p, mobs.getRank(), true));
                return;
            }
            if (it.getType().equals(Material.GREEN_CONCRETE)) {
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p, mobs.getRank(), true));
                return;
            }
        }

        //Gui édition du mob
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()  + ChatColor.GOLD +"édition")){

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) return;
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 10) {
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(10)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p, Rang.valueOf(rank), true));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 12){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(12)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p, Rang.valueOf(rank), true));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 14){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(14)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), true));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 16){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(16)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), true));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 29){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(29)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), true));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 31){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(31)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), true));
                return;
            }
            if (it.getType().equals(Material.PAPER) && e.getSlot() == 33){
                String rank = Objects.requireNonNull(Objects.requireNonNull(e.getView().getItem(33)).getItemMeta()).getDisplayName().replace(ChatColor.GOLD+"Rang ", "").replace("éditon", "");
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p,  Rang.valueOf(rank), true));
                return;
            }
        }
        //Menu bestiare rangs mobs
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang F")
                || e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang E")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang D")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang C")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang B")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang A")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang S")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) return;
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 49) {
                p.closeInventory();
                p.openInventory(gui.onGuiBestiare());
                return;
            }
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 45) {
                if (Objects.requireNonNull(it.getItemMeta()).getDisplayName().equalsIgnoreCase(ChatColor.RED +"Page 0")){
                    return;
                }
                int page = getStringToInt(it.getItemMeta().getDisplayName().replace(ChatColor.GREEN +"Page ", ""));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdate(p, e.getInventory(), false);
                return;

            }
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 53) {

                int page = getStringToInt((Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN +"Page ", "")));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdate(p, e.getInventory(), false);
                return;

            }
            return;
        }
        //Menu édition rangs mobs
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang F édition")
        || e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang E édition")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang D édition")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang C édition")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang B édition")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang A édition")
                ||e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ ChatColor.GOLD+"Rang S édition")){

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) return;
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 49) {
                p.closeInventory();
                p.openInventory(gui.onGuiEdit());
                return;
            }
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 45) {
                if (Objects.requireNonNull(it.getItemMeta()).getDisplayName().equalsIgnoreCase(ChatColor.RED +"Page 0")){
                    return;
                }
                int page = getStringToInt(it.getItemMeta().getDisplayName().replace(ChatColor.GREEN +"Page ", ""));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdate(p, e.getInventory(), true);
                return;

            }
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 53) {

                int page = getStringToInt((Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN +"Page ", "")));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdate(p, e.getInventory(), true);
                return;

            }

            if (it.getType().equals(Material.PLAYER_HEAD)){
                String mobs = Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", "");
                Mobs mobs1 = Mobs.getMobs(mobs);
                if(mobs1 == null) return;
                p.openInventory(gui.onGuiEditMobs(mobs1));
                return;
            }

        }
        //Menu édition selection de la monture
        if (e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+"Monture")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            assert m != null;
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 0) {
                return;
            }
            if (it.getType().equals(Material.OAK_DOOR)) {
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsLootStuff(m));
                return;
            }

            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 45) {
                if (Objects.requireNonNull(it.getItemMeta()).getDisplayName().equalsIgnoreCase(ChatColor.RED +"Page 0")){
                    return;
                }
                int page = getStringToInt(it.getItemMeta().getDisplayName().replace(ChatColor.GREEN +"Page ", ""));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdateMonture(p, e.getInventory(), m);
                return;

            }
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 53) {

                int page = getStringToInt((Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN +"Page ", "")));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdateMonture(p, e.getInventory(), m);
                return;
            }
            if (it.getType().equals(Material.PLAYER_HEAD)){
                String name = Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", "");
                Mobs target = Mobs.getMobs(name);
                if(target == null || target == m) return;
                if (m.getPassager() == target.getId()){
                    m.setPassager(null);
                    e.getView().setItem(3, GUI.onMonture(m, false));
                }else {
                    m.setPassager(target.getId());
                    e.getView().setItem(3, GUI.onMonture(m, false));
                }
                return;
            }
            if (it.getType().equals(Material.BARRIER)){
                m.setPassager(null);
                e.getView().setItem(3, GUI.onMonture(m, false));
                return;
            }
        }

        //Menu édition selection des mondes
        if (e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ChatColor.GRAY +"Selection des Mondes")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            assert m != null;
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 0) {
                return;
            }
            if (it.getType().equals(Material.OAK_DOOR)) {
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsSpawn(m));
                return;
            }
            if (it.getType().equals(Material.NETHER_STAR)) {
                String typeAll = getNextSelectWorldAll(m.getWorldspawn());
                m.getWorldspawn().clear();
                m.getWorldspawn().add(typeAll);
                e.getView().setItem(3, GUI.onWorldSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }

            if (it.getType().equals(Material.BARRIER)){

                m.getWorldspawn().clear();
                m.getWorldspawn().add("Aucun");
                e.getView().setItem(3, GUI.onWorldSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }

            onremoveAllWorld(m);
            if (m.getWorldspawn().contains(Objects.requireNonNull(it.getItemMeta()).getDisplayName())){
                m.getWorldspawn().remove(it.getItemMeta().getDisplayName());
                if(m.getWorldspawn().isEmpty()) m.getWorldspawn().add("Aucun");
            }else {
                m.getWorldspawn().add(it.getItemMeta().getDisplayName());
            }
            e.getView().setItem(3, GUI.onWorldSelectAll(m));
            e.getView().setItem(0, onSkull(m, false));

        }

        //Menu édition biome
        if (e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix()+ChatColor.GRAY +"Biome")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST))return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));
            assert m != null;
            if (it.getType().equals(Material.PLAYER_HEAD)) {
                return;
            }
            if(it.getType().equals(Material.OAK_DOOR)){
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsSpawn(m));
                return;
            }
            if (it.getType().equals(Material.NETHER_STAR)){

                String typeAll = getNextSelectBiomeAll(m.getBiomespawn());

                m.getBiomespawn().clear();
                m.getBiomespawn().add(typeAll);
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.OAK_SAPLING)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.FOREST.name())){
                    m.getBiomespawn().remove(Biome.FOREST.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.FOREST.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.DARK_OAK_SAPLING)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.DARK_FOREST.name())){
                    m.getBiomespawn().remove(Biome.DARK_FOREST.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.DARK_FOREST.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.ACACIA_SAPLING)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.SAVANNA.name())){
                    m.getBiomespawn().remove(Biome.SAVANNA.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.SAVANNA.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.BIRCH_SAPLING)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.BIRCH_FOREST.name())){
                    m.getBiomespawn().remove(Biome.BIRCH_FOREST.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.BIRCH_FOREST.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.SAND)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.DESERT.name())){
                    m.getBiomespawn().remove(Biome.DESERT.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.DESERT.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.TERRACOTTA)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.BADLANDS.name())){
                    m.getBiomespawn().remove(Biome.BADLANDS.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.BADLANDS.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.VINE)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.JUNGLE.name())){
                    m.getBiomespawn().remove(Biome.JUNGLE.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.JUNGLE.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.PODZOL)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.TAIGA.name())){
                    m.getBiomespawn().remove(Biome.TAIGA.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.TAIGA.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.GRASS_BLOCK)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.PLAINS.name())){
                    m.getBiomespawn().remove(Biome.PLAINS.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.PLAINS.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.SNOW)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.SNOWY_PLAINS.name())){
                    m.getBiomespawn().remove(Biome.SNOWY_PLAINS.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.SNOWY_PLAINS.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.PACKED_ICE)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.ICE_SPIKES.name())){
                    m.getBiomespawn().remove(Biome.ICE_SPIKES.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.ICE_SPIKES.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.HONEYCOMB)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.MEADOW.name())){
                    m.getBiomespawn().remove(Biome.MEADOW.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.MEADOW.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.STONE)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.WINDSWEPT_HILLS.name())){
                    m.getBiomespawn().remove(Biome.WINDSWEPT_HILLS.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.WINDSWEPT_HILLS.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.SPRUCE_LEAVES)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.WINDSWEPT_FOREST.name())){
                    m.getBiomespawn().remove(Biome.WINDSWEPT_FOREST.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.WINDSWEPT_FOREST.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.LILY_PAD)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.SWAMP.name())){
                    m.getBiomespawn().remove(Biome.SWAMP.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.SWAMP.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }

            if (it.getType().equals(Material.NETHERRACK)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.NETHER_WASTES.name())){
                    m.getBiomespawn().remove(Biome.NETHER_WASTES.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.NETHER_WASTES.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.SOUL_SAND)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.SOUL_SAND_VALLEY.name())){
                    m.getBiomespawn().remove(Biome.SOUL_SAND_VALLEY.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.SOUL_SAND_VALLEY.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.CRIMSON_FUNGUS)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.CRIMSON_FOREST.name())){
                    m.getBiomespawn().remove(Biome.CRIMSON_FOREST.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.CRIMSON_FOREST.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.WARPED_FUNGUS)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.WARPED_FOREST.name())){
                    m.getBiomespawn().remove(Biome.WARPED_FOREST.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.WARPED_FOREST.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.BASALT)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.BASALT_DELTAS.name())){
                    m.getBiomespawn().remove(Biome.BASALT_DELTAS.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.BASALT_DELTAS.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.END_STONE)){
                onremoveAllBiome(m);
                if (m.getBiomespawn().contains(Biome.END_HIGHLANDS.name())){
                    m.getBiomespawn().remove(Biome.END_HIGHLANDS.name());
                    if (m.getBiomespawn().isEmpty()) m.getBiomespawn().add("Aucun");
                }else {
                    m.getBiomespawn().add(Biome.END_HIGHLANDS.name());
                }
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }
            if (it.getType().equals(Material.BARRIER)){

                m.getBiomespawn().clear();
                m.getBiomespawn().add("Aucun");
                e.getView().setItem(3, GUI.onBiomeSelectAll(m));
                e.getView().setItem(0, onSkull(m, false));
                return;
            }

        }
        //Gui édition du stuff
        if (e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.GRAY + "Stuff")){
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            e.setCancelled(e.getSlot() != 20 && e.getSlot() != 21 && e.getSlot() != 22 && e.getSlot() != 23 && e.getSlot() != 24 && e.getSlot() != 25 && Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            ItemStack it = e.getCurrentItem();
            if (it == null)return;

            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                ItemStack helmet = e.getView().getItem(20);
                ItemStack chesplate = e.getView().getItem(21);
                ItemStack leggins = e.getView().getItem(22);
                ItemStack boots = e.getView().getItem(23);
                ItemStack hand = e.getView().getItem(24);
                ItemStack offhand = e.getView().getItem(25);


                assert m != null;
                m.setHelmet(Objects.requireNonNullElseGet(helmet, () -> new ItemStack(Material.AIR)));
                m.setChestplate(Objects.requireNonNullElseGet(chesplate, () -> new ItemStack(Material.AIR)));
                m.setLeggings(Objects.requireNonNullElseGet(leggins, () -> new ItemStack(Material.AIR)));
                m.setBoots(Objects.requireNonNullElseGet(boots, () -> new ItemStack(Material.AIR)));
                m.setHainMand(Objects.requireNonNullElseGet(hand, () -> new ItemStack(Material.AIR)));
                m.setOffMand(Objects.requireNonNullElseGet(offhand, () -> new ItemStack(Material.AIR)));
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsLootStuff(m));
                return;
            }
        }
        //Gui édition des effets de morts
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.GRAY + "DeathMenu")){

            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();


            ItemStack it = e.getCurrentItem();
            if (it == null)return;

            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));

            if  (it.getType().equals(Material.EGG)){
                p.closeInventory();
                p.openInventory(gui.onDeathMenuSpawnEdit(m));
                return;
            }
            if (it.getType().equals(Material.TNT)){
                p.closeInventory();
                p.openInventory(gui.onDeathMenuExplosion(m));
                return;
            }
            if (it.getType().equals(Material.SPLASH_POTION)){
                p.closeInventory();
                p.openInventory(gui.onDeathMenuPotion(m));
                return;
            }
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobsEffect(m));
                return;
            }
            return;
        }
        //Gui Sous-menu effects de morts partie spawn
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DeathMenu Spawn")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));

            if (e.getSlot() == 12){
                p.closeInventory();
                p.openInventory(gui.onDeathMenuSpawnEditSelectMob(p, m));
                return;
            }
            if (e.getSlot() == 14){
                assert m != null;
                if (m.getDeathEffect().getDeathSpawn().isActif()){
                    p.closeInventory();
                    p.openInventory(gui.onDeathMenuSpawnEditCountMob(m));
                }

                return;
            }

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuEdit(m));
                return;
            }


        }
        //Gui Sous-menu effects de morts partie spawn selection du mobs
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "Selection")){

            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            ItemStack it = e.getCurrentItem();
            if (it == null)return;

            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));
            Mobs target = Mobs.getMobs(Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 49) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuSpawnEdit(m));
                return;
            }

            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 45) {
                if (Objects.requireNonNull(it.getItemMeta()).getDisplayName().equalsIgnoreCase(ChatColor.RED +"Page 0")){
                    return;
                }
                int page = getStringToInt(it.getItemMeta().getDisplayName().replace(ChatColor.GREEN +"Page ", ""));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdateDeathGui(p, e.getInventory());
                return;

            }
            if (it.getType().equals(Material.PLAYER_HEAD) && e.getSlot() == 53) {

                int page = getStringToInt((Objects.requireNonNull(it.getItemMeta()).getDisplayName().replace(ChatColor.GREEN +"Page ", "")));
                if( page == -1)return;
                GUI.getPage().put(p, page);
                GUI.onUpdateDeathGui(p, e.getInventory());
                return;

            }
            if(m ==  null || target == null) return;
            if(DeathEffect.checkMobSpawning(m, target)) {
                p.sendMessage(Messages.getPrefix()+ChatColor.RED+"Vous ne pouvez pas définir ce mob cela causerait une boucle infinie");
                return;
            }

            m.getDeathEffect().getDeathSpawn().setMobs(target.getName());
            m.getDeathEffect().getDeathSpawn().setActif(true);
            m.getDeathEffect().getDeathSpawn().setCount(1);

            //désactivation des autres effets
            m.getDeathEffect().getDeathExplotion().setActif(false);
            m.getDeathEffect().getDeathPotionEffect().setActif(false);

            return;
        }

        //qunatité des mobs qui spawn
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "Quantité")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuSpawnEdit(m));
                return;
            }

            assert m != null;
            if (m.getDeathEffect().getDeathSpawn().isActif()){
                m.getDeathEffect().getDeathSpawn().setCount(it.getAmount());
            }
            return;
        }

        //Gui explosion
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + org.bukkit.ChatColor.DARK_GRAY + "Explosion edit")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            if (e.getClickedInventory() == null) return;
            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuEdit(m));
                return;
            }
            if (it.getType().equals(Material.TNT)){
                if (e.isLeftClick()){
                    p.closeInventory();
                    p.openInventory(gui.onDeathMenuPower(m));
                }
                if (e.isRightClick()){
                    assert m != null;
                    m.getDeathEffect().getDeathExplotion().setActif(false);
                }
                assert m != null;
                e.setCurrentItem(GUI.onItemEffectPower(m));
                return;
            }
            if(it.getType().equals(Material.FLINT_AND_STEEL)) {
                assert m != null;
                if (m.getDeathEffect().getDeathExplotion().isActif()) {
                    if (e.isRightClick()) {
                        //désactive
                        m.getDeathEffect().getDeathExplotion().setFire(false);
                    }
                    if (e.isLeftClick()) {
                        //active
                        m.getDeathEffect().getDeathExplotion().setFire(true);
                    }
                    e.setCurrentItem(GUI.onItemEffectFire(m));
                    return;
                }
            }
            if(it.getType().equals(Material.CRACKED_STONE_BRICKS)) {
                assert m != null;
                if (m.getDeathEffect().getDeathExplotion().isActif()) {
                    if (e.isRightClick()) {
                        //désactive
                        m.getDeathEffect().getDeathExplotion().setDestroy(false);
                    }
                    if (e.isLeftClick()) {
                        //active
                        m.getDeathEffect().getDeathExplotion().setDestroy(true);
                    }
                    e.setCurrentItem(GUI.onItemEffectDestroy(m));
                    return;
                }
            }

        }

        //Selection de la taille de l'explosion
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "Taille d'Explosion")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuExplosion(m));
                return;
            }
            if (it.getType().equals(Material.GUNPOWDER)){
                assert m != null;
                if (e.isLeftClick()){
                    m.getDeathEffect().getDeathExplotion().setActif(true);
                    m.getDeathEffect().getDeathExplotion().setPower(it.getAmount());
                    //désactive les autres effect
                    m.getDeathEffect().getDeathSpawn().setActif(false);
                    m.getDeathEffect().getDeathPotionEffect().setActif(false);
                    return;
                }
                if (e.isRightClick()){
                    m.getDeathEffect().getDeathExplotion().setActif(false);
                }
            }
        }

        //Gui de Death effect potion
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DeathPotion edit")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.POTION)){
                if (e.isLeftClick()){
                    p.closeInventory();
                    p.openInventory(gui.onMenuPotionSelect(m,   ChatColor.DARK_GRAY + "DeathPotion Selection"));
                }
                if(e.isRightClick()){
                    assert m != null;
                    m.getDeathEffect().getDeathPotionEffect().setActif(false);
                    e.setCurrentItem(GUI.onDeathEffectItemPotion(m));
                    e.getView().setItem(13, GUI.onItemPotionPorwerEffect(m));
                    e.getView().setItem(15, GUI.onItemPotionTimeEffect(m));
                }
                return;
            }
            assert m != null;
            if (m.getDeathEffect().getDeathPotionEffect().isActif()){
                if(it.getType().equals(Material.GUNPOWDER)){
                    p.closeInventory();
                    p.openInventory(gui.onMenuPotionPower(m,  ChatColor.DARK_GRAY + "DeathPotion Power"));
                    return;
                }
                if(it.getType().equals(Material.CLOCK)){
                    p.closeInventory();
                    p.openInventory(gui.onDeathMenuPotionTime(m, ChatColor.DARK_GRAY + "DeathPotion Durée"));
                    return;
                }
            }


            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                p.openInventory(gui.onDeathMenuEdit(m));
                return;
            }
        }

        //Gui de Death effect potion selection

        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DeathPotion Selection")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuPotion(m));
                return;
            }
            if (!it.getType().equals(Material.BLACK_STAINED_GLASS_PANE)){

                assert m != null;
                if (ItemtoPotion.onItemToEffect(it.getType())== null)return;
                m.getDeathEffect().getDeathPotionEffect().setActif(true);
                m.getDeathEffect().getDeathPotionEffect().setPower(1);
                m.getDeathEffect().getDeathPotionEffect().setDuration(15);
                m.getDeathEffect().getDeathPotionEffect().setPotionEffect(ItemtoPotion.onItemToEffect(it.getType()));

                //Désactive des autres effets
                m.getDeathEffect().getDeathSpawn().setActif(false);
                m.getDeathEffect().getDeathExplotion().setActif(false);
                return;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DeathPotion Power")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuPotion(m));
                return;
            }
            if (it.getType().equals(Material.GUNPOWDER)){
                assert m != null;
                m.getDeathEffect().getDeathPotionEffect().setPower(it.getAmount());
                return;
            }

        }

        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DeathPotion Durée")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 27) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDeathMenuPotion(m));
                return;
            }

            if (it.getType().equals(Material.CLOCK)){
                 String time = Objects.requireNonNull(Objects.requireNonNull(it.getItemMeta()).getLore()).get(1).replace(  ChatColor.GRAY +"Temps en seconds: "+ ChatColor.YELLOW, "");

                 if (getStringToInt(time) > 0){
                     assert m != null;
                     m.getDeathEffect().getDeathPotionEffect().setDuration( getStringToInt(time));
                 }
                return;
            }

        }

        //Gui de Death effect potion selection
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "Spawn Effect")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobsEffect(m));
                return;
            }
            if (it.getType().equals(Material.POTION)){
                if (e.isLeftClick()){
                    p.closeInventory();
                    p.openInventory(gui.onMenuPotionSelect(m, ChatColor.DARK_GRAY +"SpawnPotion Select"));
                }
                if(e.isRightClick()){
                    assert m != null;
                    m.getEffectMobsSpawn().setActif(false);
                    e.setCurrentItem(GUI.onSpawnItemPotion(m));
                    e.getView().setItem(13, GUI.onItemPotionPorwerEffect(m));
                    e.getView().setItem(15, GUI.onSpawnItemPotionTimeEffect(m));
                }
                return;
            }
            assert m != null;
            if (m.getEffectMobsSpawn().isActif()){
                if (it.getType().equals(Material.GUNPOWDER)){
                    p.closeInventory();
                    p.openInventory(gui.onMenuPotionPower(m, ChatColor.DARK_GRAY + "SpawnPotion Power"));
                    return;
                }
                if (it.getType().equals(Material.CLOCK)){
                    p.closeInventory();
                    p.openInventory(gui.onSpawnMenuPotionTime(m, ChatColor.DARK_GRAY + "SpawnPotion Durée"));
                    return;
                }
            }
            return;
        }
        //Menu selection(Spawn potion effect)
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "SpawnPotion Select")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onSpawnPotionMobs(m));
                return;
            }
            if (!it.getType().equals(Material.BLACK_STAINED_GLASS_PANE)){

                assert m != null;
                if (ItemtoPotion.onItemToEffect(it.getType())== null)return;
                m.getEffectMobsSpawn().setActif(true);
                m.getEffectMobsSpawn().setPower(1);
                m.getEffectMobsSpawn().setDuration(15);
                m.getEffectMobsSpawn().setPotionEffect(ItemtoPotion.onItemToEffect(it.getType()));
                return;
            }

        }
        //Menu power(Spawn potion effect)
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "SpawnPotion Power")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onSpawnPotionMobs(m));
                return;
            }
            if (it.getType().equals(Material.GUNPOWDER)){
                assert m != null;
                m.getEffectMobsSpawn().setPower(it.getAmount());
                return;
            }

        }
        // menu durée (Spawn potion effect)
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "SpawnPotion Durée")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 27) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onSpawnPotionMobs(m));
                return;
            }
            if (it.getType().equals(Material.CLOCK)){
                String time = Objects.requireNonNull(Objects.requireNonNull(it.getItemMeta()).getLore()).get(1).replace(  ChatColor.GRAY +"Temps en seconds: "+ ChatColor.YELLOW, "");

                if (getStringToInt(time) > -2){
                    assert m != null;
                    m.getEffectMobsSpawn().setDuration( getStringToInt(time));
                }
                return;
            }
        }

        //Gui de Damage effect apply
        if(e.getView().getTitle().equalsIgnoreCase( Messages.getPrefix() +ChatColor.DARK_GRAY + "Damage Effect")) {

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobsEffect(m));
                return;
            }
            if (it.getType().equals(Material.POTION)){
                if (e.isLeftClick()){
                    p.closeInventory();
                    p.openInventory(gui.onMenuPotionSelect(m, ChatColor.DARK_GRAY +"DamagePotion Select"));
                }
                if(e.isRightClick()){
                    assert m != null;
                    m.getEffectMobsDamage().setActif(false);
                    e.setCurrentItem(GUI.onDamageItemPotion(m));
                    e.getView().setItem(13, GUI.onDamageItemPotionPorwerEffect(m));
                    e.getView().setItem(15, GUI.onDamageItemPotionTimeEffect(m));
                }
                return;
            }
            assert m != null;
            if (m.getEffectMobsDamage().isActif()){
                if (it.getType().equals(Material.GUNPOWDER)){
                    p.closeInventory();
                    p.openInventory(gui.onMenuPotionPower(m, ChatColor.DARK_GRAY + "DamagePotion Power"));
                    return;
                }
                if (it.getType().equals(Material.CLOCK)){
                    p.closeInventory();
                    p.openInventory(gui.onSpawnMenuPotionTime(m, ChatColor.DARK_GRAY + "DamagePotion Durée"));
                    return;
                }
            }
            return;
        }
        // menu durée(Damage effect apply)
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DamagePotion Durée")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 27) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDamagePotionMobs(m));
                return;
            }
            if (it.getType().equals(Material.CLOCK)){
                String time = Objects.requireNonNull(Objects.requireNonNull(it.getItemMeta()).getLore()).get(1).replace(  ChatColor.GRAY +"Temps en seconds: "+ ChatColor.YELLOW, "");

                if (getStringToInt(time) > -2){
                    assert m != null;
                    m.getEffectMobsDamage().setDuration( getStringToInt(time));
                }
                return;
            }
        }
        //Menu power(Damage effect apply)
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DamagePotion Power")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDamagePotionMobs(m));
                return;
            }
            if (it.getType().equals(Material.GUNPOWDER)){
                assert m != null;
                m.getEffectMobsDamage().setPower(it.getAmount());
                return;
            }

        }

        //Menu selection(Damage effect apply)
        if(e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "DamagePotion Select")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            if (Objects.requireNonNull(e.getClickedInventory()).isEmpty()) return;
            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onDamagePotionMobs(m));
                return;
            }
            if (!it.getType().equals(Material.BLACK_STAINED_GLASS_PANE)){

                assert m != null;
                if (ItemtoPotion.onItemToEffect(it.getType())== null)return;
                m.getEffectMobsDamage().setActif(true);
                m.getEffectMobsDamage().setPower(1);
                m.getEffectMobsDamage().setDuration(15);
                m.getEffectMobsDamage().setPotionEffect(ItemtoPotion.onItemToEffect(it.getType()));
                return;
            }

        }

        //////////////
        //////////////
        //////////////
        //Menu des loots
        if(e.getView().getTitle().equalsIgnoreCase(  Messages.getPrefix() + ChatColor.DARK_GRAY + "Loots")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            if (Objects.requireNonNull(e.getClickedInventory()).isEmpty()) return;
            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobsLootStuff(m));
                return;
            }
            ItemStack loots = Objects.requireNonNull(e.getCursor()).clone();
            if (e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR) && it.getType().equals(Material.HOPPER)){

                assert m != null;

                if(m.getLoots().containsKey(loots)){
                    p.sendMessage(Messages.getPrefix()+ChatColor.RED+"L'item est déjà dans la table de loots.");
                    return;
                }

                if (m.getLoots().size() >= 7){
                    p.sendMessage(Messages.getPrefix()+ChatColor.RED+"Table de Loots complets");
                    return;
                }

                m.getLoots().put(loots, RangsLoots.COMMUN);

                p.closeInventory();
                p.openInventory(gui.onMenuLootMobs(m));
                return;
            }
            if(e.getSlot() == 10 || e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 13 || e.getSlot() == 14 || e.getSlot() == 15 || e.getSlot() == 16){
                p.closeInventory();
                List<String> list = Objects.requireNonNull(it.getItemMeta()).getLore();
                List<String> newlore = Objects.requireNonNull(it.getItemMeta()).getLore();
                ItemStack itemStack = it.clone();
                ItemMeta itm = itemStack.getItemMeta();
                assert list != null;
                int remove = 0;
                    for (String check : list ){
                        if(check.equalsIgnoreCase(org.bukkit.ChatColor.GRAY+""+ org.bukkit.ChatColor.ITALIC +"Click pour éditer le loots")){
                            assert newlore != null;
                            newlore.remove(remove);
                            remove--;
                            newlore.remove(remove);
                            remove--;
                            newlore.remove(remove);
                            remove--;
                            newlore.remove(remove);
                            remove--;
                        }
                        remove++;
                    }
                assert itm != null;
                itm.setLore(newlore);
                itemStack.setItemMeta(itm);
                p.openInventory(gui.onMenuLootMobsEdits(m, itemStack));
                return;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase(  Messages.getPrefix() + ChatColor.DARK_GRAY + "Loots édits")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));

            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onMenuLootMobs(m));
                return;
            }
            ItemStack target = e.getView().getItem(10);
            if (it.getType().equals(Material.BARRIER)){
                p.closeInventory();
                p.openInventory(gui.onMenuLootMobsRemove(m, target));
                return;
            }
            if (it.getType().equals(Material.PAPER)){

                assert m != null;
                if (e.getSlot() == 11){
                    m.getLoots().replace(target, RangsLoots.COMMUN);
                    p.closeInventory();
                    p.openInventory(gui.onMenuLootMobs(m));
                    return;
                }
                if (e.getSlot() == 12){
                    m.getLoots().replace(target, RangsLoots.RARE);
                    p.closeInventory();
                    p.openInventory(gui.onMenuLootMobs(m));
                    return;
                }
                if (e.getSlot() == 13){
                    m.getLoots().replace(target, RangsLoots.EPIC);
                    p.closeInventory();
                    p.openInventory(gui.onMenuLootMobs(m));
                    return;
                }
                if (e.getSlot() == 14){
                    m.getLoots().replace(target, RangsLoots.LEGENDAIRE);
                    p.closeInventory();
                    p.openInventory(gui.onMenuLootMobs(m));
                    return;
                }
                if (e.getSlot() == 15){
                    m.getLoots().replace(target, RangsLoots.MHYTIQUE);
                    p.closeInventory();
                    p.openInventory(gui.onMenuLootMobs(m));
                    return;
                }
                if (e.getSlot() == 16){
                    m.getLoots().replace(target, RangsLoots.DIVIN);
                    p.closeInventory();
                    p.openInventory(gui.onMenuLootMobs(m));
                    return;
                }
            }
        }


        //Menu edition Suppression du loots
        if (e.getView().getType().equals(InventoryType.HOPPER) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + ChatColor.DARK_GRAY + "Loots Remove")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.HOPPER));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.HOPPER)) return;
            ItemStack mobs = e.getView().getItem(0);
            ItemStack target = e.getView().getItem(2);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 4) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onMenuLootMobsEdits(m, target));
                return;
            }
            if(it.getType().equals(Material.RED_CONCRETE)){
                assert m != null;
                m.getLoots().remove(target);
                p.closeInventory();
                p.openInventory(gui.onMenuLootMobs(m));
                return;
            }
        }

        //Menu edition Statistique
        if (e.getView().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + "Stats")){
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobs(m));
                return;
            }
            //modification des dégats
            if (it.getType().equals(Material.IRON_SWORD)){
                if (TchatInteract.getEditDamage().containsKey(p)) return;
                TchatInteract.getEditDamage().put(p, m);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_DAMAGE.get());
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
                p.closeInventory();
                return;
            }
            //Modification des pv
            if (it.getType().equals(Material.GOLDEN_APPLE)){
                if (TchatInteract.getEditHealth().containsKey(p)) return;
                TchatInteract.getEditHealth().put(p, m);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_HEALTH.get());
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
                p.closeInventory();
                return;
            }
            //Modification de la vitesse
            if (it.getType().equals(Material.IRON_BOOTS)){
                if (TchatInteract.getEditSpeed().containsKey(p)) return;
                TchatInteract.getEditSpeed().put(p, m);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_SPEED.get());
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
                p.closeInventory();
                return;
            }

            //Modification de la vitesse d'attack
            if (it.getType().equals(Material.WOODEN_SWORD)){
                if (TchatInteract.getEditAttackSpeed().containsKey(p)) return;
                TchatInteract.getEditAttackSpeed().put(p, m);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_ATTACK_SPEED.get());
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
                p.closeInventory();
                return;
            }

        }

        //Menu Edition Loot & Stuff
        if (e.getView().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + "Loots & Stuff")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobs(m));
                return;
            }
            //Modification du stuff
            if (it.getType().equals(Material.IRON_CHESTPLATE)) {
                p.closeInventory();
                p.openInventory(gui.onStuff(m));
                return;
            }
            //Modification du Loots
            if(it.getType().equals(Material.CHEST)){
                p.closeInventory();
                p.openInventory(gui.onMenuLootMobs(m));
                return;
            }
            //Modification des montures
            if(it.getType().equals(Material.LEATHER_HORSE_ARMOR)){
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsMonture(p, m));
                return;
            }
        }

        //Menu Edition Loot & Stuff
        if (e.getView().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + "Spawn")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobs(m));
                return;
            }

            //Modification du rang
            if (it.getType().equals(Material.SPAWNER)){
                if(TchatInteract.getEditRang().containsKey(p)) return;
                TchatInteract.getEditRang().put(p, m);
                TchatInteract.onSetRank(p);
                p.closeInventory();
                return;
            }
            //Selection du biome
            if(it.getType().equals(Material.OAK_SAPLING)){
                p.closeInventory();
                p.openInventory(gui.onMobBiome(m));
                return;
            }
            //Selection de la hauteur de spawn
            if(it.getType().equals(Material.LADDER)){
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsSpawnHeight(m));
                return;
            }
            //Selection du monde ou le mob spawn
            if(it.getType().equals(Material.PLAYER_HEAD)){
                p.closeInventory();
                p.openInventory(gui.onGuiEditMobsSpawnWorld(m));
                return;
            }
        }

        //Menu Edition spawn
        if (e.getView().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + "Spawn")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (e.getSlot() == 0 || e.getSlot() == 8) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobsSpawn(m));
                return;
            }
        }

        //Menu Edition Loot hauteur ou il peut sapwn
        if (e.getView().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + "Hauteur de Spawn")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            ItemStack target = e.getView().getItem(22);
            if (it == null) return;

            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 18) {
                p.closeInventory();
                assert m != null;

                p.openInventory(gui.onGuiEditMobsSpawn(m));
                return;
            }
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 36) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobsSpawnHeight(m));
                return;
            }

            if (target == null)return;

            if (e.getSlot() == 11 && it.getType().equals(Material.PLAYER_HEAD)){

                p.openInventory(gui.onGuiEditMobsSpawnHeightMaxOrMin(m, false));

            }
            if (e.getSlot() == 15 && it.getType().equals(Material.PLAYER_HEAD)){
                p.openInventory(gui.onGuiEditMobsSpawnHeightMaxOrMin(m, true));
            }
            if (it.getType().equals(Material.LIME_STAINED_GLASS_PANE)){
                int amount = it.getAmount();

                if (Objects.requireNonNull(target.getItemMeta()).getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"Hauteur maximun")){
                    assert m != null;
                    m.setHeight_max(Math.min(m.getHeight_max() + amount, 320));
                    e.getView().setItem(22, GUI.onHeightmax(m));
                }else {
                    assert m != null;
                    if (m.getHeight_min()+amount > m.getHeight_max()){
                        m.setHeight_min(m.getHeight_max());
                    }else {
                        m.setHeight_min(m.getHeight_min()+amount);
                    }
                    e.getView().setItem(22, GUI.onHeightmin(m));

                }
                return;
            }
            if (it.getType().equals(Material.RED_STAINED_GLASS_PANE)){
                int amount = it.getAmount();
                if (Objects.requireNonNull(target.getItemMeta()).getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE+"Hauteur minimun")){
                    assert m != null;

                    m.setHeight_min(Math.max(m.getHeight_min() - amount, -64));
                    e.getView().setItem(22, GUI.onHeightmin(m));
                }else {
                    assert m != null;
                    if (m.getHeight_max() - amount < m.getHeight_min()){
                        m.setHeight_max(m.getHeight_min());
                    }else {
                        m.setHeight_max(m.getHeight_max()-amount);
                    }
                    e.getView().setItem(22, GUI.onHeightmax(m));
                }
                return;
            }
        }

        if (e.getView().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + "Effect")) {
            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST));

            ItemStack it = e.getCurrentItem();
            if (it == null) return;
            if (!Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.CHEST)) return;
            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN + "", ""));
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                assert m != null;
                p.openInventory(gui.onGuiEditMobs(m));
                return;
            }

            //Selection de l'effets de mort
            if (it.getType().equals(Material.SKELETON_SKULL)){
                p.closeInventory();
                p.openInventory(gui.onDeathMenuEdit(m));
                return;
            }

            if (it.getType().equals(Material.GLASS_BOTTLE)){
                p.closeInventory();
                p.openInventory(gui.onSpawnPotionMobs(m));
                return;
            }

            if(it.getType().equals(Material.SPLASH_POTION)){
                p.closeInventory();
                p.openInventory(gui.onDamagePotionMobs(m));
                return;
            }
        }

        //Gui édition du mob selectioner
        String mobs = e.getView().getTitle().replace(Messages.getPrefix(), "");
        Mobs mobs1 = Mobs.getMobs(mobs);

        if (mobs1 != null){

            Player p = (Player) e.getWhoClicked();
            GUI gui = new GUI();

            e.setCancelled(true);
            ItemStack it = e.getCurrentItem();
            if (it == null)return;
            if (Objects.requireNonNull(e.getClickedInventory()).getType().equals(InventoryType.PLAYER)) return;
            if (it.getType().equals(Material.OAK_DOOR) && e.getSlot() == 45) {
                p.closeInventory();
                p.openInventory(gui.onMobMoreGUI(p, mobs1.getRank(), true));
            }

            if (e.getClickedInventory().getType().equals(InventoryType.CHEST)){

                //Menu des statistique
                if (it.getType().equals(Material.PAPER)){
                    p.closeInventory();
                    p.openInventory(gui.onGuiEditMobsStats(mobs1));
                    return;
                }

                //Menu condition de spawn
                if (it.getType().equals(Material.EGG)){
                    p.closeInventory();
                    p.openInventory(gui.onGuiEditMobsSpawn(mobs1));
                    return;
                }

                //Modification du stuff
                if(it.getType().equals(Material.IRON_CHESTPLATE)){
                    p.closeInventory();
                    p.openInventory(gui.onGuiEditMobsLootStuff(mobs1));
                    return;
                }

                //Menu condition de spawn
                if (it.getType().equals(Material.POTION)){
                    p.closeInventory();
                    p.openInventory(gui.onGuiEditMobsEffect(mobs1));
                    return;
                }

                //Supression du mob
                if (it.getType().equals(Material.BARRIER)){
                    p.closeInventory();
                    p.openInventory(gui.onDeleteMobsConfirm(mobs1));
                }

            }
        }
    }

    @EventHandler
    private void onCloseGuiEdition(InventoryCloseEvent e){
        if (e.getView().getTitle().equalsIgnoreCase(Messages.getPrefix() + org.bukkit.ChatColor.GRAY + "Stuff")){

            ItemStack mobs = e.getView().getItem(0);
            assert mobs != null;
            Mobs m = Mobs.getMobs(Objects.requireNonNull(mobs.getItemMeta()).getDisplayName().replace(ChatColor.GREEN+"", ""));
                ItemStack helmet = e.getView().getItem(20);
                ItemStack chesplate = e.getView().getItem(21);
                ItemStack leggins = e.getView().getItem(22);
                ItemStack boots = e.getView().getItem(23);
                ItemStack hand = e.getView().getItem(24);

                assert m != null;
                m.setHelmet(Objects.requireNonNullElseGet(helmet, () -> new ItemStack(Material.AIR)));
                m.setChestplate(Objects.requireNonNullElseGet(chesplate, () -> new ItemStack(Material.AIR)));
                m.setLeggings(Objects.requireNonNullElseGet(leggins, () -> new ItemStack(Material.AIR)));
                m.setBoots(Objects.requireNonNullElseGet(boots, () -> new ItemStack(Material.AIR)));
                m.setHainMand(Objects.requireNonNullElseGet(hand, () -> new ItemStack(Material.AIR)));

        }
    }

    public int getStringToInt(String pages){
        int page;
        try  {
            page = Integer.parseInt(pages);
        }catch (NumberFormatException io){
            return -1;
        }
        return page;
    }

    public static String getNextSelectBiomeAll(List<String> all){
        if (all.contains("All")){
            return "All Nether";
        }else if (all.contains("All Nether")){
            return "All End";
        }else if (all.contains("All End")){
            return "All Overwolrd";
        }else if(all.contains("All Overwolrd")){
            return "All Overworld sauf Ocean";
        }else if (all.contains("All Overworld sauf Ocean")){
            return "All sauf Ocean";
        }else if (all.contains("All sauf Ocean")){
            return "All Ocean";
        }else if (all.contains("All Ocean")){
        return "All";
    }
        return "All";
    }

    public static String getNextSelectWorldAll(List<String> all){
        if (all.contains("All")){
            return "All Nether";
        }else if (all.contains("All Nether")){
            return "All End";
        }else if (all.contains("All End")) {
            return "All Overwolrd";
        }
        return "All";
    }

    private static void onremoveAllWorld(Mobs m){
        m.getWorldspawn().remove("All");
        m.getWorldspawn().remove("All Nether");
        m.getWorldspawn().remove("All End");
        m.getWorldspawn().remove("All Overwolrd");
        m.getWorldspawn().remove("Aucun");
    }

    private static void onremoveAllBiome(Mobs m){
        m.getBiomespawn().remove("All");
        m.getBiomespawn().remove("All Nether");
        m.getBiomespawn().remove("All End");
        m.getBiomespawn().remove("All Overwolrd");
        m.getBiomespawn().remove("All Overworld sauf Ocean");
        m.getBiomespawn().remove("All sauf Ocean");
        m.getBiomespawn().remove("Aucun");
    }

}

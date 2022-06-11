package fr.maesia.mob.listener;

import fr.maesia.mob.mob.Mobs;

import fr.maesia.mob.mob.rangs.RangsLoots;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class DeathMob implements Listener {

    public static HashMap<UUID, Mobs> deatheffect = new HashMap<>();
    public static HashMap<UUID, Mobs> lootTable = new HashMap<>();

    @EventHandler
    private void onDeathMobEffect(EntityDeathEvent e){

        for(Mobs mobs : Mobs.mobsList){
            Mobs.mobsListUuid.get(mobs).remove(e.getEntity().getUniqueId());
        }
        Player killer = e.getEntity().getKiller();



        int looting = 0;
        if (killer != null){
            if (Objects.requireNonNull(killer.getEquipment()).getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
                looting = Objects.requireNonNull(killer.getEquipment().getItemInMainHand().getItemMeta()).getEnchants().get(Enchantment.LOOT_BONUS_MOBS) * 7;
            }
        }

        if(lootTable.containsKey(e.getEntity().getUniqueId())){
            if (Bukkit.getPluginManager().getPlugin("Skils") == null){

                ondropsmobs(e.getEntity().getLocation(), e.getEntity().getUniqueId(), 90, looting);

            }else {
                /*
                TODO: Fix skills dependency.
                if (!FEC.isExistMob(FEC.monster, lootTable.get(e.getEntity().getUniqueId()))){
                    ondropsmobs(e.getEntity().getLocation(), e.getEntity().getUniqueId(), 90, looting);
                }
                if (!FEC.isExistMob(FEC.hunters, lootTable.get(e.getEntity().getUniqueId()))){
                    ondropsmobs(e.getEntity().getLocation(), e.getEntity().getUniqueId(), 90, looting);
                }
                 */
            }
        }
        if (deatheffect.isEmpty())return;
        if (deatheffect.containsKey(e.getEntity().getUniqueId())){
            Mobs mobs = deatheffect.get(e.getEntity().getUniqueId());
            if (mobs.getDeathEffect().getDeathSpawn().isActif()){
               SpawnMob.onSpawnMobs(mobs.getDeathEffect().getDeathSpawn().getMobs(), mobs.getDeathEffect().getDeathSpawn().getCount(),e.getEntity().getLocation());
            }
            if (mobs.getDeathEffect().getDeathExplotion().isActif()){
                Objects.requireNonNull(e.getEntity().getLocation().getWorld()).createExplosion(e.getEntity().getLocation(), mobs.getDeathEffect().getDeathExplotion().getPower(), mobs.getDeathEffect().getDeathExplotion().isFire(), mobs.getDeathEffect().getDeathExplotion().isDestroy());
            }
            if(mobs.getDeathEffect().getDeathPotionEffect().isActif()){
                if (e.getEntity().getKiller() != null){
                   Player p = e.getEntity().getKiller();
                   PotionEffect potionEffect = new PotionEffect(mobs.getDeathEffect().getDeathPotionEffect().getPotionEffect(), mobs.getDeathEffect().getDeathPotionEffect().getDuration(), mobs.getDeathEffect().getDeathPotionEffect().getPower(), false, false);
                   p.addPotionEffect(potionEffect.getType().createEffect(mobs.getDeathEffect().getDeathPotionEffect().getDuration()*20, mobs.getDeathEffect().getDeathPotionEffect().getPower()-1));
                }

            }
            deatheffect.remove(e.getEntity().getUniqueId());
        }

    }

    public static void ondropsmobs(Location location, UUID uuid, int proba, int bonus){
        onDropsLoot(location, lootTable.get(uuid), proba, bonus, uuid);
    }

    private static void onDropsLoot(Location location, Mobs mobs, int proba, int  bonus, UUID uuid){
        Random random = new Random();
        int brut = proba/2;
        int dice = random.nextInt(brut + 25 );
        int result = brut+dice;

        if (result> 35){
            if (mobs == null) return;
            if (mobs.getLoots().size() > 0){
                int size = mobs.getLoots().size();
                int stop = random.nextInt(size);

                int turn = 0;
                for(Map.Entry<ItemStack, RangsLoots> items : mobs.getLoots().entrySet()){
                    if (turn == stop){
                        int chance = random.nextInt(100);
                        if (chance - bonus < items.getValue().getProba() ){
                            Objects.requireNonNull(location.getWorld()).dropItem(location.add(0.0,0.2,0.0), items.getKey());
                            onDropsLoot(location, mobs, brut, bonus /2, uuid);
                        }else {
                            lootTable.remove(uuid);
                        }
                        return;

                    }
                    turn++;
                }
            }else {
                lootTable.remove(uuid);
            }
        }
    }
}


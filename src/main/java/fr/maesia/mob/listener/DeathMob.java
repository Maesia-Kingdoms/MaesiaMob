package fr.maesia.mob.listener;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.mob.Mobs;

import fr.maesia.mob.mob.rangs.RangsLoots;
import fr.maesia.mob.utils.CustomEvents.DeathCustomMobEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class DeathMob implements Listener {

    @EventHandler
    private void onDeathMobEffect(EntityDeathEvent e){

        if(!e.getEntity().getPersistentDataContainer().has(new NamespacedKey(MaesiaMob.getInstance(), "idMob"), PersistentDataType.STRING)) return;
        String key =  e.getEntity().getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "idMob"), PersistentDataType.STRING);
        if(key == null) return;
        UUID uuid = UUID.fromString(key);
        Mobs mobs = Mobs.getMobs(uuid);

        if (mobs == null)return;
        Player killer = e.getEntity().getKiller();


        if (killer == null) return;
        int looting = 0;


        if (Objects.requireNonNull(killer.getEquipment()).getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
            looting = Objects.requireNonNull(killer.getEquipment().getItemInMainHand().getItemMeta()).getEnchants().get(Enchantment.LOOT_BONUS_MOBS) * 7;
        }

        if (!mobs.getLoots().isEmpty()){
            DeathCustomMobEvent deathCustomMobEvent = new DeathCustomMobEvent(mobs, killer, mobs.getLoots());
            Bukkit.getServer().getPluginManager().callEvent(deathCustomMobEvent);
            if(deathCustomMobEvent.isCancelled()) return;
            ondropsmobs(e.getEntity().getLocation(), uuid, 90, looting);
        }


        if (!mobs.getDeathEffect().getDeathPotionEffect().isActif() && !mobs.getDeathEffect().getDeathExplotion().isActif() && !mobs.getDeathEffect().getDeathSpawn().isActif()) return;

        if (mobs.getDeathEffect().getDeathSpawn().isActif()){
            SpawnMob.onSpawnMobs(mobs.getDeathEffect().getDeathSpawn().getMobs(), mobs.getDeathEffect().getDeathSpawn().getCount(),e.getEntity().getLocation());
        }
        if (mobs.getDeathEffect().getDeathExplotion().isActif()){
            Objects.requireNonNull(e.getEntity().getLocation().getWorld()).createExplosion(e.getEntity().getLocation(), mobs.getDeathEffect().getDeathExplotion().getPower(), mobs.getDeathEffect().getDeathExplotion().isFire(), mobs.getDeathEffect().getDeathExplotion().isDestroy());
        }
        if(mobs.getDeathEffect().getDeathPotionEffect().isActif()){
            PotionEffect potionEffect = new PotionEffect(mobs.getDeathEffect().getDeathPotionEffect().getPotionEffect(), mobs.getDeathEffect().getDeathPotionEffect().getDuration(), mobs.getDeathEffect().getDeathPotionEffect().getPower(), false, false);
            killer.addPotionEffect(potionEffect.getType().createEffect(mobs.getDeathEffect().getDeathPotionEffect().getDuration()*20, mobs.getDeathEffect().getDeathPotionEffect().getPower()-1));
        }
    }



    public static void ondropsmobs(Location location, UUID uuid, int proba, int bonus){
        onDropsLoot(location, Mobs.getMobs(uuid), proba, bonus);
    }

    private static void onDropsLoot(Location location, Mobs mobs, int proba, int  bonus){
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
                            onDropsLoot(location, mobs, brut, bonus /2);
                        }
                        return;

                    }
                    turn++;
                }
            }
        }
    }
}


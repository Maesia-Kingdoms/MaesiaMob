package fr.maesia.mob.listener;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.mob.Mobs;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.UUID;


public class CombatsMobs implements Listener {

    public static HashMap<UUID, Mobs> Combatreact = new HashMap<>();
    @EventHandler
    public void onDamageByMobsCustom(EntityDamageByEntityEvent e){


        if (Combatreact.containsKey(e.getEntity().getUniqueId())){
            Entity victim = e.getEntity();
            if (Combatreact.get(e.getEntity().getUniqueId()).getEntityType().equals(EntityType.PILLAGER)){
                Pillager pillager = (Pillager) victim;
                pillager.setTarget((LivingEntity) e.getDamager());
            }
            if (Combatreact.get(e.getEntity().getUniqueId()).getEntityType().equals(EntityType.GIANT)){
                Giant giant = (Giant) victim;
                giant.setTarget((LivingEntity) e.getDamager());
            }

        }

        if(!e.getDamager().getPersistentDataContainer().has(new NamespacedKey(MaesiaMob.getInstance(), "idMob"), PersistentDataType.STRING)) return;
        String key = e.getDamager().getPersistentDataContainer().get(new NamespacedKey(MaesiaMob.getInstance(), "idMob"), PersistentDataType.STRING);
        if(key == null) return;
        UUID uuid = UUID.fromString(key);
        Mobs mobs = Mobs.getMobs(uuid);
        if(mobs == null)return;
        if(mobs.getEffectMobsDamage().isActif() && e.getEntity() instanceof Player victim){
            int time = mobs.getEffectMobsDamage().getDuration();
            PotionEffect potionEffect = new PotionEffect(mobs.getEffectMobsDamage().getPotionEffect(),time , mobs.getEffectMobsDamage().getPower(), false, false);
            if (time > -1 ){
                victim.addPotionEffect(potionEffect.getType().createEffect(time*20, mobs.getEffectMobsDamage().getPower()-1));
            }else {
                victim.addPotionEffect(potionEffect.getType().createEffect(99999999*20, mobs.getEffectMobsDamage().getPower()-1));
            }

        }
    }
}

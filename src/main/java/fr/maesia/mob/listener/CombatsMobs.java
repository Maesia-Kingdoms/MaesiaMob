package fr.maesia.mob.listener;

import fr.maesia.mob.mob.Mobs;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.UUID;


public class CombatsMobs implements Listener {

    public static HashMap<UUID, Mobs> Combateffect = new HashMap<>();
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

        if(Combateffect.containsKey(e.getDamager().getUniqueId()) && e.getEntity() instanceof Player){
            Player victim = (Player) e.getEntity();

            Mobs attacker = Combateffect.get(e.getDamager().getUniqueId());

            int time = attacker.getEffectMobsDamage().getDuration();
            PotionEffect potionEffect = new PotionEffect(attacker.getEffectMobsDamage().getPotionEffect(),time , attacker.getEffectMobsDamage().getPower(), false, false);
            if (time > -1 ){
                victim.addPotionEffect(potionEffect.getType().createEffect(time*20, attacker.getEffectMobsDamage().getPower()-1));

            }else {
                victim.addPotionEffect(potionEffect.getType().createEffect(99999999*20, attacker.getEffectMobsDamage().getPower()-1));
            }

        }
    }
}

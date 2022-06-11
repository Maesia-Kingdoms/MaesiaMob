package fr.maesia.mob.listener;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.Rang;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;

import org.bukkit.block.Biome;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class SpawnMob implements Listener {

    @EventHandler
    public void onSpawnMob(CreatureSpawnEvent e) {
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) return;

        List<Mobs> mobsList = new ArrayList<>();

        Rang rang = Rang.F;

        //proba du rang
        Random random = new Random();
        float r = random.nextFloat();

        if (r < 0.01){
            rang = Rang.S;
        }else if(r>= 0.01 && r < 0.1){
            rang = Rang.A;
        }
        else if(r>= 0.1 && r < 0.15){
            rang = Rang.B;
        }
        else if(r>= 0.15 && r < 0.30){
            rang = Rang.C;
        }
        else if(r>= 0.30 && r < 0.50){
            rang = Rang.D;
        }
        else if(r>= 0.50 && r < 0.70){
            rang = Rang.E;
        }

        //filtre des mobs : check le type, Le rang & le biome
        for (Mobs m : Mobs.mobsList) {
            if (m.getRank().equals(rang) && (m.getBiomespawn().contains("All") || m.getBiomespawn().contains(e.getLocation().getBlock().getBiome().name()) || getBiomeAll(m.getBiomespawn(), e.getLocation().getBlock().getBiome().name())) && (m.getHeight_max() <= e.getLocation().getBlockY() && m.getHeight_min() >= e.getLocation().getBlockY()) && (m.getWorldspawn().contains("All") || m.getWorldspawn().contains(Objects.requireNonNull(e.getLocation().getWorld()).getName()) || getWorldAll(m.getWorldspawn(), e.getLocation().getWorld().getEnvironment()) ) ){
                mobsList.add(m);
            }
        }
        if (mobsList.isEmpty()) return;
        if (random.nextFloat() > 0.15F){
            return;
        }
        e.setCancelled(true);
        int dice = random.nextInt(mobsList.size());
        Mobs m = mobsList.get(dice);
        MaesiaMob.getInstance().getLogger().info("Tentative de spawn " +m.getName());

        onSpawnMobs(m, 1, e.getLocation());


    }
    public static void onCustomMob(LivingEntity lE, String Name, UUID uuid, double vie, double degats,  float speeddeplacement, double attackspeed) {
        if (vie != 0) {
            AttributeInstance v = lE.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert v != null;
            v.setBaseValue(1.0);
            AttributeModifier zm = new AttributeModifier(uuid, Attribute.GENERIC_MAX_HEALTH.toString(), vie-1.0, AttributeModifier.Operation.ADD_NUMBER);
            v.addModifier(zm);
            lE.setHealth(v.getValue());

        }

        if(degats != 0) {
            AttributeInstance d = lE.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            if (d != null){
                d.setBaseValue(1.0);
                AttributeModifier zd = new AttributeModifier(uuid, Attribute.GENERIC_ATTACK_DAMAGE.toString(), degats-1.0, AttributeModifier.Operation.ADD_NUMBER);
                d.addModifier(zd);
            }
        }

        if(attackspeed != 0) {
            AttributeInstance sa = lE.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            if (sa != null){
                sa.setBaseValue(1.0);
                AttributeModifier zs = new AttributeModifier(uuid, Attribute.GENERIC_ATTACK_SPEED.toString(), attackspeed-1.0, AttributeModifier.Operation.ADD_NUMBER);
                sa.addModifier(zs);
            }
        }

        if(speeddeplacement != 0) {
            AttributeInstance s = lE.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if (s!= null){
                s.setBaseValue(0.0);
                AttributeModifier zs = new AttributeModifier(uuid, Attribute.GENERIC_MOVEMENT_SPEED.toString(), speeddeplacement, AttributeModifier.Operation.ADD_NUMBER);
                s.addModifier(zs);
            }

        }

        if (Name != null) {
            lE.setCustomName(Name);
            lE.setCustomNameVisible(true);
        }
        lE.setAI(true);

    }

    public static void onSpawnMobs(Mobs mobs, int count, Location loc) {
        for (int i = 0; i < count; i++) {
            onMobs(mobs, 0,loc);
        }
    }

    private static Entity onMobs(Mobs mobs, int count,Location loc) {

        Entity entity = Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, mobs.getEntityType());


        Mobs.mobsListUuid.get(mobs).add(entity.getUniqueId());

        LivingEntity newmob = (LivingEntity) entity;
        SpawnMob.onCustomMob(newmob, mobs.getRank().getColor() + mobs.getName(), entity.getUniqueId(), mobs.getHealth(), mobs.getDamage(), mobs.getSpeed(), mobs.getAttackspeed());

        if (mobs.getDeathEffect().getDeathSpawn().isActif() || mobs.getDeathEffect().getDeathExplotion().isActif() || mobs.getDeathEffect().getDeathPotionEffect().isActif()) {
            DeathMob.deatheffect.put(entity.getUniqueId(), mobs);
        }


        if (mobs.getEntityType().equals(EntityType.PILLAGER) || mobs.getEntityType().equals( EntityType.GIANT)){
            CombatsMobs.Combatreact.put(entity.getUniqueId(), mobs);
        }

        if (mobs.getEffectMobsDamage().isActif()) {
            CombatsMobs.Combateffect.put(entity.getUniqueId(), mobs);
        }

        if (!mobs.getLoots().isEmpty()) {
            DeathMob.lootTable.put(entity.getUniqueId(), mobs);
        }


        Objects.requireNonNull(((LivingEntity) entity).getEquipment()).setHelmet(mobs.getHelmet());
        ((LivingEntity) entity).getEquipment().setChestplate(mobs.getChestplate());
        ((LivingEntity) entity).getEquipment().setLeggings(mobs.getLeggings());
        ((LivingEntity) entity).getEquipment().setBoots(mobs.getBoots());
        ((LivingEntity) entity).getEquipment().setItemInMainHand(mobs.getHainMand());
        ((LivingEntity) entity).getEquipment().setItemInOffHand(mobs.getOffMand());

        if (mobs.getEffectMobsSpawn().isActif()) {
            PotionEffect potionEffect = new PotionEffect(mobs.getEffectMobsSpawn().getPotionEffect(), mobs.getEffectMobsSpawn().getDuration(), mobs.getEffectMobsSpawn().getPower(), false, false);
            if (mobs.getEffectMobsSpawn().getDuration() > 0) {
                ((LivingEntity) entity).addPotionEffect(potionEffect.getType().createEffect(mobs.getEffectMobsSpawn().getDuration() * 20, mobs.getEffectMobsSpawn().getPower() - 1));
            } else {
                ((LivingEntity) entity).addPotionEffect(potionEffect.getType().createEffect(99999999 * 20, mobs.getEffectMobsSpawn().getPower() - 1));
            }

        }
        if (mobs.getPassager() != null){
            Mobs passager = Mobs.getMobsUuid(mobs.getPassager());
            if (passager == null) {
                mobs.setPassager(null);
                return entity;
            }
            if (count >= 10) return entity;
            onMobs(passager, count+1, loc).addPassenger(entity);
        }
        return entity;
    }





    public static boolean getBiomeAll(List<String> all, String biome){
      if (all.contains("All Nether")){
            return onAllinBiomeNether(biome);
        }else if (all.contains("All End")){
            return onAllinBiomeEnd(biome);
        }else if(all.contains("All Overwolrd")){
            return !(onAllinBiomeEnd(biome) && onAllinBiomeNether(biome));
        }else if (all.contains("All Overworld sauf Ocean")){
            return !(onAllinBiomeEnd(biome) && onAllinBiomeNether(biome) && onAllinBiomeAquatic(biome));
        }else if (all.contains("All sauf Ocean")){
            return !onAllinBiomeAquatic(biome);
        }else if (all.contains("All Ocean")){
          return onAllinBiomeAquatic(biome);
      }
        return false;
    }

    public static boolean getWorldAll(List<String> all, World.Environment typeWorld){
        if (all.contains("All Nether")){
            return  (World.Environment.NETHER.equals(typeWorld));
        }else if (all.contains("All End")){
            return World.Environment.THE_END.equals(typeWorld);
        }else if(all.contains("All Overwolrd")){
            return World.Environment.NORMAL.equals(typeWorld);
        }
        return false;
    }

    private static boolean onAllinBiomeNether(String biome){
        return (Biome.NETHER_WASTES.name().equalsIgnoreCase(biome) || Biome.SOUL_SAND_VALLEY.name().equalsIgnoreCase(biome) || Biome.CRIMSON_FOREST.name().equalsIgnoreCase(biome) || Biome.WARPED_FOREST.name().equalsIgnoreCase(biome) || Biome.BASALT_DELTAS.name().equalsIgnoreCase(biome));
    }

    private static boolean onAllinBiomeEnd(String biome){
        return (Biome.THE_END.name().equalsIgnoreCase(biome) || Biome.SMALL_END_ISLANDS.name().equalsIgnoreCase(biome) || Biome.END_MIDLANDS.name().equalsIgnoreCase(biome) || Biome.END_HIGHLANDS.name().equalsIgnoreCase(biome) || Biome.END_BARRENS.name().equalsIgnoreCase(biome));
    }

    private static boolean onAllinBiomeAquatic(String biome){
        return (Biome.RIVER.name().equalsIgnoreCase(biome) || Biome.FROZEN_RIVER.name().equalsIgnoreCase(biome) || Biome.WARM_OCEAN.name().equalsIgnoreCase(biome) || Biome.LUKEWARM_OCEAN.name().equalsIgnoreCase(biome) || Biome.OCEAN.name().equalsIgnoreCase(biome) || Biome.DEEP_OCEAN.name().equalsIgnoreCase(biome)|| Biome.COLD_OCEAN.name().equalsIgnoreCase(biome)|| Biome.DEEP_COLD_OCEAN.name().equalsIgnoreCase(biome)|| Biome.FROZEN_OCEAN.name().equalsIgnoreCase(biome)|| Biome.DEEP_FROZEN_OCEAN.name().equalsIgnoreCase(biome));
    }



}

package fr.maesia.mob;

import fr.maesia.mob.ppe.PotionEffectMobs;
import fr.maesia.mob.rangs.Rang;
import fr.maesia.mob.rangs.RangsLoots;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Mobs {


    private final EntityType entityType;
    private final String name;
    private final UUID id;
    private UUID passager;
    private Integer height_max;
    private Integer height_min;
    private Double health;
    private Double damage;
    private Float speed;
    private Double attackspeed;
    private Rang rank;
    private List<String> biomespawn;
    private List<String> worldspawn;
    private final DeathEffect deathEffect;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private ItemStack hainMand;
    private ItemStack offMand;
    private final HashMap<ItemStack, RangsLoots> loots;
    private final PotionEffectMobs EffectMobsSpawn;
    private final PotionEffectMobs EffectMobsDamage;

    public static final List<Mobs> mobsList = new ArrayList<>();
    public static final HashMap<Mobs, List<UUID>> mobsListUuid = new HashMap<>();

    public Mobs(EntityType entityType, String name, UUID uuid){
        this.entityType = entityType;
        this.name = name;
        this.id = uuid;
        this.height_max = 320;
        this.height_min = -64;
        this.health = 10.0;
        this.damage = 2.0;
        this.speed = 0.3F;
        this.attackspeed = 1.5;
        this.rank = Rang.F;
        this.passager = null;
        this.biomespawn = new ArrayList<>();
        biomespawn.add("All");
        this.worldspawn = new ArrayList<>();
        worldspawn.add ("All");
        this.helmet = null;
        this.leggings = null;
        this.chestplate = null;
        this.boots = null;
        this.hainMand = null;
        this.offMand = null;
        this.deathEffect = new DeathEffect();
        this.EffectMobsSpawn = new PotionEffectMobs();
        this.EffectMobsDamage = new PotionEffectMobs();
        this.loots = new HashMap<>();
        List<UUID> list = new ArrayList<>();
        mobsListUuid.put(this, list);
        mobsList.add(this);
    }

    public UUID getPassager() {
        return passager;
    }

    public void setPassager(UUID passager) {
        this.passager = passager;
    }

    public List<String> getWorldspawn() {
        return worldspawn;
    }

    public void setWorldspawn(List<String> worldspawn) {
        this.worldspawn = worldspawn;
    }

    public Integer getHeight_max() {
        return height_max;
    }

    public void setHeight_max(Integer height_max) {
        this.height_max = height_max;
    }

    public Integer getHeight_min() {
        return height_min;
    }

    public void setHeight_min(Integer height_min) {
        this.height_min = height_min;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Double getHealth() {
        return health;
    }

    public Double getDamage() {
        return damage;
    }

    public void setHealth(Double healt) {
        this.health = healt;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public Rang getRank() {
        return rank;
    }

    public void setRank(Rang rank) {
        this.rank = rank;
    }

    public List<String> getBiomespawn() {
        return biomespawn;
    }

    public void setBiomespawn(List<String> biomespawn) {
        this.biomespawn = biomespawn;
    }

    public Double getAttackspeed() {
        return attackspeed;
    }

    public void setAttackspeed(Double attackspeed) {
        this.attackspeed = attackspeed;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public static Mobs getMobsUuid(UUID uuid){
        for (Mobs mobs : mobsList){
            if (mobs.getId().equals(uuid)){
                return mobs;
            }
        }
        return null;
    }

    public static Mobs getMobs(String name){
        for(Mobs mobs : mobsList){
            if (mobs.getName().equalsIgnoreCase(name)){
                return mobs;
            }
        }
        return null;
    }

    public static void removeMobs(Mobs mobs){
        mobsList.remove(mobs);
    }

    public HashMap<ItemStack, RangsLoots> getLoots() {
        return loots;
    }

    //Death Effects

    public DeathEffect getDeathEffect() {
        return deathEffect;
    }

    //Stuff mobs
    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;

    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        if (chestplate.getType().equals(Material.CHAINMAIL_CHESTPLATE) || chestplate.getType().equals(Material.NETHERITE_CHESTPLATE) || chestplate.getType().equals(Material.DIAMOND_CHESTPLATE) || chestplate.getType().equals(Material.IRON_CHESTPLATE) || chestplate.getType().equals(Material.GOLDEN_CHESTPLATE) || chestplate.getType().equals(Material.CHAINMAIL_LEGGINGS) || chestplate.getType().equals(Material.LEATHER_CHESTPLATE)){
            this.chestplate = chestplate;
        }else {
            this.chestplate = null;
        }

    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggins) {
        if (leggins.getType().equals(Material.CHAINMAIL_LEGGINGS) || leggins.getType().equals(Material.NETHERITE_LEGGINGS) || leggins.getType().equals(Material.DIAMOND_LEGGINGS) || leggins.getType().equals(Material.IRON_LEGGINGS) || leggins.getType().equals(Material.GOLDEN_LEGGINGS) || leggins.getType().equals(Material.CHAINMAIL_LEGGINGS) || leggins.getType().equals(Material.LEATHER_LEGGINGS)){
            this.leggings = leggins;
        }else {
            this.leggings = null;
        }

    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        if (boots.getType().equals(Material.CHAINMAIL_BOOTS) || boots.getType().equals(Material.NETHERITE_BOOTS) || boots.getType().equals(Material.DIAMOND_BOOTS) || boots.getType().equals(Material.IRON_BOOTS) || boots.getType().equals(Material.GOLDEN_BOOTS) || boots.getType().equals(Material.CHAINMAIL_BOOTS) || boots.getType().equals(Material.LEATHER_BOOTS)){
            this.boots = boots;
        }else {
            this.boots = null;
        }
    }

    public ItemStack getHainMand() {
        return hainMand;
    }

    public void setHainMand(ItemStack hainmand) {
        this.hainMand = hainmand;
    }

    public ItemStack getOffMand() {
        return offMand;
    }

    public void setOffMand(ItemStack offMand) {
        this.offMand = offMand;
    }

    public PotionEffectMobs getEffectMobsSpawn() {
        return EffectMobsSpawn;
    }

    public PotionEffectMobs getEffectMobsDamage() {
        return EffectMobsDamage;
    }


    public Mobs onUuidMobs(UUID uuid){
        for (Map.Entry<Mobs, List<UUID>> mobs : mobsListUuid.entrySet()){
            if (mobs.getValue().contains(uuid)) return mobs.getKey();
        }
        return null;
    }
}

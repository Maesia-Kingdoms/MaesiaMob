package fr.maesia.mob.spawner;

import fr.maesia.mob.MaesiaMob;
import fr.maesia.mob.listener.SpawnMob;
import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.Rang;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Spawner {

    private final UUID id;
    private String name;
    private final Set<UUID> mobs;

    private final Set<UUID> mobsSpawn;

    private int countSpawn;
    private int limit;

    private int spawnMin;

    private int spawnMax;

    private Long delay;
    private Long lastSpawn;
    private final Location location;
    private int radius;

    private static final Set<Spawner> spawnerList = new HashSet<>();

    public Spawner( String name, Location location) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.radius = 3;
        this.limit = 1;
        this.delay = 60*1000L;
        this.mobs= new HashSet<>();
        this.mobsSpawn= new HashSet<>();
        this.countSpawn = 0;
        this.lastSpawn = -1L;
        this.spawnMin = 1;
        this.spawnMax = 1;
        spawnerList.add(this);
    }

    public Spawner( String name, Location location, UUID id) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.radius = 3;
        this.limit = 1;
        this.delay = 60*1000L;
        this.mobs= new HashSet<>();
        this.mobsSpawn= new HashSet<>();
        this.countSpawn = 0;
        this.lastSpawn = -1L;
        this.spawnMin = 1;
        this.spawnMax = 1;
        spawnerList.add(this);
    }

    public static Spawner getId(UUID id){
        if (getSpawnerList().isEmpty())return null;
        for(Spawner spawner : getSpawnerList()) if (spawner.getId().equals(id))return spawner;
        return null;
    }

    public static Set<Spawner> getSpawnerList() {
        return spawnerList;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public int getRadius() {
        return radius +1;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Set<UUID> getMobs() {
        return mobs;
    }

    public static void removeMob(Mobs mobs){
        removeMob(mobs.getId());
    }
    public static void removeMob(UUID mobs){
        for(Spawner spawner : spawnerList){
            spawner.getMobs().remove(mobs);
        }
    }

    public void changeStatus(Mobs mobs){
        changeStatus(mobs.getId());
    }
    public void changeStatus(UUID mobs){
        if(getMobs().contains(mobs)) {
            getMobs().remove(mobs);
            return;
        }
        getMobs().add(mobs);
    }

    public Set<UUID> getMobsSpawn() {
        return mobsSpawn;
    }

    public int getCountSpawn() {
        return countSpawn;
    }

    public void addCountSpawn(int count) {
        this.countSpawn += count;
    }

    public void setCountSpawn(int count) {
        this.countSpawn = count;
    }

    public int getSpawnMin() {
        return spawnMin;
    }

    public void setSpawnMin(int spawnMin) {
        this.spawnMin = spawnMin;
    }

    public int getSpawnMax() {
        return spawnMax;
    }

    public void setSpawnMax(int spawnMax) {
        this.spawnMax = spawnMax;
    }

    public List<String> getNameMob(){
        List<String> stringList = new ArrayList<>();
        if (getMobs().isEmpty())return stringList;
        for(UUID uuid: getMobs()){
            if (Mobs.getMobs(uuid) != null)stringList.add(Objects.requireNonNull(Mobs.getMobs(uuid)).getName());
        }
        return stringList;
    }
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;

    }

    public Long getDelay() {
        return delay;
    }
    public static String getDelayTemps(Long delay) {

            if (delay== 1000L * 60) {
                return "1 minute";
            } else if (delay == 1000L * 60 * 5) {
                return "5 minutes";
            } else if (delay == 1000L * 60 * 10) {
                return "10 minutes";
            } else if (delay == 1000L * 60 * 15) {
                return "15 minutes";
            } else if (delay == 1000L * 60 * 30) {
                return "30 minutes";
            } else if (delay == 1000L * 60 * 60) {
                return "1 Heure";
            } else if (delay == 1000L * 60 * 60 * 2) {
                return "2 Heures";
            }
            return "Infinie";

    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Long getLastSpawn() {
        return lastSpawn;
    }

    public void setLastSpawn(Long lastSpawn) {
        this.lastSpawn = lastSpawn;
    }

    public void getEntitiesAroundPoint() {
        updateList();
        if(getLocation().getWorld() == null || getLocation().getWorld().getEntities().isEmpty())return;
        for (Entity entity : Objects.requireNonNull(getLocation().getWorld()).getEntities()) {
            if (entity.getWorld() != getLocation().getWorld()) continue;
            if (entity instanceof Player && ((Player) entity).getGameMode().equals(GameMode.SPECTATOR)) continue; //spectators OFF
            if (entity.getLocation().distanceSquared(getLocation()) <= getRadius() * getRadius()) {

               if(System.currentTimeMillis() >= getLastSpawn()){
                   if (getMobs().isEmpty() || getMobsSpawn().size() >= getLimit()) return;
                   spawn();
                   getLocation().getWorld().spawnParticle(Particle.FLAME, getLocation(), 7, 0.0, 0.2 , 0.0, 0.04);

               }
            }
        }
    }

    private void updateList(){
        Set<UUID> list = new HashSet<>();
        for(UUID uuid : getMobsSpawn()){
            Entity entity =  Bukkit.getEntity(uuid);
            if (entity == null) list.add(uuid);
        }
        getMobsSpawn().removeAll(list);
    }
    public static HashSet<Material> bad_blocks = new HashSet<>();

    static {
        bad_blocks.add(Material.LAVA);
        bad_blocks.add(Material.FIRE);
        bad_blocks.add(Material.CACTUS);
        bad_blocks.add(Material.WATER);
        bad_blocks.add(Material.MAGMA_BLOCK);
    }
    public void spawn() {
        Random dice = new Random();
        int count = getSpawnMin();
        if (getSpawnMin() != getSpawnMax()) count = dice.nextInt(getSpawnMin(), getSpawnMax());
        for(int i = 0; i  < count; i++){
            Mobs mobs = randomMobs();
            if (mobs == null) return;
            Location location = randomLocation(0);
            if (location == null) return;
            Entity entity = SpawnMob.onSpawnerSpawnMob(mobs,0, location);
            entity.getPersistentDataContainer().set(new NamespacedKey(MaesiaMob.getInstance(), "SpawnerId"), PersistentDataType.STRING, getId().toString());
            getMobsSpawn().add(entity.getUniqueId());
            setLastSpawn(System.currentTimeMillis()+getDelay());
            addCountSpawn(1);
        }
    }

    private Mobs randomMobs(){
        if (getMobs().isEmpty()) return null;
        for(UUID uuid : getMobs()){
            Mobs mobs =  Mobs.getMobs(uuid);
            if (mobs == null){
                removeMob(uuid);
                return randomMobs();
            }
            if (getMobs().size() == 1) return mobs;
            if (mobs.getRank().equals(Rang.randomRang()))return mobs;
        }
        return randomMobs();
    }

    private Location randomLocation(int turn){
        int turnNext = turn+1;
        Random dice = new Random();
        int x = dice.nextInt(getLocation().getBlockX() - getRadius(), getLocation().getBlockX() + getRadius());
        int y = dice.nextInt(getLocation().getBlockY() -(getRadius()/5), getLocation().getBlockY()+ (getRadius()/2));
        int z = dice.nextInt(getLocation().getBlockZ() - getRadius(), getLocation().getBlockZ() + getRadius());
        Location check = new Location(getLocation().getWorld(), x, y,z);
        Block cblock = Objects.requireNonNull(check.getWorld()).getBlockAt(x, y-1, z);
        Block ablock = check.getWorld().getBlockAt(x, y, z);
        Block ablock2 = check.getWorld().getBlockAt(x, y + 1, z);
        if (cblock.getType().isSolid() &&
                !bad_blocks.contains(cblock.getType()) &&
                ablock.getType().isAir() &&
                ablock2.getType().isAir()){
            return check;
        }
        if (turnNext == 300) return null;
        return randomLocation(turnNext);
    }

    public static void start(){

        long update = 60L;
        Bukkit.getScheduler().runTaskTimer(MaesiaMob.getInstance(),()->{
            if (getSpawnerList().isEmpty())return;

            for(Spawner spawner : getSpawnerList()) spawner.getEntitiesAroundPoint();


        },80L, update);
    }




    //Système de sauvegarde et de chargement

    public static void onSave() throws IOException {
        File file = new File(MaesiaMob.getInstance().getDataFolder(), "Spawner/Spawner.yml");
        if (file.exists()){
            if (Spawner.spawnerList.isEmpty()){
                Files.deleteIfExists(Path.of(file.getPath()));
                return;
            }
        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String s = "Spawner";
        configuration.set(s, "");
        for(Spawner spawner: spawnerList){

            configuration.set(s+"."+spawner.getName()+".Id", spawner.getId().toString());
            configuration.set(s+"."+spawner.getName()+".Location.World", Objects.requireNonNull(spawner.getLocation().getWorld()).getName());
            configuration.set(s+"."+spawner.getName()+".Location.X", spawner.getLocation().getBlockX());
            configuration.set(s+"."+spawner.getName()+".Location.Y", spawner.getLocation().getBlockY());
            configuration.set(s+"."+spawner.getName()+".Location.Z", spawner.getLocation().getBlockZ());

            configuration.set(s+"."+spawner.getName()+".Limit", spawner.getLimit());
            configuration.set(s+"."+spawner.getName()+".Radius", spawner.getRadius());
            configuration.set(s+"."+spawner.getName()+".SpawnMin", spawner.getSpawnMin());
            configuration.set(s+"."+spawner.getName()+".SpawnMax", spawner.getSpawnMax());

            configuration.set(s+"."+spawner.getName()+".Delay", spawner.getDelay());
            configuration.set(s+"."+spawner.getName()+".TotalSpawn", spawner.getCountSpawn());
            configuration.set(s+"."+spawner.getName()+".MobSpawnerList", translateUuidToString(spawner.getMobs()));
            configuration.set(s+"."+spawner.getName()+".MobSpawnList", translateUuidToString(spawner.getMobsSpawn()));

        }
        configuration.save(file);
    }

    private static List<String> translateUuidToString(Set<UUID> list){
        List<String> uuidString = new ArrayList<>();
        for(UUID uuid : list){
            uuidString.add(uuid.toString());
        }
        return uuidString;
    }

    public static void onLoad() {
        File file = new File(MaesiaMob.getInstance().getDataFolder(), "Spawner/Spawner.yml");
        if (!file.exists()) return;
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String s = "Spawner";
        if (!configuration.contains(s+".")) return;
        for (String key : Objects.requireNonNull(configuration.getConfigurationSection(s + ".")).getKeys(false)) {
            UUID uuid = UUID.fromString(Objects.requireNonNull(configuration.getString(s + "." + key + ".Id")));
            Location location = new Location(Bukkit.getWorld(Objects.requireNonNull(configuration.getString(s + "." + key + ".Location.World"))),  configuration.getInt(s + "." + key + ".Location.X"),  configuration.getInt(s + "." + key + ".Location.Y"),  configuration.getInt(s + "." + key + ".Location.Z"));

            Spawner spawner = new Spawner(key, location, uuid);

            spawner.setCountSpawn(configuration.getInt(s + "." + key + ".TotalSpawn"));
            spawner.setDelay(configuration.getLong(s + "." + key + ".Delay"));
            spawner.setRadius(configuration.getInt(s + "." + key + ".Radius"));
            spawner.setSpawnMin(configuration.getInt(s + "." + key + ".SpawnMin"));
            spawner.setSpawnMax(configuration.getInt(s + "." + key + ".SpawnMax"));
            spawner.setLimit(configuration.getInt(s + "." + key + ".Limit"));
            spawner.getMobs().addAll(translateStringToUuid(configuration.getStringList(s+"."+ key+".MobSpawnerList")));
            spawner.getMobsSpawn().addAll(translateStringToUuid(configuration.getStringList(s+"."+ key+".MobSpawnList")));

        }
    }

    private static Set<UUID> translateStringToUuid(List<String> list){
        Set<UUID> uuidString = new HashSet<>();
        for(String key : list){
            UUID uuid = UUID.fromString(key);
            uuidString.add(uuid);
        }
        return uuidString;
    }
}


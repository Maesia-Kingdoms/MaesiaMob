package fr.maesia.mob.utils.LoadUnload;

import fr.maesia.mob.MaesiaMob;

import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.Rang;
import fr.maesia.mob.mob.rangs.RangsLoots;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class LoadUnLoad {


    public static void onSaveEntityCustom() throws IOException {
        File file = new File(MaesiaMob.getInstance().getDataFolder(), "Mobs/ListMobsCustomSpawn.yml");
        if (file.exists()){
            if (Mobs.mobsListUuid.isEmpty()){
                Files.deleteIfExists(Path.of(file.getPath()));
                return;
            }
        }
        if (Mobs.mobsListUuid.isEmpty()) return;
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String m = "Mobs";
        configuration.set(m, "");

        if (!Mobs.mobsListUuid.isEmpty()) configuration.set(m+".Uuid", onListUuidToListString());



        configuration.save(file);
    }

    private static List<String> onListUuidToListString(){
        List<String> stringUuid = new ArrayList<>();
        for(UUID convect : Mobs.mobsListUuid){
            stringUuid.add(convect.toString());
        }
        return stringUuid;
    }

    public static void onSave() throws IOException {
        File file = new File(MaesiaMob.getInstance().getDataFolder(), "Mobs/Mobs.yml");
        if (file.exists()){
            if (Mobs.mobsList.isEmpty()){
                Files.deleteIfExists(Path.of(file.getPath()));
                return;
            }
        }
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String m = "Mobs";
        configuration.set(m, "");
        for(Mobs mobs: Mobs.mobsList){
            configuration.set(m+"."+mobs.getName()+".Type", mobs.getEntityType().name());
            configuration.set(m+"."+mobs.getName()+".Id", mobs.getId().toString());
            configuration.set(m+"."+mobs.getName()+".Dégats", mobs.getDamage());
            configuration.set(m+"."+mobs.getName()+".Pv", mobs.getHealth());
            configuration.set(m+"."+mobs.getName()+".Speed", mobs.getSpeed());
            configuration.set(m+"."+mobs.getName()+".AttackSpeed", mobs.getAttackspeed());
            configuration.set(m+"."+mobs.getName()+".Biome", mobs.getBiomespawn());
            configuration.set(m+"."+mobs.getName()+".Rank", mobs.getRank().name());
            configuration.set(m+"."+mobs.getName()+".HauteurMax", mobs.getHeight_max());
            configuration.set(m+"."+mobs.getName()+".HauteurMin", mobs.getHeight_min());
            configuration.set(m+"."+mobs.getName()+".World", mobs.getWorldspawn());
            configuration.set(m+"."+mobs.getName()+".Monture", mobs.getPassager());
            unloadbackpack(Objects.requireNonNull(configuration.createSection(m+"."+mobs.getName()+".Helmet")), mobs.getHelmet());
            unloadbackpack(Objects.requireNonNull(configuration.createSection(m+"."+mobs.getName()+".Chestplate")), mobs.getChestplate());
            unloadbackpack(Objects.requireNonNull(configuration.createSection(m+"."+mobs.getName()+".Leggings")), mobs.getLeggings());
            unloadbackpack(Objects.requireNonNull(configuration.createSection(m+"."+mobs.getName()+".Boots")), mobs.getBoots());
            unloadbackpack(Objects.requireNonNull(configuration.createSection(m+"."+mobs.getName()+".Hand")), mobs.getHainMand());
            unloadbackpack(Objects.requireNonNull(configuration.createSection(m+"."+mobs.getName()+".offHand")), mobs.getOffMand());
            configuration.set(m+"."+mobs.getName()+".DeathEffect", "");
            if (mobs.getDeathEffect().getDeathSpawn().isActif() && mobs.getDeathEffect().getDeathSpawn().getMobs() != null){
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Spawn.name", mobs.getDeathEffect().getDeathSpawn().getMobs().getName());
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Spawn.Quantité", mobs.getDeathEffect().getDeathSpawn().getCount());
            }
            if(mobs.getDeathEffect().getDeathExplotion().isActif()){
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Explosion.Size", mobs.getDeathEffect().getDeathExplotion().getPower());
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Explosion.Fire", mobs.getDeathEffect().getDeathExplotion().isFire());
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Explosion.Destroy", mobs.getDeathEffect().getDeathExplotion().isDestroy());
            }
            if(mobs.getDeathEffect().getDeathPotionEffect().isActif()){
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Effect.Name", mobs.getDeathEffect().getDeathPotionEffect().getPotionEffect().getName());
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Effect.Time", mobs.getDeathEffect().getDeathPotionEffect().getDuration());
                configuration.set(m+"."+mobs.getName()+".DeathEffect.Effect.Power", mobs.getDeathEffect().getDeathPotionEffect().getPower());
            }

            if(mobs.getEffectMobsSpawn().isActif()){
                configuration.set(m+"."+mobs.getName()+".SpawnEffect.Name", mobs.getEffectMobsSpawn().getPotionEffect().getName());
                configuration.set(m+"."+mobs.getName()+".SpawnEffect.Time", mobs.getEffectMobsSpawn().getDuration());
                configuration.set(m+"."+mobs.getName()+".SpawnEffect.Power", mobs.getEffectMobsSpawn().getPower());
            }
            if(mobs.getEffectMobsDamage().isActif()){
                configuration.set(m+"."+mobs.getName()+".DamageEffect.Name", mobs.getEffectMobsDamage().getPotionEffect().getName());
                configuration.set(m+"."+mobs.getName()+".DamageEffect.Time", mobs.getEffectMobsDamage().getDuration());
                configuration.set(m+"."+mobs.getName()+".DamageEffect.Power", mobs.getEffectMobsDamage().getPower());
            }
            if(!mobs.getLoots().isEmpty()){
                int count = 0;
                for(Map.Entry<ItemStack, RangsLoots> items : mobs.getLoots().entrySet()){
                    unloadbackpack(configuration.createSection(m+"."+mobs.getName()+".Loots."+count), items.getKey());
                    configuration.set(m+"."+mobs.getName()+".Loots."+count+".Rangs", items.getValue().name());
                    count++;
                }
            }



        }
        configuration.save(file);
    }

    private static void unloadbackpack(ConfigurationSection section, ItemStack itemStack){
        if (itemStack != null){
            String encoded;
            try {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeObject(itemStack);
                os.flush();

                byte[] bytes = io.toByteArray();
                encoded = Base64.getEncoder().encodeToString(bytes);
                section.set("item", encoded);


            }catch (IOException ex){
                System.out.println("bug create byte item");
            }
        }
    }



    public static void onLoad() {
        File file = new File(MaesiaMob.getInstance().getDataFolder(), "Mobs/Mobs.yml");
        if (!file.exists()) return;
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String m = "Mobs";
        for (String key : Objects.requireNonNull(configuration.getConfigurationSection(m + ".")).getKeys(false)) {
            UUID uuid = UUID.fromString(Objects.requireNonNull(configuration.getString(m + "." + key + ".Id")));
            Mobs mobs = new Mobs(EntityType.valueOf(configuration.getString(m + "." + key + ".Type")), key, uuid);
            mobs.setRank(Rang.valueOf(configuration.getString(m + "." + key + ".Rank")));

            if (configuration.contains(m+"."+mobs.getName()+".HauteurMax")){
                mobs.setHeight_max(configuration.getInt(m+"."+mobs.getName()+".HauteurMax"));
                mobs.setHeight_min(configuration.getInt(m+"."+mobs.getName()+".HauteurMin"));
            }
            if (configuration.contains(m+"."+mobs.getName()+".World")){
                mobs.setWorldspawn(configuration.getStringList(m+"."+mobs.getName()+".World"));
            }
            if (configuration.contains(m+"."+mobs.getName()+".Monture")){
                UUID monture = UUID.fromString(Objects.requireNonNull(configuration.getString(m + "." + mobs.getName() + ".Monture")));
                mobs.setPassager(monture);
            }

            mobs.setDamage(configuration.getDouble(m + "." + key + ".Dégats"));
            mobs.setHealth(configuration.getDouble(m + "." + key + ".Pv"));
            mobs.setSpeed((float) configuration.getDouble(m + "." + key + ".Speed"));
            mobs.setAttackspeed(configuration.getDouble(m + "." + key + ".AttackSpeed"));

            mobs.setBiomespawn(configuration.getStringList(m + "." + key + ".Biome"));

            mobs.setHelmet(loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Helmet"))));
            mobs.setChestplate(loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Chestplate"))));
            mobs.setLeggings(loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Leggings"))));
            mobs.setBoots(loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Boots"))));
            mobs.setHainMand(loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Hand"))));
            mobs.setOffMand(loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".offHand"))));

            if (configuration.contains(m + "." + key + ".DeathEffect.Spawn.name")) {
                mobs.getDeathEffect().getDeathSpawn().setMobs(configuration.getString(m + "." + key + ".DeathEffect.Spawn.name"));
                mobs.getDeathEffect().getDeathSpawn().setCount(configuration.getInt(m + "." + key + ".DeathEffect.Spawn.Quantité"));
                mobs.getDeathEffect().getDeathSpawn().setActif(true);

                MaesiaMob.getInstance().getLogger().info(mobs.getName() + " Spawn " + configuration.getString(m + "." + key + ".DeathEffect.Spawn") + " Quantité " + mobs.getDeathEffect().getDeathSpawn().getCount());
            }
            if (configuration.contains(m + "." + key + ".DeathEffect.Explosion.Size")) {
                mobs.getDeathEffect().getDeathExplotion().setPower((float) configuration.getDouble(m + "." + mobs.getName() + ".DeathEffect.Explosion.Size"));
                mobs.getDeathEffect().getDeathExplotion().setFire(configuration.getBoolean(m + "." + mobs.getName() + ".DeathEffect.Explosion.Fire"));
                mobs.getDeathEffect().getDeathExplotion().setDestroy(configuration.getBoolean(m + "." + mobs.getName() + ".DeathEffect.Explosion.Destroy"));
                mobs.getDeathEffect().getDeathExplotion().setActif(true);
            }
            if (configuration.contains(m + "." + key + ".DeathEffect.Effect.Name")) {
                mobs.getDeathEffect().getDeathPotionEffect().setPotionEffect(PotionEffectType.getByName(Objects.requireNonNull(configuration.getString(m + "." + mobs.getName() + ".DeathEffect.Effect.Name"))));
                mobs.getDeathEffect().getDeathPotionEffect().setDuration(configuration.getInt(m + "." + mobs.getName() + ".DeathEffect.Effect.Time"));
                mobs.getDeathEffect().getDeathPotionEffect().setPower(configuration.getInt(m + "." + mobs.getName() + ".DeathEffect.Effect.Power"));
                mobs.getDeathEffect().getDeathPotionEffect().setActif(true);

            }
            if (configuration.contains(m + "." + key + ".SpawnEffect.Name")) {
                mobs.getEffectMobsSpawn().setPotionEffect(PotionEffectType.getByName(Objects.requireNonNull(configuration.getString(m + "." + mobs.getName() + ".SpawnEffect.Name"))));
                mobs.getEffectMobsSpawn().setDuration(configuration.getInt(m + "." + mobs.getName() + ".SpawnEffect.Time"));
                mobs.getEffectMobsSpawn().setPower(configuration.getInt(m + "." + mobs.getName() + ".SpawnEffect.Power"));
                mobs.getEffectMobsSpawn().setActif(true);
            }
            if (configuration.contains(m + "." + key + ".DamageEffect.Name")) {
                mobs.getEffectMobsDamage().setPotionEffect(PotionEffectType.getByName(Objects.requireNonNull(configuration.getString(m + "." + mobs.getName() + ".DamageEffect.Name"))));
                mobs.getEffectMobsDamage().setDuration(configuration.getInt(m + "." + mobs.getName() + ".DamageEffect.Time"));
                mobs.getEffectMobsDamage().setPower(configuration.getInt(m + "." + mobs.getName() + ".DamageEffect.Power"));
                mobs.getEffectMobsDamage().setActif(true);
            }

            if (configuration.contains(m+"."+key+".Loots")){
                for (String keyb : Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Loots")).getKeys(false)) {
                    ItemStack it = loadItem(Objects.requireNonNull(configuration.getConfigurationSection(m + "." + mobs.getName() + ".Loots." + keyb)));
                    mobs.getLoots().put(it, RangsLoots.valueOf(configuration.getString(m + "." + mobs.getName() + ".Loots." + keyb + ".Rangs")));
                }
            }

        }
    }

    private static ItemStack loadItem(ConfigurationSection section){
        if ( section.getString("item") == null) return new ItemStack(Material.AIR);
        try {
            byte[] decode = Base64.getDecoder().decode(String.valueOf(section.get(".item")));
            ByteArrayInputStream in = new ByteArrayInputStream(decode);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            return (ItemStack) is.readObject();
        }catch (IOException | ClassNotFoundException exception){
            System.out.println("bug");
        }
        return new ItemStack(Material.AIR);
    }



    public static void onLoadEntityCustom() {
        File file = new File(MaesiaMob.getInstance().getDataFolder(), "Mobs/ListMobsCustomSpawn.yml");
        if (!file.exists()) return;
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        String m = "Mobs";
        if (configuration.contains(m+".")){
            for(String key : Objects.requireNonNull(configuration.getConfigurationSection(m + ".")).getKeys(false)){
                Mobs mobs = Mobs.getMobs(key);
                if (mobs != null){
                    Mobs.mobsListUuid.addAll(onListStringToListUuid(configuration.getStringList(m+".Uuid")));
                }
            }
        }

    }

    private static List<UUID> onListStringToListUuid(List<String> strings){
        List<UUID> uuids = new ArrayList<>();
        for(String convect : strings){
            UUID uuid = UUID.fromString(convect);
            uuids.add(uuid);
        }
        return uuids;
    }


}

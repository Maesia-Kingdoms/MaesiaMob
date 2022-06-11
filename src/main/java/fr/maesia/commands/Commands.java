package fr.maesia.commands;

import fr.maesia.mob.Mobs;
import fr.maesia.MaesiaMobFiles.Messages.Messages;
import fr.maesia.MaesiaMobFiles.Messages.MessagesValues;
import fr.maesia.listener.SpawnMob;
import fr.maesia.utils.GUI;
import fr.maesia.utils.LoadUnload.LoadUnLoad;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Commands implements TabExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GUI gui = new GUI();
            if (player.isOp()){
                if (command.getName().equalsIgnoreCase("moremob")) {
                    if (args.length == 0){
                        onHelp(player);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Help")) {
                        onHelp(player);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("Spawn")) {
                        if (args.length < 2){
                            player.sendMessage(Messages.getPrefix()+Messages.COMMANDS_SPAWN_EXACT.get());
                            return true;
                        }
                            StringBuilder name = new StringBuilder();
                            int count = 0;
                            for(String part : args){
                                if(count >= 1){
                                    name.append(part).append(" ");
                                }
                                count++;
                            }
                            Mobs mobs = Mobs.getMobs(String.valueOf(name));
                            if (mobs == null){
                                player.sendMessage(Messages.getPrefix()+Messages.COMMANDS_SPAWN_EXIST.get());
                                return true;
                            }
                            SpawnMob.onSpawnMobs(mobs, 1, player.getLocation());
                            return true;

                    }
                    if (args[0].equalsIgnoreCase("Create")) {
                        if (args.length < 3) {
                            player.sendMessage(Messages.getPrefix() + Messages.COMMANDS_CREATE_EXACT.get());
                            return true;
                        }
                        for (EntityType e : EntityType.values()) {
                            if (e.name().equalsIgnoreCase(args[1])) {
                                StringBuilder name = new StringBuilder();
                                int count = 0;
                                for(String part : args){
                                    if(count >= 2){
                                        name.append(part).append(" ");
                                    }
                                    count++;
                                }
                                Mobs mobs = Mobs.getMobs(String.valueOf(name));
                                if (mobs != null){
                                    player.sendMessage(Messages.getPrefix()+Messages.COMMANDS_CREATE_EXIST.get());
                                    return true;
                                }
                                UUID uuid = UUID.randomUUID();
                                new Mobs(e, String.valueOf(name), uuid);
                                player.sendMessage(Messages.getPrefix() + Messages.COMMANDS_CREATE_SUCCESS.get().replace(MessagesValues.MOB.toName(), ChatColor.translateAlternateColorCodes('&', String.valueOf(name))));
                                return true;
                            }
                        }
                        player.sendMessage(Messages.getPrefix() + Messages.COMMANDS_CREATE_EXACT.get());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Edit")) {
                        player.openInventory(gui.onGuiEdit());
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Reload")) {
                        try {
                            LoadUnLoad.onSaveEntityCustom();
                            LoadUnLoad.onSave();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LoadUnLoad.onLoad();
                        LoadUnLoad.onLoadEntityCustom();
                        player.sendMessage(Messages.getPrefix()+ChatColor.GREEN+"Reload completed");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("Save")) {
                        try {
                            LoadUnLoad.onSaveEntityCustom();
                            LoadUnLoad.onSave();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage(Messages.getPrefix()+ChatColor.GREEN+"Save success");
                        return true;
                    }
                    onHelp(player);
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("Bestiaire")) {
                player.openInventory(gui.onGuiBestiare());
                return true;
            }
            return false;
        }
    return false;
}


    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender,@Nonnull Command command,@Nonnull String label,@Nonnull String[] args) {
        List<String> arguments = new ArrayList<>();
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("moremob")){
                if (args.length == 1 && player.isOp()){
                    arguments.add("Help");
                    arguments.add("Create");
                    arguments.add("Spawn");
                    arguments.add("Edit");
                    arguments.add("Reload");
                    arguments.add("Save");
                    return arguments;
                }
                if (args.length == 2){
                    if (args[0].equalsIgnoreCase("Create")){
                        arguments.add(EntityType.BLAZE.name());
                        arguments.add(EntityType.ZOMBIE.name());
                        arguments.add(EntityType.SKELETON.name());
                        arguments.add(EntityType.SLIME.name());
                        arguments.add(EntityType.ENDERMAN.name());
                        arguments.add(EntityType.ENDERMITE.name());
                        arguments.add(EntityType.EVOKER.name());
                        arguments.add(EntityType.WITCH.name());
                        arguments.add(EntityType.WITHER_SKELETON.name());
                        arguments.add(EntityType.HUSK.name());
                        arguments.add(EntityType.STRAY.name());
                        arguments.add(EntityType.SPIDER.name());
                        arguments.add(EntityType.PHANTOM.name());
                        arguments.add(EntityType.CAVE_SPIDER.name());
                        arguments.add(EntityType.PILLAGER.name());
                        arguments.add(EntityType.PIGLIN.name());
                        arguments.add(EntityType.PIGLIN_BRUTE.name());
                        arguments.add(EntityType.DROWNED.name());
                        arguments.add(EntityType.GIANT.name());
                        arguments.add(EntityType.PIGLIN.name());
                        arguments.add(EntityType.PIGLIN_BRUTE.name());
                        arguments.add(EntityType.CREEPER.name());
                        arguments.add(EntityType.GHAST.name());
                        arguments.add(EntityType.ZOMBIFIED_PIGLIN.name());
                        arguments.add(EntityType.HORSE.name());
                        arguments.add(EntityType.ZOMBIE_HORSE.name());
                        arguments.add(EntityType.SKELETON_HORSE.name());
                        return arguments;
                    }
                    if(args[0].equalsIgnoreCase("Spawn")){
                        if(Mobs.mobsList.isEmpty()) return null;
                        for(Mobs mobs : Mobs.mobsList){
                            arguments.add(mobs.getName());
                        }
                        return arguments;
                    }

                }
            }

        }
        return null;
    }

    public void onHelp(Player p){
        if (p.isOp()){
            p.sendMessage(ChatColor.GRAY+"----------------"+Messages.getPrefix()+ChatColor.GRAY+"----------------");
            p.sendMessage(ChatColor.GRAY+"Voirs toutes les commandes -->"+ChatColor.YELLOW+"/mm Help");
            p.sendMessage(Messages.COMMANDS_CREATE_EXACT.get());
            p.sendMessage(ChatColor.GRAY+"Editer un mobs -->"+ChatColor.YELLOW+"/mm edit");
            p.sendMessage(Messages.COMMANDS_SPAWN_EXACT.get());
            p.sendMessage(ChatColor.GRAY+"Relaod du plugins -->"+ChatColor.YELLOW+"/mm reload");
            p.sendMessage(ChatColor.GRAY+"Sauvegarde des mobs "+ChatColor.RED+" (a faire après chaque création ou éditions)"+ChatColor.GRAY +" -->"+ChatColor.YELLOW+"/mm save");
            p.sendMessage(ChatColor.GRAY+"----------------"+Messages.getPrefix()+ChatColor.GRAY+"----------------");
        }else {
            p.sendMessage(Messages.getPrefix()+Messages.PERMISSION.get());
        }

    }
}

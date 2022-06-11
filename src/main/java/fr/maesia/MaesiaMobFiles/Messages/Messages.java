package fr.maesia.MaesiaMobFiles.Messages;

import fr.maesia.MaesiaMob;
import fr.maesia.MaesiaMobFiles.MaesiaMobFiles;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public enum Messages {

    PREFIX,
    PERMISSION,

    COMMANDS_CREATE_EXACT,
    COMMANDS_SPAWN_EXACT,
    COMMANDS_CREATE_SUCCESS,
    COMMANDS_CREATE_EXIST,
    COMMANDS_SPAWN_EXIST,

    //EDIT
    EDIT_CANCEL,
    EDIT_CANCEL_SUCCESS,

    //rank mob
    EDIT_RANK,
    EDIT_RANK_SUCCESS,

    //DAMAGE, HEALTH & Speed
    EDIT_NUMBER_ERROR,
    EDIT_NUMBER_FLOAT_ERROR,

    EDIT_HEALTH,
    EDIT_DAMAGE,
    EDIT_SPEED,
    EDIT_ATTACK_SPEED,

    EDIT_DAMAGE_SUCCESS,
    EDIT_HEALTH_SUCCESS,
    EDIT_SPEED_SUCCESS,
    EDIT_ATTACK_SPEED_SUCCESS;

    private static final Map<Messages, String> Values = new HashMap<>();

    static {
        for (Messages msg : values()){
            Values.put(msg, msg.getFromFile());
        }

        MaesiaMob.getInstance().getLogger().info("Message file read successfully!");
    }
    public static String getPrefix(){
        return PREFIX.get() + ChatColor.RESET+" ";
    }
    public String get(){
        return Values.get(this);
    }

    private String getFromFile(){
        FileConfiguration config = MaesiaMobFiles.Messages.getConfig();
        String key = name().toLowerCase().replace('_', '-');
        String value = config.getString(key);
        if (value == null){
            value = "";
        }

        return ChatColor.translateAlternateColorCodes('&', value);

    }
}

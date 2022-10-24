package fr.maesia.mob.spawner;

import fr.maesia.mob.spawner.GUI.MenuEditSpawner;
import fr.maesia.mob.utils.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MenuSpawnerChat implements Listener {

    @EventHandler
    private void onInteractMenu(AsyncPlayerChatEvent e){


        //Spawner
        if (PlayerData.hasPersitentDataSring(e.getPlayer(), "Radius")) MenuEditSpawner.chatEditSpawnerRadiusEdit(e);
        if (PlayerData.hasPersitentDataSring(e.getPlayer(), "Limit")) MenuEditSpawner.chatEditSpawnerLimitEdit(e);
        if (PlayerData.hasPersitentDataSring(e.getPlayer(), "SpawnMin")) MenuEditSpawner.chatEditSpawnMinEdit(e);
        if (PlayerData.hasPersitentDataSring(e.getPlayer(), "SpawnMax")) MenuEditSpawner.chatEditSpawnMaxEdit(e);

    }
}

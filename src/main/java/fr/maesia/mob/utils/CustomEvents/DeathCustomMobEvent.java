package fr.maesia.mob.utils.CustomEvents;

import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.RangsLoots;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DeathCustomMobEvent extends Event implements Cancellable {


    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Mobs mobs;
    private Player killer;
    private HashMap<ItemStack, RangsLoots> loots;
    private boolean cancellled;

    public DeathCustomMobEvent(Mobs mob, Player killer, HashMap<ItemStack, RangsLoots> loots){
        this.mobs = mob;
        this.killer = killer;
        this.loots = loots;
    }

    public Mobs getMobs() {
        return mobs;
    }

    public boolean isCancelled() {
        return false;
    }

    public void setCancelled(boolean cancel) {
        this.cancellled = cancel;
    }
}

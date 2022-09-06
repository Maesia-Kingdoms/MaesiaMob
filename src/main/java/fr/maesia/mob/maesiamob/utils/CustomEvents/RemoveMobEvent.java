package fr.maesia.mob.maesiamob.utils.CustomEvents;


import fr.maesia.mob.maesiamob.Mob.Mobs;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RemoveMobEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private Mobs mobs;
    private boolean cancellled;

    public RemoveMobEvent(Mobs mob){
        this.mobs = mob;
    }


    public Mobs getMobs() {
        return mobs;
    }


    public boolean isCancelled() {
        return false;
    }

    public void setCancelled(boolean cancel) {

    }
}

package fr.maesia.mob.utils.CustomEvents;

import fr.maesia.mob.mob.Mobs;
import fr.maesia.mob.mob.rangs.RangsLoots;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class DeathCustomMobEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Mobs mobs;
    private final Player killer;
    private final Entity entity;
    private final EntityType entityType;
    private final HashMap<ItemStack, RangsLoots> loots;
    private boolean drops;
    private int probility;
    private boolean cancellled;

    public DeathCustomMobEvent(Mobs mob, Player killer, HashMap<ItemStack, RangsLoots> loots, boolean drops, int probility, Entity entity, EntityType entityType){
        this.mobs = mob;
        this.killer = killer;
        this.loots = loots;
        this.drops = drops;
        this.entity = entity;
        this.probility = probility;
        this.entityType = entityType;
    }

    public Mobs getMobs() {
        return mobs;
    }

    public Player getKiller() {
        return killer;
    }

    public HashMap<ItemStack, RangsLoots> getLoots() {
        return loots;
    }

    public boolean isDrops() {
        return drops;
    }

    public void setDrops(boolean drops) {
        this.drops = drops;
    }

    public int getProbility() {
        return probility;
    }

    public void setProbility(int probility) {
        this.probility = probility;
    }

    public Entity getEntity() {
        return entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public boolean isCancelled() {
        return false;
    }

    public void setCancelled(boolean cancel) {
        this.cancellled = cancel;
    }
}

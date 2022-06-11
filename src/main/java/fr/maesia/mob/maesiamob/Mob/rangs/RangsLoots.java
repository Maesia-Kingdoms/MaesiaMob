package fr.maesia.mob.maesiamob.Mob.rangs;

import net.md_5.bungee.api.ChatColor;

public enum RangsLoots {


    COMMUN(80, ChatColor.GREEN),
    RARE(50, ChatColor.AQUA),
    EPIC(20, ChatColor.DARK_PURPLE),
    LEGENDAIRE(10, ChatColor.GOLD),
    MHYTIQUE(1, ChatColor.RED),
    DIVIN(-30, ChatColor.DARK_RED);


    private final int proba;
    private final ChatColor Color;

    RangsLoots(int proba, ChatColor color){
        this.proba = proba;
        this.Color =  color;
    }

    public int getProba() {
        return proba;
    }

    public ChatColor getColor() {
        return Color;
    }
}

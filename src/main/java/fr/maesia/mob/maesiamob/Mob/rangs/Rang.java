package fr.maesia.mob.maesiamob.Mob.rangs;

import net.md_5.bungee.api.ChatColor;

public enum Rang {

    F(ChatColor.GRAY),
    E(ChatColor.GREEN),
    D(ChatColor.GREEN),
    C(ChatColor.AQUA),
    B(ChatColor.DARK_PURPLE),
    A(ChatColor.GOLD),
    S(ChatColor.RED);

    private final ChatColor color;

    Rang(ChatColor color){
      this.color =color;
    }
    public ChatColor getColor() {
        return color;
    }
}

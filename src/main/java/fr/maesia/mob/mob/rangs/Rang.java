package fr.maesia.mob.mob.rangs;

import net.md_5.bungee.api.ChatColor;

import java.util.Random;

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

    public static Rang randomRang(){
        Rang rang = Rang.F;

        //proba du rang
        Random random = new Random();
        float r = random.nextFloat();

        if (r < 0.01){
            rang = Rang.S;
        }else if(r>= 0.01 && r < 0.1){
            rang = Rang.A;
        }
        else if(r>= 0.1 && r < 0.15){
            rang = Rang.B;
        }
        else if(r>= 0.15 && r < 0.30){
            rang = Rang.C;
        }
        else if(r>= 0.30 && r < 0.50){
            rang = Rang.D;
        }
        else if(r>= 0.50 && r < 0.70){
            rang = Rang.E;
        }
        return rang;
    }
}

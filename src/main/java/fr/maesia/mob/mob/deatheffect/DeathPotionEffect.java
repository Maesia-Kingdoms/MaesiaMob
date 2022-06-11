package fr.maesia.mob.mob.deatheffect;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DeathPotionEffect {

    private boolean actif;
    private PotionEffectType potionEffect;
    private int power;
    private int duration;

    public DeathPotionEffect(){
        actif =false;
        power = 0;
        duration = 0;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public PotionEffectType getPotionEffect() {
        return potionEffect;
    }

    public void setPotionEffect(PotionEffectType potionEffect) {
        this.potionEffect = potionEffect;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

package fr.maesia.mob.maesiamob.Mob.ppe;

import org.bukkit.potion.PotionEffectType;

public class PotionEffectMobs {

    private boolean actif;
    private PotionEffectType potionEffect;
    private int power;
    private int duration;

    public PotionEffectMobs(){
        this.actif = false;
        this.potionEffect = null;
        this.power = 0;
        this.duration = 0;
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

package fr.maesia.mob.mob.deatheffect;

import fr.maesia.mob.mob.Mobs;

public class DeathSpawn {


    private String mobs;
    private int count;
    private boolean actif;
    public DeathSpawn(){
        this.mobs = null;
        this.actif = false;
    }

    public Mobs getMobs() {
        return Mobs.getMobs(mobs);
    }

    public void setMobs(String mobs) {
        this.mobs = mobs;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}

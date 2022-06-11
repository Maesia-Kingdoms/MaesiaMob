package fr.maesia.mob.deatheffect;

public class DeathExplotion {


    private boolean actif;
    private boolean fire;
    private boolean destroy;
    private float power;

    public DeathExplotion(){
        this.actif = false;
        this.fire = false;
        this.destroy = false;
        this.power =0;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }
}

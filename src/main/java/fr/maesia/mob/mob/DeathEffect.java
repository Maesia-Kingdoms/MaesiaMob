package fr.maesia.mob.mob;

import fr.maesia.mob.mob.deatheffect.DeathExplotion;
import fr.maesia.mob.mob.deatheffect.DeathPotionEffect;
import fr.maesia.mob.mob.deatheffect.DeathSpawn;

public class DeathEffect {


    private final DeathSpawn deathSpawn;
    private final DeathExplotion deathExplotion;
    private final DeathPotionEffect deathPotionEffect;

    public DeathEffect(){
       this.deathSpawn = new DeathSpawn();
       this.deathExplotion = new DeathExplotion();
       this.deathPotionEffect = new DeathPotionEffect();
    }

    public DeathSpawn getDeathSpawn() {
        return deathSpawn;
    }

    public DeathExplotion getDeathExplotion() {
        return deathExplotion;
    }

    public DeathPotionEffect getDeathPotionEffect() {
        return deathPotionEffect;
    }

    public static boolean checkMobSpawning(Mobs mob, Mobs target){
        if(!target.getDeathEffect().getDeathSpawn().isActif()) return false;
        if(target.getDeathEffect().getDeathSpawn().getMobs().getId().equals(mob.getId())) return true;
        return checkMobSpawning(mob, target.getDeathEffect().getDeathSpawn().getMobs());
    }
}

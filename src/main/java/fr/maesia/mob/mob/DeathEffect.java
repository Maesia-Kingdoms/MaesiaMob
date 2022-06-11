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
}

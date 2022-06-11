package fr.maesia.mob.maesiamob.Mob;

import fr.maesia.mob.maesiamob.Mob.deatheffect.DeathExplotion;
import fr.maesia.mob.maesiamob.Mob.deatheffect.DeathPotionEffect;
import fr.maesia.mob.maesiamob.Mob.deatheffect.DeathSpawn;

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

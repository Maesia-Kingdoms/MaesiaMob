package fr.maesia.utils.PotionEffectItem;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class ItemtoPotion {




    public static PotionEffectType onItemToEffect(Material material){

        if(material.equals(Material.HONEY_BOTTLE)){
            return PotionEffectType.CONFUSION;
        }
        if(material.equals(Material.ENDER_PEARL)){
            return PotionEffectType.BLINDNESS;
        }
        if(material.equals(Material.ROTTEN_FLESH)){
            return PotionEffectType.HUNGER;
        }
        if(material.equals(Material.SCUTE)){
            return PotionEffectType.SLOW;
        }
        if(material.equals(Material.WOODEN_SWORD)){
            return PotionEffectType.WEAKNESS;
        }
        if(material.equals(Material.FERMENTED_SPIDER_EYE)){
            return PotionEffectType.INCREASE_DAMAGE;
        }
        if(material.equals(Material.SPIDER_EYE)){
            return PotionEffectType.POISON;
        }
        if(material.equals(Material.WITHER_ROSE)){
            return PotionEffectType.WITHER;
        }
        if(material.equals(Material.GLISTERING_MELON_SLICE)){
            return PotionEffectType.HEAL;
        }
        if(material.equals(Material.GHAST_TEAR)){
            return PotionEffectType.REGENERATION;
        }
        if(material.equals(Material.MAGMA_CREAM)){
            return PotionEffectType.FIRE_RESISTANCE;
        }
        if(material.equals(Material.GLASS)){
            return PotionEffectType.INVISIBILITY;
        }
        if(material.equals(Material.GOLDEN_CARROT)){
            return PotionEffectType.NIGHT_VISION;
        }
        if(material.equals(Material.RABBIT_FOOT)){
            return PotionEffectType.JUMP;
        }
        if(material.equals(Material.IRON_CHESTPLATE)){
            return PotionEffectType.DAMAGE_RESISTANCE;
        }
        return null;
    }
}

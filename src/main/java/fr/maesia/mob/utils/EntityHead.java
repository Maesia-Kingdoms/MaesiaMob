package fr.maesia.mob.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public enum EntityHead {

    PIGLIN(EntityType.PIGLIN, true, "9f18107d275f1cb3a9f973e5928d5879fa40328ff3258054db6dd3e7c0ca6330"),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, true, "3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf"),
    PIG(EntityType.PIG, false, "621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4="),
    PILLAGER(EntityType.PILLAGER, true, "4aee6bb37cbfc92b0d86db5ada4790c64ff4468d68b84942fde04405e8ef5333"),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, true, "41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224"),
    PHANTOM(EntityType.PHANTOM, true, "7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982"),
    HUSK(EntityType.HUSK, true, "9b9da6b8d06cd28d441398b96766c3b4f370de85c7898205e5c429f178a24597"),
    WITHER_SKELETON(EntityType.WITHER_SKELETON, true, "f5ec964645a8efac76be2f160d7c9956362f32b6517390c59c3085034f050cff"),
    WITCH(EntityType.WITCH, true, "fce6604157fc4ab5591e4bcf507a749918ee9c41e357d47376e0ee7342074c90"),
    EVOKER(EntityType.EVOKER, true, "d954135dc82213978db478778ae1213591b93d228d36dd54f1ea1da48e7cba6"),
    ENDERMITE(EntityType.ENDERMITE, true, "5a1a0831aa03afb4212adcbb24e5dfaa7f476a1173fce259ef75a85855"),
    ENDERMAN(EntityType.ENDERMAN, true, "96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951"),
    ZOMBIE(EntityType.ZOMBIE, true, "64528b3229660f3dfab42414f59ee8fd01e80081dd3df30869536ba9b414e089"),
    BLAZE(EntityType.BLAZE, true, "b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0"),
    SKELETON(EntityType.SKELETON, true, "301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2"),
    SLIME(EntityType.SLIME, true, "bb13133a8fb4ef00b71ef9bab639a66fbc7d5cffcc190c1df74bf2161dfd3ec7"),
    SPIDER(EntityType.SPIDER, true, "5f7e82446fab1e41577ba70ab40e290ef841c245233011f39459ac6f852c8331"),
    STRAY(EntityType.STRAY, true, "78ddf76e555dd5c4aa8a0a5fc584520cd63d489c253de969f7f22f85a9a2d56"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, true, "a1c97a06efde04d00287bf20416404ab2103e10f08623087e1b0c1264a1c0f0c"),
    AXOLOTL(EntityType.AXOLOTL, false, "766dd0c3b4ec07f789c092f39f34fb68061666b6ef61f7e001e3e6f166996e6e"),
    BAT(EntityType.BAT, false, "473af69ed9bf67e2f5403dd7d28bbe32034749bbfb635ac1789a412053cdcbf0"),
    BEE(EntityType.BEE, false, "259001a851bb1b9e9c05de5d5c68b1ea0dc8bd86babf188e0aded8f912c07d0d=="),
    CAT(EntityType.CAT, false,"2e6505cdb814b6800042989fa0ccbd5edab7ec2b098b3e3f9a2c0e00b8e0c96"),
    CHICKEN(EntityType.CHICKEN, false, "65dc8c491f17e82e3ee075f09fbdea97edf6d3e7db1e4bb8b2001a80d79a5b1f=="),
    COW(EntityType.COW, false, "be8456155142cbe4e61353ffbaff304d3d9c4bc9247fc27b92e33e6e26067edd"),
    CREEPER(EntityType.CREEPER, true, "f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952"),
    ELDER_GUARDIEN(EntityType.ELDER_GUARDIAN, true, "1c797482a14bfcb877257cb2cff1b6e6a8b8413336ffb4c29a6139278b436b"),
    FOX(EntityType.FOX, false, "9d300eae1063d4d2e30558b17e53370cd6279f6c6e83ec26523c28dec391d064"),
    GHAST(EntityType.GHAST, true, "de8a38e9afbd3da10d19b577c55c7bfd6b4f2e407e44d4017b23be9167abff02"),
    GLOW_SQUID(EntityType.GLOW_SQUID, false, "57327ee11812b764c7ade70b282cce4c58e635b2015244081d1490543da7280e"),
    GOAT(EntityType.GOAT, false, "457a0d538fa08a7affe312903468861720f9fa34e86d44b89dcec5639265f03"),
    GUARDIAN(EntityType.GUARDIAN, true, "495290e090c238832bd7860fc033948c4d031353533ac8f67098823b7f667f1c"),
    HOGLIN(EntityType.HOGLIN, false, "9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75"),
    ILLUSIONER(EntityType.ILLUSIONER, true, "4639d325f4494258a473a93a3b47f34a0c51b3fceaf59fee87205a5e7ff31f68"),
    IRON_GOLEM(EntityType.IRON_GOLEM, false, "e13f34227283796bc017244cb46557d64bd562fa9dab0e12af5d23ad699cf697"),
    LLAMA(EntityType.LLAMA, false, "6d2ffce9a174fe1c084e2d82052182d94f95ed436b75ff7ea7a4e94d94c72d8a"),
    MULE(EntityType.MULE, false, "a0486a742e7dda0bae61ce2f55fa13527f1c3b334c57c034bb4cf132fb5f5f"),
    MUSHROOM_COW(EntityType.MUSHROOM_COW, false, "2b52841f2fd589e0bc84cbabf9e1c27cb70cac98f8d6b3dd065e55a4dcb70d77"),
    OCELOT(EntityType.OCELOT, false,"8c433c1347313b23b67eec92f8807aed2566ec29fd416bdf7a59c22596628355"),
    PANDA(EntityType.PANDA, false, "d8cdd4f285632c25d762ece25f4193b966c2641b15d9bdbc0a113023de76ab="),
    PARROT(EntityType.PARROT, false, "5fc9a3b9d5879c2150984dbfe588cc2e61fb1de1e60fd2a469f69dd4b6f6a993"),
    POLAR_BEAR(EntityType.POLAR_BEAR, false, "442123ac15effa1ba46462472871b88f1b09c1db467621376e2f71656d3fbc"),
    RABBIT(EntityType.RABBIT, false, "cc4349fe9902dd76c1361f8d6a1f79bff6f433f3b7b18a47058f0aa16b9053f"),
    RAVAGER(EntityType.RAVAGER, true, "1cb9f139f9489d86e410a06d8cbc670c8028137508e3e4bef612fe32edd60193"),
    SHEEP(EntityType.SHEEP, false,"f31f9ccc6b3e32ecf13b8a11ac29cd33d18c95fc73db8a66c5d657ccb8be70"),
    SHULKER(EntityType.SHULKER, true, "2e33ebd12ae6dbfa2344df16da8fc6f3597ff48017fbe383abd1669cbf54562d"),
    SILVERFISH(EntityType.SILVERFISH, true, "92ec2c3cb95ab77f7a60fb4d160bced4b879329b62663d7a9860642e588ab210"),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, false, "47effce35132c86ff72bcae77dfbb1d22587e94df3cbc2570ed17cf8973a"),
    SNOWMAN(EntityType.SNOWMAN, false, "668129623ffde22cbc534915ae38a5d60f32daedbc4fd5d2c310fea537ed"),
    SQUID(EntityType.SQUID, false, "49c2c9ce67eb5971cc5958463e6c9abab8e599adc295f4d4249936b0095769dd"),
    STRIDER(EntityType.STRIDER, false, "401c81657dc2622817ff3ffd8cbf90e4e29d0f1b233be795c4c3dea9aafaf05"),
    TRADER_LLAMA(EntityType.TRADER_LLAMA, false, "20fdfa60c624fb667c8313b2fb1dab40e0ad2e6e469b567bf596ad26392319c5"),
    VEX(EntityType.VEX, true, "5e7330c7d5cd8a0a55ab9e95321535ac7ae30fe837c37ea9e53bea7ba2de86b"),
    VILLAGER(EntityType.VILLAGER, false, "4ca8ef2458a2b10260b8756558f7679bcb7ef691d41f534efea2ba75107315cc"),
    VINDICATOR(EntityType.VINDICATOR, true, "6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173"),
    WANDERING_TRADER(EntityType.WANDERING_TRADER, false, "5f1379a82290d7abe1efaabbc70710ff2ec02dd34ade386bc00c930c461cf932"),
    WOLF(EntityType.WOLF, false, "69d1d3113ec43ac2961dd59f28175fb4718873c6c448dfca8722317d67"),
    ZOGLIN(EntityType.ZOGLIN, true, "e67e18602e03035ad68967ce090235d8996663fb9ea47578d3a7ebbc42a5ccf9"),
    ZONBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, true, "961f19ffd8ae425792c4b181756adff8d48174aeef58a0f327a28c742c72442"),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, true, "8954d0d1c286c1b34fb091841c06aed741a1bf9b65b9a430e4e5ca1d1c4b9f6f"),
    TURTLE(EntityType.TURTLE, false, "212b58c841b394863dbcc54de1c2ad2648af8f03e648988c1f9cef0bc20ee23c"),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, false, "d22950f2d3efddb18de86f8f55ac518dce73f12a6e0f8636d551d8eb480ceec"),
    DROWNED(EntityType.DROWNED, true, "c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c"),
    DOLPHIN(EntityType.DOLPHIN, false, "8e9688b950d880b55b7aa2cfcd76e5a0fa94aac6d16f78e833f7443ea29fed3"),
    DONKEY(EntityType.DONKEY, false, "63a976c047f412ebc5cb197131ebef30c004c0faf49d8dd4105fca1207edaff3"),
    HORSE(EntityType.HORSE, false, "397a3c20da5eea01c4132c3ff45ea80a364ad42f74dbf797cca4f8ab2d42adb3"),
    WARDEN(EntityType.WARDEN, true, "c6f74361fb00490a0a98eeb814544ecdd775cb55633dbb114e60d27004cb1020"),
    FROG(EntityType.FROG, false, "ce62e8a048d040eb0533ba26a866cd9c2d0928c931c50b4482ac3a3261fab6f0"),
    ALLAY(EntityType.ALLAY, false, "da82888584f0db1cc4f00226ae86ccce2c0f38d878f906b37fd6920e675b4a83"),
    GIANT(EntityType.GIANT, true, "c98547c90ea11c31e9baba500c27bb2c6f5aa0c3c96bf0942549c04b87911869");

    private final EntityType entityType;
    private final boolean monster;
    private final String texture;

    EntityHead (EntityType type, boolean monster, String texture){
        this.entityType =type;
        this.monster = monster;
        this.texture = texture;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static EntityHead getEntity(EntityType entityType){
        for(EntityHead entityHead :EntityHead.values()){
          if (entityHead.entityType.equals(entityType)) return entityHead;
        }
        return null;
    }

    public boolean isMonster() {
        return monster;
    }

    public String getTexture(){
        return texture;
    }

    public String getUrlTexture(){
        return  "http://textures.minecraft.net/texture/"+texture;
    }
    public PlayerTextures getSkin() throws MalformedURLException {
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(getEntityType().name());
        PlayerTextures playerTextures = playerProfile.getTextures();
        URL url;
        url = new URL(getUrlTexture());
        playerTextures.setSkin(url);
        return playerTextures;
    }

    public static ItemStack[] getEntityHead() throws MalformedURLException {
        List<ItemStack> itemStacks= new ArrayList<>();
        for(EntityHead head : EntityHead.values()){
          itemStacks.add(new ItemBuilder(Material.PLAYER_HEAD)
                    .setName(head.name())
                    .setLore(" "
                            , ChatColor.GRAY+"Type: "+ChatColor.YELLOW+ head.getEntityType()
                            , ChatColor.GRAY+"Agressif: "+ChatColor.YELLOW+head.isMonster()
                    )
                  .setPlayerSkullUrl(head.getTexture())
                  .getItemStack());
        }
        return itemStacks.toArray(new ItemStack[0]);
    }
}

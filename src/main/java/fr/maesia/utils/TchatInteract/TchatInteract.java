package fr.maesia.utils.TchatInteract;

import fr.maesia.MaesiaMobFiles.Messages.Messages;
import fr.maesia.MaesiaMobFiles.Messages.MessagesValues;
import fr.maesia.mob.Mobs;
import fr.maesia.mob.rangs.Rang;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
public class TchatInteract implements Listener {


    private static final HashMap<Player, Mobs> editRang = new HashMap<>();
    private static final HashMap<Player, Mobs> editDamage = new HashMap<>();
    private static final HashMap<Player, Mobs> editHealth= new HashMap<>();
    private static final HashMap<Player, Mobs> editSpeed= new HashMap<>();
    private static final HashMap<Player, Mobs> editAttackSpeed= new HashMap<>();

    public static HashMap<Player, Mobs> getEditRang() {
        return editRang;
    }
    public static HashMap<Player, Mobs> getEditDamage() {return  editDamage;}
    public static HashMap<Player, Mobs> getEditHealth() {return  editHealth;}
    public static HashMap<Player, Mobs> getEditSpeed() {return  editSpeed;}
    public static HashMap<Player, Mobs> getEditAttackSpeed() {return  editAttackSpeed;}

    @EventHandler
    public void onInteractTchat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (editRang.containsKey(p)){
            e.setCancelled(true);

            onCheckedit(p, e.getMessage());
        }
        if (editDamage.containsKey(p)){
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("cancel")){
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL_SUCCESS.get());
                editDamage.remove(p);
                return;
            }
            double damage = isArgDouble(p, e.getMessage());
            if (damage <= 0.0) return;
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_HEALTH_SUCCESS.get().replace(MessagesValues.MOB.toName(),editDamage.get(p).getName()).replace(MessagesValues.VALUE.toName(), String.valueOf(damage)));
            editDamage.get(p).setDamage(damage);
            editDamage.remove(p);
            return;
        }
        if (editHealth.containsKey(p)){
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("cancel")){
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL_SUCCESS.get());
                editHealth.remove(p);
                return;
            }
            double health = isArgDouble(p, e.getMessage());
            if (health <= 0.0) return;
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_HEALTH_SUCCESS.get().replace(MessagesValues.MOB.toName(),editHealth.get(p).getName()).replace(MessagesValues.VALUE.toName(), String.valueOf(health)));
            editHealth.get(p).setHealth(health);
            editHealth.remove(p);
            return;
        }
        if (editSpeed.containsKey(p)){
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("cancel")){
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL_SUCCESS.get());
                editSpeed.remove(p);
                return;
            }
            float speed = isArgFloat(p, e.getMessage());
            if (speed == 0.0) return;
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_SPEED_SUCCESS.get().replace(MessagesValues.MOB.toName(), editSpeed.get(p).getName()).replace(MessagesValues.VALUE.toName(), String.valueOf(speed)));
            editSpeed.get(p).setSpeed(speed);
            editSpeed.remove(p);
        }
        if (editAttackSpeed.containsKey(p)){
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("cancel")){
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL_SUCCESS.get());
                editAttackSpeed.remove(p);
                return;
            }
            Double speed = isArgDouble(p, e.getMessage());
            if (speed == 0.0) return;
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_ATTACK_SPEED_SUCCESS.get().replace(MessagesValues.MOB.toName(), editAttackSpeed.get(p).getName()).replace(MessagesValues.VALUE.toName(), String.valueOf(speed)));
            editAttackSpeed.get(p).setAttackspeed(speed);
            editAttackSpeed.remove(p);
        }
    }


    public double isArgDouble(Player p, String args){
        try {
            return Double.parseDouble(args.replace(',', '.'));

        }catch (NumberFormatException io){
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_NUMBER_ERROR.get());
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
            return 0.0;
        }
    }

    public float isArgFloat(Player p, String args){
        try {
            if (Float.parseFloat(args.replace(',', '.'))> 1){
                return 1.0F;
            }
            return Float.parseFloat(args.replace(',', '.'));
        }catch (NumberFormatException io){
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_NUMBER_FLOAT_ERROR.get());
            p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
            return 0.0F;
        }
    }

    public void onCheckedit(Player p, String args){

            if (args.equalsIgnoreCase("cancel")){
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL_SUCCESS.get());
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("0")){
                editRang.get(p).setRank(Rang.F);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.F.name())
                );
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("1")){
                editRang.get(p).setRank(Rang.E);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.E.name())
                );
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("2")){
                editRang.get(p).setRank(Rang.D);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.D.name())
                );
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("3")){
                editRang.get(p).setRank(Rang.C);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.C.name())
                );
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("4")){
                editRang.get(p).setRank(Rang.B);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.B.name())
                );
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("5")){
                editRang.get(p).setRank(Rang.A);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.A.name())
                );
                editRang.remove(p);
                return;
            }
            else if (args.equalsIgnoreCase("6")){
                editRang.get(p).setRank(Rang.S);
                p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK_SUCCESS.get()
                        .replace(MessagesValues.MOB.toName(), editRang.get(p).getName())
                        .replace(MessagesValues.RANK.toName(), Rang.S.name())
                );
                editRang.remove(p);
                return;
            }
            onSetRank(p);
    }


    public static void onSetRank(Player p){
        p.sendMessage(Messages.getPrefix()+Messages.EDIT_RANK.get());
        p.sendMessage(Messages.getPrefix()+Messages.EDIT_CANCEL.get());
        int num =0;
        for(Rang rang: Rang.values()){
            p.sendMessage(ChatColor.YELLOW+""+num+" -"+ChatColor.GOLD+"Rang "+rang);
            num++;
        }
    }

}

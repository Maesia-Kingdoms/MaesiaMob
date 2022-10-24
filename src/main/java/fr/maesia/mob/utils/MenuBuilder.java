package fr.maesia.mob.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {

    private Inventory inv;
    private String title;
    private Integer raw;



    public MenuBuilder(Integer raw, String title){
        if(raw >6) raw = 6;
        setRow(raw);
        setTitle(title);
        this.inv = Bukkit.createInventory(null, 9, this.title);
    }

    public MenuBuilder(String title){
        raw = 1;
        setTitle(title);
    }

    public MenuBuilder(int raw) {
        title = null;
        setRow(raw);
    }

    public MenuBuilder setTypeInventory(InventoryType type){
        raw = -1;
        inv = Bukkit.createInventory( null ,type, title);
        return this;
    }

    public MenuBuilder(){
        this.raw =1;
        this.title = " ";
        this.inv = Bukkit.createInventory(null, 9);
    }
    public Inventory getInv() {
        return inv;
    }

    public MenuBuilder setRow(int line){
        raw = Math.min(line, 6);
        this.inv = Bukkit.createInventory(null, raw*9,this.title);
        return this;
    }

    public MenuBuilder setTitle(String title){
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.inv = Bukkit.createInventory(null, inv.getSize(),this.title);
        return this;
    }

    public MenuBuilder setItems(ItemStack itemStack, Integer... slots){
        for(Integer slot: slots){
            inv.setItem(slot, itemStack);
        }
        return this;
    }



    public MenuBuilder addItemStack(ItemStack... itemStacks){
        for (ItemStack itemStack : itemStacks){
            inv.addItem(itemStack);
        }
        return this;
    }

    public MenuBuilder addItemStack(Integer started, Integer finish, ItemStack... itemStacks){
        int count = 0;
        for (ItemStack itemStack : itemStacks){
            if (count >= started && count <= finish ){
                inv.addItem(itemStack);
            }
           count++;
        }
        return this;
    }

    public MenuBuilder paternFrame(ItemStack itemStack){
        if (raw <= 2) return this;
        List<Integer> integers =new ArrayList<>();
        for(int i = raw; i  >= 0; i--){
            if (i*9 != raw*9) integers.add((i*9)+8);
            if (raw == i){
                integers.add((i*9)-1);
            }else {
                integers.add((i*9));
            }
        }
        for(int i =0; i < 9; i++) integers.add(i);
        for(int i = ((raw-1)*9); i < raw*9; i++) integers.add(i);
        Integer[] list = integers.toArray(new Integer[0]);
        setItems(itemStack,list);
        return this;
    }
    public MenuBuilder paternAll(ItemStack itemStack){
        for(int i = 0; i < raw*9; i++){
            setItems(itemStack, i);
        }
        return this;
    }
    public MenuBuilder paternTwoByTwo(ItemStack... itemStacks){
        List<ItemStack> items = new ArrayList<>(List.of(itemStacks));
        int turn =0;
        for(int pos = 0; pos < raw *9; pos+= 2 ){
            if(inv.getItem(pos) == null && turn < items.size()){
                setItems(items.get(turn), pos);
                turn++;
            }
        }
        return this;
    }
}

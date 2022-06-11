package fr.maesia.mob.MaesiaMobFiles.Messages;

public enum MessagesValues {
    PLAYER("player"),
    MOB("mob"),
    COUNT("count"),
    POWER("power"),
    RANK("rank"),
    VALUE("value");


    private final String name;

    MessagesValues(String name){
        this.name = name;
    }

    public String toName(){
        return "{" +name+ "}";
    }
}

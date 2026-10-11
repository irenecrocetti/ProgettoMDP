package it.unicam.cs.mpgc.rpg130573.Model.Villains;

public class Darcy extends Villain{
    public static final int DARCY_HEALTH = 100;
    public static final int DARCY_ACCURACY = 70;
    public static final int DARCY_XP_RELEASED = 50;

    public Darcy(){
        super("Darcy", DARCY_HEALTH, DARCY_HEALTH, DARCY_ACCURACY, DARCY_XP_RELEASED);
        //TODO aggiungere incantesimi
    }
}


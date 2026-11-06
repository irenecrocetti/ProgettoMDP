package it.unicam.cs.mpgc.rpg130573.model.managers;

/**
 * Manager di un'entità che non può perdere punti vita.
 * Sia il danno diretto che i danni nel tempo passano per loseHp(),
 * quindi ignorarlo qui copre ogni fonte di danno.
 */
public class InvulnerableStatusManager extends StatusManager{

    public InvulnerableStatusManager(int hp) {
        super(hp, hp);
    }

    @Override
    public void loseHp(int amount){
        // nessun effetto
    }

    @Override
    public boolean isInvulnerable(){
        return true;
    }
}

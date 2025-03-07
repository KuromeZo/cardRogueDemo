package com.mygdx.game.logic.cards;

import com.mygdx.game.logic.entities.Entity;
import com.mygdx.game.logic.entities.Player;

public class CoverCard extends Card {
    private boolean inCover;

    public CoverCard(String name, String description,boolean isPlayer, boolean inCover) {
        super(name, description, isPlayer);
        this.inCover = inCover;
    }
    @Override
    public void applyEffect(Entity target, Entity attacker) {
        System.out.println("cover - " + target.getName());
        if(target instanceof Player){
            ((Player) target).addCoverProtection();
        }
    }
}

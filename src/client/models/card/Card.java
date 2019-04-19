package client.models.card;

import client.models.card.spell.Spell;

public class Card {
    private String name;
    private String description;
    private String cardId;
    private CardType type;
    private Spell[] spells;
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private int price;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCardId() {
        return this.cardId;
    }

    public CardType getType() {
        return this.type;
    }

    public Spell[] getSpells() {
        return this.spells;
    }

    public int getDefaultAp() {
        return this.defaultAp;
    }

    public int getDefaultHp() {
        return this.defaultHp;
    }

    public int getMannaPoint() {
        return this.mannaPoint;
    }

    public AttackType getAttackType() {
        return this.attackType;
    }

    public int getRange() {
        return this.range;
    }

    public int getPrice() {
        return price;
    }

    public boolean hasCombo() {
        return hasCombo;
    }
}
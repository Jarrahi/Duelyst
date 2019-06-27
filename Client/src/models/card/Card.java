package models.card;


import models.ICard;
import models.card.spell.Spell;
import models.exceptions.InputException;

import java.util.ArrayList;

public class Card implements ICard {
    private String name;
    private String description;
    private String cardId;
    private String spriteName;
    private CardType type;
    private ArrayList<Spell> spells;
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private int price;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;

    public Card(String name, String description,  String spriteName, CardType type,ArrayList<Spell> spells,  int defaultAp, int defaultHp, int mannaPoint, int price, AttackType attackType, int range, boolean hasCombo) {
        this.name = name;
        this.description = description;
        this.cardId = name;
        this.spriteName = spriteName;
        this.type = type;
        this.spells = spells;
        this.defaultAp = defaultAp;
        this.defaultHp = defaultHp;
        this.mannaPoint = mannaPoint;
        this.price = price;
        this.attackType = attackType;
        this.range = range;
        this.hasCombo = hasCombo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().getName().equals(obj.getClass().getName())) return false;
        Card card = (Card) obj;
        return this.cardId.equalsIgnoreCase(card.cardId);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCardId() {
        return this.cardId;
    }

    public String getSpriteName() {
        return spriteName;
    }

    @Override
    public CardType getType() {
        return this.type;
    }

    public ArrayList<Spell> getSpells() {
        return this.spells;
    }

    @Override
    public int getDefaultAp() {
        return this.defaultAp;
    }

    @Override
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

    @Override
    public int getPrice() {
        return price;
    }

    public boolean hasCombo() {
        return hasCombo;
    }

    public boolean nameContains(String cardName) {
        return name.toLowerCase().contains(cardName.toLowerCase());
    }

    public boolean isSameAs(String cardName) {
        return name.equalsIgnoreCase(cardName);
    }

    public void checkValidation() throws InputException {
        if (name == null || name.isEmpty())
            throw new InputException("name is empty");
        if (description == null || description.isEmpty())
            throw new InputException("description is empty");
        if (spriteName== null || spriteName.isEmpty())
            throw new InputException("sprite is empty");
        if ((type == CardType.SPELL || type == CardType.HERO )&& spells.isEmpty()){
            throw new InputException("Spell is empty");
        }
    }
}
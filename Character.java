//Manuel Moya Valdivia 260510582

import java.util.ArrayList;
import java.util.Random;

public class Character {

//Attributes
    private final String name;
    private final double attackValue;
    private final double maxHealth;
    private double currHealth;
    private int numWins;
    private static ArrayList<Spell> spellAt;

//Constructor
    public Character(String name, double attack, double maximumHealth, int numOfWins){
        this.name = name;
        this.attackValue = attack;
        this.maxHealth = maximumHealth;
        this.currHealth = maximumHealth;
        this.numWins = numOfWins;
    }

//Getters
    public String getName() {
        return name;
    }
    public double getAttackValue() {
        return attackValue;
    }
    public double getMaxHealth() {
        return maxHealth;
    }
    public double getCurrHealth() {
        return currHealth;
    }
    public int getNumWins() {
        return numWins;
    }

//toString()
    @Override
    public String toString() {
        return "Name: " + name + "\nHealth: " + currHealth;
    }

//returns damage in double data type
    public double getAttackDamage(int seed){
        Random random = new Random(seed);
        double randomValue = random.nextDouble();
        while (randomValue <0.7){
            randomValue = random.nextDouble();
        }
        return getAttackValue() * randomValue;
    }


    //takeDamage
    public void takeDamage(double AttackDamage){
        currHealth -= AttackDamage;
    }

    //increaseWins
    public void increaseWins(){
        numWins++;
    }

    public static void setSpells(ArrayList<Spell> spells) {
        spellAt = new ArrayList<>(spells);
    }

    public static void displaySpells(){
        System.out.println("\nHere are the available spells:");
        for(Spell i : spellAt){
            System.out.print(i);
        }
    }

    //casting a spell
    public double castSpell(String spellName, int seed){
        int matches = 0;
        double spellDamage = 0;

        for (Spell i : spellAt){
            if (spellName.equalsIgnoreCase(i.getName())){
                matches++;
                spellDamage = i.getMagicDamage(seed);
                break;
            }
        }

        if (matches == 0)
            return -1;

        if (spellDamage==0)
            return 0;

        return spellDamage;
   }
}

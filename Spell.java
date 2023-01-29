//Manuel Moya Valdivia 260510582

import java.util.Random;

public class Spell {

    private String name;
    private double minDamage;
    private double maxDamage;
    private double succRate;

//Constructor
    public Spell(String name, double minDamage, double maxDamage, double successRate){
        this.name = name;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.succRate = successRate;
    }

    public String getName() {
        return name;
    }

    //getting Magic Damage
    public double getMagicDamage(int seed){
        Random random = new Random(seed);
        double randomNumber = random.nextDouble();

        if(randomNumber>succRate)
            return 0;

        else {
            double damage = random.nextDouble()*10;
            while (damage<minDamage || damage >maxDamage)
                damage = random.nextDouble()*10;

            return damage;
        }

    }

    @Override
    public String toString() {
        return name + " >Damage: " + minDamage + " to " + maxDamage + "< >Chance: "+succRate*100 + "%<\n";
    }
}

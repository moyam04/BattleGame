//Manuel Moya Valdivia 260510582

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BattleGame {

    private static Random rand = new Random();

    public static void main(String[] args) {

        playGame("player.txt", "monster.txt", "spells.txt");


    }

    public static void playGame(String playerFile, String monsterFile, String spells){

        Character Player = FileIO.readCharacter(playerFile);
        Character Monster = FileIO.readCharacter(monsterFile);

        //Printing character's facts
        System.out.println(Player + "\nAttack: " + Player.getAttackValue() + "\nNumber of Wins: " + Player.getNumWins());
        System.out.println("\n"+Monster+ "\nAttack: " +Monster.getAttackValue() + "\nNumber of Wins: " + Monster.getNumWins());

        boolean spellsInGame = true;
        try{
            ArrayList<Spell> arrayOfSpells = new ArrayList<>(FileIO.readSpells(spells));
            Character.setSpells(arrayOfSpells);
            Player.displaySpells();
        }
        catch (FileNotFoundException e){
            System.out.println("The game will be played without spells");
            spellsInGame = false;
        }

        //Opening scanner
        Scanner input = new Scanner(System.in);

        while (Player.getCurrHealth()>0 && Monster.getCurrHealth()>0){

            System.out.print("\nEnter ATTACK, QUIT or a spell name to initiate combat: ");
            String answer = input.nextLine();

              switch (answer) {
                  case "quit":
                      if (answer.equals("quit")) {
                          Player.takeDamage(Player.getCurrHealth());
                          Monster.increaseWins();
                          setWinner(Monster,monsterFile);
                      }
                      break;
                  case "attack":
                      if (answer.equals("attack")) {
                          playerAttack(Player, Monster);
                          if(Monster.getCurrHealth()<0){
                              setWinner(Player,playerFile);
                              break;
                          }
                          monsterAttack(Monster, Player);
                          if(Player.getCurrHealth()<0)
                              setWinner(Monster,monsterFile);
                      }
                      break;
                  default:
                      if (spellsInGame){
                          castingSpellInBattleGame(Player, Monster, answer);
                          if(Monster.getCurrHealth()<0){
                              setWinner(Player,playerFile);
                              break;
                          }
                          monsterAttack(Monster,Player);
                          if(Player.getCurrHealth()<0)
                              setWinner(Monster,monsterFile);
                      }
                      if(!spellsInGame){
                          System.out.println("Input not recognized. Only attack or quit.");
                      }

                      }

        }

        //closing scanner
        input.close();
    }

    //player attacking monster
    private static void playerAttack(Character Player, Character Monster){
        double attack = Player.getAttackDamage(rand.nextInt());
        String attackStr = String.format("%1$.2f",attack);
        System.out.println("\n" + Player.getName() + " attack for " + attackStr + " damage!");
        Monster.takeDamage(attack);

        if (Monster.getCurrHealth()<0) {
            Player.increaseWins();
            System.out.println("You have won");
        }
        else if (Monster.getCurrHealth()>0){
            double damage = Monster.getCurrHealth();
            String damageStr = String.format("%1$.2f",damage);
            System.out.println(Monster.getName() + "\'s current health is " + damageStr);
        }

    }

    //monster attacking player
    private static void monsterAttack(Character Monster, Character Player){
        double attack = Monster.getAttackDamage(rand.nextInt());
        String attackStr = String.format("%1$.2f",attack);
        System.out.println("\n" + Monster.getName() + " attack for " + attackStr + " damage!");
        Player.takeDamage(attack);

        if (Player.getCurrHealth()<0) {
            Monster.increaseWins();
            System.out.println("You have lost");
        }

        else if(Player.getCurrHealth()>0){
            double damage = Player.getCurrHealth();
            String damageStr = String.format("%1$.2f",damage);
            System.out.println(Player.getName() + "\'s current health is " + damageStr);
        }

    }

    private static void castingSpellInBattleGame (Character Player, Character Monster, String nameOfSpell){
        double spellDamage = Player.castSpell(nameOfSpell, rand.nextInt());


            if (spellDamage<0)
                System.out.println(Player.getName() + " tried to cast " + nameOfSpell + " but there's no such spell!");

            if (spellDamage==0)
               System.out.println(Player.getName() + " tried to cast " + nameOfSpell + " but fails!");

            if (spellDamage>0) {
                String attackStr = String.format("%1$.2f",spellDamage);
                System.out.println("\n" + Player.getName() + " casts " + nameOfSpell + " dealing " + attackStr + " damage!");
                Monster.takeDamage(spellDamage);
                if (Monster.getCurrHealth()<0) {
                    Player.increaseWins();
                    System.out.println("You have won");
                }
                double damage = Monster.getCurrHealth();
                String damageStr = String.format("%1$.2f",damage);
                System.out.println(Monster.getName() + "\'s current health is " + damageStr);
            }

    }

    private static void setWinner(Character Winner,String winnerFile){
        FileIO.writeCharacter(Winner,winnerFile);
    }
}
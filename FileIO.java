
import java.io.*;
import java.util.ArrayList;

public class FileIO {

    public static Character readCharacter(String fileName){

        ArrayList<String> attributes = new ArrayList<>();
        String name;
        double attack;
        double maximumHealth;
        int numWins;

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                attributes.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");;
            return null;
        } catch (IOException e) {
            System.out.println("Something went wrong");;
            return null;
        }

        //passing attributes to create the character
        try{
            name = String.valueOf(attributes.get(0));
            attack = Double.parseDouble(String.valueOf(attributes.get(1)));
            maximumHealth = Double.parseDouble(String.valueOf(attributes.get(2)));
            numWins = Integer.parseInt(String.valueOf(attributes.get(3)));
            return new Character(name,attack,maximumHealth,numWins);
        }
        catch (IllegalArgumentException e){
            System.out.println("The game cannot be played. It seems a character attribute is not in the right format.");
            System.exit(1);
            return null;
        }
    }

    //getting spells from file
    public static ArrayList<Spell> readSpells(String fileName) throws FileNotFoundException {

        ArrayList<Spell> spells = new ArrayList<>();

        //Attributes of a spell
        ArrayList<String> currentSpell = new ArrayList<>();
        String name;
        double minDamage;
        double maxDamage;
        double succRate;

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split("\t");
                for (int i = 0; i < attributes.length; i++){
                    currentSpell.add(i,attributes[i]);
                }
                try{
                    name = String.valueOf(currentSpell.get(0));
                    minDamage = Double.parseDouble(String.valueOf(currentSpell.get(1)));
                    maxDamage = Double.parseDouble(String.valueOf(currentSpell.get(2)));
                    succRate = Double.parseDouble(String.valueOf(currentSpell.get(3)));
                    spells.add(new Spell(name, minDamage,maxDamage,succRate));
                }
                catch (IllegalArgumentException e){
                    System.out.println("\nThe game cannot be played. It seems one or more spell attributes are in the wrong format.");
                    System.exit(1);
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("\nThe game will be played without spells.");
        } catch (IOException e) {
            System.out.println("\nSomething went wrong");
            return null;
        }
            return spells;
    }

    //writing the winner to the file
    public static void writeCharacter(Character winner, String winnerFile){

        File fileName = new File(winnerFile);

            try {
                FileWriter fileWriter = new FileWriter(fileName);
                Writer output = new BufferedWriter(fileWriter);
                output.write(winner.getName());
                output.write("\n");
                output.write(String.valueOf(winner.getAttackValue()));
                output.write("\n");
                output.write(String.valueOf(winner.getMaxHealth()));
                output.write("\n");
                output.write(String.valueOf(winner.getNumWins()));
                output.close();
                System.out.println("Successfully wrote to file: " + winnerFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
